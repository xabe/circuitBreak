package com.xabe.binary.protocol.connector.builder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public enum ResultTypeImpl implements ResultType {
	ONE(){
		
		@Override
		public ParameterizedType createType(Type clazz) {
			return new ParameterizedTypeImpl(clazz);
		}
		
	},LIST(){
		
		@Override
		public ParameterizedType createType(Type clazz) {
			return new ParameterizedTypeImpl(List.class, clazz);
		}
	};

}
