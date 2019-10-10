package com.eli173.linksanitizer

import com.android.volley.Request
import com.android.volley.toolbox.BaseHttpStack
import com.android.volley.toolbox.HttpResponse

class NoRedirectHttpStack: BaseHttpStack() {
    override fun executeRequest(
        request: Request<*>?,
        additionalHeaders: MutableMap<String, String>?
    ): HttpResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}