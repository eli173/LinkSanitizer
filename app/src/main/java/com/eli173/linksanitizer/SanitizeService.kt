package com.eli173.linksanitizer

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class SanitizeService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        Log.d("173", "binded to sanitize service?")
        return null
    }

}