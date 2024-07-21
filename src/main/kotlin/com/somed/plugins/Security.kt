package com.somed.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.somed.model.AuthResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import java.util.*


private val jwtAudience = System.getenv("jwt.audience").toString()
private val jwtIssuer = System.getenv("jwt.domain").toString()
private val jwtSecret = System.getenv("jwt.secret").toString()

private const val CLAIM = "email"

val myRealm = System.getenv("jwt.realm").toString()

fun Application.configureSecurity() {

    install(Authentication) {
        jwt {

            realm= myRealm

            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtIssuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim(CLAIM).asString() != ""){
                    JWTPrincipal(payload = credential.payload)
                }else{
                    null
                }
            }

            challenge { defaultScheme, realm ->
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = AuthResponse(
                        errorMessage = "Token is not valid or has expired"
                    )
                )
            }
        }
    }
}

fun generateToken(email: String): String{
    return JWT.create()
        .withAudience(jwtAudience)
        .withIssuer(jwtIssuer)
        .withClaim(CLAIM, email)
        .withExpiresAt(Date(System.currentTimeMillis() + 60000))
        .sign(Algorithm.HMAC256(jwtSecret))
}