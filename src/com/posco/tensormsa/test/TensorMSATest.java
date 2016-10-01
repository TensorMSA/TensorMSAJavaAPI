package com.posco.tensormsa.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.posco.tensormsa.TensorMSAClient;
import com.posco.tensormsa.predict.PredictRequest;
import com.posco.tensormsa.predict.PredictResult;

public class TensorMSATest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void main(String[] args) throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		List<Map<String, String>> predict_data = new ArrayList();
		
		Map<String, String> mapdate = new HashMap<>();
		mapdate.put("name", "Andy");
		mapdate.put("univ", "a");
		mapdate.put("org", "1");
		mapdate.put("eng", "800");
		mapdate.put("gender", "female");	
		mapdate.put("age", "50");
		
		predict_data.add(mapdate);
		
		
		PredictRequest request = new PredictRequest();
		request.setNn_id("nn0000009");
		request.setNn_type("cnn");
		request.setRun_type("local");
		request.setEpoch(null);
		request.setTestset(null);
		request.setPredict_data(predict_data);
		
		TensorMSAClient client = new TensorMSAClient();
		
		PredictResult predictResult = client.predict(request);
		
		System.out.println("predictResult.getStatus : " + predictResult.getStatus());
		System.out.println("predictResult.getResult : " + predictResult.getResult());
		
		
		
		
		

	}

}
