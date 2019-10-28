package com.eli173.linksanitizer

import android.os.AsyncTask
import android.widget.TextView
import java.net.URL


class FirstHandler(private val url: URL, private val nextHandler: URLHandler): AsyncTask<URL, Unit, URL>() {

    override fun doInBackground(vararg params: URL): URL {
        return url
    }

    override fun onPostExecute(result: URL) {
        nextHandler.execute(result)
    }

}

class FinalHandler(val opener: (URL)->Unit, textView: TextView): URLHandler(null, textView) {
    override val classString = "Finally"
    override fun backgroundTask(url: URL): URL {
        return url
    }
    override fun onPostExecute(result: URL) {
        opener(result)
    }
}
