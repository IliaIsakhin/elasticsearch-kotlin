package ilia.isakhin.es.async.listener

import ilia.isakhin.es.async.service.EsDataGenerator
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class FillElasticsearchListener(private val esDataGenerator: EsDataGenerator) {

    @EventListener(value = [ApplicationReadyEvent::class])
    fun onReady() {
        // esDataGenerator.generateRandomData(10_000)
    }
}
