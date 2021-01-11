package com.flyingdutchman.simple_analytics

import android.util.Log
import java.util.*

class AndroidCrashLogger : CrashReporter {
    private val TAG = this::class.java.simpleName

    override fun simulateCrash() {
        //TODO Here you can throw a RuntimeException or use a related method of your crash report SDK
    }

    override fun isAnalyticsEnabled(): Boolean = true

    override fun enableAnalytics(enabled: Boolean) {}

    override fun logLevel(logLevel: Int) {}

    override fun start() {}

    override fun apiKey(): String {
        val apiKey = UUID.randomUUID()
        Log.d(TAG, "$TAG generate an random uuid as apiKey: $apiKey")
        return apiKey.toString()
    }

    override fun trackEvent(eventTrack: Event) {
        when (eventTrack) {
            is Event.CrashTrack -> {
                Log.e(TAG, "$TAG Report crash: ${eventTrack.eventName}")
            }
            else -> {
                //TODO You can choose what kind of events you want to be aware about
            }
        }
    }

}