package com.eli173.linksanitizer

import android.content.Context
import android.net.Uri
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class Cleaner(val uri: Uri, val ctx: Context, callback: (Uri) -> Unit) {
    var url: String
    val queue: RequestQueue
    val opener: (Uri) -> Unit
    init {
        url = uri.toString()
        queue = Volley.newRequestQueue(ctx)
        queue.start()
        opener = callback
    }
    fun getHeader() {
        Log.d("173","in the log thing dammit")
        val request = HeadRequest<String>(url,
            Response.Listener { resp ->
                Log.d("173", "in hr rl")
                opener(uri)
            },
            Response.ErrorListener { x -> Log.d("173", "in hr el") }
        )
        queue.add(request)

    }
}