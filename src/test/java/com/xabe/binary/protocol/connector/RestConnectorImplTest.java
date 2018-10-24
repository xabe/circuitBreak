package com.xabe.binary.protocol.connector;

import com.xabe.binary.protocol.circuitbreak.WrapperCircuitBreaker;
import com.xabe.binary.protocol.connector.builder.ClientRequestBuilder;
import com.xabe.binary.protocol.connector.builder.ClientRequestInfo;
import com.xabe.binary.protocol.connector.builder.ResultTypeImpl;
import com.xabe.binary.protocol.model.GithubUser;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.function.Supplier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class RestConnectorImplTest {

    @Test
    public void shouldHaveConstructorWithWrapperCircuitBreaker() throws Exception {
        final WrapperCircuitBreaker circuitBreaker = Mockito.mock(WrapperCircuitBreaker.class);
        assertThat(new RestConnectorImpl(circuitBreaker), is(notNullValue()));
    }

    @Test
    public void givenAClientRequestInfoWhenInvokeGetSimpleObjectThenReturnObject() throws Exception {
        //Given
        final ArgumentCaptor<Supplier> argumentCaptor = ArgumentCaptor.forClass(Supplier.class);
        final ClientRequestInfo clientRequestInfo = ClientRequestBuilder.builder().withClass(GithubUser.class).withMediaType(MediaType.APPLICATION_JSON).withResultType(ResultTypeImpl.ONE).withUrl("fake").build();
        final WrapperCircuitBreaker circuitBreaker = call -> {
                try{
                    return call.get();
                }catch (Exception e){};
                return new GithubUser("","","");
            };
        final Client client = ClientBuilder.newClient();
        final RestConnector restConnector = new RestConnectorImpl(client, circuitBreaker);

        //When
        Optional<Object> result = restConnector.getSimpleObject(clientRequestInfo);

        //Then
        assertThat(result, is(notNullValue()));

    }



}