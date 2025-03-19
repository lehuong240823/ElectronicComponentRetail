package org.example.project

import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    //override val name: String = "Android"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun signInWithGoogle(): String {
    return ""
}

actual fun getPageSize(): Int {
    return ANDROID_PAGE_SIZE
}

