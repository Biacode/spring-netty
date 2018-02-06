package org.biacode.spring.netty.server.configuration;

import io.netty.channel.nio.NioEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Arthur Asatryan.
 * Date: 1/31/18
 * Time: 3:51 PM
 */
@Configuration
public class EventLoopGroupConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventLoopGroupConfiguration.class);

    //region Dependencies
    @Value("${spring.netty.boss.thread.size}")
    private int bossGroupThreadSize;

    @Value("${spring.netty.worker.thread.size}")
    private int workerGroupThreadSize;
    //endregion

    //region Public methods
    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup() {
        LOGGER.debug("Creating boss loop group with boss thread count - {}", bossGroupThreadSize);
        return new NioEventLoopGroup(bossGroupThreadSize);
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup() {
        LOGGER.debug("Creating worker loop group with worker thread count - {}", bossGroupThreadSize);
        return new NioEventLoopGroup(workerGroupThreadSize);
    }
    //endregion

}
