package org.biacode.spring.netty.example.websocket.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.biacode.spring.netty.core.response.ResponsePayload;

/**
 * Created by Arthur Asatryan.
 * Date: 5/12/18
 * Time: 11:06 PM
 */
public class HelloMessage implements ResponsePayload {

    //region Properties
    @JsonProperty("command")
    private String command;

    @JsonProperty("message")
    private String message;
    //endregion

    //region Constructors
    public HelloMessage() {
        // Default constructor
    }

    public HelloMessage(final String command, final String message) {
        this.command = command;
        this.message = message;
    }
    //endregion

    //region Equals, HashCode and ToString
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HelloMessage)) {
            return false;
        }
        final HelloMessage that = (HelloMessage) o;
        return new EqualsBuilder()
                .append(command, that.command)
                .append(message, that.message)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(command)
                .append(message)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("command", command)
                .append("message", message)
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

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
    //endregion
}