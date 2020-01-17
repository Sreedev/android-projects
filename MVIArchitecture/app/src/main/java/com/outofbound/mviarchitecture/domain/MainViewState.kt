package com.outofbound.mviarchitecture.domain

sealed class MainViewState {
    object LoadingState : MainViewState()
    data class DataLoadedState(val fetchedData: String) : MainViewState()
    data class ErrorState(val data: String) : MainViewState()
}
