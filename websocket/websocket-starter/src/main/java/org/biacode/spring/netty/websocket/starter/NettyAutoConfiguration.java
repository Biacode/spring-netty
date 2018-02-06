package org.biacode.spring.netty.websocket.starter;

import org.biacode.spring.netty.core.annotation.NettyController;
import org.biacode.spring.netty.websocket.starter.configuration.NettyAnnotationDrivenConfiguration;
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
@ConditionalOnClass(NettyController.class)
@EnableConfigurationProperties(NettyProperties.class)
@Import(NettyAnnotationDrivenConfiguration.class)
public class NettyAutoConfiguration {
}
