package org.example.project

class WasmPlatform: Platform {
    //override val name: String = "Web with Kotlin/Wasm"
    override val name: String = "Web"
}

actual fun getPlatform(): Platform = WasmPlatform()
actual fun signInwithGoogle() {
    js("window.open('https://example.com', '_blank')")
}