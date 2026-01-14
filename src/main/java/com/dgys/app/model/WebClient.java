package com.dgys.app.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.dgys.app.ui.AuthPanel.AuthOption;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebClient {
	private HttpClient httpClient = HttpClientBuilder.create().build();
	private List<RequestData> requestDataList;
	private File configFile = new File("WebClientGUI.json");
	
	public WebClient() {
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			requestDataList = objectMapper.readValue(configFile, new TypeReference<List<RequestData>>() {});
		} catch(Exception e) {
			requestDataList = new ArrayList<>();
		}
	}
	
	public String sendRequest(RequestData requestData) throws IOException {
		if(requestData.getMethod().equals("POST") && requestData.getBodyOption() == 2) {
			return postWithUrlEncodedParam(requestData);
		} else if(requestData.getMethod().equals("POST") && requestData.getBodyOption() == 3) {
			return postWithJSON(requestData);
		} else if(requestData.getMethod().equals("GET") && requestData.getAuthType() == AuthOption.BEARERTOKEN) {
			return getWithBearerToken(requestData);
		}
		
		return "";
	}
	
	private String postWithUrlEncodedParam(RequestData requestData) throws IOException {
		HttpPost httpPost = new HttpPost(requestData.getUrl());
		
		for(Map.Entry<String, String> entry : requestData.getHeaderParams().entrySet()) {
			httpPost.setHeader(entry.getKey(), entry.getValue());
		}
		
		if(requestData.getHeaderParams().isEmpty())
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(requestData.getUrlEncodedParams());
		httpPost.setEntity(entity);
		
		return getResponse(httpPost);
	}
	
	private String getWithBearerToken(RequestData requestData) throws IOException {		
		HttpGet httpGet = new HttpGet(requestData.getUrl());
		
		for(Map.Entry<String, String> entry : requestData.getHeaderParams().entrySet()) {
			httpGet.setHeader(entry.getKey(), entry.getValue());
		}
		
		if(requestData.getHeaderParams().isEmpty())
			httpGet.addHeader("Authorization", "Bearer " + requestData.getTokenText());
		
		return getResponse(httpGet);
	}
	
	private String postWithJSON(RequestData requestData) throws IOException {		
		HttpPost httpPost = new HttpPost(requestData.getUrl());
		
		for(Map.Entry<String, String> entry : requestData.getHeaderParams().entrySet()) {
			httpPost.setHeader(entry.getKey(), entry.getValue());
		}
		
		if(requestData.getHeaderParams().isEmpty())
			httpPost.setHeader("Content-Type", "application/json");
		
		httpPost.setEntity(new StringEntity(requestData.getJsonBody(),"UTF-8"));
		
		return getResponse(httpPost);
	}
	
	public void saveRequestData(RequestData requestData) throws IOException {
		if (!requestDataList.contains(requestData))
			requestDataList.add(requestData);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(configFile, requestDataList);
	}
	
	public void deleteRequestData(RequestData requestData) throws IOException {
		if (!requestDataList.contains(requestData))
			return;
		
		requestDataList.remove(requestData);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(configFile, requestDataList);
	}
	
	public List<RequestData> getRequestDataList() {
		return requestDataList;
	}
	
	public RequestData newRequestData() {
		RequestData requestData = new RequestData();
		requestData.setName("");
		requestData.setUrl("");
		requestData.setMethod("GET");
		requestData.setAuthType(AuthOption.NONE);
		requestData.setTokenText("");
		requestData.setBodyOption(Byte.parseByte("1"));
		requestData.setJsonBody("");
		requestData.setHeaderParams(new HashMap<>());
		requestData.setUrlEncodedParams(new ArrayList<>());
		return requestData;
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
