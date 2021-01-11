package com.flyingdutchman.simple_analytics

import android.util.Log
import java.util.*

class AndroidLogTracker : Tracker {
    private val TAG = this::class.java.simpleName



    override fun isAnalyticsEnabled(): Boolean {
        Log.d(TAG, "$TAG is enable ${BuildConfig.DEBUG} ")
        return BuildConfig.DEBUG
    }

    override fun enableAnalytics(enabled: Boolean) {
        Log.d(TAG, "$TAG enable analytics $enabled ")
    }

    override fun logLevel(logLevel: Int) {
        Log.d(TAG, "$TAG log level: $logLevel")
    }

    override fun start() {
        Log.d(TAG, "$TAG has started!!")
    }

    override fun apiKey(): String {
        val apiKey = UUID.randomUUID()
        Log.d(TAG, "$TAG generate an random uuid as apiKey: $apiKey")
        return apiKey.toString()
    }

    override fun trackEvent(eventTrack: Event) {
        when(eventTrack){
            is Event.EventTrack -> {
                Log.d(TAG, "$TAG trackEvent ${eventTrack.eventName}")

            }
            else -> {
                //TODO You can choose what kind of events you want to be aware about
            }
        }
    }

}