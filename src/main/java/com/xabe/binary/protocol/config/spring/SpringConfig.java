package com.xabe.binary.protocol.config.spring;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.xabe.binary.protocol.circuitbreak.CircuitBreakAkka;
import com.xabe.binary.protocol.circuitbreak.CircuitBreakResilience;
import com.xabe.binary.protocol.circuitbreak.WrapperCircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.*;
import org.springframework.context.event.ContextClosedEvent;

@Configuration
@ComponentScan(basePackages = {"com.xabe.binary.protocol.service","com.xabe.binary.protocol.resource","com.xabe.binary.protocol.connector"})
public class SpringConfig implements ApplicationListener<ContextClosedEvent> {

    @Autowired
    private ActorSystem actorSystem;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        actorSystem.terminate();
    }

    @Bean
    public ActorSystem getActorSystem(){
        final Config config = ConfigFactory.load();
        return  ActorSystem.create( "actorSystem" , config);
    }

    @Bean
    @Profile("default")
    public WrapperCircuitBreaker getCircuitBreakAkka(ActorSystem actorSystem){
        return new CircuitBreakAkka(actorSystem);
    }

    @Bean
    @Profile("!default")
    public WrapperCircuitBreaker getCircuitBreakResilience(){
        return new CircuitBreakResilience();
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
