package com.xabe.binary.protocol.resource;

import com.xabe.binary.protocol.model.GithubUser;
import com.xabe.binary.protocol.payload.GithubUserPayload;
import com.xabe.binary.protocol.payload.StatusPayload;
import com.xabe.binary.protocol.service.GithubService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.ws.rs.WebApplicationException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GithubResourceTest {

    @Test
    public void shouldReturnStatusPayload() throws Exception {
        //Given
        final GithubService service = mock(GithubService.class);
        final GithubResource resource = new GithubResource(service);

        //When
        final StatusPayload result = resource.getStatus();


        //Then
        assertThat(result, is(notNullValue()));
        assertThat(result.getStatus(), is(GithubResource.OK));
    }

    @Test
    public void givenAUserWhenInvokeGetUserReturnThenGithubUser() throws Exception {
        //Given
        final GithubUser githubUser = new GithubUser("fake","fake","fake");
        final GithubService service = mock(GithubService.class);
        final GithubResource resource = new GithubResource(service);

        when(service.getUser(any())).thenReturn(Optional.of(githubUser));

        //When
        final GithubUserPayload result = resource.getUser("user");

        //Then
        assertThat(result,is(notNullValue()));
    }


    @Test
    public void givenAIncorrectUserWhenInvokeGetUserReturnWebApplicationException() throws Exception {
        //Given
        final GithubService service = mock(GithubService.class);
        final GithubResource resource = new GithubResource(service);

        when(service.getUser(any())).thenReturn(Optional.empty());


        //When
        Assertions.assertThrows(WebApplicationException.class, ()-> resource.getUser("user"));

    }
}