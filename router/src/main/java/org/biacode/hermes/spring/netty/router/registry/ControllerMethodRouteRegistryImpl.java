package org.biacode.hermes.spring.netty.router.registry;

import org.biacode.hermes.spring.netty.core.registry.ControllerMethodRouteRegistry;
import org.biacode.hermes.spring.netty.core.registry.model.HermesControllerMethodRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Arthur Asatryan.
 * Date: 1/31/18
 * Time: 4:40 PM
 */
@Component
public class ControllerMethodRouteRegistryImpl implements ControllerMethodRouteRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerMethodRouteRegistryImpl.class);

    //region Properties
    private Map<String, HermesControllerMethodRoute> routes = new ConcurrentHashMap<>();
    //endregion

    //region Public methods
    public void addRoute(final HermesControllerMethodRoute route) {
        assertHermesControllerMethodRoute(route);
        LOGGER.debug("Adding controller method route - {}", route);
        final String command = route.getCommand();
        if (routes.containsKey(command)) {
            LOGGER.error("Route with command - {} already exists", command);
            throw new IllegalArgumentException("Route with command - " + command + " already exists");
        }
        routes.put(command, route);
    }

    public HermesControllerMethodRoute getRoute(final String command) {
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
    private void assertHermesControllerMethodRoute(final HermesControllerMethodRoute route) {
        Assert.notNull(route, "The route should not be null");
        Assert.notNull(route.getCommand(), "The route command should not be null");
        Assert.notNull(route.getTargetMethod(), "The route target method should not be null");
        Assert.notNull(route.getTargetObject(), "The route target object should not be null");
        Assert.notNull(route.getRequestClass(), "The route request class should not be null");
    }
    //endregion

}
