package com.binarycase.saudiassociation.utils

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView.Recycler
import android.support.v7.widget.RecyclerView.State
import android.util.AttributeSet
import android.util.Log

class GridLayoutManagerWrapper : GridLayoutManager {
  constructor(
    context: Context?,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
  ) : super(context, attrs, defStyleAttr, defStyleRes)

  constructor(
    context: Context?,
    spanCount: Int
  ) : super(context, spanCount)

  constructor(
    context: Context?,
    spanCount: Int,
    orientation: Int,
    reverseLayout: Boolean
  ) : super(context, spanCount, orientation, reverseLayout)

  override fun onLayoutChildren(
    recycler: Recycler?,
    state: State?
  ) {
    try {
      super.onLayoutChildren(recycler, state)
    }catch (e :IndexOutOfBoundsException){
      Log.e("probe", "meet a IOOBE in RecyclerView")

    }
  }
}