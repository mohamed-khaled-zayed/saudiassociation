package com.binarycase.saudiassociation.utils

sealed class StateView {
  data class Success<out T>(val data: T) : StateView()
  data class PaginationSuccess<out T>(val data :T):StateView()
  data class Errors(val error: Throwable?) : StateView()
  data class PaginationError(val error: Throwable?) : StateView()

  object Empty : StateView()
  object Loading : StateView()
  object PaginationLoading : StateView()
  object PaginationFinished:StateView()

}