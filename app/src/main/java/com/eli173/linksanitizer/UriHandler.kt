package com.eli173.linksanitizer

import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import android.widget.TextView

abstract class UriHandler(val nextHandler: UriHandler?, val textView: TextView): AsyncTask<Uri, Unit, Uri>() {
    abstract val classString: String
    abstract fun backgroundTask(uri: Uri): Uri

    final override fun doInBackground(vararg params: Uri): Uri {
        return backgroundTask(params.get(0))
    }

    final override fun onPostExecute(result: Uri) {
        textView.append("\n${classString}: ${result.toString()}")
        nextHandler?.execute(result)
    }

}