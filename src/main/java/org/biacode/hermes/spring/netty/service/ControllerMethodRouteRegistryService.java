package org.biacode.hermes.spring.netty.service;

import io.netty.util.internal.PlatformDependent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentMap;

/**
 * Created by Arthur Asatryan.
 * Date: 1/31/18
 * Time: 4:40 PM
 */
@Service
public class ControllerMethodRouteRegistryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerMethodRouteRegistryService.class);

    //region Properties
    private ConcurrentMap<String, NettyControllerMethodRoute> routes = PlatformDependent.newConcurrentHashMap();
    //endregion

    //region Public methods
    public void addRoute(final String command, final NettyControllerMethodRoute route) {
        LOGGER.debug("Adding controller method route with command - {} and route - {}", command, route);
        routes.put(command, route);
    }

    public NettyControllerMethodRoute getRoute(final String command) {
        LOGGER.debug("Getting controller method route for command - {}", command);
        return routes.get(command);
    }
    //endregion

}
