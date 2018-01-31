package org.biacode.hermes.spring.netty.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arthur Asatryan.
 * Date: 1/31/18
 * Time: 4:24 PM
 */
public class WebsocketCommandAwareNettyControllerRequest {

    //region Properties
    @JsonProperty("command")
    private String command;
    //endregion

    //region Constructors
    public WebsocketCommandAwareNettyControllerRequest() {
    }

    public WebsocketCommandAwareNettyControllerRequest(final String command) {
        this.command = command;
    }
    //endregion

    //region Equals, HashCode and ToString
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WebsocketCommandAwareNettyControllerRequest)) {
            return false;
        }
        final WebsocketCommandAwareNettyControllerRequest that = (WebsocketCommandAwareNettyControllerRequest) o;
        return new EqualsBuilder()
                .append(command, that.command)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(command)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("command", command)
                .toString();
    }
    //endregion

    //region Properties getters and setters
    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }
    //endregion

}
