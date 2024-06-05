package com.somed.di

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun Application.configDI(){
    install(Koin){
        modules(appModule)
    }
}