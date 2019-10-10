package com.eli173.linksanitizer

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class SanitizeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("173", intent.dataString)

        val uri = intent.data


        // cleaning here? okay
        val cleaner = Cleaner(uri, this, this::openURL)
        cleaner.getHeader()
        Log.d("173","uh oh...")



    }

    fun openURL(uri: Uri) {
        Log.d("173","ok tf is this")
        val prefs = getSharedPreferences(getString(R.string.prefs_key), Context.MODE_PRIVATE)
        val defaultBrowserPkg = prefs.getString("browser","")

        // TODO: check for installs, do something when default isn't selected
        if (defaultBrowserPkg != "") {
            // it's well-defined and should work, if it's still installed
            val browseIntent = Intent(Intent.ACTION_VIEW, uri)
            browseIntent.setPackage(defaultBrowserPkg)
            if(browseIntent.resolveActivity(packageManager) != null) {
                startActivity(browseIntent)
            }
        }

    }

}