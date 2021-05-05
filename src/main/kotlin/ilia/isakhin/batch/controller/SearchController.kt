package ilia.isakhin.batch.controller

import ilia.isakhin.batch.service.EsSearchService
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.asyncsearch.SubmitAsyncSearchRequest
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class SearchController(private val searchService: EsSearchService) {

    @GetMapping("/async")
    suspend fun searchAsync(): ResponseEntity<Mono<SearchResponse>> = ok().body(
        searchService.searchAsyncEs(
            SubmitAsyncSearchRequest(SearchSourceBuilder().apply { query(QueryBuilders.matchAllQuery()) }, "record")
        )
    )

    @GetMapping("/block")
    fun search(): ResponseEntity<SearchResponse> = ok().body(
        searchService.search(
            SearchRequest("record".apply { SearchSourceBuilder().apply { query(QueryBuilders.matchAllQuery()) } })
        )
    )
}