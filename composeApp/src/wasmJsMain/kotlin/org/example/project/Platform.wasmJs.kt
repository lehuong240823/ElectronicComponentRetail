package org.example.project

import kotlinx.browser.window

class WasmPlatform: Platform {
    //override val name: String = "Web with Kotlin/Wasm"
    override val name: String = "Web"
}

actual fun getPlatform(): Platform = WasmPlatform()

actual fun signInWithGoogle(): String {
    window.location.href = getGoogleAuthUrl(WEB_REDIRECT_URL)
    val urlParams = window.location.search
    val params = urlParams.substring(1).split("&").associate {
        val (key, value) = it.split("=")
        key to value
    }
    println(params["code"]!!)
    return params["code"]!!
}

actual fun getPageSize(): Int {
    return WEB_PAGE_SIZE
}