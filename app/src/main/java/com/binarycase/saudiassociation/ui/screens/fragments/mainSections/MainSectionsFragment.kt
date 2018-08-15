package com.binarycase.saudiassociation.ui.screens.fragments.mainSections

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.binarycase.saudiassociation.R
import com.binarycase.saudiassociation.databinding.MainSectionsListItemBinding
import com.binarycase.saudiassociation.models.SectionsModel
import com.binarycase.saudiassociation.service.response.MainSectionsResponse
import com.binarycase.saudiassociation.ui.base.BaseFragmentWithInjector
import com.binarycase.saudiassociation.ui.screens.fragments.categorySubList.CategorySubListFragment
import com.binarycase.saudiassociation.utils.GridLayoutManagerWrapper
import com.binarycase.saudiassociation.utils.StateView
import com.binarycase.saudiassociation.utils.StateView.Errors
import com.binarycase.saudiassociation.utils.StateView.Loading
import com.binarycase.saudiassociation.utils.StateView.Success
import com.tripl3dev.luffyyview.baseAdapter.BaseListAdapter
import com.tripl3dev.luffyyview.baseAdapter.MainHolderInterface
import com.tripl3dev.prettystates.StatesConstants
import com.tripl3dev.prettystates.setState
import kotlinx.android.synthetic.main.fragment_main_sections_layout.mainSectionsList

class MainSectionsFragment : BaseFragmentWithInjector() {

  lateinit var mAdapter: BaseListAdapter<MainSectionsResponse>
  var sectionsList: ArrayList<MainSectionsResponse> = ArrayList()
  lateinit var mainSectionVM: MainSectionsViewModel

  override fun getActivityVM(): Class<out ViewModel> {
    return MainSectionsViewModel::class.java

  }

  override fun isRetained(): Boolean {
    return true
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_main_sections_layout, container, false)
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    mainSectionVM = vm as MainSectionsViewModel
    sectionsListListener()
    setUpListView()
    mainSectionVM.fetchSectionsList()
  }

  private fun sectionsListListener() {
    mainSectionVM.getMainSections().observe(this, Observer<StateView> { t ->
      when(t){
        is Success<*> ->{
          mainSectionsList.setState(StatesConstants.NORMAL_STATE)
          val list = t.data as ArrayList<MainSectionsResponse>
          sectionsList.clear()
          sectionsList.addAll(list)
          mAdapter.originalList=sectionsList
          mAdapter.notifyDataSetChanged()
        }
        is Loading ->{
          mainSectionsList.setState(StatesConstants.LOADING_STATE)
        }
        is Errors ->{
          val retry  = mainSectionsList.setState(StatesConstants.ERROR_STATE).findViewById<Button>(R.id.retryButton)
          retry.setOnClickListener {
            mainSectionVM.fetchSectionsList()
          }
        }
      }
    })
  }

  private fun setUpListView() {
    mAdapter = BaseListAdapter(object : MainHolderInterface<MainSectionsResponse> {
      override fun getView(type: Int): Int {
        return R.layout.main_sections_list_item
      }

      override fun getListCopy(): java.util.ArrayList<MainSectionsResponse>? {
        return sectionsList
      }

      override fun getViewData(
        holder: ViewHolder,
        t: MainSectionsResponse,
        position: Int
      ) {
        val itemBinding = DataBindingUtil.bind<MainSectionsListItemBinding>(holder.itemView)
        itemBinding?.sectionModel = t
        itemBinding?.executePendingBindings()
        itemBinding?.root?.setOnClickListener {
          activity!!.supportFragmentManager.beginTransaction().replace(R.id.fragmentContent,CategorySubListFragment.newInstance(t.id,t.name),CategorySubListFragment.Tag)
              .addToBackStack(null).commit()
        }
      }

    }, context!!)
    mainSectionsList.layoutManager = GridLayoutManagerWrapper(context!!,2)
    mainSectionsList.setHasFixedSize(true)
    mainSectionsList.adapter = mAdapter
  }

  override fun mainActivityTitleRES(): Int {
    return R.string.main_sections_title
  }
}