package org.biacode.hermes.spring.netty.websocket.configuration;

import org.biacode.hermes.spring.netty.server.configuration.ServerAnnotationDrivenConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * Created by Arthur Asatryan.
 * Date: 2/1/18
 * Time: 4:48 PM
 */
@ComponentScan("org.biacode.hermes.spring.netty.websocket")
@Import(ServerAnnotationDrivenConfiguration.class)
public class WebsocketAnnotationDrivenConfiguration {
}
