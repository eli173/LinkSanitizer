package com.eli173.linksanitizer

import android.net.Uri
import android.support.test.InstrumentationRegistry
import android.widget.TextView
import org.junit.Test

import org.junit.Assert.*

class AMPHandlerTest {

    class DummyHandler(textView: TextView): UriHandler(null, textView) {
        override val classString: String
            get() = "DummyHandler"

        override fun backgroundTask(uri: Uri): Uri {
            return uri
        }
    }

    @Test
    fun backgroundTask() {
        val tv = TextView(InstrumentationRegistry.getContext())
        val dh = DummyHandler(tv)
        val ah = AMPHandler(dh, tv)
        // testing cache links
        val bareCacheUri = Uri.parse("https://example-com.cdn.ampproject.org/c/example.com/amp_document.html")
        val sslCacheUri = Uri.parse("https://example-com.cdn.ampproject.org/c/s/example.com/amp_document.html")
        val sslDestUri = Uri.parse("https://example.com/amp_document.html")
        val bareDestUri = Uri.parse("http://example.com/amp_document.html")
        val sslResult = ah.backgroundTask(sslCacheUri)
        val bareResult = ah.backgroundTask(bareCacheUri)
        assertEquals(sslDestUri, sslResult)
        assertEquals(bareDestUri, bareResult)
        // testing google viewer links
        val viewerUri = Uri.parse("https://www.google.com/amp/www.example.com/amp.doc.html")
        val expectedUri = Uri.parse("www.example.com/amp.doc.html")
        val viewerResult = ah.backgroundTask(viewerUri)
        assertEquals(expectedUri, viewerResult)
    }
}