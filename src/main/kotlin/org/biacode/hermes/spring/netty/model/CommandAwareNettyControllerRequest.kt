package org.biacode.hermes.spring.netty.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by Arthur Asatryan.
 * Date: 1/28/18
 * Time: 7:40 PM
 */
open class CommandAwareNettyControllerRequest(
        @JsonProperty("command")
        var command: String? = null
) : NettyControllerRequest