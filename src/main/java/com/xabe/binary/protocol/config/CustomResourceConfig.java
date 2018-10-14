package com.xabe.binary.protocol.config;

import com.xabe.binary.protocol.config.jackson.ObjectMapperContextResolver;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.spring.SpringLifecycleListener;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.springframework.context.ApplicationContext;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class CustomResourceConfig extends ResourceConfig {

    private static final String CONTEXT_CONFIG = "contextConfig";

    public CustomResourceConfig(ApplicationContext applicationContext) {
        packages("com.xabe.binary.protocol.resource");
        register(JacksonFeature.class);
        register(new ObjectMapperContextResolver());
        register(SpringLifecycleListener.class);
        register(RequestContextFilter.class);
        property( CONTEXT_CONFIG, applicationContext );
        property( ServerProperties.BV_FEATURE_DISABLE, true );
        property( ServerProperties.RESOURCE_VALIDATION_IGNORE_ERRORS, true );
        property( ServerProperties.TRACING, "ALL" );
        property( ServerProperties.TRACING_THRESHOLD, "VERBOSE" );
    }

}
