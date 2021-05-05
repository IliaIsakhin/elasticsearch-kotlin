package ilia.isakhin.batch.configuration

import ilia.isakhin.batch.dto.Record
import ilia.isakhin.batch.loggerFor
import org.slf4j.LoggerFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class NoOpProcessor: ItemProcessor<Record, Record> {

    private val id = UUID.randomUUID().toString()

    init {
        logger.info("Processor has been created with id $id")
    }

    override fun process(item: Record): Record {
        Thread.sleep(100)
        return item
    }

    companion object {
        private val logger = loggerFor<NoOpProcessor>()
    }
}