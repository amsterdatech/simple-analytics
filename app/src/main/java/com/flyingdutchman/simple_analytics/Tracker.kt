package com.flyingdutchman.simple_analytics

import androidx.annotation.StringDef
import com.flyingdutchman.simple_analytics.EventName.Companion.CAUSE
import com.flyingdutchman.simple_analytics.EventName.Companion.EXCEPTION
import com.flyingdutchman.simple_analytics.EventName.Companion.MESSAGE
import com.flyingdutchman.simple_analytics.EventName.Companion.PAGE_VIEW
import com.flyingdutchman.simple_analytics.EventName.Companion.PROPERTY_COLUMN_NUMBER
import com.flyingdutchman.simple_analytics.EventName.Companion.PROPERTY_FILE
import com.flyingdutchman.simple_analytics.EventName.Companion.PROPERTY_LINE_NUMBER
import com.flyingdutchman.simple_analytics.EventName.Companion.PROPERTY_METHOD
import com.flyingdutchman.simple_analytics.EventName.Companion.STACK_TRACE

interface Tracker {

    fun isAnalyticsEnabled(): Boolean

    fun enableAnalytics(enabled: Boolean)

    fun logLevel(logLevel: Int)

    fun start()

    fun apiKey(): String

    fun trackEvent(eventTrack: Event)
}

interface Trackable {
    @EventName
    val eventName: String
    val properties: Map<String, String>?
}

sealed class Event : Trackable {
    data class EventTrack(
        @EventName override val eventName: String,
        override val properties: Map<String, String>? = null
    ) : Event() {
        companion object
    }

    data class CrashTrack(
        @EventName override val eventName: String,
        override val properties: Map<String, String>? = null
    ) : Event() {
        companion object
    }

}


@Retention(AnnotationRetention.SOURCE)
@StringDef(
    PAGE_VIEW, EXCEPTION, STACK_TRACE, PROPERTY_FILE, PROPERTY_LINE_NUMBER,
    PROPERTY_COLUMN_NUMBER, PROPERTY_METHOD, CAUSE, MESSAGE
)
annotation class EventName {
    companion object {
        const val PAGE_VIEW = "pageView"
        const val EXCEPTION = "exception"
        const val STACK_TRACE = "stack_trace"
        const val CAUSE = "cause"
        const val MESSAGE = "message"

        const val PROPERTY_FILE = "file"
        const val PROPERTY_LINE_NUMBER = "lineNumber"
        const val PROPERTY_COLUMN_NUMBER = "columnNumber"
        const val PROPERTY_METHOD = "method"


        /**
         * {
        "stacktrace":[
        {
        "file":"MainActivity.kt",
        "lineNumber":56,
        "columnNumber":23,
        "method":"com.example.MainActivity.onCreate"
        }
        ]
        }
         */
    }
}