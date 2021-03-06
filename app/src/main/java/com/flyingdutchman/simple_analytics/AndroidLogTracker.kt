package com.flyingdutchman.simple_analytics

import android.content.Context
import android.util.Log
import java.util.*

class AndroidLogTracker(private val context: Context) : Tracker {
    private val TAG = this::class.java.simpleName

    private fun tag(): String =
        "${context.applicationInfo.name}:${context.applicationInfo.packageName}:$TAG"

    override fun isAnalyticsEnabled(): Boolean {
        Log.d(
            TAG,
            "${tag()} is enable ${BuildConfig.DEBUG}"
        )
        return BuildConfig.DEBUG
    }

    override fun enableAnalytics(enabled: Boolean) {
        Log.d(TAG, "${tag()} enable analytics $enabled ")
    }

    override fun logLevel(logLevel: Int) {
        Log.d(TAG, "${tag()} log level: $logLevel")
    }

    override fun start() {
        Log.d(TAG, "${tag()} has started!!")
    }

    override fun apiKey(): String {
        val apiKey = UUID.randomUUID()
        Log.d(TAG, "${tag()} generate an random uuid as apiKey: $apiKey")
        return apiKey.toString()
    }

    override fun trackEvent(eventTrack: Event) {
        when (eventTrack) {
            is Event.EventTrack -> {
                Log.d(TAG, "${tag()} trackEvent ${eventTrack.eventName}")
            }
            else -> {
                //TODO You can choose what kind of events you want to be aware about
            }
        }
    }

}