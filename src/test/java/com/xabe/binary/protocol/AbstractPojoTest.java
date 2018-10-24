package com.xabe.binary.protocol;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;


public abstract class AbstractPojoTest<T> {

    protected T pojo;
    protected T equalsPojo;
    protected T otherPojo;


    public abstract void beforeTests();

    @Test
    public void shouldBeEquals() {
        assertThat(pojo, is(equalsPojo));
    }

    @Test
    public void shouldBeDistinct() {
        assertThat(pojo, is(not(otherPojo)));
    }

    @Test
    public void shouldProduceSameHashCode() {
        assertThat(pojo.hashCode(), is(equalsPojo.hashCode()));
    }

    @Test
    public void shouldProduceDistinctHashCode() {
        assertThat(pojo.hashCode(), is(not(otherPojo.hashCode())));
    }

    @Test
    public void equalsMethodCheckTheType() {
        assertThat(pojo, is(not("a string")));
    }

    @Test
    public void equalsMethodWithNullObject() {
        assertThat(pojo, is(notNullValue()));

    }

    @Test
    public void equalsMethodWithSameObject() {
        final T samePojo = pojo;
        assertThat(pojo, is(samePojo));
    }

    @Test
    public void shouldHaveMethodAccessors() throws IntrospectionException, IllegalArgumentException {

        Arrays.stream(Introspector.getBeanInfo(pojo.getClass()).getPropertyDescriptors()).forEach(propertyDescriptor -> {
            try {
                assertThat(propertyDescriptor.getReadMethod().invoke(pojo), is(Matchers.notNullValue()));
            } catch (IllegalAccessException | InvocationTargetException e) {
                new RuntimeException(e);
            }
        });
    }

    @Test
    public void shouldHaveAllPropertiesAccesibles() throws IntrospectionException, IllegalArgumentException {
        Arrays.stream(Introspector.getBeanInfo(pojo.getClass()).getPropertyDescriptors()).forEach(propertyDescriptor -> {
            try {
                Field field = FieldUtils.getField(pojo.getClass(), propertyDescriptor.getName());
                if (field != null) {
                    field.setAccessible(true);
                    assertThat(propertyDescriptor.getReadMethod().invoke(pojo), Matchers.is(field.get(pojo)));
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                new RuntimeException(e);
            }
        });
    }
}