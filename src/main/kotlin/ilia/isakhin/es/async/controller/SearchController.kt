package ilia.isakhin.es.async.controller

import ilia.isakhin.es.async.service.EsSearchService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity

@RestController
class SearchController(private val searchService: EsSearchService) {

    @GetMapping("/search")
    suspend fun search(@RequestParam("query") query: String) = with(ResponseEntity.ok()) {
        contentType(MediaType.APPLICATION_JSON)
        body(searchService.search(query))
    }
}
