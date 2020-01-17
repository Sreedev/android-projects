package com.outofbound.mviarchitecture.view

import com.outofbound.mviarchitecture.domain.MainViewState
import io.reactivex.Observable

interface MainView{
    /**
     * Render the state in the UI
     */
    fun render(state: MainViewState)
}
