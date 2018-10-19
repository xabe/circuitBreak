package com.xabe.binary.protocol.config.spring;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.xabe.binary.protocol.circuitbreak.CircuitBreakAkka;
import com.xabe.binary.protocol.circuitbreak.CircuitBreakResilience;
import com.xabe.binary.protocol.circuitbreak.WrapperCircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.*;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {"com.xabe.binary.protocol.service","com.xabe.binary.protocol.resource","com.xabe.binary.protocol.connector"})
public class SpringConfig implements ApplicationListener<ContextClosedEvent> {

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private Properties properties;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        actorSystem.terminate();
    }

    @Bean
    public ActorSystem getActorSystem(){
        final Config config = ConfigFactory.load();
        return  ActorSystem.create( "actorSystem" , config);
    }

    @Bean(name="myProperties")
    public Properties getMyProperties() throws IOException {
        return PropertiesLoaderUtils.loadProperties(new ClassPathResource("/application.properties"));
    }

    @Bean
    @Profile("default")
    public CircuitBreakAkka getCircuitBreakAkka(ActorSystem actorSystem, @Qualifier("myProperties") Properties properties){
        return new CircuitBreakAkka(actorSystem,properties);
    }

    @Bean
    @Profile("!default")
    public WrapperCircuitBreaker getCircuitBreakResilience(){
        return new CircuitBreakResilience(properties);
    }

    @Configuration
    @Profile("default")
    @PropertySource("classpath:application.properties")
    static class Defaults
    { }

    @Configuration
    @Profile("test")
    @PropertySource({"classpath:test.properties"})
    static class Overrides
    {

    }
}
