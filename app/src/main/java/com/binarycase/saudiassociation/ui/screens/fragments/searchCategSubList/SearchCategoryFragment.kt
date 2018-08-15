package com.binarycase.saudiassociation.ui.screens.fragments.searchCategSubList

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.binarycase.saudiassociation.R
import com.binarycase.saudiassociation.R.layout
import com.binarycase.saudiassociation.R.string
import com.binarycase.saudiassociation.databinding.ExamsListItemBinding
import com.binarycase.saudiassociation.myApp.MyApplication
import com.binarycase.saudiassociation.service.response.CategSubListResponse
import com.binarycase.saudiassociation.service.response.CategSubListResponse.CategData
import com.binarycase.saudiassociation.ui.base.BaseFragmentWithInjector
import com.binarycase.saudiassociation.utils.LinearLayoutManagerWrapper
import com.binarycase.saudiassociation.utils.StateView
import com.binarycase.saudiassociation.utils.StateView.Errors
import com.binarycase.saudiassociation.utils.StateView.Loading
import com.binarycase.saudiassociation.utils.StateView.Success
import com.tripl3dev.luffyyview.baseAdapter.BaseListAdapter
import com.tripl3dev.luffyyview.baseAdapter.MainHolderInterface
import com.tripl3dev.prettystates.setState
import kotlinx.android.synthetic.main.fragment_category_sub_list.categorySubList
import ru.alexbykov.nopaginate.paginate.NoPaginate
import java.util.ArrayList

class SearchCategoryFragment : BaseFragmentWithInjector() {
  override fun getActivityVM(): Class<out ViewModel> {
    return SearchCategViewModel::class.java
  }

  init {
    setHasOptionsMenu(true)
  }

  companion object {
    const val Tag = "SearchCategoryFragment"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel = vm as SearchCategViewModel
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_category_sub_list, container, false)
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    categorySubList.setState(MyApplication.WORDS_SEARCH)
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
    searchItem.expandActionView()
    searchView.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        searchView.clearFocus()
        searchFor(query)
        return true
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        return false
      }
    })
  }

  private lateinit var mAdapter: BaseListAdapter<CategSubListResponse.CategData>

  private lateinit var viewModel: SearchCategViewModel

  private lateinit var paginate: NoPaginate
  private fun setUpListView() {
    mAdapter = BaseListAdapter(object : MainHolderInterface<CategData> {
      override fun getView(type: Int): Int {
        return layout.exams_list_item
      }

      override fun getListCopy(): ArrayList<CategData>? {
        return viewModel.wordsList
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
    val myListView = categorySubList.listView

    myListView.layoutManager = LinearLayoutManagerWrapper(context)
    myListView.adapter = mAdapter
    paginate = NoPaginate.with(myListView)
        .setOnLoadMoreListener {
          if (!this::paginate.isInitialized) return@setOnLoadMoreListener
          paginate.showLoading(true)
        }
        .setLoadingTriggerThreshold(0)
        .build()
  }

  private fun toggleFav(
    it: CategData,
    position: Int
  ) {
    viewModel.toggleFavourite(it)
        .observe(this, Observer<StateView> { t ->
          when (t) {
            is Success<*> -> {
              val isfav = t.data as Boolean
              viewModel.wordsList[position].isFavourite = isfav
              mAdapter.originalList = viewModel.wordsList
              mAdapter.notifyItemChanged(position)
              it.isFavLoading.set(false)
            }
            is Errors -> {
              Toast.makeText(context, string.server_error, Toast.LENGTH_SHORT)
                  .show()
              it.isFavLoading.set(false)
            }
            is Loading -> {
              it.isFavLoading.set(true)
            }
          }
        })
  }

  override fun onResume() {
    super.onResume()
    setUpListView()
    subCategoryListListener()
  }

  override fun onStop() {
    super.onStop()
    viewModel.currentPage = 1
    paginate.unbind()
  }

  private fun subCategoryListListener() {
    viewModel.getCategorySubListener()
        .observe(this, Observer {
        })
  }

  private var currentQuery: String = ""

  fun searchFor(query: String?) {
    currentQuery = query ?: ""
  }

}