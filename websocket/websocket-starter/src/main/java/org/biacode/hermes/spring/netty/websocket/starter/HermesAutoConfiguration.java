package org.biacode.hermes.spring.netty.websocket.starter;

import org.biacode.hermes.spring.netty.core.annotation.HermesController;
import org.biacode.hermes.spring.netty.websocket.starter.configuration.HermesAnnotationDrivenConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Arthur Asatryan.
 * Date: 1/28/18
 * Time: 8:02 PM
 */
@Configuration
@ConditionalOnClass(HermesController.class)
@EnableConfigurationProperties(NettyProperties.class)
@Import(HermesAnnotationDrivenConfiguration.class)
public class HermesAutoConfiguration {
}
