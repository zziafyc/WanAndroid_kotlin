package com.zziafyc.wanandroidkotlin

import android.app.Application
import android.content.Context
import android.util.Log
import kotlin.properties.Delegates

class App : Application() {
    companion object {
        const val TAG: String = "Application"
        lateinit var instance: Application
        var context: Context by Delegates.notNull()
            private set
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Application onCreate")
        instance = this
        context = applicationContext
        initConfig()

    }

    private fun initConfig() {

    }
}