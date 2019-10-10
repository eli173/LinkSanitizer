package com.eli173.linksanitizer

import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class RedirectHandler(var uri: Uri, val callback: (Uri) -> Unit): AsyncTask<Uri, Unit, Uri>() {
    override fun doInBackground(vararg params: Uri?): Uri {
        do{
            val url = URL(uri.toString())
            val conn = url.openConnection() as HttpsURLConnection
            conn.requestMethod = "HEAD"
            conn.instanceFollowRedirects = false
            conn.connect()
            val code = conn.responseCode
            Log.d("173", conn.responseMessage)
            Log.d("173", code.toString())
            uri = when (code) {
                // I don't have a good enough understanding of 1xx or 2xx codes yet
                // 4xx and 5xx codes I will just pass on to the browser to show the user the error
                // so I'm left with the 3xx codes to sort through and figure out
                // 300 I want to let the browser decide
                // looks like 301 and 308 are the only ones I want to take action on right now
                301 -> Uri.parse(conn.getHeaderField("location"))
                308 -> Uri.parse(conn.getHeaderField("location"))
                else -> uri
            }
        } while ((code == 301) or (code == 308))
        return uri
}

    override fun onPostExecute(result: Uri) {
        callback(result)
    }

}