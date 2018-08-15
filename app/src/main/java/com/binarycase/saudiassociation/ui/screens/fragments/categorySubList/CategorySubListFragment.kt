package com.binarycase.saudiassociation.ui.screens.fragments.categorySubList

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.RecyclerView.ViewHolder
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MenuItem.OnActionExpandListener
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.binarycase.saudiassociation.R
import com.binarycase.saudiassociation.databinding.ExamsListItemBinding
import com.binarycase.saudiassociation.myApp.MyApplication
import com.binarycase.saudiassociation.service.response.CategSubListResponse
import com.binarycase.saudiassociation.service.response.CategSubListResponse.CategData
import com.binarycase.saudiassociation.ui.base.BaseFragmentWithInjector
import com.binarycase.saudiassociation.utils.LinearLayoutManagerWrapper
import com.binarycase.saudiassociation.utils.StateView
import com.binarycase.saudiassociation.utils.StateView.Empty
import com.binarycase.saudiassociation.utils.StateView.Errors
import com.binarycase.saudiassociation.utils.StateView.Loading
import com.binarycase.saudiassociation.utils.StateView.PaginationError
import com.binarycase.saudiassociation.utils.StateView.PaginationFinished
import com.binarycase.saudiassociation.utils.StateView.PaginationLoading
import com.binarycase.saudiassociation.utils.StateView.PaginationSuccess
import com.binarycase.saudiassociation.utils.StateView.Success
import com.binarycase.saudiassociation.utils.hideKeyboardFrom
import com.tripl3dev.luffyyview.baseAdapter.BaseListAdapter
import com.tripl3dev.luffyyview.baseAdapter.MainHolderInterface
import com.tripl3dev.prettystates.StatesConstants
import com.tripl3dev.prettystates.setState
import kotlinx.android.synthetic.main.fragment_category_sub_list.categorySubList
import ru.alexbykov.nopaginate.paginate.NoPaginate
import java.util.ArrayList

class CategorySubListFragment : BaseFragmentWithInjector() {

  lateinit var viewModel: CategorySubListViewModel
  lateinit var mAdapter: BaseListAdapter<CategData>

  companion object {
    const val Tag = "CategorySubListFragment"
    val CAT_ID = "CAT_OBJECT"
    val CAT_NAME = "CAT_NAME"

    @JvmStatic fun newInstance(
      catId: Int,
      catName: String
    ) =
      CategorySubListFragment().apply {
        setHasOptionsMenu(true)
        arguments = Bundle().apply {
          putInt(CAT_ID, catId)
          putString(CAT_NAME, catName)
        }
      }
  }

  override fun getActivityVM(): Class<out ViewModel> {
    return CategorySubListViewModel::class.java
  }

  override fun isRetained(): Boolean {
    return false
  }

  private var catId: Int = 0

  private var catName: String = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
      catId = it.getInt(CAT_ID)
      catName = it.getString(CAT_NAME)
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_category_sub_list, container, false)
  }

  override fun onCreateOptionsMenu(
    menu: Menu?,
    inflater: MenuInflater?
  ) {
    super.onCreateOptionsMenu(menu, inflater)
    inflater!!.inflate(R.menu.search_menu, menu)
    val searchItem = menu?.findItem(R.id.action_search)
    val searchView: android.support.v7.widget.SearchView =
      searchItem?.actionView as android.support.v7.widget.SearchView
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        searchFor(query)
        searchView.hideKeyboardFrom(context!!)
        return true
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        return false
      }

    })
    searchItem.setOnActionExpandListener(object : OnActionExpandListener {
      override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
        inSearching = true
        resetPagination()
        viewModel.stopFetchingCall()
        categorySubList.setState(MyApplication.WORDS_SEARCH)
        return true
      }

      override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        Log.e("subCateg", "Collapsed")
        inSearching = false
        resetPagination()
        viewModel.stopSearchCall()
        viewModel.fetchSectionsList(catId)
        return true
      }
    })
  }

  private fun resetPagination() {
    viewModel.resetPagination()
    try {

      paginate.unbind()
    }catch (e:Exception){
      Log.e("paginate","notRegistered")
    }
    setUpPagination()
  }

  private fun setUpPagination() {
    paginate = NoPaginate.with(categorySubList.listView)
        .setOnLoadMoreListener {
          if (!this::paginate.isInitialized) return@setOnLoadMoreListener
          paginate.showLoading(true)
          if (viewModel.inPagination()) {
            if (inSearching) {
              viewModel.searchForExamsList(currentQuery)
            } else {
              viewModel.fetchSectionsList(catId)
            }
          } else {
            categorySubList.setState(StatesConstants.LOADING_STATE)
            if (inSearching) {
              viewModel.searchForExamsList(currentQuery)
            } else {
              viewModel.fetchSectionsList(catId)
            }
          }

        }
        .build()
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    viewModel = vm as CategorySubListViewModel
    subCategoryListListener()
    setUpListView()
    viewModel.fetchSectionsList(catId)
  }



  override fun mainActivityTitleString(): String {
    return catName
  }

  private lateinit var paginate: NoPaginate

  private fun setUpListView() {
    viewModel.currentPage=1
    mAdapter = BaseListAdapter(object : MainHolderInterface<CategData> {
      override fun getView(type: Int): Int {
        return R.layout.exams_list_item
      }

      override fun getListCopy(): ArrayList<CategData>? {
        return viewModel.categSubList
      }

      override fun getViewData(
        holder: ViewHolder,
        t: CategData,
        position: Int
      ) {
        val itemBinding = DataBindingUtil.bind<ExamsListItemBinding>(holder.itemView)
        itemBinding?.isExpanded = t.isExpanded
        itemBinding?.categoryModel = t
        itemBinding?.imageButton?.setOnClickListener {
          t.isExpanded.set(!t.isExpanded.get())
        }
        itemBinding?.imageButton2?.setOnClickListener {
          toggleFav(t, position)
        }
        itemBinding?.executePendingBindings()
      }
    }, context!!)
    var myListView = categorySubList.listView
    myListView.layoutManager = LinearLayoutManagerWrapper(context)
    myListView.adapter = mAdapter
    setUpPagination()
  }

  private fun toggleFav(
    it: CategData,
    position: Int
  ) {
    viewModel.toggleFavourite(it)
        .observe(this, Observer<StateView> { t ->
          when (t) {
            is StateView.Success<*> -> {
              val isfav = t.data as Boolean
              if (catId != 0 ||inSearching) {
                viewModel.categSubList[position].isFavourite = isfav
                mAdapter.originalList = viewModel.categSubList
                mAdapter.notifyItemChanged(position)
                it.isFavLoading.set(false)
              } else {
                viewModel.categSubList.removeAt(position)
                mAdapter.originalList = viewModel.categSubList
                mAdapter.notifyItemRemoved(position)
              }
            }
            is StateView.Errors -> {
              Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT)
                  .show()
              it.isFavLoading.set(false)
            }
            is StateView.Loading -> {
              it.isFavLoading.set(true)
            }
          }
        })
  }

  val pageIndex = 4
  private fun subCategoryListListener() {
    viewModel.getCategorySubListener()
        .observe(this, Observer {
          when (it) {
            is Success<*> -> {
              categorySubList.setState(StatesConstants.NORMAL_STATE)
              val list = it.data as CategSubListResponse
              viewModel.categSubList.clear()
              viewModel.categSubList.addAll(list.data)
              mAdapter.originalList.clear()
              mAdapter.originalList.addAll(list.data)
              mAdapter.notifyDataSetChanged()
              endPaginate(list.currentPage == list.lastPage)
            }
            is PaginationSuccess<*> -> {
              val list = it.data as CategSubListResponse
              viewModel.categSubList.addAll(list.data)
              val oldSize = mAdapter.originalList.size
              mAdapter.originalList.addAll(list.data)
              mAdapter.notifyItemRangeInserted(oldSize - 1, mAdapter.originalList.size)
              endPaginate(list.currentPage == list.lastPage)
            }
            is Empty -> {
              categorySubList.setState(StatesConstants.EMPTY_STATE)
              endPaginate(true)
            }
            is Errors -> {
              val retryBt = categorySubList.setState(StatesConstants.ERROR_STATE)
                  .findViewById<Button>(R.id.retryButton)
              retryBt.setOnClickListener {
                if (inSearching)
                  viewModel.searchForExamsList(currentQuery)
                else
                  viewModel.fetchSectionsList(catId)
              }
            }
            is PaginationError -> {
              paginate.showError(true)
            }
            is PaginationFinished -> {
              endPaginate(true)
            }
            is PaginationLoading -> {
              paginate.showLoading(true)
            }
            is Loading -> {
              categorySubList.setState(StatesConstants.LOADING_STATE)
            }
          }
        })
  }

  private var inSearching: Boolean = false
  private var currentQuery: String = ""

  fun searchFor(query: String?) {
    viewModel.searchForExamsList(query)
    currentQuery = query ?: ""
  }

  fun endPaginate(hasNoMoreData: Boolean) {
    when {
      hasNoMoreData -> {
        paginate.setNoMoreItems(true)
        paginate.showLoading(false)
        return
      }
      else -> {
        viewModel.currentPage++
        paginate.showLoading(false)
        paginate.setNoMoreItems(false)
        return
      }
    }
  }

  override fun onStop() {
    super.onStop()
    try {

      paginate.unbind()
    }catch (e:Exception){
      Log.e("paginate","notRegistered")
    }  }
}