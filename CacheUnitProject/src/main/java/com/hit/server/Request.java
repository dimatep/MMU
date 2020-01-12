package com.hit.server;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class Request<T> implements Serializable {

	T body;
	Map<String, String> headers;
	
	public Request(T body, Map<String, String> headers) {
		this.body = body;
		this.headers = headers;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	@Override
	public String toString() {
		return "Request [headers" + headers + ", body=" + body + "]";
	}
}
