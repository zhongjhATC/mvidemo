package com.zhongjh.mvidemo.phone.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.jakewharton.rxbinding2.view.clicks
import com.zhongjh.mvidemo.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MviActivity<MainView, MainPresenter>(), MainView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun createPresenter() = MainPresenter()

    override fun sayHelloWorldIntent() = helloWorldButton.clicks()

    override fun render(state: MainViewState) {
        when (state) {
            is MainViewState.LoadingState -> renderLoadingState()
            is MainViewState.DataState -> renderDataState(state)
            is MainViewState.ErrorState -> renderErrorState(state)
        }
    }

    private fun renderLoadingState() {
        loadingIndicator.visibility = View.VISIBLE
        helloWorldTextview.visibility = View.GONE
    }

    private fun renderDataState(dataState: MainViewState.DataState) {
        loadingIndicator.visibility = View.GONE
        helloWorldTextview.apply {
            visibility = View.VISIBLE
            text = dataState.greeting
        }
    }

    private fun renderErrorState(errorState: MainViewState.ErrorState) {
        loadingIndicator.visibility = View.GONE
        helloWorldTextview.visibility = View.GONE
        Toast.makeText(this, "error ${errorState.error}", Toast.LENGTH_LONG).show()
    }

}