package org.biacode.hermes.spring.netty.model

import java.lang.reflect.Method

/**
 * Created by Arthur Asatryan.
 * Date: 1/27/18
 * Time: 8:56 PM
 */
data class NettyControllerMethodRoute(
        val method: Method,
        val requestClass: Class<*>,
        val beanObject: Any
)