package com.binarycase.saudiassociation.ui.screens.fragments.staticContentScreens

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.binarycase.saudiassociation.R
import com.binarycase.saudiassociation.ui.base.BaseFragmentWithInjector
import com.binarycase.saudiassociation.ui.screens.fragments.StaticContentViewModel
import com.binarycase.saudiassociation.ui.screens.fragments.callUsScreen.CallUsViewModel
import com.binarycase.saudiassociation.utils.StateView.Loading
import com.binarycase.saudiassociation.utils.StateView.Success
import com.tripl3dev.prettystates.StatesConstants
import com.tripl3dev.prettystates.setState
import kotlinx.android.synthetic.main.static_content_fragment.contentText
import kotlinx.android.synthetic.main.static_content_fragment.textContainer

class PolicyFragment :BaseFragmentWithInjector(){

  override fun mainActivityTitleRES(): Int {
    return R.string.usage_policy
  }


  lateinit var viewModel: StaticContentViewModel
  override fun getActivityVM(): Class<out ViewModel> {
    return StaticContentViewModel::class.java
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel = vm as StaticContentViewModel
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.static_content_fragment, container, false)
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    setContentListener()
    viewModel.fetchData(StaticContentViewModel.USAGE_POLICY)
  }

  private fun setContentListener() {
    viewModel.getStaticContent()
        .observe(this, Observer {
          when (it) {
            is Success<*> -> {
              var content = it.data as String
              textContainer.setState(StatesConstants.NORMAL_STATE)
              setHtmlText(content)
            }
            is Loading -> {
              textContainer.setState(StatesConstants.LOADING_STATE)
            }
            is Error -> {
              val retryBt = textContainer.setState(StatesConstants.ERROR_STATE)
                  .findViewById<Button>(R.id.retryButton)
              retryBt.setOnClickListener {
                viewModel.fetchData(StaticContentViewModel.USAGE_POLICY)

              }

            }
          }
        })
  }

  private fun setHtmlText(html: String) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
      contentText.text = Html.fromHtml(html)
    } else {
      contentText.text = Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)

    }
  }
}