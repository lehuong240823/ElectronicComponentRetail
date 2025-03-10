package org.example.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun signInwithGoogle()

fun isWeb(): Boolean {
    return getPlatform().name.contains("Web")
}

fun isDesktop(): Boolean {
    return getPlatform().name.contains("Java")
}

fun isAndroid(): Boolean {
    return getPlatform().name.contains("Android")
}