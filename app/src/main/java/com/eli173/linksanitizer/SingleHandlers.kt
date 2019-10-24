package com.eli173.linksanitizer

import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import android.widget.TextView


class FirstHandler(val uri: Uri, val nextHandler: UriHandler): AsyncTask<Uri, Unit, Uri>() {

    override fun doInBackground(vararg params: Uri): Uri {
        return uri
    }

    override fun onPostExecute(result: Uri) {
        nextHandler.execute(result)
    }

}

class FinalHandler(val opener: (Uri)->Unit, textView: TextView): UriHandler(null, textView) {
    override val classString = "Finally"
    override fun backgroundTask(uri: Uri): Uri {
        //opener(uri)
        return uri
    }
    override fun onPostExecute(result: Uri) {
        opener(result)
    }
}
