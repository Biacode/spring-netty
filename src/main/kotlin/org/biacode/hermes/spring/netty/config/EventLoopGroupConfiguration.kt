package org.biacode.hermes.spring.netty.config

import io.netty.channel.nio.NioEventLoopGroup
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by Arthur Asatryan.
 * Date: 1/23/18
 * Time: 10:55 PM
 */
@Configuration
class EventLoopGroupConfiguration {

    //region Dependencies
    @Value("\${spring.netty.boss.thread.size}")
    private val bossGroupThreadSize: Int = 0

    @Value("\${spring.netty.worker.thread.size}")
    private val workerGroupThreadSize: Int = 0
    //endregion

    //region Beans
    @Bean(destroyMethod = "shutdownGracefully")
    fun bossGroup(): NioEventLoopGroup {
        logger.debug("Creating boss loop group with boss thread count - {}", bossGroupThreadSize)
        return NioEventLoopGroup(bossGroupThreadSize)
    }

    @Bean(destroyMethod = "shutdownGracefully")
    fun workerGroup(): NioEventLoopGroup {
        logger.debug("Creating worker loop group with worker thread count - {}", bossGroupThreadSize)
        return NioEventLoopGroup(workerGroupThreadSize)
    }
    //endregion

    //region Companion object
    companion object {
        private val logger = LoggerFactory.getLogger(EventLoopGroupConfiguration::class.java)
    }
    //endregion
}