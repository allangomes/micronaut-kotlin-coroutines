package com.allangomes.micronaut.routes

import com.allangomes.micronaut.services.GreetingService
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.micronaut.ktor.KtorApplicationBuilder
import javax.inject.Singleton

@Singleton
class GreetingRoutes(val greetingService: GreetingService) : KtorApplicationBuilder({

    routing {

        get("/hello/{name}") {
            val r = greetingService.sayHi(call.parameters["name"].toString())
            call.respond(r)
        }

    }
})
