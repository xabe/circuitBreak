package com.xabe.binary.protocol.connector.builder;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class ResultTypeImplTest {

    @Test
    public void givenAClassWhenInvokeCreateTypeThenReturnParameterizedType() throws Exception {
        //Given
        final Class aClass = String.class;

        //When
        final ParameterizedType result = ResultTypeImpl.ONE.createType(aClass);

        //Then
        assertThat(result, is(notNullValue()));
        assertThat(result.getRawType(), is(notNullValue()));
        assertThat(result.getActualTypeArguments().length, is(0));
        assertThat(result.getOwnerType(), is(nullValue()));

    }

    @Test
    public void givenAClassWhenInvokeCreateTypeThenReturnListParameterizedType() throws Exception {
        //Given
        final Class aClass = String.class;

        //When
        final ParameterizedType result = ResultTypeImpl.LIST.createType(aClass);

        //Then
        assertThat(result, is(notNullValue()));
        assertThat(result.getRawType(), is(notNullValue()));
        assertThat(result.getActualTypeArguments().length, is(1));
        assertThat(result.getOwnerType(), is(nullValue()));

    }

}