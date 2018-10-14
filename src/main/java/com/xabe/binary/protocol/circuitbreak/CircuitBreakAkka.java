package com.xabe.binary.protocol.circuitbreak;

import akka.actor.ActorSystem;
import akka.pattern.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

public class CircuitBreakAkka implements WrapperCircuitBreaker {

    private static final Logger LOGGER = LoggerFactory.getLogger(CircuitBreakAkka.class);
    private final CircuitBreaker circuitBreaker;

    public CircuitBreakAkka(ActorSystem actorSystem) {
        this.circuitBreaker = new CircuitBreaker(
                actorSystem.dispatcher(),
                actorSystem.scheduler(),
                1,
                FiniteDuration.create(1, TimeUnit.SECONDS),
                FiniteDuration.create(10, TimeUnit.SECONDS))
                .onOpen(this::open)
                .onHalfOpen(this::halfOpen)
                .onClose(this::close);
    }

    private void close() {
        LOGGER.info("-----> CLOSE CIRCUIT BREAK");
    }

    private void halfOpen() {
        LOGGER.info("-----> HALF OPEN CIRCUIT BREAK");
    }

    private void open() {
        LOGGER.info("-----> OPEN CIRCUIT BREAK");
    }

    @Override
    public <T> T callWithCircuitBreaker(Supplier<T> call) {
        return circuitBreaker.callWithSyncCircuitBreaker(call::get);
    }
}
