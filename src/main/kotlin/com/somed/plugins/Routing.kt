package com.somed.plugins

import com.somed.route.authRouting
import com.somed.route.followsRouting
import com.somed.route.postRouting
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        authRouting()
        followsRouting()
        postRouting()
        static {
            resources("static")
        }
    }
}
