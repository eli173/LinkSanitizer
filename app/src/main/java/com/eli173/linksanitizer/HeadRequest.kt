package com.eli173.linksanitizer

import android.util.Log
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError

class HeadRequest<T>(url: String, listener: Response.Listener<T> , errorListener: Response.ErrorListener) : Request<T>(Method.HEAD, url, errorListener) {
    val listener = listener
    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        if (response == null) {
            Log.d("173", "response is null!?")
            return Response.error(VolleyError())
        }
        Log.d("173",response.statusCode.toString())
        Log.d("173",response.allHeaders.toString())

        return Response.success(null,null)
    }

    override fun deliverResponse(response: T) {
        listener.onResponse(response)
    }
}