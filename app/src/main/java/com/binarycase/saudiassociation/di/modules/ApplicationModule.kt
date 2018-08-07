package com.binarycase.saudiassociation.di.modules

import android.content.Context
import com.binarycase.saudiassociation.di.qualifiers.ForApplication
import com.binarycase.saudiassociation.myApp.MyApplication
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

  @Provides
  @ForApplication
  fun applicationContext(app: MyApplication): Context {
    return app.applicationContext
  }

}