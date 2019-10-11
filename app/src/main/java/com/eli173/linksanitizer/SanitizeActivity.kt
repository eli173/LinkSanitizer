package com.eli173.linksanitizer

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class SanitizeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.redirecting)

        val uri = intent.data

        Log.i(TAG, "Received URL ${uri.toString()}")

        // PUT YOUR NEW SUBCLASSES SOMEWHERE DOWN HERE
        val fin = FinalHandler(::openUri)
        val amp = AMPHandler(fin)
        val red = RedirectHandler(amp)
        val fh = FirstHandler(uri, red)

        fh.execute()

    }

    fun openUri(uri: Uri) {
        Log.i(TAG,"Opening URL: ${uri.toString()}")
        val prefs = getSharedPreferences(getString(R.string.prefs_key), Context.MODE_PRIVATE)
        val defaultBrowserPkg = prefs.getString("browser","")

        // TODO: check for installs, do something when default isn't selected, make this pretty
        if (defaultBrowserPkg != "") {
            val browseIntent = Intent(Intent.ACTION_VIEW, uri)
            browseIntent.setPackage(defaultBrowserPkg)
            if(browseIntent.resolveActivity(packageManager) != null) {
                startActivity(browseIntent)
            }
        }
        this.finish()
    }

}