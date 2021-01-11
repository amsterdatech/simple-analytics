package com.flyingdutchman.simple_analytics

import androidx.annotation.StringDef
import com.flyingdutchman.simple_analytics.EVENT.Companion.PAGE_VIEW

interface Tracker {

    fun isAnalyticsEnabled(): Boolean

    fun enableAnalytics(enabled: Boolean)

    fun logLevel(logLevel: Int)

    fun start()

    fun apiKey(): String

    fun trackEvent(eventTrack: EventTrack)
}

interface Trackable

data class EventTrack(@EVENT val eventName: String, val properties: Map<String, String>? = null) : Trackable

@Retention(AnnotationRetention.SOURCE)
@StringDef(PAGE_VIEW)
annotation class EVENT {
    companion object {
        const val PAGE_VIEW = "pageView"
    }
}