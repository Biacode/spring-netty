package org.biacode.hermes.spring.netty.config

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import io.netty.channel.nio.NioEventLoopGroup
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowire
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.annotation.PreDestroy

/**
 * Created by Arthur Asatryan.
 * Date: 1/22/18
 * Time: 6:40 PM
 */
@Configuration
class WebsocketServerConfiguration {

    //region Dependencies
    @Value("\${spring.netty.host.port}")
    private val tcpPort: Int = 0

    @Autowired
    private lateinit var serverBootstrap: ServerBootstrap

    @Autowired
    private lateinit var bossGroup: NioEventLoopGroup

    @Autowired
    private lateinit var workerGroup: NioEventLoopGroup
    //endregion

    //region Beans
    @Bean(name = ["serverChannel"], autowire = Autowire.BY_NAME)
    fun serverChannel(): Channel {
        logger.debug("Starting server on port - {}", tcpPort)
        return serverBootstrap.bind(tcpPort).sync().channel().closeFuture().channel()
    }

    @PreDestroy
    fun stop() {
        logger.debug("Closing server channel")
        serverChannel().close()
        logger.debug("Closing server channel's parent")
        serverChannel().parent().close()
        logger.debug("Shutting down boss group")
        bossGroup.shutdownGracefully()
        logger.debug("Shutting down worker group")
        workerGroup.shutdownGracefully()
    }
    //endregion

    //region Companion object
    companion object {
        private val logger = LoggerFactory.getLogger(WebsocketServerConfiguration::class.java)
    }
    //endregion
}