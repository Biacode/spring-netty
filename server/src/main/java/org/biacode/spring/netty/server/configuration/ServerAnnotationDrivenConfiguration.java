package org.biacode.spring.netty.server.configuration;

import org.biacode.spring.netty.router.configuration.RouterAnnotationDrivenConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Arthur Asatryan.
 * Date: 2/1/18
 * Time: 4:48 PM
 */
@ComponentScan("org.biacode.spring.netty.server")
@PropertySource(value = {
        "classpath:netty-default.properties",
        "classpath:netty-custom.properties"
}, ignoreResourceNotFound = true)
@Import(RouterAnnotationDrivenConfiguration.class)
public class ServerAnnotationDrivenConfiguration {
}
