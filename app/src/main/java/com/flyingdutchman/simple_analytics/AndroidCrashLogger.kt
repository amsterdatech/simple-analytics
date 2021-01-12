package com.flyingdutchman.simple_analytics

import android.content.Context
import android.text.TextUtils
import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer
import java.util.*

/**
 * https://github.com/ACRA/acra
 * https://www.bugsnag.com/blog/error-handling-on-android-part-7
 * https://www.bugsnag.com/blog/error-handling-on-android-part-6
 * https://www.bugsnag.com/blog/error-handling-on-android-part-5
 * https://www.bugsnag.com/blog/error-handling-on-android-part-4
 * https://www.bugsnag.com/blog/error-handling-on-android-part-3
 * https://www.bugsnag.com/blog/error-handling-on-android-part-2
 * https://www.bugsnag.com/blog/error-handling-on-android-part-1

 */
class AndroidCrashLogger(private val context: Context) : CrashReporter,
    Thread.UncaughtExceptionHandler {
    private val TAG = this::class.java.simpleName

    private fun tag(): String =
        "${context.applicationInfo.name}:${context.applicationInfo.packageName}:$TAG"


    private var defaultHandler: Thread.UncaughtExceptionHandler? =
        Thread.getDefaultUncaughtExceptionHandler()

    override fun simulateCrash() {
        //TODO Here you can throw a RuntimeException or use a related method of your crash report SDK
    }

    override fun isAnalyticsEnabled(): Boolean = true

    override fun enableAnalytics(enabled: Boolean) {}

    override fun logLevel(logLevel: Int) {}

    override fun start() {
        wrapDefaultExceptionHandler()
    }

    override fun apiKey(): String {
        val apiKey = UUID.randomUUID()
        Log.d(TAG, "${tag()} generate an random uuid as apiKey: $apiKey")
        return apiKey.toString()
    }

    override fun trackEvent(eventTrack: Event) {
        when (eventTrack) {
            is Event.CrashTrack -> {
                Log.e(TAG, "${tag()} Report crash: ${eventTrack.eventName}")
            }
            else -> {
                //TODO You can choose what kind of events you want to be aware about
            }
        }
    }

    /**
     * You can report it using trackEvent or do your own logic, but remember, you are running in the Main Thread.
     * Besides, you shouldn't rely on heavy libraries like RxJava, but Coroutines or Executors are easy to follow as well
     */
    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        try {
            analytics(context)
                .track {
                    name = EventName.EXCEPTION
                    EventName.STACK_TRACE to extractStackTraceInfo(throwable)
                    EventName.MESSAGE to throwable.message.toString()
                    EventName.CAUSE to throwable.cause.toString()
                }
        } finally {
            /**
             * Dispatch to default Android handler to show system dialog or finish the app (System.exit(0) or Activity.finish())
             */
            defaultHandler?.uncaughtException(thread, throwable)
        }
    }

    private fun extractStackTraceInfo(throwable: Throwable): String {
        val stackTraceElements = throwable.stackTrace
        val res = StringBuilder()
        for (e in stackTraceElements) {
            res.append(EventName.PROPERTY_FILE, e.fileName)
            res.append(EventName.PROPERTY_METHOD, e.methodName)
            res.append(EventName.PROPERTY_LINE_NUMBER, e.lineNumber.toString())
        }
        return res.toString()
    }

    private fun wrapDefaultExceptionHandler() {
        context.applicationContext.mainLooper.thread.uncaughtExceptionHandler = this
    }

    private fun extractStackTrace(msg: String?, th: Throwable?): String {
        val result: Writer = StringWriter()
        PrintWriter(result).use { printWriter ->
            if (msg != null && !TextUtils.isEmpty(msg)) {
                printWriter.println(msg)
            }
            th?.printStackTrace(printWriter)
            return result.toString()
        }
    }
}