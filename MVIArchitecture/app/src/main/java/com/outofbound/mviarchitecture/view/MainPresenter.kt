package com.outofbound.mviarchitecture.view

import com.outofbound.mviarchitecture.data.MainRepository
import com.outofbound.mviarchitecture.domain.MainViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class MainPresenter {
    private val compositeDisposable = CompositeDisposable()
    private lateinit var view: MainView
    private var repository = MainRepository

    fun bind(view: MainView) {
        this.view = view
        compositeDisposable.add(observeListDisplay())
    }

    fun unbind() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    private fun observeListDisplay() = repository.loadHelloWorldText()
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { view.render(MainViewState.LoadingState) }
        .doOnNext { view.render(MainViewState.DataLoadedState(it)) }
        .doOnError { view.render(MainViewState.DataLoadedState(it.message.toString())) }
        .subscribe()
}
