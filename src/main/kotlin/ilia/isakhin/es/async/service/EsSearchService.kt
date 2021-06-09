package ilia.isakhin.es.async.service

import ilia.isakhin.es.async.client.EsSearchClient
import ilia.isakhin.es.async.config.EsProperties
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.asyncsearch.SubmitAsyncSearchRequest
import org.elasticsearch.common.unit.Fuzziness
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.stereotype.Service

@Service
class EsSearchService(private val esSearchClient: EsSearchClient, esProperties: EsProperties) {

    private val indexName = esProperties.indexName

    suspend fun search(term: String): List<String> {
        val sourceBuilder = generateSourceBuilder(term)
        val searchRequest = SubmitAsyncSearchRequest(sourceBuilder, indexName).apply {
            requestCache = false
        }
        return esSearchClient.search(searchRequest)
            .run { mapResults(this) }
    }

    private val mapResults: (t: SearchResponse) -> List<String> = { response -> response.hits.hits.map { it.id } }

    private fun generateSourceBuilder(term: String) = SearchSourceBuilder()
        .apply {
            query(QueryBuilders.matchQuery("name", term).fuzziness(Fuzziness.ONE))
            size(10_000)
        }
}
