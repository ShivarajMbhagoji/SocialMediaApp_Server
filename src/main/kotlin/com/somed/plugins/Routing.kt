package com.somed.plugins

import com.somed.route.authRouting
import com.somed.route.followsRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        authRouting()
        followsRouting()
    }
}
