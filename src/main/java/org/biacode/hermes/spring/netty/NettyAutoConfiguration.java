package org.biacode.hermes.spring.netty;

import org.biacode.hermes.spring.netty.annotation.NettyController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Arthur Asatryan.
 * Date: 1/28/18
 * Time: 2:21 PM
 */
@Configuration
@ConditionalOnClass(NettyController.class)
public class NettyAutoConfiguration {
}
