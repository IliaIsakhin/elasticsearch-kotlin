package ilia.isakhin.es.async.controller

import ilia.isakhin.es.async.service.EsDataGenerator
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController("/generate")
class GenerateDataController(private val esDataGenerator: EsDataGenerator) {
    
    @PostMapping("/random")
    fun populateData(@RequestParam("size") size: Int) =  esDataGenerator.generateRandomData(size)
}
