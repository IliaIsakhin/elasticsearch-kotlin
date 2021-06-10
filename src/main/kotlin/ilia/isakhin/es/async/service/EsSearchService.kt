package ilia.isakhin.es.async.service

import ilia.isakhin.es.async.client.EsSearchClient
import ilia.isakhin.es.async.config.EsProperties
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.asyncsearch.SubmitAsyncSearchRequest
import org.elasticsearch.common.unit.Fuzziness
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.aggregations.AggregationBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.stereotype.Service

@Service
class EsSearchService(private val esSearchClient: EsSearchClient, esProperties: EsProperties) {

    private val indexNames = esProperties.indexName

    suspend fun search(term: String): List<String> {
        val sourceBuilder = SearchSourceBuilder().apply {
            query(QueryBuilders.termQuery("name", term))
            size(10_000)
        }
        val searchRequest = SubmitAsyncSearchRequest(sourceBuilder, indexNames).apply {
            requestCache = false
        }
        return esSearchClient.search(searchRequest)
            .run { mapResults(this) }
    }

    private val mapResults: (t: SearchResponse) -> List<String> = { response -> response.hits.hits.map { it.id } }
}
