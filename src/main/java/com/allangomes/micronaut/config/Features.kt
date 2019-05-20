package com.allangomes.micronaut.config

import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.micronaut.ktor.KtorApplicationBuilder
import java.text.DateFormat
import javax.inject.Singleton

@Singleton
class Features : KtorApplicationBuilder({
    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
    }
})
