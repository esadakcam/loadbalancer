package com.akcam.loadbalancer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(LoadBalancerConfiguration::class)
class LoadbalancerApplication

fun main(args: Array<String>) {
	runApplication<LoadbalancerApplication>(*args)
}
