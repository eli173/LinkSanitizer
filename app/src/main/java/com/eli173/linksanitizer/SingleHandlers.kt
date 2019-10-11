package com.eli173.linksanitizer

import android.net.Uri
import android.os.AsyncTask
import android.util.Log



class FirstHandler(val uri: Uri, val nextHandler: UriHandler): AsyncTask<Uri, Unit, Uri>() {

    override fun doInBackground(vararg params: Uri): Uri {
        return uri
    }

    override fun onPostExecute(result: Uri) {
        nextHandler.execute(result)
    }

}

class FinalHandler(val opener: (Uri)->Unit): UriHandler(null) {
    override fun backgroundTask(uri: Uri): Uri {
        opener(uri)
        return uri
    }
}