package org.biacode.hermes.spring.netty.service

import org.biacode.hermes.spring.netty.model.ControllerMethodRoute
import org.springframework.stereotype.Service

/**
 * Created by Arthur Asatryan.
 * Date: 1/28/18
 * Time: 7:54 PM
 */
@Service
class ControllerMethodRouteRegistryService {
    private val routes = mutableMapOf<String, ControllerMethodRoute>()

    fun addRoute(commandType: String, controllerMethodRoute: ControllerMethodRoute) {
        routes[commandType] = controllerMethodRoute
    }

    fun getRoute(commandType: String): ControllerMethodRoute {
        return routes[commandType]!!
    }
}