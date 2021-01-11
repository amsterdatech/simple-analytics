package com.flyingdutchman.simple_analytics

import android.content.Context

interface Analytics {
    fun track(setup: EventBuilder.() -> Unit = {})
}

class AnalyticsImpl(
    private val context: Context,
    private val trackers: MutableList<Tracker>
) : Analytics {

    init {
        trackers.forEach {
            if (it.isAnalyticsEnabled()) {
                it.start()
            }
        }
    }

    override fun track(setup: EventBuilder.() -> Unit) {
        val eventBuilder = EventBuilder()
        eventBuilder.setup()

        val event = eventBuilder.build()

        event?.let { event ->
            if (event is EventTrack) {
                trackers.forEach { tracker: Tracker ->
                    tracker.trackEvent(event)
                }
            }
        }
    }
}