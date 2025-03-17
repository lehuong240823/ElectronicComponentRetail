package org.example.project

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.project.core.DESKTOP_PAGE_SIZE
import org.example.project.core.DESKTOP_REDIRECT_URL
import org.example.project.core.getGoogleAuthUrl
import java.awt.Desktop
import java.net.URI
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun signInWithGoogle(): String {
    val (server, future) = startLocalServer()

    try {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(URI(getGoogleAuthUrl(DESKTOP_REDIRECT_URL)))
        } else {
            println("Desktop not supported")
        }
        return future.get().also { println(it) }
    } catch (e: Exception) {
        println("Error: ${e.message}")
        return ""
    } finally {
        server.stop(0, 0, TimeUnit.SECONDS)
    }
}

actual fun getPageSize(): Int {
    return DESKTOP_PAGE_SIZE
}

fun startLocalServer(): Pair<EmbeddedServer<NettyApplicationEngine, NettyApplicationEngine.Configuration>, CompletableFuture<String>> {
    val future = CompletableFuture<String>()
    val server = embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) { json() }
        install(StatusPages)
        routing {
            get("/") {
                val authCode = call.request.queryParameters["code"]
                if (authCode != null) {
                    call.respondText("You can close this window now.", ContentType.Text.Html)
                    future.complete(authCode)
                } else {
                    call.respondText("Authorization failed!", ContentType.Text.Plain)
                }
            }
        }
    }.start(wait = false)
    return Pair(server, future)
}
