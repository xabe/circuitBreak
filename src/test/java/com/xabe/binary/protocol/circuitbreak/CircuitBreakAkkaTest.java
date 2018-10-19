package com.xabe.binary.protocol.circuitbreak;

import akka.actor.ActorSystem;
import akka.testkit.TestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import scala.concurrent.duration.Duration;

import java.util.Properties;

public class CircuitBreakAkkaTest extends AbstractCircuitBreakBase {

    private static ActorSystem actorSystem = null;


    @BeforeClass
    public static void start() {
        actorSystem = ActorSystem.create( "test" );
    }
    


    @AfterClass
    public static void stopCassandraEmbedded() {
        TestKit.shutdownActorSystem(actorSystem, Duration.create("5 seconds"), true);
    }


    @Override
    protected WrapperCircuitBreaker getInstanceCircuitBreak() {
        return new CircuitBreakAkka(actorSystem, new Properties());
    }
}