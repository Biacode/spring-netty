package org.biacode.spring.netty.router.registry;

import org.biacode.spring.netty.core.registry.ControllerMethodRouteRegistry;
import org.biacode.spring.netty.core.registry.model.NettyControllerMethodRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.Map;

/**
 * Created by Arthur Asatryan.
 * Date: 1/31/18
 * Time: 4:40 PM
 */
@Component
public class ControllerMethodRouteRegistryImpl implements ControllerMethodRouteRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerMethodRouteRegistryImpl.class);

    //region Properties
    private final Map<String, NettyControllerMethodRoute> routes = new ConcurrentReferenceHashMap<>();
    //endregion

    //region Public methods
    public void addRoute(final NettyControllerMethodRoute route) {
        assertNettyControllerMethodRoute(route);
        LOGGER.debug("Adding controller method route - {}", route);
        final String command = route.getCommand();
        if (routes.containsKey(command)) {
            LOGGER.error("Route with command - {} already exists", command);
            throw new IllegalArgumentException("Route with command - " + command + " already exists");
        }
        routes.put(command, route);
    }

    public NettyControllerMethodRoute getRoute(final String command) {
        Assert.notNull(command, "The route command should not be null");
        LOGGER.debug("Getting controller method route for command - {}", command);
        if (routes.get(command) == null) {
            LOGGER.error("Can not find route for command - {}", command);
            throw new IllegalArgumentException("Can not find route for command - " + command);
        }
        return routes.get(command);
    }
    //endregion

    //region Utility methods
    private void assertNettyControllerMethodRoute(final NettyControllerMethodRoute route) {
        Assert.notNull(route, "The route should not be null");
        Assert.notNull(route.getCommand(), "The route command should not be null");
        Assert.notNull(route.getTargetMethod(), "The route target method should not be null");
        Assert.notNull(route.getTargetObject(), "The route target object should not be null");
        Assert.notNull(route.getRequestClass(), "The route request class should not be null");
    }
    //endregion

}
