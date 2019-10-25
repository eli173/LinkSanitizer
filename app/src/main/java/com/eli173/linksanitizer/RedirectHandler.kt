package com.eli173.linksanitizer

import android.net.Uri
import android.util.Log
import android.widget.TextView
import java.net.HttpURLConnection
import java.net.URL


class RedirectHandler(nextHandler: UriHandler, textView: TextView): UriHandler(nextHandler, textView) {
    override val classString = "Redirect Handler"
    override fun backgroundTask(uri: Uri): Uri {
        var newuri = uri
        do {
            Log.d(TAG, "RedirectHandler: ${newuri.toString()}")
            val url = URL(newuri.toString())
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "HEAD"
            conn.instanceFollowRedirects = false
            conn.connect()
            val code = conn.responseCode
            Log.d(TAG, code.toString())
            if((code == 301) or (code == 308)) {
                val location = conn.getHeaderField("location")
                Log.d(TAG, location)
                // this if statement is meant to handle redirects that give relative locations
                if(location[0] == '/') {
                    newuri = Uri.parse(newuri.scheme + "://" + newuri.authority + location)
                    Log.d(TAG, "bs " + newuri.toString())
                }
                else {
                    newuri = Uri.parse(location)
                }
            }
        } while ((code == 301) or (code == 308))
        if(newuri != uri) {
            Log.i(TAG, "Redirected to ${newuri.toString()}")
        }
        return newuri
    }
}
