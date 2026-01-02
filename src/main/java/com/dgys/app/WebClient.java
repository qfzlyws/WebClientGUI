package com.dgys.app;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class WebClient {
	private HttpClient httpClient = HttpClientBuilder.create().build();
	
	public String postWithUrlEncodedParam(String urlString, Map<String, String> headerParams, List<NameValuePair> urlEncodedParams) throws IOException {
		HttpPost httpPost = new HttpPost(urlString);
		
		for(Map.Entry<String, String> entry : headerParams.entrySet()) {
			httpPost.setHeader(entry.getKey(), entry.getValue());
		}
		
		if(headerParams.isEmpty())
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(urlEncodedParams);
		httpPost.setEntity(entity);
		
		return getResponse(httpPost);
	}
	
	public String getWithBearerToken(String urlString, Map<String, String> headerParams, String token) throws IOException {		
		HttpGet httpGet = new HttpGet(urlString);
		
		for(Map.Entry<String, String> entry : headerParams.entrySet()) {
			httpGet.setHeader(entry.getKey(), entry.getValue());
		}
		
		if(headerParams.isEmpty())
			httpGet.addHeader("Authorization", "Bearer " + token);
		
		return getResponse(httpGet);
	}
	
	public String postWithJSON(String urlString, Map<String, String> headerParams, String jsonBody) throws IOException {		
		HttpPost httpPost = new HttpPost(urlString);
		
		for(Map.Entry<String, String> entry : headerParams.entrySet()) {
			httpPost.setHeader(entry.getKey(), entry.getValue());
		}
		
		if(headerParams.isEmpty())
			httpPost.setHeader("Content-Type", "application/json");
		
		httpPost.setEntity(new StringEntity(jsonBody,"UTF-8"));
		
		return getResponse(httpPost);
	}
	
	private String getResponse(HttpUriRequest request) throws IOException {
		String response = "";
		
		HttpResponse httpResponse = httpClient.execute(request);
		int responseCode = httpResponse.getStatusLine().getStatusCode();
		
		if(responseCode != 200)
			response = httpResponse.toString();
		else
			response = EntityUtils.toString(httpResponse.getEntity());
		
		return response;
	}
}
