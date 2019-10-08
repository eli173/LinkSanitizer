package com.eli173.linksanitizer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class SanitizeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("173", intent.dataString)
    }

}