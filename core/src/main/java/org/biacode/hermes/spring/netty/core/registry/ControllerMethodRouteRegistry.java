package org.biacode.hermes.spring.netty.core.registry;

import org.biacode.hermes.spring.netty.core.registry.model.HermesControllerMethodRoute;

/**
 * Created by Arthur Asatryan.
 * Date: 2/1/18
 * Time: 12:40 AM
 */
public interface ControllerMethodRouteRegistry {
    void addRoute(final HermesControllerMethodRoute route);

    HermesControllerMethodRoute getRoute(final String command);
}
