package org.example.project

import java.awt.Desktop
import java.net.URI

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
    //override val name: String = "Desktop"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun signInwithGoogle() {
    if (Desktop.isDesktopSupported()) {
        Desktop.getDesktop().browse(URI("https://www.google.com/"))

    } else {
        println("Desktop not supported")
    }
}