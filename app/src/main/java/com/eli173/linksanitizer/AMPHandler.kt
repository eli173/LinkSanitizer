package com.eli173.linksanitizer

import android.net.Uri
import android.widget.TextView

class AMPHandler(nextHandler: UriHandler, textView: TextView): UriHandler(nextHandler, textView) {
    override val classString = "AMP Handler"
    override fun backgroundTask(uri: Uri): Uri {
        val googViewerPrefix = "https://www.google.com/amp/"
        if(uri.toString().startsWith(googViewerPrefix)) {
            return Uri.parse(uri.toString().substringAfter(googViewerPrefix))
        }
        // cache urls
        val ampCdn = "cdn.ampproject.org"
        if(uri.host.endsWith(ampCdn)) {
            // complicated.. see https://developers.google.com/amp/cache/overview
            val csuri = uri.toString().substringAfter(uri.host)
            // this string is of the form /x/x/url or /x/url, first case is https, second http
            val isSSL = (csuri[3] == 's') and (csuri[4] == '/')
            if(isSSL) {
                return Uri.parse("https://${csuri.substring(5)}") // /c/s/
            }
            else {
                return Uri.parse("http://${csuri.substring(3)}") // /c/
            }
        }
        else return uri
    }
}