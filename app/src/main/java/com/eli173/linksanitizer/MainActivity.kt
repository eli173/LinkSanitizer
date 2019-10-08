package com.eli173.linksanitizer

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sampleUri = Uri.parse("https://eli173.com")
        val browserFinderIntent = Intent(Intent.ACTION_VIEW, sampleUri)

        var browsers = packageManager.queryIntentActivities(browserFinderIntent, PackageManager.MATCH_ALL)
        Log.d("173", browsers.map{ x -> x.activityInfo.packageName}.toString())
        browsers = browsers.filter { b -> b.activityInfo.packageName != "com.eli173.linksanitizer" }

        setContentView(R.layout.activity_main)

        val prefs = getSharedPreferences(getString(R.string.prefs_key), Context.MODE_PRIVATE)
        prefs.contains("browser")

        val rg = findViewById<RadioGroup>(R.id.radiogrp)
        for (b in browsers) {
            val button = RadioButton(this)
            if(b.activityInfo.packageName == prefs.getString("browser", "")) {
                button.toggle()
            }
            button.text = b.activityInfo.packageName
            button.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    val x = b.activityInfo.packageName
                    Log.d("173", "found the button listener for $x")
                    with(prefs.edit()) {
                        putString("browser", x)
                        commit()
                    }
                }
            }
            rg.addView(button)
        }

    }

    fun promptDefault(view: View) {
        val defIntent = Intent(android.provider.Settings.ACTION_SETTINGS)
        Log.d("173","prompt default reached")
        startActivityForResult(defIntent, 0)
        /*
        Log.d("173",defIntent.resolveActivity(packageManager).toString())
        if(defIntent.resolveActivity(packageManager) != null) {
            startActivity(defIntent)
        }*/
    }
}
