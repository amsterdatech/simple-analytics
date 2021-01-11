package com.flyingdutchman.simple_analytics

interface Analytics {
    fun track(setup: EventBuilder.() -> Unit = {})
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