package com.flyingdutchman.simple_analytics

import android.content.Context

interface Analytics {
    fun track(setup: EventBuilder.() -> Unit = {})

    companion object :
        Holder<Analytics, Context, AnalyticsBuilder.() -> Unit>(
            { context: Context, builder: AnalyticsBuilder.() -> Unit ->
                AnalyticsBuilder(context).apply(builder).build()
            }
        ) {
        val TAG = AnalyticsImpl::class.java.simpleName
    }
}

class AnalyticsImpl(private val trackers: MutableList<Tracker>) : Analytics {
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
            if (event is Event) {
                trackers.forEach { tracker: Tracker ->
                    tracker.trackEvent(event)
                }
            }
        }
    }
}