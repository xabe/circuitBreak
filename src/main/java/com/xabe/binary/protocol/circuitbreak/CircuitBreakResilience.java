package com.xabe.binary.protocol.circuitbreak;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Properties;
import java.util.function.Supplier;

public class CircuitBreakResilience implements WrapperCircuitBreaker<Object> {
    public static final String ATTEMPTS = "attempts";
    public static final String SLICE = "slice";
    public static final String DEFAULT_VALUE = "1";
    public static final String DEFAULT_VALUE_ATTEMPTS = DEFAULT_VALUE;
    public static final String DEFAULT_VALUE_SLICE = "5";
    private static Logger logger = LoggerFactory.getLogger(CircuitBreakResilience.class);
    private final CircuitBreaker circuitBreaker;

    public CircuitBreakResilience(Properties properties) {
        final CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(NumberUtils.toFloat(properties.getProperty(ATTEMPTS, DEFAULT_VALUE_ATTEMPTS)))
                .ringBufferSizeInHalfOpenState(NumberUtils.toInt(properties.getProperty(ATTEMPTS, DEFAULT_VALUE_ATTEMPTS)))
                .ringBufferSizeInClosedState(NumberUtils.toInt(properties.getProperty(ATTEMPTS, DEFAULT_VALUE_ATTEMPTS)))
                .enableAutomaticTransitionFromOpenToHalfOpen()
                .waitDurationInOpenState(Duration.ofSeconds(NumberUtils.toLong(properties.getProperty(SLICE, DEFAULT_VALUE_SLICE))))
                .build();
        this.circuitBreaker = CircuitBreaker.of("my-circuit-break",config);
        circuitBreaker.getEventPublisher().onEvent(event -> logger.info(event.toString()));
    }


    @Override
    public Object callWithCircuitBreaker(Supplier<Object> call) {
        return CircuitBreaker.decorateSupplier(circuitBreaker,call).get();
    }
}
