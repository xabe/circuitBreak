package com.xabe.binary.protocol.resource;

import com.xabe.binary.protocol.payload.GithubUserPayload;
import com.xabe.binary.protocol.payload.StatusPayload;
import com.xabe.binary.protocol.service.GithubService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Component
public class GithubResource {

    public static final String OK = "OK" ;
    private final GithubService githubService;

    @Inject
    public GithubResource(GithubService githubService) {
        this.githubService = githubService;
    }

    @Path("/status")
    @GET
    public StatusPayload getStatus() {
        return new StatusPayload(OK);
    }

    @Path("/user/{user}")
    @GET
    public GithubUserPayload getUser(@PathParam("user") String user) {
        return githubService.getUser(user).map(GithubUserPayload::create).orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }
}
