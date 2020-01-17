package com.outofbound.mviarchitecture.domain

import com.outofbound.mviarchitecture.data.MainRepository
import io.reactivex.Observable

object MainUseCase{
    fun getHelloWorldText(): Observable<MainViewState> {
        return MainRepository.loadHelloWorldText()
            .map<MainViewState> { MainViewState.DataLoadedState(it) }
            .startWith(MainViewState.LoadingState)
            .onErrorReturn { MainViewState.ErrorState(it.message.toString()) }
    }
}
