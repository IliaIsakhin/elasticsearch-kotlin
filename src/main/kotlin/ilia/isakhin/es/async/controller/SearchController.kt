package ilia.isakhin.es.async.controller

import ilia.isakhin.es.async.service.EsSearchService
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/search")
class SearchController(private val searchService: EsSearchService) {

    @GetMapping("/async")
    suspend fun searchAsync(@RequestParam("query") query: String): ResponseEntity<Mono<List<String>>> = ok().body(
        searchService.searchAsync(query)
    )

    @GetMapping("/sync")
    fun search(@RequestParam("query") query: String): ResponseEntity<List<String>> = ok().body(
        searchService.search(query)
    )
}
