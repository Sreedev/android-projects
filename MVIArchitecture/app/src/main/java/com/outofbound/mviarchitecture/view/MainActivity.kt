package com.outofbound.mviarchitecture.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.outofbound.mviarchitecture.R
import com.outofbound.mviarchitecture.domain.MainViewState
import io.reactivex.Observable

class MainActivity : AppCompatActivity(), MainView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var s = MainPresenter()
        s.bind(this)
    }

    override fun render(state: MainViewState) {
        when(state) {
            is MainViewState.LoadingState -> renderLoadingState()
            is MainViewState.DataLoadedState -> renderDataLoadedState(state)
            is MainViewState.ErrorState -> renderErrorState(state)
        }
    }

    private fun renderErrorState(state: MainViewState.ErrorState) {
        Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
    }

    private fun renderDataLoadedState(state: MainViewState.DataLoadedState) {
        Toast.makeText(this,state.fetchedData,Toast.LENGTH_SHORT).show()
    }

    private fun renderLoadingState() {
        Toast.makeText(this,"Loading!!!!",Toast.LENGTH_SHORT).show()
    }
}
