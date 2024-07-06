package com.somed.route

import com.somed.model.AuthResponse
import com.somed.model.SignInParams
import com.somed.model.SignUpParams
import com.somed.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.authRouting(){
    val repository by inject<UserRepository>()

    route("signup"){
        post {
            val params=call.receiveNullable<SignUpParams>()

            if (params == null){
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = AuthResponse(
                        errorMessage = "Invalid credentials!"
                    )
                )

                return@post
            }

            val result=repository.signUp(params)

            call.respond(
                status = result.code,
                message = result.data
            )
        }
    }


    route("login"){
        post {
            val params=call.receiveNullable<SignInParams>()

            if (params == null){
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = AuthResponse(
                        errorMessage = "Invalid credentials!"
                    )
                )

                return@post
            }

            val result=repository.signIn(params)

            call.respond(
                status = result.code,
                message = result.data
            )
        }
    }
}