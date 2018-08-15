package com.binarycase.saudiassociation.utils

import android.content.Context
import android.os.Build
import android.os.LocaleList
import java.util.Locale

class ContextWrapper(base: Context) : android.content.ContextWrapper(base) {
  companion object {

    fun wrap(context: Context, lang_code: String): ContextWrapper {
      var context = context
      val newLocale: Locale
//      val model = (context.applicationContext as MyApplication).language_model
      //        model.isArabic.set(lang_code.equals(Language_Model.LANGUAGE.ARABIC));
      val res = context.resources
      val configuration = res.configuration

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        newLocale = Locale(lang_code)
        configuration.setLocale(newLocale)

        val localeList = LocaleList(newLocale)
        LocaleList.setDefault(localeList)
        configuration.locales = localeList

        context = context.createConfigurationContext(configuration)

      } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        newLocale = Locale(lang_code)
        configuration.setLocale(newLocale)
        context = context.createConfigurationContext(configuration)

      } else {
        newLocale = configuration.locale
        configuration.locale = newLocale
        res.updateConfiguration(configuration, res.displayMetrics)
      }

      return ContextWrapper(context)
    }
  }
}
