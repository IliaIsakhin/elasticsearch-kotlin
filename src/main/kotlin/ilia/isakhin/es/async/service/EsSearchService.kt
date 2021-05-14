package ilia.isakhin.es.async.service

import ilia.isakhin.es.async.client.EsSearchClient
import ilia.isakhin.es.async.prop.EsProperties
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.asyncsearch.SubmitAsyncSearchRequest
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class EsSearchService(private val esSearchClient: EsSearchClient, esProperties: EsProperties) {
    
    private val indexName = esProperties.indexName
    
    suspend fun searchAsync(term: String): Mono<List<String>> {
        val sourceBuilder = generateSourceBuilder(term)
        val request = SubmitAsyncSearchRequest(sourceBuilder, indexName).apply {
            requestCache = false
        }

        return esSearchClient.searchAsyncEs(request)
            .map(mapResults)
    }

    fun search(term: String): List<String> {
        val sourceBuilder = generateSourceBuilder(term)
        val request = SearchRequest(arrayOf(indexName), sourceBuilder).requestCache(false)

        return mapResults(esSearchClient.search(request))
    }

    private val mapResults: (t: SearchResponse) -> List<String> = { response -> response.hits.hits.map { it.id } }

    private fun generateSourceBuilder(term: String) = SearchSourceBuilder()
        .apply {
            query(QueryBuilders.matchQuery("name", term).fuzziness(2))
            size(10_000)
        }
}
