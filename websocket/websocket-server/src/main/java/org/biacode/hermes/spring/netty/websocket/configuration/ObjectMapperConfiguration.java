package org.biacode.hermes.spring.netty.websocket.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Arthur Asatryan.
 * Date: 1/31/18
 * Time: 4:00 PM
 */
@Configuration
public class ObjectMapperConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectMapperConfiguration.class);

    //region Public methods
    @Bean
    public ObjectMapper objectMapper() {
        LOGGER.debug("Creating jackson object mapper.");
        return new ObjectMapper();
    }
    //endregion
}
