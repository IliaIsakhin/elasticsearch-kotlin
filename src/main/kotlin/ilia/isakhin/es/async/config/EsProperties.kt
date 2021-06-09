package ilia.isakhin.es.async.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("es.index")
class EsProperties {
    lateinit var host: String
    lateinit var port: String
    lateinit var indexName: String
}
