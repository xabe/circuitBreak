package com.xabe.binary.protocol.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class StatusPayload implements Serializable {

    private final String status;

    @JsonCreator
    public StatusPayload(
            @JsonProperty("status") String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof StatusPayload) {
            StatusPayload that = (StatusPayload) o;
            return new EqualsBuilder()
                    .append(status, that.status)
                    .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(status)
                .toHashCode();
    }
}
