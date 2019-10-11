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

val TAG: String = "LinkSanitizer"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sampleUri = Uri.parse("https://eli173.com")
        val browserFinderIntent = Intent(Intent.ACTION_VIEW, sampleUri)

        var browsers = packageManager.queryIntentActivities(browserFinderIntent, PackageManager.MATCH_ALL)
        browsers = browsers.filter { b -> b.activityInfo.packageName != "com.eli173.linksanitizer" }

        setContentView(R.layout.activity_main)

        val prefs = getSharedPreferences(getString(R.string.prefs_key), Context.MODE_PRIVATE)
        prefs.contains("browser")

        val rg = findViewById<RadioGroup>(R.id.radiogrp)
        for (b in browsers) {
            val button = RadioButton(this)
            button.text = b.activityInfo.packageName
            button.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    val x = b.activityInfo.packageName
                    with(prefs.edit()) {
                        putString("browser", x)
                        commit()
                    }
                }
            }
            rg.addView(button)
            if(b.activityInfo.packageName == prefs.getString("browser", "")) {
                rg.check(button.id)
            }

        }

    }

    fun promptDefault(view: View) {
        val defIntent = Intent(android.provider.Settings.ACTION_SETTINGS)
        startActivityForResult(defIntent, 0)
    }
}
