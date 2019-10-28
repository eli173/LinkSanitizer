package com.eli173.linksanitizer

import android.widget.TextView
import java.net.URL

class AMPHandler(nextHandler: URLHandler, textView: TextView): URLHandler(nextHandler, textView) {
    // see https://blog.amp.dev/2017/02/06/whats-in-an-amp-url/ for info about the link types
    override val classString = "AMP Handler"
    override fun backgroundTask(url: URL): URL {
        val googViewerPrefix = "https://www.google.com/amp/"
        if(url.toString().startsWith(googViewerPrefix)) {
            /* Important note:
            * I'm not sure how these are supposed to be interpreted,
            * as in I don't know whether SSL/TLS is used or not...
            * Google doesn't exactly provide the clearest info on this
            * */
            return URL("https://" + url.toString().substringAfter(googViewerPrefix))
        }
        // cache urls
        val ampCdn = "cdn.ampproject.org"
        if(url.host.endsWith(ampCdn)) {
            // complicated.. see https://developers.google.com/amp/cache/overview
            val csuri = url.toString().substringAfter(url.host)
            // this string is of the form /x/x/url or /x/url, first case is https, second http
            val isSSL = (csuri[3] == 's') and (csuri[4] == '/')
            val index = if (isSSL) 5 else 3
            // 5 for /c/s/, 3 for /c/
            return URL("https://${csuri.substring(index)}")
            /*if(isSSL) {
                return URL("https://${csuri.substring(5)}") // /c/s/
            }
            else {
                return URL("http://${csuri.substring(3)}") // /c/
            }*/
        }
        else return url
    }
}