package com.binarycase.saudiassociation.di.components

import com.binarycase.saudiassociation.di.modules.ApplicationModule
import com.binarycase.saudiassociation.myApp.MyApplication
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {
  fun injectApp(myApp: MyApplication)
  @Component.Builder
  interface Builder {
    fun builder(): ApplicationComponent
    @BindsInstance
    fun applicationContext(appContext: MyApplication): Builder
  }
}