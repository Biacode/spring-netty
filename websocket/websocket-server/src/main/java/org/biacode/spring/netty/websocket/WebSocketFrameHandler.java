package org.biacode.spring.netty.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.biacode.spring.netty.core.registry.ControllerMethodRouteRegistry;
import org.biacode.spring.netty.core.registry.model.NettyControllerMethodRoute;
import org.biacode.spring.netty.core.response.ResponsePayload;
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
    private ObjectMapper objectMapper;

    @Autowired
    private ControllerMethodRouteRegistry registryService;
    //endregion

    //region Public methods
    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final TextWebSocketFrame frame) throws Exception {
        final String request = frame.text();
        LOGGER.debug("Channel - {} received text - {}", ctx.channel(), request);
        final JsonNode jsonNode = objectMapper.readTree(request);
        final JsonNode command = jsonNode.get("command");
        final Mutable<Object> invocationResult = new MutableObject<>();
        if (command != null) {
            final String commandText = command.asText();
            LOGGER.debug("Got command - {}", commandText);
            final NettyControllerMethodRoute route = registryService.getRoute(commandText);
            LOGGER.debug("Got route - {} for command - {}", route, command);
            if (route.isContextNeeded()) {
                LOGGER.debug("The command - {} requested for netty context - {}", commandText, ctx);
                if (route.getRequestClass().equals(NettyControllerMethodRoute.NoRequestClass.class)) {
                    invocationResult.setValue(route.getTargetMethod().invoke(route.getTargetObject(), ctx));
                } else {
                    invocationResult.setValue(route.getTargetMethod().invoke(route.getTargetObject(), objectMapper.readValue(request, route.getRequestClass()), ctx));
                    writeResponse(ctx, invocationResult);
                }
            } else {
                if (route.getRequestClass().equals(NettyControllerMethodRoute.NoRequestClass.class)) {
                    invocationResult.setValue(route.getTargetMethod().invoke(route.getTargetObject()));
                } else {
                    invocationResult.setValue(route.getTargetMethod().invoke(route.getTargetObject(), objectMapper.readValue(request, route.getRequestClass())));
                    writeResponse(ctx, invocationResult);
                }
            }
        }
    }
    //endregion

    //region Utility methods
    private void writeResponse(final ChannelHandlerContext ctx, final Mutable<Object> invocationResult) {
        // This write to socket is experimental, and will be modified in future
        if (invocationResult.getValue() instanceof ResponsePayload) {
            try {
                final String responseJson = objectMapper.writer()
                        .forType(invocationResult.getValue().getClass())
                        .writeValueAsString(invocationResult.getValue());
                ctx.channel().writeAndFlush(new TextWebSocketFrame(responseJson));
            } catch (final JsonProcessingException e) {
                LOGGER.error("Can not deserialize invocation result - {} for class - {}", invocationResult, invocationResult.getValue().getClass());
            }
        }
    }
    //endregion

}
