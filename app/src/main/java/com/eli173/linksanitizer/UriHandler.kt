package com.eli173.linksanitizer

import android.net.Uri
import android.os.AsyncTask

abstract class UriHandler(var uri: Uri, val nextHandler: UriHandler): AsyncTask<Uri, Unit, Uri>() {
    abstract fun backgroundTask(uri: Uri): Uri

    final override fun doInBackground(vararg params: Uri?): Uri {
        return backgroundTask(uri)
    }

    final override fun onPostExecute(result: Uri) {
        nextHandler.backgroundTask(result)
    }

}