package com.zhongjh.mvidemo.phone.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hannesdorfmann.mosby3.mvi.MviActivity

class MainActivity : MviActivity<MainView,> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}