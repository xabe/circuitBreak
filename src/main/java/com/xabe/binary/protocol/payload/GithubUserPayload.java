package com.xabe.binary.protocol.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xabe.binary.protocol.model.GithubUser;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class GithubUserPayload {

    private final String login;
    private final String type;
    private final String name;

    @JsonCreator
    public GithubUserPayload(@JsonProperty("login") String login,
                      @JsonProperty("type") String type,
                      @JsonProperty("name") String name) {
        this.login = login;
        this.type = type;
        this.name = name;
    }

    GithubUserPayload(GithubUser githubUser) {
        this.login = githubUser.getLogin();
        this.type = githubUser.getType();
        this.name = githubUser.getName();
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
        if (o instanceof GithubUserPayload) {
            GithubUserPayload that = (GithubUserPayload) o;
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

    public static GithubUserPayload create(GithubUser githubUser) {
        return new GithubUserPayload(githubUser);
    }
}
