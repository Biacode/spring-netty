package org.biacode.spring.netty.core.registry.model;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Arthur Asatryan.
 * Date: 2/10/18
 * Time: 4:55 PM
 */
@RunWith(EasyMockRunner.class)
public class NettyControllerMethodRouteTest extends EasyMockSupport {

    //region Test methods
    @Test
    public void testCreate() throws NoSuchMethodException {
        // test data
        resetAll();
        // expectations
        replayAll();
        // test scenario
        final String command = "room/add";
        final NettyControllerMethodRouteTest targetObject = new NettyControllerMethodRouteTest();
        final Class<NettyControllerMethodRouteTest> requestClass = NettyControllerMethodRouteTest.class;
        final Method method = this.getClass().getMethod("resetAll");
        // without request class
        final NettyControllerMethodRoute route1 = new NettyControllerMethodRoute(
                command,
                method,
                targetObject
        );
        assertThat(route1).isNotNull();
        assertThat(route1.getCommand()).isNotNull().isEqualTo(command);
        assertThat(route1.getTargetMethod()).isNotNull().isEqualTo(method);
        assertThat(route1.getRequestClass()).isNotNull();
        assertThat(route1.getTargetObject()).isNotNull().isEqualTo(targetObject);
        // with request class
        final NettyControllerMethodRoute route2 = new NettyControllerMethodRoute(
                command,
                method,
                targetObject,
                requestClass
        );
        assertThat(route2).isNotNull();
        assertThat(route2.getCommand()).isNotNull().isEqualTo(command);
        assertThat(route2.getTargetMethod()).isNotNull().isEqualTo(method);
        assertThat(route2.getRequestClass()).isNotNull().isEqualTo(requestClass);
        assertThat(route2.getTargetObject()).isNotNull().isEqualTo(targetObject);
        verifyAll();
    }
    //endregion

}