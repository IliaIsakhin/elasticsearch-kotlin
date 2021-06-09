package ilia.isakhin.es.async.config

import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EsConfig {

    @Bean
    fun esClient(properties: EsProperties) =
        RestHighLevelClient(
            RestClient.builder(
                HttpHost(properties.host, properties.port.toInt(), "http")
            )
        )
}
