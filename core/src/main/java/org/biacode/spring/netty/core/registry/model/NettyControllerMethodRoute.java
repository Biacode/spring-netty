package org.biacode.spring.netty.core.registry.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.lang.reflect.Method;

/**
 * Created by Arthur Asatryan.
 * Date: 2/1/18
 * Time: 12:41 AM
 */
public class NettyControllerMethodRoute {

    //region Properties
    private final String command;

    private final Method targetMethod;

    private final Object targetObject;

    private final Class<?> requestClass;

    private boolean contextNeeded;
    //endregion

    //region Constructors
    public NettyControllerMethodRoute(final String command,
                                      final Method targetMethod,
                                      final Object targetObject) {
        this.command = command;
        this.targetMethod = targetMethod;
        this.targetObject = targetObject;
        this.requestClass = NoRequestClass.class;
    }

    public NettyControllerMethodRoute(final String command,
                                      final Method targetMethod,
                                      final Object targetObject,
                                      final Class<?> requestClass) {
        this.command = command;
        this.targetMethod = targetMethod;
        this.targetObject = targetObject;
        this.requestClass = requestClass;
    }

    public NettyControllerMethodRoute(final String command,
                                      final Method targetMethod,
                                      final Object targetObject,
                                      final Class<?> requestClass,
                                      boolean contextNeeded) {
        this.command = command;
        this.targetMethod = targetMethod;
        this.targetObject = targetObject;
        this.requestClass = requestClass;
        this.contextNeeded = contextNeeded;
    }

    public NettyControllerMethodRoute(final String command,
                                      final Method targetMethod,
                                      final Object targetObject,
                                      boolean contextNeeded) {
        this.command = command;
        this.targetMethod = targetMethod;
        this.targetObject = targetObject;
        this.contextNeeded = contextNeeded;
        this.requestClass = NoRequestClass.class;
    }
    //endregion

    //region Inner classes
    public static final class NoRequestClass {
        private NoRequestClass() {
        }
    }
    //endregion

    //region Equals, HashCode and ToString
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NettyControllerMethodRoute)) {
            return false;
        }
        final NettyControllerMethodRoute that = (NettyControllerMethodRoute) o;
        return new EqualsBuilder()
                .append(command, that.command)
                .append(targetMethod, that.targetMethod)
                .append(requestClass, that.requestClass)
                .append(targetObject, that.targetObject)
                .append(contextNeeded, that.contextNeeded)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(command)
                .append(targetMethod)
                .append(requestClass)
                .append(targetObject)
                .append(contextNeeded)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("command", command)
                .append("method", targetMethod)
                .append("requestClass", requestClass)
                .append("targetObject", targetObject)
                .append("contextNeeded", contextNeeded)
                .toString();
    }
    //endregion

    //region Properties getters and setters
    public String getCommand() {
        return command;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public Class<?> getRequestClass() {
        return requestClass;
    }

    public boolean isContextNeeded() {
        return contextNeeded;
    }

    public void setContextNeeded(final boolean contextNeeded) {
        this.contextNeeded = contextNeeded;
    }
    //endregion
}
