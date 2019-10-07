package com.eli173.linksanitizer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun promptDefault(view: View) {
        Log.d("173","prompt default reached")

    }
}
