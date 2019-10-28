package com.eli173.linksanitizer

import android.util.Log
import android.widget.TextView
import java.net.HttpURLConnection
import java.net.URL


class RedirectHandler(nextHandler: URLHandler, textView: TextView): URLHandler(nextHandler, textView) {
    override val classString = "Redirect Handler"
    override fun backgroundTask(url: URL): URL {
        var newurl = url
        do {
            //Log.d(TAG, "RedirectHandler: ${newurl.toString()}")
            val conn = newurl.openConnection() as HttpURLConnection
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
                    newurl = URL(newurl.protocol + "://" + newurl.authority + location)
                    Log.d(TAG, "bs $newurl}")
                }
                else {
                    newurl = URL(location)
                }
            }
        } while ((code == 301) or (code == 308))
        if(newurl != url) {
            Log.i(TAG, "Redirected to $newurl")
        }
        return newurl
    }
}
