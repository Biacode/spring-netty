package org.biacode.spring.netty.websocket.starter.configuration;

import org.biacode.spring.netty.websocket.configuration.WebsocketAnnotationDrivenConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Arthur Asatryan.
 * Date: 1/31/18
 * Time: 3:54 PM
 */
@Configuration
@ComponentScan("org.biacode.spring.netty.websocket.starter")
@Import(WebsocketAnnotationDrivenConfiguration.class)
public class NettyAnnotationDrivenConfiguration {
}
