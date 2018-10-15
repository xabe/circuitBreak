package com.xabe.binary.protocol.service;

import com.xabe.binary.protocol.connector.RestConnector;
import com.xabe.binary.protocol.model.GithubUser;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class GithubServiceImplTest {

    private String url = "url";
    private RestConnector restConnector = mock(RestConnector.class);
    private GithubService githubService = new GithubServiceImpl(restConnector,url);

    @Test
    public void givenAUserWhenInvokeGetUserReturnGitUser() throws Exception {
        //Given
        final String user = "user";
        final GithubUser githubUser = new GithubUser("ok", "ok", "OK");
        when(restConnector.getSimpleObject(any())).thenReturn(Optional.of(githubUser));

        //When
        final Optional<GithubUser> result = githubService.getUser(user);


        //Then
        assertThat(result, is(Matchers.notNullValue()));
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(githubUser));
    }

    @Test
    public void givenAUserIncorrectWhenInvokeGetUserReturnGitUserDefault() throws Exception {
        //Given
        final String user = "user";
        final GithubUser githubUser = new GithubUser("CB", "CB", "CB");
        when(restConnector.getSimpleObject(any())).thenThrow(new RuntimeException());

        //When
        final Optional<GithubUser> result = githubService.getUser(user);


        //Then
        assertThat(result, is(Matchers.notNullValue()));
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(githubUser));
    }

}