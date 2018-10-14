package com.xabe.binary.protocol.connector;

import akka.pattern.CircuitBreaker;
import com.xabe.binary.protocol.circuitbreak.WrapperCircuitBreaker;
import com.xabe.binary.protocol.model.GithubUser;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestConnectorImplTest {

    @Test
    public void shoudlHaveConstructorWithWrapperCircuitBreaker() throws Exception {
        final WrapperCircuitBreaker circuitBreaker = Mockito.mock(WrapperCircuitBreaker.class);
        assertThat(new RestConnectorImpl(circuitBreaker), is(notNullValue()));
    }

    @Test
    public void givenAClientRequestInfoWhenInvokeGetSimpleObjectThenReturnObject() throws Exception {
        //Given
        final ClientRequestInfo clientRequestInfo = ClientRequestBuilder.builder().withClass(GithubUser.class).withMediaType(MediaType.APPLICATION_JSON).withResultType(ResultTypeImpl.ONE).withUrl("fake").build();
        final WrapperCircuitBreaker circuitBreaker = Mockito.mock(WrapperCircuitBreaker.class);
        final Client client = mock(Client.class);
        final WebTarget webTarget = mock(WebTarget.class);
        final RestConnector restConnector = new RestConnectorImpl(client, circuitBreaker);
        when(circuitBreaker.callWithCircuitBreaker(any())).thenReturn(new GithubUser("fake","fake","fake"));
        when(client.target(anyString())).thenReturn(webTarget);

        //When
        GithubUser result = restConnector.getSimpleObject(clientRequestInfo);

        //Then
        assertThat(result, is(notNullValue()));

    }

}