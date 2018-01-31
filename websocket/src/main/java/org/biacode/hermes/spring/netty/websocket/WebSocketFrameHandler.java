package org.biacode.hermes.spring.netty.websocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.biacode.hermes.spring.netty.core.registry.ControllerMethodRouteRegistry;
import org.biacode.hermes.spring.netty.core.registry.model.HermesControllerMethodRoute;
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
    private ControllerMethodRouteRegistry registryService;
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
            final HermesControllerMethodRoute route = registryService.getRoute(commandText);
            LOGGER.debug("Invoking method - {} for command - {}", route.getTargetMethod().getName(), commandText);
            route.getTargetMethod().invoke(route.getTargetObject(), jacksonObjectMapper.readValue(request, route.getRequestClass()));
        }
        // This write to socket is experimental, and will be removed in future
        ctx.channel().writeAndFlush(new TextWebSocketFrame(request));
    }
    //endregion

}
