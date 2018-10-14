package com.xabe.binary.protocol.it.resource;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.common.Slf4jNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.xabe.binary.protocol.model.GithubUser;
import com.xabe.binary.protocol.payload.StatusPayload;
import eu.rekawek.toxiproxy.Proxy;
import eu.rekawek.toxiproxy.ToxiproxyClient;
import eu.rekawek.toxiproxy.model.ToxicDirection;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.Timeout;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class GithubResourceTest_IT {

    //docker run --rm --name proxy -p 8474:8474 -p 5000:5000 shopify/toxiproxy

    public static final String FAKE_XABE = "/fake/xabe";
    private static WireMockServer wireMockServer;
    private static ToxiproxyClient client;
    private static Proxy httpProxy;
    private static final Integer PORT = 5000;
    private static final Integer PORT_WIREMOCK = 9999;

    @BeforeClass
    public static void setUp() throws IOException {
        final WireMockConfiguration options = loadWireMockConfiguration();
        wireMockServer = new WireMockServer( options );
        wireMockServer.start();
        setUpWireMock();

        client = new ToxiproxyClient("127.0.0.1", 8474);
        httpProxy = client.createProxy("http-tproxy-test", "127.0.0.1:"+PORT, "127.0.0.1:"+PORT_WIREMOCK);
        httpProxy.toxics().latency("latency-toxic", ToxicDirection.DOWNSTREAM, 5_000);
    }

    @AfterClass
    public static void setDown() throws IOException {
        wireMockServer.shutdown();
        httpProxy.delete();
    }

    public static WireMockConfiguration loadWireMockConfiguration() throws UnsupportedEncodingException {
        final String path = GithubResourceTest_IT.class.getClassLoader().getResource( "mock-server/wiremock" ).getPath();
        final String decodePath = URLDecoder.decode( path, StandardCharsets.UTF_8.name() );
        return wireMockConfig().port( PORT_WIREMOCK ).usingFilesUnderDirectory( decodePath ).notifier( new ConsoleNotifier( true ) );
    }


    public static void setUpWireMock() {
        wireMockServer.stubFor(get(urlMatching(FAKE_XABE)).willReturn( aResponse().withHeader( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON ).withBodyFile("github/user.json").withStatus( HttpServletResponse.SC_OK ) ));
        wireMockServer.stubFor(get(urlMatching(FAKE_XABE)).willReturn( aResponse().withHeader( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON ).withBodyFile("github/user.json").withStatus( HttpServletResponse.SC_OK ) ));
    }


    @Test
    public void shouldGetStatus() throws Exception {
        //Given
        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target( "http://localhost:8008"  ).path( "/v1" ).path( "/status" );

        //When
        final Response response = client.target(target.getUri())
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON + "; charset=" + StandardCharsets.UTF_8.name())
                .get();

        //Then
        final StatusPayload statusPayload = response.readEntity(StatusPayload.class);
        assertThat(statusPayload, is(notNullValue()));
    }


    @Test
    public void givenAUserWhenInvokeGetUserThenReturnGithubUser() throws Exception {
        //Given
        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target( "http://localhost:8008"  ).path( "/v1" ).path( "/user/xabe" );

        //When
        final LocalDateTime start = LocalDateTime.now();
        final Response response = client.target(target.getUri())
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON + "; charset=" + StandardCharsets.UTF_8.name())
                .get();

        //Then
        final GithubUser githubUser = response.readEntity(GithubUser.class);
        final LocalDateTime end = LocalDateTime.now();
        System.out.println(String.format("Durantion %d seconds",Duration.between(start, end).getSeconds()));
        assertThat(githubUser, is(notNullValue()));
        assertThat(githubUser.getLogin(), is("CB"));
        TimeUnit.SECONDS.sleep(2);
        wireMockServer.verify(getRequestedFor(urlMatching(FAKE_XABE)));
    }

    @Test
    public void givenAUserWhenInvokeGetUserThenReturnFailFastGithubUser() throws Exception {
        //Given
        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target( "http://localhost:8008"  ).path( "/v1" ).path( "/user/xabe" );

        //When
        final LocalDateTime start = LocalDateTime.now();
        final Response response = client.target(target.getUri())
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON + "; charset=" + StandardCharsets.UTF_8.name())
                .get();

        //Then
        final GithubUser githubUser = response.readEntity(GithubUser.class);
        final LocalDateTime end = LocalDateTime.now();
        System.out.println(String.format("Durantion %d seconds",Duration.between(start, end).getSeconds()));
        assertThat(githubUser, is(notNullValue()));
        assertThat(githubUser.getLogin(), is("CB"));
        TimeUnit.SECONDS.sleep(2);
        wireMockServer.verify(getRequestedFor(urlMatching(FAKE_XABE)));
    }

}
