package org.biacode.hermes.spring.netty.service

import io.netty.util.internal.PlatformDependent
import org.biacode.hermes.spring.netty.model.NettyControllerMethodRoute
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentMap

/**
 * Created by Arthur Asatryan.
 * Date: 1/28/18
 * Time: 7:54 PM
 */
@Service
class ControllerMethodRouteRegistryService {

    //region Properties
    private val routes: ConcurrentMap<String, NettyControllerMethodRoute> = PlatformDependent.newConcurrentHashMap()
    //endregion

    //region Public methods
    fun addRoute(command: String, route: NettyControllerMethodRoute) {
        logger.debug("Adding controller method route with command - {} and route - {}", command, route)
        routes[command] = route
    }

    fun getRoute(command: String): NettyControllerMethodRoute {
        logger.debug("Getting controller method route for command - {}", command)
        return routes[command]!!
    }
    //endregion

    //region Companion object
    companion object {
        private val logger = LoggerFactory.getLogger(ControllerMethodRouteRegistryService::class.java)
    }
    //endregion
}