package ilia.isakhin.es.async.client

import org.elasticsearch.action.ActionListener
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.action.bulk.BulkResponse
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.asyncsearch.AsyncSearchResponse
import org.elasticsearch.client.asyncsearch.SubmitAsyncSearchRequest
import org.springframework.stereotype.Service
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Service
class EsSearchClient(private val client: RestHighLevelClient) {

    suspend fun search(request: SubmitAsyncSearchRequest) =
        suspendCoroutine<SearchResponse> { continuation ->
            val callback = AsyncSearchResponseActionListener(continuation)

            client.asyncSearch().submitAsync(request, RequestOptions.DEFAULT, callback)
        }

    fun bulkInsert(bulk: BulkRequest): BulkResponse = client.bulk(bulk, RequestOptions.DEFAULT)
}

class AsyncSearchResponseActionListener(private val continuation: Continuation<SearchResponse>) :
    ActionListener<AsyncSearchResponse> {
    override fun onResponse(response: AsyncSearchResponse) = continuation.resume(response.searchResponse)

    override fun onFailure(ex: Exception) = continuation.resumeWithException(ex)
}
