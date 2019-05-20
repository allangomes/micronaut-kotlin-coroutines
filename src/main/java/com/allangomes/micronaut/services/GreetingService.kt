package com.allangomes.micronaut.services

import com.allangomes.micronaut.model.Greeting
import kotlinx.coroutines.*

import javax.inject.Singleton

@Singleton
class GreetingService {

    suspend fun sayHi(name: String): Greeting {
        delay(10000)
//        Thread.sleep(10000)
        return Greeting("Hello $name")
    }

}
