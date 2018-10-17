package com.xabe.binary.protocol.connector.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientRequestBuilder {

	private String url;
	private ResultTypeImpl resultType;
	private String mediaType;
	private Class<?> clazz;
	private List<String> uriParams = new ArrayList<>();

	private ClientRequestBuilder() {
	}

	public static ClientRequestBuilder builder() {
		return new ClientRequestBuilder();
	}

	public ClientRequestBuilder withUrl(String url) {
		this.url = url;
		return this;
	}

	public ClientRequestBuilder withResultType(ResultTypeImpl resultType) {
		this.resultType = resultType;
		return this;
	}

	public ClientRequestBuilder withMediaType(String mediaType) {
		this.mediaType = mediaType;
		return this;
	}

	public ClientRequestBuilder withClass(Class<?> clazz) {
		this.clazz = clazz;
		return this;
	}
	
	public ClientRequestBuilder withUriParams(String uriParams) {
		this.uriParams.add(uriParams);
		return this;
	}

	public ClientRequestInfo build() {
		Objects.requireNonNull(url, "Required url");
		Objects.requireNonNull(resultType, "Required result type");
		Objects.requireNonNull(clazz, "Required type class result");
		Objects.requireNonNull(mediaType, "Required media type");
		return new ClientRequestInfo(url,resultType.createType(clazz), mediaType, uriParams);
	}

	

}
