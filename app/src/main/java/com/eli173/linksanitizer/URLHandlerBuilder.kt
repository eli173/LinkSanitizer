package com.eli173.linksanitizer

import android.widget.TextView
import java.net.URL

class URLHandlerBuilder(nextHandler: URLHandler, textView: TextView, private val className: String,
                        val urlHandlerFunction: (URL) -> URL): URLHandler(nextHandler, textView) {
    override val classString: String
        get() = className

    override fun backgroundTask(url: URL): URL {
        return urlHandlerFunction(url)
    }

    // this class probably needs some integration testing

}