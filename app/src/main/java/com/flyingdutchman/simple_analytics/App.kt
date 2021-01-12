package com.flyingdutchman.simple_analytics

import android.app.Application
import android.os.StrictMode

class App : Application() {
    override fun onCreate() {
        super.onCreate()


        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDialog()
                .build()
        )
        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder().detectAll()
                .penaltyLog()
                .build()
        )

        analytics(this) {
            kit(AndroidCrashLogger(context))
            kit(AndroidLogTracker(context))
        }
    }
}