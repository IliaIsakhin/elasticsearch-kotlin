package ilia.isakhin.es.async

import ilia.isakhin.es.async.prop.EsProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties

@SpringBootApplication
@EnableConfigurationProperties(EsProperties::class)
class SpringApp

fun main() {
     SpringApplication.run(SpringApp::class.java)
}

inline fun <reified T:Any> loggerFor(): Logger = LoggerFactory.getLogger(T::class.java)
