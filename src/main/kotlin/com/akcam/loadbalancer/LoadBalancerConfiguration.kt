package com.akcam.loadbalancer

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "proxy")
class LoadBalancerConfiguration {
    lateinit var port: Integer
    lateinit var backends: List<BackendProperties>
}

data class BackendProperties(
    var ip: String,
    var port: Integer
)
