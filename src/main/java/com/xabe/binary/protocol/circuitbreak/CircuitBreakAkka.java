package com.xabe.binary.protocol.circuitbreak;

import akka.actor.ActorSystem;
import akka.pattern.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class CircuitBreakAkka implements WrapperCircuitBreaker {

    public static final String CLOSE_CIRCUIT_BREAK = "-----> CLOSE CIRCUIT BREAK";
    public static final String HALF_OPEN_CIRCUIT_BREAK = "-----> HALF OPEN CIRCUIT BREAK";
    public static final String OPEN_CIRCUIT_BREAK = "-----> OPEN CIRCUIT BREAK";
    private static Logger LOGGER = LoggerFactory.getLogger(CircuitBreakAkka.class);
    private final CircuitBreaker circuitBreaker;

    public CircuitBreakAkka(ActorSystem actorSystem) {
        this.circuitBreaker = new CircuitBreaker(
                actorSystem.dispatcher(),
                actorSystem.scheduler(),
                1,
                FiniteDuration.create(1, TimeUnit.SECONDS),
                FiniteDuration.create(5, TimeUnit.SECONDS))
                .onOpen(this::open)
                .onHalfOpen(this::halfOpen)
                .onClose(this::close);
    }

    void close() {
        LOGGER.info(CLOSE_CIRCUIT_BREAK);
    }

    void halfOpen() {
        LOGGER.info(HALF_OPEN_CIRCUIT_BREAK);
    }

    void open() {
        LOGGER.info(OPEN_CIRCUIT_BREAK);
    }

    @Override
    public <T> T callWithCircuitBreaker(Supplier<T> call) {
        return circuitBreaker.callWithSyncCircuitBreaker(call::get);
    }
}
