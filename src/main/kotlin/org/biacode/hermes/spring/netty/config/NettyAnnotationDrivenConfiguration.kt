package org.biacode.hermes.spring.netty.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

/**
 * Created by Arthur Asatryan.
 * Date: 1/28/18
 * Time: 8:54 PM
 */
@Configuration
@ComponentScan("org.biacode.hermes.spring.netty")
@PropertySource("classpath:netty-default.properties")
class NettyAnnotationDrivenConfiguration
