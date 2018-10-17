package com.xabe.binary.protocol.circuitbreak;

import akka.actor.ActorSystem;
import akka.testkit.TestKit;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import scala.concurrent.duration.Duration;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class CircuitBreakAkkaTest {

    private static ActorSystem actorSystem = null;
    private CircuitBreakAkka circuitBreakAkka;
    private Logger logger;

    @BeforeClass
    public static void start() {
        actorSystem = ActorSystem.create( "test" );
    }
    
    @Before
    public void init() throws NoSuchFieldException, IllegalAccessException {
        this.circuitBreakAkka = new CircuitBreakAkka(actorSystem);
        this.logger = mock(Logger.class);
        final Field mockedField = circuitBreakAkka.getClass().getDeclaredField("LOGGER");
        mockedField.setAccessible(true);
        mockedField.set(circuitBreakAkka.getClass(), logger);
    }

    @AfterClass
    public static void stopCassandraEmbedded() {
        TestKit.shutdownActorSystem(actorSystem, Duration.create("5 seconds"), true);
    }
    
    @Test
    public void givenASupplierWhenInvokeCallWithCircuitBreakerThenReturnString() throws Exception {
        //Given
        final ThrowingSupplier<String> supplier = () -> {TimeUnit.SECONDS.sleep(2); return"";};

        //When
        try{
            circuitBreakAkka.callWithCircuitBreaker(supplier);
        }catch (Exception e){
            assertThat(e, is(instanceOf(TimeoutException.class)));
        }

        //Then
        verify(logger,timeout(1000).atLeastOnce()).info(CircuitBreakAkka.OPEN_CIRCUIT_BREAK);
        TimeUnit.SECONDS.sleep(6);
        verify(logger,timeout(1000).atLeastOnce()).info(CircuitBreakAkka.HALF_OPEN_CIRCUIT_BREAK);

        circuitBreakAkka.callWithCircuitBreaker(() -> "OK");
        verify(logger,timeout(1000).atLeastOnce()).info(CircuitBreakAkka.CLOSE_CIRCUIT_BREAK);

    }

    @FunctionalInterface
    public interface ThrowingSupplier<U> extends Supplier<U> {

        @Override
        default U get() {
            try {
                return getThrows();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        U getThrows() throws Exception;
    }
}