package org.biacode.spring.netty.example.websocket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.biacode.spring.netty.core.annotation.NettyCommand;
import org.biacode.spring.netty.core.annotation.NettyContext;
import org.biacode.spring.netty.core.annotation.NettyController;
import org.biacode.spring.netty.core.annotation.NettyRequest;
import org.biacode.spring.netty.example.websocket.controller.model.HelloMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.UUID;

/**
 * Created by Arthur Asatryan.
 * Date: 5/12/18
 * Time: 11:06 PM
 */
@NettyController
public class SpringNettyHelloController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringNettyHelloController.class);

    //region Dependencies
    @Autowired
    private ObjectMapper jacksonObjectMapper;
    //endregion

    //region Constructors
    public SpringNettyHelloController() {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }
    //endregion

    //region Public methods
    @NettyCommand("hello")
    public HelloMessage hello(@NettyRequest final HelloMessage helloMessage, @NettyContext ChannelHandlerContext ctx) throws JsonProcessingException {
        Assert.notNull(helloMessage.getCommand(), "The hello command should not be null");
        Assert.notNull(helloMessage.getMessage(), "The hello message should not be null");
        Assert.notNull(ctx, "The netty context should not be null");
        LOGGER.info("Got command - {}", helloMessage.getCommand());
        LOGGER.info("Got message - {}", helloMessage.getMessage());
        LOGGER.info("Got channel with id - {}", ctx.channel().id());
        final HelloMessage response = new HelloMessage(UUID.randomUUID().toString(), "Hello, Client!");
        LOGGER.info("Response message using netty context - {}", ctx);
        ctx.writeAndFlush(new TextWebSocketFrame(jacksonObjectMapper.writeValueAsString(response)));
        LOGGER.info("Response message using response payload - {}", response);
        return response;
    }
    //endregion
}
