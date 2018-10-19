package com.xabe.binary.protocol.circuitbreak;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public abstract class AbstractCircuitBreakBase {

    protected WrapperCircuitBreaker circuitBreakAkka;
    protected Logger logger;

    @Before
    public void init() throws NoSuchFieldException, IllegalAccessException {
        this.circuitBreakAkka = getInstanceCircuitBreak();
        this.logger = mock(Logger.class);
        final Field mockedField = circuitBreakAkka.getClass().getDeclaredField("logger");
        mockedField.setAccessible(true);
        mockedField.set(circuitBreakAkka.getClass(), logger);
        doAnswer(invocationOnMock -> {
            final Object argument = invocationOnMock.getArguments()[0];
            System.out.println(argument);
            return null;
        }).when(logger).info(anyString());
    }

    protected abstract WrapperCircuitBreaker getInstanceCircuitBreak();

    @Test
    public void givenASupplierWhenInvokeCallWithCircuitBreakerThenReturnString() throws Exception {
        //Given
        final ThrowingSupplier<String> supplier = () -> {
            TimeUnit.SECONDS.sleep(2); throw new TimeoutException();};

        //When
        try{
            circuitBreakAkka.callWithCircuitBreaker(supplier);
        }catch (Exception e){

        }

        //Then
        verify(logger,timeout(1000).atLeastOnce()).info(anyString());
        TimeUnit.SECONDS.sleep(6);
        verify(logger,timeout(1000).atLeast(2)).info(anyString());
        circuitBreakAkka.callWithCircuitBreaker(() -> "OK");
        verify(logger,timeout(1000).atLeast(3)).info(anyString());

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
