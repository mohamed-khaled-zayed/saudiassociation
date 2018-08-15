package com.binarycase.saudiassociation.utils

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.support.v7.app.AppCompatActivity
import java.util.Locale

object Localization_Utils {
  fun changeLocality(
    context: AppCompatActivity,
    language: String,
    recreate: Boolean
  ) {
    val res = context.applicationContext.resources
    val dm = res.displayMetrics
    val configuration = res.configuration
//    val model = (context.applicationContext as App).getLanguage_model()
//    model.isArabic.set(language == Language_Model.LANGUAGE.ARABIC)

    val locale = Locale(language)
    Locale.setDefault(locale)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      configuration!!.setLocale(locale)
    } else {
      configuration!!.locale = locale
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      context.applicationContext.createConfigurationContext(configuration)
    } else {
      res.updateConfiguration(configuration, null)
    }
    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        configuration.setLayoutDirection(locale)
        res.updateConfiguration(configuration, dm)
      }
      context.invalidateOptionsMenu()
    } catch (e: Exception) {
      e.printStackTrace()
    }

    if (recreate)
      context.recreate()
  }

  fun changeLang(
    context: Context,
    lang_code: String
  ): ContextWrapper {
    var context = context
    val sysLocale: Locale
//    val model = (context.applicationContext as App).getLanguage_model()
//    model.isArabic.set(lang_code == Language_Model.LANGUAGE.ARABIC)
    val rs = context.resources
    val config = rs.configuration

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      sysLocale = Locale(lang_code)
    } else {
      sysLocale = config.locale
    }
    if (lang_code != "" && sysLocale.language != lang_code) {
      val locale = Locale(lang_code)
      Locale.setDefault(locale)
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        config.setLocale(locale)
      } else {
        config.locale = locale
      }
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        context = context.createConfigurationContext(config)
      } else {
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
      }
    }

    return ContextWrapper(context)
  }
}
