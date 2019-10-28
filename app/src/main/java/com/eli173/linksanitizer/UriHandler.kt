package com.eli173.linksanitizer

import android.net.Uri
import android.os.AsyncTask
import android.widget.TextView
import java.lang.ref.WeakReference

abstract class UriHandler(private val nextHandler: UriHandler?, textView: TextView): AsyncTask<Uri, Unit, Uri>() {
    abstract val classString: String
    abstract fun backgroundTask(uri: Uri): Uri

    private val tv = WeakReference<TextView>(textView)

    final override fun onPreExecute() {
        super.onPreExecute()

    }

    final override fun doInBackground(vararg params: Uri): Uri {
        return backgroundTask(params[0])
    }

    override fun onPostExecute(result: Uri) {
        tv.get()?.append("\n${classString}: ${result}")
        nextHandler?.execute(result)
    }

}