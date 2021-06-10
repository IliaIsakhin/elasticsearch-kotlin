package ilia.isakhin.es.async.service

import ilia.isakhin.es.async.client.EsSearchClient
import ilia.isakhin.es.async.loggerFor
import ilia.isakhin.es.async.config.EsProperties
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.action.index.IndexRequest
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

@Service
class EsDataGenerator(private val esSearchClient: EsSearchClient, esProperties: EsProperties) {

    private val indexName = esProperties.indexName

    fun generateRandomData(size: Int) {
        val chunks = splitChunks(size)
        val r = ThreadLocalRandom.current()
        
        chunks.forEach {
            val bulk = BulkRequest()
            repeat(it) {
                bulk.add(IndexRequest(indexName).apply {
                    source(
                        mapOf(
                            "name" to UUID.randomUUID().toString(),
                            "date" to LocalDate.of(r.nextInt(2000), r.nextInt(1, 12), r.nextInt(1, 28)),
                            "count" to r.nextInt(1000),
                        )
                    )
                })
            }
            
            val result = esSearchClient.bulkInsert(bulk)
            log.info("Inserted ${result.items.size} items")
        }
    }

    private fun splitChunks(size: Int): List<Int> {
        val result = mutableListOf<Int>()
        var temp = size

        while (temp > BULK_SIZE) {
            temp -= BULK_SIZE
            result += BULK_SIZE
        }
        result += temp

        return result
    }

    companion object {
        private const val BULK_SIZE = 1_000
        
        private val log = loggerFor<EsDataGenerator>()
    }
}
