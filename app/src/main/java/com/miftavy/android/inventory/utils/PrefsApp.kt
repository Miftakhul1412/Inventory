package com.miftavy.android.inventory.utils

import android.app.Application
import android.content.ContextWrapper
import com.pixplicity.easyprefs.library.Prefs

class PrefsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Prefs.Builder()
            .setContext(this)
            .setUseDefaultSharedPreference(true)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .build()
    }
}