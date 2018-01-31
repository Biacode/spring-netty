package org.biacode.hermes.spring.netty.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.biacode.hermes.spring.netty.service.ControllerMethodRouteRegistryService;
import org.biacode.hermes.spring.netty.service.NettyControllerMethodRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Arthur Asatryan.
 * Date: 1/31/18
 * Time: 4:28 PM
 */
@Component
@ChannelHandler.Sharable
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketFrameHandler.class);

    //region Dependencies
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Autowired
    private ControllerMethodRouteRegistryService registryService;
    //endregion

    //region Public methods
    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final TextWebSocketFrame frame) throws Exception {
        final String request = frame.text();
        LOGGER.debug("Channel - {} received text - {}", ctx.channel(), request);
        // TODO: I believe it is possible to optimize deserialization of request here.
        final JsonNode jsonNode = jacksonObjectMapper.readTree(request);
        final JsonNode command = jsonNode.get("command");
        if (command != null) {
            final String commandText = command.asText();
            final NettyControllerMethodRoute route = registryService.getRoute(commandText);
            LOGGER.debug("Invoking method - {} for command - {}", route.getMethod().getName(), commandText);
            route.getMethod().invoke(route.getBeanObject(), jacksonObjectMapper.readValue(request, route.getRequestClass()));
        }
        // This write to socket is experimental, and will be removed in future
        ctx.channel().writeAndFlush(new TextWebSocketFrame(request));
    }
    //endregion

}
