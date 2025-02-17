package io.heapy.kotbot.web

import io.heapy.kotbot.web.routes.events
import io.heapy.kotbot.web.routes.health
import io.heapy.kotbot.web.routes.metrics
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.routing.routing

interface Server {
    fun start()
}

class KtorServer(
    private val metricsScrapper: () -> String,
) : Server {
    override fun start() {
        val server = embeddedServer(CIO, port = 8080) {
            JSON()

            routing {
                events()
                health()
                metrics(metricsScrapper)
            }
        }

        server.start(false)
    }
}
