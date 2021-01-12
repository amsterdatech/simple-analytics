package com.flyingdutchman.simple_analytics

import android.content.Context

annotation class DslAnalytics

@DslAnalytics
open class EventBuilder(var name: String? = null) {
    val data: MutableMap<String, String>? by lazy {
        mutableMapOf<String, String>()
    }

    inline fun String.to(value: String) {
        data?.let {
            if (!it.containsKey(this)) {
                it[this] = value
            }
        }
    }

    fun put(key: String, value: String): EventBuilder {
        data?.let {
            if (!it.containsKey(key)) {
                it[key] = value
            }
        }
        return this@EventBuilder
    }

    fun build(): Trackable? {
        if (name.isNullOrBlank()) throw IllegalArgumentException("Events should contain a name look at DISPLAY/EVENT annotation class")
        name?.let {
            return when (it) {
                EventName.PAGE_VIEW -> Event.EventTrack(it, data)
                EventName.EXCEPTION -> Event.CrashTrack(it, data)
                else -> {
                    null
                }
            }
        }
        return null
    }
}

fun analytics(context: Context, initializer: AnalyticsBuilder.() -> Unit = {}): Analytics =
    Analytics.with(context, initializer)

fun analytics(context: Context): Analytics = Analytics.with(context) {

}

@DslAnalytics
class AnalyticsBuilder(val context: Context) {

    private val trackers = mutableListOf<Tracker>()

    fun kit(tracker: Tracker) = apply {
        trackers += tracker
    }

    fun build(): Analytics {
        return AnalyticsImpl(trackers = trackers)
    }
}
