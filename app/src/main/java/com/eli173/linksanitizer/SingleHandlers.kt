package com.eli173.linksanitizer

import android.net.Uri
import android.os.AsyncTask
import android.util.Log



class FirstHandler(val uri: Uri, val nextHandler: UriHandler): AsyncTask<Uri, Unit, Uri>() {

    override fun doInBackground(vararg params: Uri): Uri {
        Log.d("173", "starting fh2")
        return uri
    }

    override fun onPostExecute(result: Uri) {
        nextHandler.execute(result)
    }

}

class FinalHandler(val opener: (Uri)->Unit): UriHandler(null) {
    override fun backgroundTask(uri: Uri): Uri {
        Log.d("173", "made it to the end")
        opener(uri)
        return uri
    }
}