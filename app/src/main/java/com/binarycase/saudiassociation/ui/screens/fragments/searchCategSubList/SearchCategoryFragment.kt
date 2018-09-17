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
import com.binarycase.saudiassociation.ui.screens.mainScreen.MainActivity
import com.binarycase.saudiassociation.utils.LinearLayoutManagerWrapper
import com.binarycase.saudiassociation.utils.StateView
import com.binarycase.saudiassociation.utils.StateView.Errors
import com.binarycase.saudiassociation.utils.StateView.Loading
import com.binarycase.saudiassociation.utils.StateView.Success
import com.tripl3dev.luffyyview.baseAdapter.BaseListAdapter
import com.tripl3dev.luffyyview.baseAdapter.MainHolderInterface
import com.tripl3dev.prettystates.StatesConstants
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
        const val Tag = "SEARCH_CATEGORY_FRAGMENT"
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


    private lateinit var mAdapter: BaseListAdapter<CategSubListResponse.CategData>

    private lateinit var viewModel: SearchCategViewModel

    private lateinit var paginate: NoPaginate

    private fun setUpListView() {
        viewModel.currentPage = 1
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
                itemBinding?.root?.setOnClickListener {
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
        setUpPagination()
    }

    private fun setUpPagination() {
        paginate = NoPaginate.with(categorySubList.listView)
                .setOnLoadMoreListener {
                    if (!this::paginate.isInitialized) return@setOnLoadMoreListener
                    paginate.showLoading(true)
                    if (viewModel.inPagination()) {
                        viewModel.searchForExamsList(currentQuery)
                    } else {
                        categorySubList.setState(StatesConstants.LOADING_STATE)
                        viewModel.searchForExamsList(currentQuery)
                    }

                }
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
                            viewModel.categSubList[position].isFavourite = isfav
                            mAdapter.originalList = viewModel.categSubList
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
                        is StateView.PaginationSuccess<*> -> {
                            val list = it.data as CategSubListResponse
                            viewModel.categSubList.addAll(list.data)
                            val oldSize = mAdapter.originalList.size
                            mAdapter.originalList.addAll(list.data)
                            mAdapter.notifyItemRangeInserted(oldSize - 1, mAdapter.originalList.size)
                            endPaginate(list.currentPage == list.lastPage)
                        }
                        is StateView.Empty -> {
                            categorySubList.setState(StatesConstants.EMPTY_STATE)
                            endPaginate(true)
                        }
                        is Errors -> {
                            val retryBt = categorySubList.setState(StatesConstants.ERROR_STATE)
                                    .findViewById<Button>(R.id.retryButton)
                            retryBt.setOnClickListener {
                                viewModel.searchForExamsList(currentQuery)
                            }
                        }
                        is StateView.PaginationError -> {
                            Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show()
                        }
                        is StateView.PaginationFinished -> {
                            endPaginate(true)
                        }
                        is StateView.PaginationLoading -> {
                            paginate.showLoading(true)
                        }
                        is Loading -> {
                            categorySubList.setState(StatesConstants.LOADING_STATE)
                        }
                    }
                })
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


    override fun onResume() {
        super.onResume()
        setUpListView()
        subCategoryListListener()
    }

    override fun onStop() {
        super.onStop()
        viewModel.resetPagination()
        paginate.unbind()
        (activity as MainActivity).clearSearchField()
    }


    private var currentQuery: String = ""

    fun searchFor(query: String?) {
        currentQuery = query!!
        viewModel.searchForExamsList(query)
    }

}