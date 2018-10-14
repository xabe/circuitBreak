package com.xabe.binary.protocol.resource;

import com.xabe.binary.protocol.model.GithubUser;
import com.xabe.binary.protocol.payload.StatusPayload;
import com.xabe.binary.protocol.service.GithubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Component
public class GithubResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(GithubResource.class);

    private final GithubService githubService;

    @Inject
    public GithubResource(GithubService githubService) {
        this.githubService = githubService;
    }

    @Path("/status")
    @GET
    public StatusPayload getStatus() {
        return new StatusPayload("OK");
    }

    @Path("/user/{user}")
    @GET
    public GithubUser getUser(@PathParam("user") String user) {
        return githubService.getUser(user);
    }
}
