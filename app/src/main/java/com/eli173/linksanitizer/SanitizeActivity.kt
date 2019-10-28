package com.eli173.linksanitizer

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.net.URL

class SanitizeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.redirecting)

        val uri = intent.data
        val url = URL(uri.toString())

        Log.i(TAG, "Received URL ${uri?.toString()}")

        val outputTextView = findViewById<TextView>(R.id.results)
        outputTextView.append("Starting URL: ${uri?.toString()}")

        val prefs = getSharedPreferences("com.eli173.linksanitizer.prefs", Context.MODE_PRIVATE)
        val viewswitch = prefs.getBoolean("viewswitch",false)
        val finalFun = if(viewswitch) ::justLook else ::openURL

        // PUT YOUR NEW SUBCLASSES SOMEWHERE DOWN HERE
        val fin = FinalHandler(finalFun, outputTextView)
        val amp = AMPHandler(fin, outputTextView)
        val red = RedirectHandler(amp, outputTextView)
        val fh = FirstHandler(url, red)
        try {
            fh.execute()
        }
        catch (e: Exception) {
            finalFun(url)
        }
    }

    private fun justLook(url: URL) {
        val button = findViewById<Button>(R.id.gobutton)
        button.visibility = View.VISIBLE
        button.setOnClickListener { openURL(url) }
        return
    }
    private fun openURL(url: URL) {
        Log.i(TAG,"Opening URL: $url")
        val prefs = getSharedPreferences(getString(R.string.prefs_key), Context.MODE_PRIVATE)
        val defaultBrowserPkg = prefs.getString("browser","")

        // TODO: check for installs, do something when default isn't selected, make this pretty
        if (defaultBrowserPkg != "") {
            val uri = Uri.parse(url.toString())
            val browseIntent = Intent(Intent.ACTION_VIEW, uri)
            browseIntent.setPackage(defaultBrowserPkg)
            if(browseIntent.resolveActivity(packageManager) != null) {
                startActivity(browseIntent)
            }
        }
        this.finish()
    }

}