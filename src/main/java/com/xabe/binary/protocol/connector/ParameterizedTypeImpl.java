package com.xabe.binary.protocol.connector;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@SuppressWarnings("serial")
public class ParameterizedTypeImpl implements ParameterizedType, Serializable {

	private final Type rawType;
	private final Type actualTypeArguments[];
	
	public ParameterizedTypeImpl(Type rawType, Type... actualTypeArguments) {
		this.rawType = rawType;
		this.actualTypeArguments = actualTypeArguments;
	}
	
	
	@Override
	public Type[] getActualTypeArguments() {
		return actualTypeArguments;
	}
	
	@Override
	public Type getRawType() {
		return rawType;
	}

	@Override
	public Type getOwnerType() {
		return null;
	}

}
