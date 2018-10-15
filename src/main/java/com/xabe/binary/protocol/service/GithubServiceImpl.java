package com.xabe.binary.protocol.service;

import com.xabe.binary.protocol.connector.ClientRequestBuilder;
import com.xabe.binary.protocol.connector.ClientRequestInfo;
import com.xabe.binary.protocol.connector.RestConnector;
import com.xabe.binary.protocol.connector.ResultTypeImpl;
import com.xabe.binary.protocol.model.GithubUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GithubServiceImpl implements GithubService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GithubServiceImpl.class);
    private final RestConnector restConnector;
    private final String url;

    @Autowired
    public GithubServiceImpl(RestConnector restConnector,@Value( "${github.url.user}" )String url) {
        this.restConnector = restConnector;
        this.url = url;
        LOGGER.info("The actual url {}",url);
    }
    
    @Override
    public Optional<GithubUser> getUser(String user) {
        try{
        final ClientRequestInfo clientRequestInfo = ClientRequestBuilder.builder().withUrl(url).withClass(GithubUser.class).withResultType(ResultTypeImpl.ONE).withMediaType(MediaType.APPLICATION_JSON_VALUE).withUriParams(new String[]{user}).build();
        return restConnector.getSimpleObject(clientRequestInfo);
        }
        catch (Exception e){
            LOGGER.error("Error get user {}",e.getMessage());
            return Optional.of(new GithubUser("CB","CB","CB"));
        }
    }
}
