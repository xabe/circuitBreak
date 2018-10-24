package com.xabe.binary.protocol.connector;


import com.xabe.binary.protocol.circuitbreak.WrapperCircuitBreaker;
import com.xabe.binary.protocol.config.jackson.ObjectMapperContextResolver;
import com.xabe.binary.protocol.connector.builder.ClientRequestInfo;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.function.Supplier;

@Component
public class RestConnectorImpl implements RestConnector {

    private final Client client;
    private final WrapperCircuitBreaker circuitBreaker;

    @Autowired
    public RestConnectorImpl(WrapperCircuitBreaker circuitBreaker) {
        this.client = ClientBuilder.newClient(new ClientConfig().register(JacksonFeature.class).register(new ObjectMapperContextResolver()).property(ClientProperties.CONNECT_TIMEOUT,2000).property(ClientProperties.READ_TIMEOUT,2000));
        this.circuitBreaker = circuitBreaker;
    }


    RestConnectorImpl(Client client,WrapperCircuitBreaker circuitBreaker) {
        this.client = client;
        this.circuitBreaker = circuitBreaker;
    }

    @Override
    public Optional<Object> getSimpleObject(ClientRequestInfo clientRequestInfo) {
        return Optional.ofNullable(circuitBreaker.callWithCircuitBreaker(this.call(clientRequestInfo)));
    }

    private <T> Supplier<T> call(ClientRequestInfo clientRequestInfo) {
        return () ->{
            final Response response = client.target(clientRequestInfo.getUrl())
                    .request(clientRequestInfo.getMediaType())
                    .header(HttpHeaders.ACCEPT, clientRequestInfo.getMediaType())
                    .get();
            return response.readEntity(new GenericType<>(clientRequestInfo.getType()));
        };
    }
}
