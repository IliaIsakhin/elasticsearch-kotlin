package ilia.isakhin.batch.configuration

import ilia.isakhin.batch.dto.Record
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.partition.support.SimplePartitioner
import org.springframework.batch.item.file.transform.LineAggregator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@Configuration
class BatchConfig {

    @Bean
    fun lineAggregator(): LineAggregator<Record> = LineAggregator<Record> {
        it.toString()
    }

//    @Bean
    fun partitionerJob(jobBuilderFactory: JobBuilderFactory, partitionStep: Step): Job? {
        return jobBuilderFactory.get("partitioningJob")
            .incrementer(RunIdIncrementer())
            .start(partitionStep)
            .build()
    }

//    @Bean
    fun partitionStep(stepBuilderFactory: StepBuilderFactory, slaveStep: Step, taskExecutor: TaskExecutor): Step? {
        return stepBuilderFactory.get("partitionStep")
            .partitioner(slaveStep)
            .partitioner("slaveStep", SimplePartitioner())
            .taskExecutor(taskExecutor)
            .gridSize(3)
            .build()
    }

//    @Bean
    fun slaveStep(
        stepBuilderFactory: StepBuilderFactory,
        esReader: EsReader,
        esWriter: EsWriter,
        noOpProcessor: NoOpProcessor
    ): Step? {
        return stepBuilderFactory.get("slaveStep")
            .chunk<Record, Record>(10)
            .reader(esReader)
            .processor(noOpProcessor)
            .writer(esWriter)
            .build()
    }

//    @Bean
    fun taskExecutor(): TaskExecutor? {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 3
        executor.maxPoolSize = 3
        executor.setQueueCapacity(30)
        return executor
    }

    @Bean
    fun esReader() = EsReader()
}