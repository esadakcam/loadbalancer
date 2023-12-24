package com.akcam.loadbalancer

import jakarta.annotation.PostConstruct
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

@Component
class TcpServer(private val configuration: LoadBalancerConfiguration) {

    private val counter = AtomicInteger(0)
    private val serverSocket = ServerSocket(configuration.port.toInt())

    @PostConstruct
    fun listen() {
        logger.info("Tcp Server started to listen.")
        while (true) {
            val client = serverSocket.accept()
            logger.info("A client is accepted $client.")
            thread { handleIncomingConnection(client) }
        }
    }

    private fun handleIncomingConnection(incomingConnection: Socket) {

        val backend = configuration.backends[counter.getAndIncrement() % configuration.backends.size]
        logger.info("$backend is selected for request from ${incomingConnection.inetAddress}")

        val outGoingConnection = Socket(backend.ip, backend.port.toInt())
        thread { incomingConnection.getInputStream().transferTo(outGoingConnection.getOutputStream()) }
        thread { outGoingConnection.getInputStream().transferTo(incomingConnection.getOutputStream()) }
    }

    companion object {
        private val logger = LogManager.getLogger()
    }


}