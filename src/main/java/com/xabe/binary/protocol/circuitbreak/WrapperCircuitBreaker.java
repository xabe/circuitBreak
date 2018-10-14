package com.xabe.binary.protocol.circuitbreak;

import java.util.function.Supplier;

public interface WrapperCircuitBreaker {

    <T> T callWithCircuitBreaker(Supplier<T> call);
}
