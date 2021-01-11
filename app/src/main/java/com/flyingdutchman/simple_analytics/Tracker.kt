package com.flyingdutchman.simple_analytics

import androidx.annotation.StringDef
import com.flyingdutchman.simple_analytics.EVENT.Companion.EXCEPTION
import com.flyingdutchman.simple_analytics.EVENT.Companion.PAGE_VIEW
import com.flyingdutchman.simple_analytics.EVENT.Companion.STACK_TRACE

interface Tracker {

    fun isAnalyticsEnabled(): Boolean

    fun enableAnalytics(enabled: Boolean)

    fun logLevel(logLevel: Int)

    fun start()

    fun apiKey(): String

    fun trackEvent(eventTrack: Event)
}

interface Trackable{
    @EVENT val eventName: String
    val properties: Map<String, String>?
}

sealed class Event : Trackable {
    data class EventTrack(
        @EVENT override val eventName: String,
        override val properties: Map<String, String>? = null
    ) : Event()

    data class CrashTrack(
        @EVENT override val eventName: String,
        override val properties: Map<String, String>? = null
    ) : Event()

}


@Retention(AnnotationRetention.SOURCE)
@StringDef(PAGE_VIEW, EXCEPTION, STACK_TRACE)
annotation class EVENT {
    companion object {
        const val PAGE_VIEW = "pageView"
        const val EXCEPTION = "exception"
        const val STACK_TRACE = "stack_trace"
    }

}