package ilia.isakhin.es.async.service

import ilia.isakhin.es.async.client.EsSearchClient
import ilia.isakhin.es.async.loggerFor
import ilia.isakhin.es.async.prop.EsProperties
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.action.index.IndexRequest
import org.springframework.stereotype.Service

@Service
class EsDataGenerator(private val esSearchClient: EsSearchClient, esProperties: EsProperties) {

    private val indexName = esProperties.indexName
    private val clientNames = esProperties.clientNames

    fun generateRandomData(size: Int) {
        val chunks = splitChunks(size)

        chunks.forEach {
            val bulk = BulkRequest()
            repeat(it) {
                bulk.add(IndexRequest(indexName).apply {
                    source(mapOf("name" to clientNames.random()))
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
        private const val BULK_SIZE = 100
        
        private val log = loggerFor<EsDataGenerator>() 
    }
}
