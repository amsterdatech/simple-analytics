package com.flyingdutchman.simple_analytics

open class Holder<out T : Any, Context, AnalyticsBuilder>(creator: (Context, AnalyticsBuilder) -> T) {
    private var creator: ((Context, AnalyticsBuilder) -> T)? = creator
    @Volatile
    private var instance: T? = null


    fun with(context: Context, builder: AnalyticsBuilder): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(context, builder)
                instance = created
                creator = null
                created
            }
        }
    }
}
