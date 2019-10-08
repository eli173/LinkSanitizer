package com.eli173.linksanitizer

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class SanitizeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("173", intent.dataString)

        val uri = intent.data

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

        /*
        val browseIntent = Intent(Intent.ACTION_VIEW, intent.data)
        val title = resources.getString(R.string.choose_browser)
        val chooser = Intent.createChooser(browseIntent, title)
        val acts = packageManager.queryIntentActivities(browseIntent,PackageManager.MATCH_DEFAULT_ONLY)
        Log.d("173",acts.toString())
        Log.d("173",browseIntent.resolveActivity(packageManager).toString())
        //if (browseIntent.resolveActivity(packageManager) != null) {
        startActivity(chooser)
        //}
        */

    }

}