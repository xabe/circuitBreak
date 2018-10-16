package com.xabe.binary.protocol.connector.builder;

import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ClientRequestBuilderTest {

    @Test
    public void shouldCreateClientClientRequestInfo() throws Exception {
        final ClientRequestInfo clientRequestInfo = ClientRequestBuilder.builder().withClass(String.class).withMediaType(MediaType.APPLICATION_JSON).withResultType(ResultTypeImpl.ONE).withUrl("url").withUriParams(new String[]{"param"}).build();
        assertThat(clientRequestInfo, is(notNullValue()));
        assertThat(clientRequestInfo.getUrl(), is("url"));
        assertThat(clientRequestInfo.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        assertThat(clientRequestInfo.getType(), is(notNullValue()));
        assertThat(clientRequestInfo.getUriParams(), is(notNullValue()));
        assertThat(clientRequestInfo.isUriParams(), is(true));
    }

}