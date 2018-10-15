package com.xabe.binary.protocol.resource;

import com.xabe.binary.protocol.model.GithubUser;
import com.xabe.binary.protocol.payload.GithubUserPayload;
import com.xabe.binary.protocol.payload.StatusPayload;
import com.xabe.binary.protocol.service.GithubService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.ws.rs.WebApplicationException;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GithubResourceTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

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
        exception.expect(WebApplicationException.class);


        //When
        resource.getUser("user");

    }
}