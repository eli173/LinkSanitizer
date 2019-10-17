package com.eli173.linksanitizer

import android.net.Uri
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.widget.TextView
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RedirectHandlerTest {

    class DummyHandler(textView: TextView): UriHandler(null, textView) {
        override val classString: String
            get() = "DummyHandler"

        override fun backgroundTask(uri: Uri): Uri {
            return uri
        }
    }

    @Test
    fun backgroundTask() {
        //assert(true)
        val tv = TextView(InstrumentationRegistry.getContext())
        val dh = DummyHandler(tv)
        val rh = RedirectHandler(dh, tv)
        val baseUri = Uri.parse("http://test.eli173.com/")
        val testUri300 = Uri.parse("http://test.eli173.com/301/")
        val destUri = Uri.parse("http://test.eli173.com/")
        val baseBT = rh.backgroundTask(baseUri)
        assertEquals(baseUri, baseBT)
        val redirUri = rh.backgroundTask(testUri300)
        assertEquals(destUri, redirUri)
        assert(true)
    }
    /*
    class DummyHandler: UriHandler(null, TextView(null)) {
        override val classString: String
            get() = "DummyHandler"
        override fun backgroundTask(uri: Uri): Uri {
            return uri
        }
    }

    @Test
    fun backgroundTask() {
        val dh = DummyHandler()
        val rh = RedirectHandler(dh, TextView(null))
        val normalUri = Uri.parse("http://test.eli173.com")
        val testUri300 = Uri.parse("http://test.eli173.com/301/")
        val destUri = Uri.parse("http://test.eli173.com")
        assert(true)
    }
    */
}