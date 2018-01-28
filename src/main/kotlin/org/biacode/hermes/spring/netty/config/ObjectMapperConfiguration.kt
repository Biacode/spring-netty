package org.biacode.hermes.spring.netty.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by Arthur Asatryan.
 * Date: 1/23/18
 * Time: 11:01 PM
 */
@Configuration
class ObjectMapperConfiguration {

    //region Beans
    @Bean
    fun objectMapper(): ObjectMapper {
        logger.debug("Creating object mapper.")
        return ObjectMapper()
    }
    //endregion

    //region Companion object
    companion object {
        private val logger = LoggerFactory.getLogger(ObjectMapperConfiguration::class.java)
    }
    //endregion

}