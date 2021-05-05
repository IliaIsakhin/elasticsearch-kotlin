package ilia.isakhin.batch.configuration

import ilia.isakhin.batch.dto.Record
import ilia.isakhin.batch.loggerFor
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemReader
import org.springframework.stereotype.Component
import java.util.UUID
import java.util.concurrent.CopyOnWriteArrayList

open class EsReader: ItemReader<Record> {

    private val id = UUID.randomUUID().toString()
    private val data = mutableListOf<Record>().apply {
        repeat(100) {
            add(Record(UUID.randomUUID().toString()))
        }
    }

    init {
        logger.info("EsReader has been created with id $id")
    }

    private val iterator = data.iterator()

    @Synchronized
    override fun read(): Record? {
        while (iterator.hasNext()) {
            logger.info("Read from $id.")
            return iterator.next()
        }

        return null
    }

    companion object {
        private val logger = loggerFor<EsReader>()
    }
}