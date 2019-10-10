package com.eli173.linksanitizer

import android.net.Uri
import android.os.AsyncTask

abstract class UriHandler(val nextHandler: UriHandler?): AsyncTask<Uri, Unit, Uri>() {
    abstract fun backgroundTask(uri: Uri): Uri

    final override fun doInBackground(vararg params: Uri): Uri {
        return backgroundTask(params.get(0))
    }

    final override fun onPostExecute(result: Uri) {

        nextHandler?.execute(result)
    }

}