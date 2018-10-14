package com.xabe.binary.protocol.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class GithubUser {
    private final String login;
    private final String type;
    private final String name;

    @JsonCreator
    public GithubUser(@JsonProperty("login") String login,
                      @JsonProperty("type") String type,
                      @JsonProperty("name") String name) {
        this.login = login;
        this.type = type;
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof GithubUser) {
            GithubUser that = (GithubUser) o;
            return new EqualsBuilder()
                    .append(login, that.login)
                    .append(type, that.type)
                    .append(name, that.name)
                    .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(login)
                .append(type)
                .append(name)
                .toHashCode();
    }
}
