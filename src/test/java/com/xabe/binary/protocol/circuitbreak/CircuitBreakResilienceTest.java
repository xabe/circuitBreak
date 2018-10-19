package com.xabe.binary.protocol.circuitbreak;

import java.util.Properties;

public class CircuitBreakResilienceTest extends AbstractCircuitBreakBase{

    @Override
    protected WrapperCircuitBreaker getInstanceCircuitBreak() {
        return new CircuitBreakResilience(new Properties());
    }
}