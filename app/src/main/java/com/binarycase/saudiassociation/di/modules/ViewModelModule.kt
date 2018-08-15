package com.binarycase.saudiassociation.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.binarycase.saudiassociation.ui.screens.fragments.callUsScreen.CallUsViewModel
import com.binarycase.saudiassociation.ui.screens.fragments.categorySubList.CategorySubListViewModel
import com.binarycase.saudiassociation.ui.screens.fragments.mainSections.MainSectionsViewModel
import com.binarycase.saudiassociation.ui.screens.fragments.StaticContentViewModel
import com.binarycase.saudiassociation.ui.screens.fragments.searchCategSubList.SearchCategViewModel
import com.binarycase.saudiassociation.ui.screens.fragments.whoAreWeScreen.WhoAreWeViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModels[modelClass]?.get() as T
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

  @Binds
  internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

  @Binds
  @IntoMap
  @ViewModelKey(CallUsViewModel::class)
  internal abstract fun CallUsViewModel(viewModel: CallUsViewModel): ViewModel


  @Binds
  @IntoMap
  @ViewModelKey(MainSectionsViewModel::class)
  internal abstract fun MainSectionsViewModel(viewModel: MainSectionsViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(StaticContentViewModel::class)
  internal abstract fun PolicyViewModel(viewModel: StaticContentViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(WhoAreWeViewModel::class)
  internal abstract fun WhoAreWeViewModel(viewModel: WhoAreWeViewModel): ViewModel


  @Binds
  @IntoMap
  @ViewModelKey(CategorySubListViewModel::class)
  internal abstract fun CategorySubListViewModel(viewModel: CategorySubListViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(SearchCategViewModel::class)
  internal abstract fun SearchCategViewModel(viewModel: SearchCategViewModel): ViewModel

}