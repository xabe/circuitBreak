package com.xabe.binary.protocol.connector;

import org.springframework.web.util.UriTemplate;

import java.lang.reflect.ParameterizedType;

public class ClientRequestInfo {

	private final String url;
	private final ParameterizedType type;
	private final String mediaType;
	private String[] uriParams;

	public ClientRequestInfo(String url, ParameterizedType createType, String mediaType, String[] uriParams) {
		this.url = url;
		this.type = createType;
		this.mediaType = mediaType;
		this.uriParams = uriParams;
	}

	public String getUrl() {
		if(isUriParams()){
			return resolveUrl();
		}
		return url;
	}

	private String resolveUrl() {
		final UriTemplate uriTemplate = new UriTemplate(this.url);
		return uriTemplate.expand(this.getUriParams()).toString();
	}

	public ParameterizedType getType() {
		return type;
	}

	public String getMediaType() {
		return mediaType;
	}
	
	public String[] getUriParams() {
		return uriParams;
	}
	
	public boolean isUriParams(){
		return this.uriParams != null && uriParams.length > 0;
	}

}
