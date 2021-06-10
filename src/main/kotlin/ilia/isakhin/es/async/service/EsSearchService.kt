package ilia.isakhin.es.async.service

import ilia.isakhin.es.async.client.EsSearchClient
import ilia.isakhin.es.async.config.EsProperties
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.aggregations.AggregationBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.stereotype.Service

@Service
class EsSearchService(private val esSearchClient: EsSearchClient, esProperties: EsProperties) {

    private val indexNames = arrayOf(esProperties.indexName)

    fun search(term: String): List<String> {
        val sourceBuilder = SearchSourceBuilder().apply {
            query(QueryBuilders.wildcardQuery("name", term))
            aggregation(AggregationBuilders.stats("agg").field("count"))
            size(10_000)
        }
        val searchRequest = SearchRequest(indexNames, sourceBuilder).apply {
            requestCache(false)
        }
        return esSearchClient.search(searchRequest)
            .let { mapResults(it) }
    }

    private fun mapResults(response: SearchResponse): List<String> = response.hits.hits.map { it.id }
}
