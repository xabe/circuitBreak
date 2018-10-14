package com.xabe.binary.protocol.circuitbreak;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.function.Supplier;

public class CircuitBreakResilience implements WrapperCircuitBreaker {

    private static final Logger LOGGER = LoggerFactory.getLogger(CircuitBreakResilience.class);
    private final CircuitBreaker circuitBreaker;

    public CircuitBreakResilience() {
        final CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(1l)
                .ringBufferSizeInHalfOpenState(1)
                .ringBufferSizeInClosedState(1)
                .enableAutomaticTransitionFromOpenToHalfOpen()
                .waitDurationInOpenState(Duration.ofSeconds(30l))
                .build();
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
        this.circuitBreaker = registry.circuitBreaker("my-circuit-break");
        circuitBreaker.getEventPublisher().onEvent(event -> LOGGER.info("{}",event));
    }


    @Override
    public <T> T callWithCircuitBreaker(Supplier<T> call) {
        return (T) CircuitBreaker.decorateSupplier(circuitBreaker,call::get).get();
    }
}
