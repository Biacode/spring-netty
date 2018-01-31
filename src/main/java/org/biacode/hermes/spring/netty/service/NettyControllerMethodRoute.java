package org.biacode.hermes.spring.netty.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.lang.reflect.Method;

/**
 * Created by Arthur Asatryan.
 * Date: 1/31/18
 * Time: 4:25 PM
 */
public class NettyControllerMethodRoute {

    //region Properties
    @JsonProperty("method")
    private Method method;

    @JsonProperty("requestClass")
    private Class<?> requestClass;

    @JsonProperty("beanObject")
    private Object beanObject;
    //endregion

    //region Constructors
    public NettyControllerMethodRoute() {
    }

    public NettyControllerMethodRoute(final Method method, final Class<?> requestClass, final Object beanObject) {
        this.method = method;
        this.requestClass = requestClass;
        this.beanObject = beanObject;
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
                .append(method, that.method)
                .append(requestClass, that.requestClass)
                .append(beanObject, that.beanObject)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(method)
                .append(requestClass)
                .append(beanObject)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("method", method)
                .append("requestClass", requestClass)
                .append("beanObject", beanObject)
                .toString();
    }
    //endregion

    //region Properties getters and setters
    public Method getMethod() {
        return method;
    }

    public void setMethod(final Method method) {
        this.method = method;
    }

    public Class<?> getRequestClass() {
        return requestClass;
    }

    public void setRequestClass(final Class<?> requestClass) {
        this.requestClass = requestClass;
    }

    public Object getBeanObject() {
        return beanObject;
    }

    public void setBeanObject(final Object beanObject) {
        this.beanObject = beanObject;
    }
    //endregion

}
