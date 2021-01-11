package com.flyingdutchman.simple_analytics

interface CrashReporter : Tracker {
    fun simulateCrash()
}