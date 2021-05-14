package ilia.isakhin.es.async

import ilia.isakhin.es.async.controller.SearchController
import ilia.isakhin.es.async.service.EsSearchService
import org.elasticsearch.action.ActionListener
import org.elasticsearch.action.ActionResponse
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.asyncsearch.AsyncSearchResponse
import org.elasticsearch.client.asyncsearch.SubmitAsyncSearchRequest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.internal.stubbing.answers.AnswersWithDelay
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(SpringExtension::class)
@WebFluxTest(controllers = [SearchController::class])
@Import(EsSearchService::class)
class SpringAppTest {

    @MockBean
    private lateinit var client: RestHighLevelClient

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun testAsyncSearch() {
        `when`(
            client.asyncSearch().submitAsync(
                any(SubmitAsyncSearchRequest::class.java),
                any(RequestOptions::class.java),
                any(ActionListener::class.java) as ActionListener<AsyncSearchResponse>?
            )
        ).thenAnswer {
            AnswersWithDelay(3_000) { ActionResponse.Empty.INSTANCE }
        }

        webTestClient.post()
            .uri("/create")
            .contentType(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
    }
}
