package ilia.isakhin.batch.configuration

import ilia.isakhin.batch.dto.Record
import ilia.isakhin.batch.loggerFor
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.file.FlatFileItemWriter
import org.springframework.batch.item.file.transform.LineAggregator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import java.util.UUID
import javax.annotation.PostConstruct

@Component
class EsWriter(): ItemWriter<Record>{//: FlatFileItemWriter<Record>() {

    private val id = UUID.randomUUID().toString()

    init {
        logger.info("EsWriter has been created with id $id")
//        setResource(ClassPathResource(filename))
    }

//    @Autowired
//    override fun setLineAggregator(lineAggregator: LineAggregator<Record>) {
//        super.setLineAggregator(lineAggregator)
//    }

    override fun write(items: MutableList<out Record>) {
        items.forEach {
            logger.info("Item processed writer with id $id")
        }
    }

    companion object {
        private val logger = loggerFor<EsWriter>()
    }
}