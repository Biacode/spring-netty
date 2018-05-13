package org.biacode.spring.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Arthur Asatryan.
 * Date: 1/31/18
 * Time: 4:36 PM
 */
@Component
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketServerInitializer.class);

    //region Dependencies
    @Value("${spring.netty.websocket.path}")
    private String websocketPath;

    @Autowired
    private WebSocketFrameHandler webSocketFrameHandler;
    //endregion

    //region Public methods
    @Override
    protected void initChannel(final SocketChannel ch) throws Exception {
        LOGGER.debug("Creating channel pipeline");
        ch.pipeline()
                .addLast("httpServerCodec", new HttpServerCodec())
                .addLast("httpObjectAggregator", new HttpObjectAggregator(65536))
                .addLast("webSocketServerCompressionHandler", new WebSocketServerCompressionHandler())
                .addLast("webSocketServerProtocolHandler", new WebSocketServerProtocolHandler(websocketPath, null, true))
                .addLast("webSocketFrameHandler", webSocketFrameHandler);
        LOGGER.debug("Channel pipeline successfully created");
    }
    //endregion

}
