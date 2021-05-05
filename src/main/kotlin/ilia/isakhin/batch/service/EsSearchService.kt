package ilia.isakhin.batch.service

import kotlinx.coroutines.reactor.mono
import org.elasticsearch.action.ActionListener
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.AsyncSearchClient
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.asyncsearch.AsyncSearchResponse
import org.elasticsearch.client.asyncsearch.SubmitAsyncSearchRequest
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Service
class EsSearchService(private val client: RestHighLevelClient) {

    suspend fun searchAsyncEs(request: SubmitAsyncSearchRequest): Mono<SearchResponse> =
        mono {
            suspendCoroutine { continuation ->
                val callback = object : ActionListener<AsyncSearchResponse> {
                    override fun onResponse(response: AsyncSearchResponse) =
                        continuation.resume(response.searchResponse)

                    override fun onFailure(ex: Exception) = throw ex
                }
                client.asyncSearch().submitAsync(request, RequestOptions.DEFAULT, callback, 3_000)
            }
        }

    fun search(request: SearchRequest): SearchResponse {
        Thread.sleep(3_000)
        return client.search(request, RequestOptions.DEFAULT)
    }
}

fun AsyncSearchClient.submitAsync(request: SubmitAsyncSearchRequest, options: RequestOptions,
                                  listener: ActionListener<AsyncSearchResponse>, timeToSleep: Long) {
    Thread.sleep(timeToSleep)
    this.submitAsync(request, options, listener)
}