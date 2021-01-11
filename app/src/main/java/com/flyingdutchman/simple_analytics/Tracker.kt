package com.flyingdutchman.simple_analytics

import androidx.annotation.StringDef
import com.flyingdutchman.simple_analytics.EVENT_NAME.Companion.EXCEPTION
import com.flyingdutchman.simple_analytics.EVENT_NAME.Companion.PAGE_VIEW
import com.flyingdutchman.simple_analytics.EVENT_NAME.Companion.STACK_TRACE

interface Tracker {

    fun isAnalyticsEnabled(): Boolean

    fun enableAnalytics(enabled: Boolean)

    fun logLevel(logLevel: Int)

    fun start()

    fun apiKey(): String

    fun trackEvent(eventTrack: Event)
}

interface Trackable{
    @EVENT_NAME val eventName: String
    val properties: Map<String, String>?
}

sealed class Event : Trackable {
    data class EventTrack(
        @EVENT_NAME override val eventName: String,
        override val properties: Map<String, String>? = null
    ) : Event(){
        companion object
    }

    data class CrashTrack(
        @EVENT_NAME override val eventName: String,
        override val properties: Map<String, String>? = null
    ) : Event(){
        companion object
    }

}


@Retention(AnnotationRetention.SOURCE)
@StringDef(PAGE_VIEW, EXCEPTION, STACK_TRACE)
annotation class EVENT_NAME {
    companion object {
        const val PAGE_VIEW = "pageView"
        const val EXCEPTION = "exception"
        const val STACK_TRACE = "stack_trace"
    }

}