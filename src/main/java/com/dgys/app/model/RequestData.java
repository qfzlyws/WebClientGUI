package com.dgys.app.model;

import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import com.dgys.app.ui.AuthPanel.AuthOption;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = RequestDataDeserializer.class)
public class RequestData {
	private String name;
	private String url;
	private String method;
	private Map<String,String> headerParams;
	private AuthOption authType;
	private String tokenText;
	private byte bodyOption;
	private String jsonBody;
	private List<NameValuePair> urlEncodedParams;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Map<String, String> getHeaderParams() {
		return headerParams;
	}
	public void setHeaderParams(Map<String, String> headerParams) {
		this.headerParams = headerParams;
	}
	public AuthOption getAuthType() {
		return authType;
	}
	public void setAuthType(AuthOption authType) {
		this.authType = authType;
	}
	public String getTokenText() {
		return tokenText;
	}
	public void setTokenText(String tokenText) {
		this.tokenText = tokenText;
	}
	public byte getBodyOption() {
		return bodyOption;
	}
	public void setBodyOption(byte bodyOption) {
		this.bodyOption = bodyOption;
	}
	public String getJsonBody() {
		return jsonBody;
	}
	public void setJsonBody(String bodyJson) {
		this.jsonBody = bodyJson;
	}
	public List<NameValuePair> getUrlEncodedParams() {
		return urlEncodedParams;
	}
	public void setUrlEncodedParams(List<NameValuePair> urlEncodedParams) {
		this.urlEncodedParams = urlEncodedParams;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
