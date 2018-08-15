package com.binarycase.saudiassociation.utils

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import cn.jzvd.JZVideoPlayerStandard
import com.squareup.picasso.Picasso
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
import android.graphics.Color
import android.support.v4.content.ContextCompat.startActivity
import java.util.Random

@BindingAdapter("bind:imageUrl")
fun ImageView.loadUrl(imageUrl: String) {
  Picasso.get()
      .load(imageUrl)
      .into(this)
}


@BindingAdapter("bind:videoUrl")
fun JZVideoPlayerStandard.loadVideo(videoUrl: String) {
  this.setUp(videoUrl,cn.jzvd.JZVideoPlayer.SCREEN_WINDOW_LIST)

}


@BindingAdapter("bind:shareText")
fun View.setShareText(shareBody : String){
  this.setOnClickListener {
    val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
    sharingIntent.type = "text/plain"
    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody)
    this.context.startActivity(Intent.createChooser(sharingIntent,"Share Through"))
  }
}

@BindingAdapter("bind:setRandColor")
fun View.setRandomColor(hasRandomColor: Boolean){
    val rnd =  Random()
    this.setBackgroundColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)))
}