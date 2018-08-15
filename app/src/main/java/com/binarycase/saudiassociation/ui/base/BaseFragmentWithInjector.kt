package com.binarycase.saudiassociation.ui.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binarycase.saudiassociation.di.modules.ViewModelFactory
import com.binarycase.saudiassociation.myApp.MyApplication
import com.binarycase.saudiassociation.ui.screens.mainScreen.MainActivity
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract  class BaseFragmentWithInjector :Fragment(){
  @Inject
  lateinit var factory: ViewModelFactory
  lateinit var vm: ViewModel
  lateinit var disposal: Disposable

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    (activity?.application as MyApplication).networkComponent.inject(this)
    vm = ViewModelProviders.of(this, factory)[getActivityVM()]
    if (isRetained()) retainInstance=true

  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    return super.onCreateView(inflater, container, savedInstanceState)
  }

  override fun onResume() {
    super.onResume()
    if (mainActivityTitleRES()!=-1){
      (activity as MainActivity).supportActionBar?.setTitle(mainActivityTitleRES())
    }
    if (!mainActivityTitleString().isNullOrEmpty()){
      (activity as MainActivity).supportActionBar?.setTitle(mainActivityTitleString())
    }
  }
 open  fun  isRetained():Boolean{
    return false
  }
  abstract fun getActivityVM(): Class<out ViewModel>

open fun mainActivityTitleRES():Int{
  return -1
}open fun mainActivityTitleString():String{
  return ""
}
}