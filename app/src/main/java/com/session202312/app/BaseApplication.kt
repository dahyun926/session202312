package com.session202312.app

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.session202312.app.db.PokemonDB
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: BaseApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        patchEOFException()
    }

    private fun patchEOFException() {
        System.setProperty("http.keepAlive", "false")
    }
}

