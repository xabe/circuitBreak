package com.xabe.binary.protocol.connector.builder;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.util.UriTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class ClientRequestInfo {

	private final String url;
	private final ParameterizedType type;
	private final String mediaType;
	private List<String> uriParams;

	public ClientRequestInfo(String url, ParameterizedType createType, String mediaType, List<String> uriParams) {
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
		return uriTemplate.expand(this.getUriParams().toArray()).toString();
	}

	public ParameterizedType getType() {
		return type;
	}

	public String getMediaType() {
		return mediaType;
	}
	
	public List<String> getUriParams() {
		return uriParams;
	}
	
	public boolean isUriParams(){
		return CollectionUtils.isNotEmpty(this.uriParams);
	}

}
