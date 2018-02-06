package org.biacode.spring.netty.core.registry;

import org.biacode.spring.netty.core.registry.model.NettyControllerMethodRoute;

/**
 * Created by Arthur Asatryan.
 * Date: 2/1/18
 * Time: 12:40 AM
 */
public interface ControllerMethodRouteRegistry {
    void addRoute(final NettyControllerMethodRoute route);

    NettyControllerMethodRoute getRoute(final String command);
}
