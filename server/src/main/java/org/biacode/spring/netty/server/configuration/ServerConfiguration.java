package org.biacode.spring.netty.server.configuration;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

/**
 * Created by Arthur Asatryan.
 * Date: 1/31/18
 * Time: 4:14 PM
 */
@Configuration
public class ServerConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerConfiguration.class);

    //region Dependencies
    @Value("${spring.netty.host.port}")
    private int tcpPort;

    @Autowired
    private ServerBootstrap serverBootstrap;

    @Autowired
    private NioEventLoopGroup bossGroup;

    @Autowired
    private NioEventLoopGroup workerGroup;
    //endregion

    //region Public method
    @Bean
    public Channel serverChannel() throws InterruptedException {
        LOGGER.debug("Starting server on port - {}", tcpPort);
        return serverBootstrap.bind(tcpPort).sync().channel().closeFuture().channel();
    }

    @PreDestroy
    public void stop() throws InterruptedException {
        LOGGER.debug("Closing server channel");
        serverChannel().close();
        LOGGER.debug("Closing server channel's parent");
        serverChannel().parent().close();
        LOGGER.debug("Shutting down boss group");
        bossGroup.shutdownGracefully();
        LOGGER.debug("Shutting down worker group");
        workerGroup.shutdownGracefully();
    }
    //endregion

}
