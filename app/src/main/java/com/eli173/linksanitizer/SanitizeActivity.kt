package com.eli173.linksanitizer

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView

class SanitizeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.redirecting)

        val uri = intent.data

        Log.i(TAG, "Received URL ${uri?.toString()}")

        val outputTextView = findViewById<TextView>(R.id.results)
        outputTextView.append("Starting URL: ${uri?.toString()}")

        val prefs = getSharedPreferences("com.eli173.linksanitizer.prefs", Context.MODE_PRIVATE)
        val viewswitch = prefs.getBoolean("viewswitch",false)
        val final_fun = if(viewswitch) ::justLook else ::openUri

        // PUT YOUR NEW SUBCLASSES SOMEWHERE DOWN HERE
        val fin = FinalHandler(final_fun, outputTextView)
        val amp = AMPHandler(fin, outputTextView)
        val red = RedirectHandler(amp, outputTextView)
        val fh = FirstHandler(uri, red)
        try {
            fh.execute()
        }
        catch (e: Exception) {
            final_fun(uri)
        }
    }

    fun justLook(uri:Uri) {
        Log.i(TAG, "Final URL: ${uri.toString()}")
        return
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