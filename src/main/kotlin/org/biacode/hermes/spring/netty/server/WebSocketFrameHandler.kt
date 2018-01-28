package org.biacode.hermes.spring.netty.server

import com.fasterxml.jackson.databind.ObjectMapper
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler
import org.biacode.hermes.spring.netty.service.ControllerMethodRouteRegistryService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


/**
 * Created by Arthur Asatryan.
 * Date: 1/22/18
 * Time: 4:28 PM
 */
@Component
@ChannelHandler.Sharable
class WebSocketFrameHandler : SimpleChannelInboundHandler<TextWebSocketFrame>() {

    //region Dependencies
    @Autowired
    private lateinit var jacksonObjectMapper: ObjectMapper

    @Autowired
    private lateinit var controllerMethodRouteRegistryService: ControllerMethodRouteRegistryService
    //endregion

    //region Public methods
    override fun channelRead0(ctx: ChannelHandlerContext, frame: TextWebSocketFrame) {
        val request = frame.text()
        logger.debug("Channel - {} received frame - {}", ctx.channel(), request)
        // TODO: I believe it is possible to optimize deserialization of request here.
        val jsonMap = jacksonObjectMapper.readTree(request)
        jsonMap["command"]?.let {
            val route = controllerMethodRouteRegistryService.getRoute(it.asText())
            route.method.invoke(route.beanObject, jacksonObjectMapper.readValue(request, route.requestClass))
        }
        ctx.channel().writeAndFlush(TextWebSocketFrame(request))
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        logger.debug("Channel - {} read completed", ctx.channel())
        ctx.channel().flush()
    }

    override fun userEventTriggered(ctx: ChannelHandlerContext, evt: Any) {
        logger.debug("Channel - {} user event - {} is triggered", ctx.channel(), evt)
        if (evt is WebSocketServerProtocolHandler.HandshakeComplete) {
            logger.info("Channel - {} handshake completed", ctx.channel())
        }
    }

    override fun channelActive(ctx: ChannelHandlerContext) {
        logger.debug("Channel - {} is active", ctx.channel())
    }

    override fun channelInactive(ctx: ChannelHandlerContext) {
        logger.debug("Channel - {} is inactive", ctx.channel())
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        logger.error("Channel - {} cause message - {} cause - {}", ctx.channel(), cause.message, cause)
    }
    //endregion

    //region Companion object
    companion object {
        private val logger = LoggerFactory.getLogger(WebSocketFrameHandler::class.java)
    }
    //endregion
}