package com.allangomes.micronaut

import io.ktor.server.netty.NettyApplicationEngine
import io.micronaut.ktor.KtorApplication
import io.micronaut.runtime.Micronaut
import org.slf4j.LoggerFactory
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class Application : KtorApplication<NettyApplicationEngine.Configuration>({

    applicationEngineEnvironment {
        log = LoggerFactory.getLogger(Application::class.java)
    }

    applicationEngine {
        connectionGroupSize = 1
        workerGroupSize = 1
        callGroupSize = 1
    }
}) {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            CoroutineContext::class.java.genericInterfaces
            Micronaut.run(Application::class.java, *args)
        }

    }

}