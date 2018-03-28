package org.biacode.spring.netty.router.registry;

import org.biacode.spring.netty.core.registry.ControllerMethodRouteRegistry;
import org.biacode.spring.netty.core.registry.model.NettyControllerMethodRoute;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by Arthur Asatryan.
 * Date: 2/10/18
 * Time: 6:07 PM
 */
@RunWith(EasyMockRunner.class)
public class ControllerMethodRouteRegistryImplTest extends EasyMockSupport {

    //region Test subject and mocks
    @TestSubject
    private ControllerMethodRouteRegistry controllerMethodRouteRegistry = new ControllerMethodRouteRegistryImpl();
    //endregion

    //region Test methods

    //region addRoute
    @Test
    public void testAddRouteWithInvalidArguments() {
        // test data
        resetAll();
        // expectations
        replayAll();
        // test scenario
        assertThatThrownBy(() -> controllerMethodRouteRegistry.addRoute(null)).isExactlyInstanceOf(IllegalArgumentException.class);
        verifyAll();
    }

    @Test
    public void testAddRouteWhenCommandAlreadyExists() throws NoSuchMethodException {
        // test data
        resetAll();
        final NettyControllerMethodRoute route = buildNettyControllerMethodRoute();
        // expectations
        replayAll();
        // test scenario
        verifyAll();
    }

    @Test
    public void testAddAndGetRoute() throws NoSuchMethodException {
        // test data
        resetAll();
        final NettyControllerMethodRoute route = buildNettyControllerMethodRoute();
        // expectations
        replayAll();
        // test scenario
        controllerMethodRouteRegistry.addRoute(route);
        final NettyControllerMethodRoute result = controllerMethodRouteRegistry.getRoute(route.getCommand());
        assertThat(result).isEqualTo(route);
        verifyAll();
    }
    //endregion

    //endregion

    //region Utility methods
    private NettyControllerMethodRoute buildNettyControllerMethodRoute() throws NoSuchMethodException {
        final String command = UUID.randomUUID().toString();
        final Method targetMethod = this.getClass().getMethod("resetAll");
        final Object targetObject = this;
        return new NettyControllerMethodRoute(command, targetMethod, targetObject);
    }
    //endregion

}