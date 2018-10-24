package com.xabe.binary.protocol.circuitbreak;

import akka.actor.ActorSystem;
import akka.testkit.TestKit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import scala.concurrent.duration.Duration;

import java.util.Properties;

public class CircuitBreakAkkaTest extends AbstractCircuitBreakBase {

    private static ActorSystem actorSystem = null;


    @BeforeAll
    public static void start() {
        actorSystem = ActorSystem.create( "test" );
    }
    


    @AfterAll
    public static void stop() {
        TestKit.shutdownActorSystem(actorSystem, Duration.create("5 seconds"), true);
    }


    @Override
    protected WrapperCircuitBreaker getInstanceCircuitBreak() {
        return new CircuitBreakAkka(actorSystem, new Properties());
    }
}