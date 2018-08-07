package com.binarycase.saudiassociation.ui.screens.mainScreen

import android.arch.lifecycle.ViewModel
import android.os.Bundle
import com.binarycase.saudiassociation.R.layout
import com.binarycase.saudiassociation.ui.base.BaseActivityWithInjector

class MainActivity : BaseActivityWithInjector() {
  override fun getActivityVM(): Class<out ViewModel> {
 return MainActivityViewModel::class.java
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)
  }
}
