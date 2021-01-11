package com.flyingdutchman.simple_analytics

annotation class DslAnalytics

@DslAnalytics
open class EventBuilder(var name: String? = null) {
    var data: MutableMap<String, String>? = null

    private fun initData() {
        if (data == null) {
            data = mutableMapOf()
        }
    }

    fun put(key: String, value: String): EventBuilder {
        initData()
        data?.let {
            if (!it.containsKey(key)) {
                it[key] = value
            }
        }
        return this@EventBuilder
    }

    fun build(): Trackable? {
        if (name.isNullOrBlank()) throw IllegalArgumentException("Events should contain a name look at DISPLAY/EVENT annotation class")
        name?.let { return EventTrack(it, data) }
        return null
    }
}