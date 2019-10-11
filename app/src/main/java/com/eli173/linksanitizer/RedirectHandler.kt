package com.eli173.linksanitizer

import android.net.Uri
import android.util.Log
import java.net.HttpURLConnection
import java.net.URL


class RedirectHandler(nextHandler: UriHandler): UriHandler(nextHandler) {
    override fun backgroundTask(uri: Uri): Uri {
        var newuri = uri
        do {
            val url = URL(newuri.toString())
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "HEAD"
            conn.instanceFollowRedirects = false
            conn.connect()
            val code = conn.responseCode
            newuri = when (code) {
                // I don't have a good enough understanding of 1xx or 2xx codes yet
                // 4xx and 5xx codes I will just pass on to the browser to show the user the error
                // so I'm left with the 3xx codes to sort through and figure out
                // 300 I want to let the browser decide
                // looks like 301 and 308 are the only ones I want to take action on right now
                301 -> Uri.parse(conn.getHeaderField("location"))
                308 -> Uri.parse(conn.getHeaderField("location"))
                else -> newuri
            }
        } while ((code == 301) or (code == 308))
        if(newuri != uri) {
            Log.i(TAG, "Redirected to ${newuri.toString()}")
        }
        return newuri
    }
}
