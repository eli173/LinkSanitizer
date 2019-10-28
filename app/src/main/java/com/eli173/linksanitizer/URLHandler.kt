package com.eli173.linksanitizer

import android.os.AsyncTask
import android.widget.TextView
import java.lang.ref.WeakReference
import java.net.URL

abstract class URLHandler(private val nextHandler: URLHandler?, textView: TextView): AsyncTask<URL, Unit, URL>() {
    abstract val classString: String
    abstract fun backgroundTask(url: URL): URL

    private val tv = WeakReference<TextView>(textView)

    final override fun onPreExecute() {
        super.onPreExecute()

    }

    final override fun doInBackground(vararg params: URL): URL {
        return backgroundTask(params[0])
    }

    override fun onPostExecute(result: URL) {
        tv.get()?.append("\n$classString: $result")
        nextHandler?.execute(result)
    }

}