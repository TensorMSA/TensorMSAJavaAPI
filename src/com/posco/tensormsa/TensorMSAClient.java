package com.posco.tensormsa;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.posco.tensormsa.predict.PredictRequest;
import com.posco.tensormsa.predict.PredictResult;


public class TensorMSAClient {
	
	private ObjectMapper objectMapper;
	private String host = "http://192.168.92.177:8989";
	
	public void setHost(String host) {
		
		this.host = host;
		
	}
	
	
	// »ý¼ºÀÚ
	public TensorMSAClient() {
	
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);	
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.setSerializationInclusion(Include.ALWAYS);
		
	
	}
	


	public PredictResult predict(PredictRequest request) throws ClientProtocolException, IOException{
		
		String url = host + "/api/v1/type/cnn/predict/";
		
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		
		String jsonParam = objectMapper.writeValueAsString(request);
		System.out.println("param : " + jsonParam);
		StringEntity params = new StringEntity(jsonParam);
		
		post.setEntity(params);
		post.addHeader("content-type", "application/x-www-form-urlencoded");
				
		HttpResponse response = client.execute(post);
		
		
		
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + post.getEntity());
		System.out.println("Response Code : " +
                                    response.getStatusLine().getStatusCode());
		
		
		
		
		//PredictResult result = new PredictResult();
		
		
		BufferedReader rd = new BufferedReader(
	            new InputStreamReader(response.getEntity().getContent()));
			
			
			
			StringBuffer result = new StringBuffer();
			
			String line = "\n";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

		    String resultall = result.toString();
			
			resultall = refineJson(resultall);
				
			
			
			
			PredictResult predictresult = objectMapper.readValue(resultall, PredictResult.class);
			
			
			
		
		
		return predictresult;
		
	}


	private String refineJson(String resultall) {
		if(resultall.startsWith("\"")) {
			resultall = resultall.substring(1,resultall.length()-1);
		} 
		
		resultall = resultall.replaceAll("\\\\\"", "\"");
		System.out.println("----------resultall--------- : " + resultall);
		return resultall;
	}
}
