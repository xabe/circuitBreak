package com.xabe.binary.protocol.circuitbreak;

import akka.actor.ActorSystem;
import akka.pattern.CircuitBreaker;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Properties;
import java.util.function.Supplier;

public class CircuitBreakAkka implements WrapperCircuitBreaker<Object> {

    public static final String CLOSE_CIRCUIT_BREAK = "-----> CLOSE CIRCUIT BREAK";
    public static final String HALF_OPEN_CIRCUIT_BREAK = "-----> HALF OPEN CIRCUIT BREAK";
    public static final String OPEN_CIRCUIT_BREAK = "-----> OPEN CIRCUIT BREAK";
    public static final String ATTEMPTS = "attempts";
    public static final String TIMEOUT = "timeout";
    public static final String SLICE = "slice";
    public static final String DEFAULT_VALUE = "1";
    public static final String DEFAULT_VALUE_ATTEMPTS = DEFAULT_VALUE;
    public static final String DEFAULT_VALUE_TIMEOUT = "1";
    public static final String DEFAULT_VALUE_SLICE = "5";
    private static Logger logger = LoggerFactory.getLogger(CircuitBreakAkka.class);
    private final CircuitBreaker circuitBreaker;

    public CircuitBreakAkka(ActorSystem actorSystem, Properties properties) {
        this.circuitBreaker = new CircuitBreaker(
                actorSystem.dispatcher(),
                actorSystem.scheduler(),
                NumberUtils.toInt(properties.getProperty(ATTEMPTS, DEFAULT_VALUE_ATTEMPTS)),
                Duration.ofSeconds(NumberUtils.toLong(properties.getProperty(TIMEOUT, DEFAULT_VALUE_TIMEOUT))),
                Duration.ofSeconds(NumberUtils.toLong(properties.getProperty(SLICE, DEFAULT_VALUE_SLICE))))
                .onOpen(this::open)
                .onHalfOpen(this::halfOpen)
                .onClose(this::close);
    }

    void close() {
        logger.info(CLOSE_CIRCUIT_BREAK);
    }

    void halfOpen() {
        logger.info(HALF_OPEN_CIRCUIT_BREAK);
    }

    void open() {
        logger.info(OPEN_CIRCUIT_BREAK);
    }

    @Override
    public Object callWithCircuitBreaker(Supplier<Object> call) {
        return circuitBreaker.callWithSyncCircuitBreaker(call::get);
    }
}
