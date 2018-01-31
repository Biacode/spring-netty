package org.biacode.hermes.spring.netty.starter.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Arthur Asatryan.
 * Date: 1/31/18
 * Time: 3:54 PM
 */
@Configuration
@ComponentScan("org.biacode.hermes.spring.netty")
@PropertySource({
        "classpath:netty-default.properties",
        "classpath:netty-custom.properties"
})
public class NettyAnnotationDrivenConfiguration {
}
