package com.xabe.binary.protocol.connector.builder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public interface ResultType {
	
	ParameterizedType createType(Type clazz);

}
