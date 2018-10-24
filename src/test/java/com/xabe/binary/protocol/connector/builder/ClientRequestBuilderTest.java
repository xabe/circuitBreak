package com.xabe.binary.protocol.connector.builder;

import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ClientRequestBuilderTest {

    @Test
    public void shouldCreateClientClientWithPathParamRequestInfo() throws Exception {
        final ClientRequestInfo clientRequestInfo = ClientRequestBuilder.builder().withClass(String.class).withMediaType(MediaType.APPLICATION_JSON).withResultType(ResultTypeImpl.ONE).withUrl("url/{param}").withUriParams("path").build();
        assertThat(clientRequestInfo, is(notNullValue()));
        assertThat(clientRequestInfo.getUrl(), is("url/path"));
        assertThat(clientRequestInfo.getMediaType(), is(MediaType.APPLICATION_JSON));
        assertThat(clientRequestInfo.getType(), is(notNullValue()));
        assertThat(clientRequestInfo.getUriParams(), is(notNullValue()));
        assertThat(clientRequestInfo.isUriParams(), is(true));
    }

    @Test
    public void shouldCreateClientClientWithoutPathParamRequestInfo() throws Exception {
        final ClientRequestInfo clientRequestInfo = ClientRequestBuilder.builder().withClass(String.class).withMediaType(MediaType.APPLICATION_JSON).withResultType(ResultTypeImpl.ONE).withUrl("url").build();
        assertThat(clientRequestInfo, is(notNullValue()));
        assertThat(clientRequestInfo.getUrl(), is("url"));
        assertThat(clientRequestInfo.getMediaType(), is(MediaType.APPLICATION_JSON));
        assertThat(clientRequestInfo.getType(), is(notNullValue()));
        assertThat(clientRequestInfo.getUriParams(), is(hasSize(0)));
        assertThat(clientRequestInfo.isUriParams(), is(false));
    }

}