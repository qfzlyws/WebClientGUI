package com.dgys.app.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.dgys.app.ui.AuthPanel.AuthOption;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

@SuppressWarnings("serial")
public class RequestDataDeserializer extends StdDeserializer<RequestData> {

	protected RequestDataDeserializer() {
		super(RequestData.class);
	}

	@Override
	public RequestData deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		JsonNode jsonNode = p.getCodec().readTree(p);
		String name = jsonNode.get("name").asText();
		String url = jsonNode.get("url").asText();
		String method = jsonNode.get("method").asText();
		Map<String,String> headerParams = new HashMap<>();
		
		jsonNode.get("headerParams").fields().forEachRemaining(field -> 
			headerParams.put(field.getKey(), field.getValue().asText())
		);
		
		AuthOption authType = AuthOption.valueOf(jsonNode.get("authType").asText());
		String tokenText = jsonNode.get("tokenText").asText();
		byte bodyOption = (byte) jsonNode.get("bodyOption").asInt();
		String jsonBody = jsonNode.get("jsonBody").asText();
		List<NameValuePair> urlEncodedParams = new ArrayList<>();
		
		jsonNode.get("urlEncodedParams").forEach(field ->
			urlEncodedParams.add(new BasicNameValuePair(field.get("name").asText(), field.get("value").asText()))
		);
		
		RequestData requestData = new RequestData();
		requestData.setName(name);
		requestData.setUrl(url);
		requestData.setMethod(method);
		requestData.setHeaderParams(headerParams);
		requestData.setAuthType(authType);
		requestData.setTokenText(tokenText);
		requestData.setBodyOption(bodyOption);
		requestData.setJsonBody(jsonBody);
		requestData.setUrlEncodedParams(urlEncodedParams);
		
		return requestData;
	}	
}
