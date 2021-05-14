package ilia.isakhin.es.async.prop

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("es.index")
class EsProperties {
    
    lateinit var indexName: String
    lateinit var clientNames: List<String>
}
