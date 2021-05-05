package ilia.isakhin.batch

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
//@EnableBatchProcessing
class SpringApp

fun main() {
    val context = SpringApplication.run(SpringApp::class.java)
//    val jobLauncher = context.getBean(JobLauncher::class.java)
//    val job = context.getBean(Job::class.java)
//    println("Starting the batch job")
//    try {
//        val execution = jobLauncher.run(job, JobParameters())
//        println("Job Status : ${execution.status}")
//    } catch (e: Exception) {
//        e.printStackTrace()
//        println("Job failed ${e.message}")
//    }



//    context.close()
}

inline fun <reified T:Any> loggerFor(): Logger = LoggerFactory.getLogger(T::class.java)
