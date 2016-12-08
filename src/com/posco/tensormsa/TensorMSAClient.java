package com.posco.tensormsa;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.posco.tensormsa.predict.ContentResult;
import com.posco.tensormsa.predict.PredictRequest;
import com.posco.tensormsa.predict.PredictResult;


public class TensorMSAClient {
	
	private ObjectMapper objectMapper;
	//private String host = "http://192.168.92.177:8989";
	// http://52.78.19.96:8989
	private String host = "http://52.78.19.96:8989";
	
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
	


//	public PredictResult predict(String networkId , List<Map<String,String>> request) throws ClientProtocolException, IOException{
	
	public List<List<Object>> predict(String networkId , String request) throws ClientProtocolException, IOException{
		
		
		String url = host + "/api/v1/type/wdnn/predict/" + networkId + "/";
			
		System.out.println("request ===" + request);
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		File file = new File(request);
		String message = "post";
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addBinaryBody("upfile", file, ContentType.DEFAULT_BINARY, request);
		builder.addTextBody("text", message, ContentType.DEFAULT_BINARY);
		HttpEntity entity = builder.build();
		
	// 	String jsonParam = objectMapper.writeValueAsString(request);
	//	String jsonParam = "[{'pclass':'6st', 'survived':'tag', 'sex':'female', 'age':'60', 'embarked':'Southamptom', 'boat':'2'}]";
//		System.out.println("param : " + jsonParam);
//		StringEntity params = new StringEntity(jsonParam);
		System.out.println("param entity : " + entity);
		post.setEntity(entity);
		//post.addHeader("content-type", "application/x-www-form-urlencoded");
				
		HttpResponse response = client.execute(post);
		
		
		
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + post.getEntity());
		System.out.println("Response Code : " +
                                    response.getStatusLine().getStatusCode());
		
		
		
		
		//   PredictResult result = new PredictResult();
		
		
		  

		    String resultall = getResponseContent(response);
		    System.out.println("----------real resultall 1 --------- : " + resultall);
		    
			resultall = refineJson(resultall);
			
			//System.out.println("----------real resultall 2--------- : " + resultall);
			
		//	resultall = refineJson(resultall);
			
			System.out.println("----------real resultall 3--------- : " + resultall);
			resultall = resultall.replace("\"\"[", "\"[");
			System.out.println("----------real resultall 4--------- : " + resultall);
			resultall = resultall.replace("]\"", "]");
			
			resultall = resultall.substring(1, resultall.length() - 1);
			System.out.println("----------real resultall 5--------- : " +  resultall);
			//PredictResult predictresult = objectMapper.readValue(resultall, PredictResult.class);
//			 "[["Sepal length", "Sepal width", "Petal length", "Petal width", "Species", "label", "predict_label"], [5.1, 3.5, 1.4, 0.2, "setosa", 0, "setosa"], [4.9, 3.0, 1.4, 0.2, "setosa", 0, "setosa"], [4.7, 3.2, 1.3, 0.2, "setosa", 0, "setosa"], [4.6, 3.1, 1.5, 0.2, "setosa", 0, "setosa"]]"
//		    resultall = "[[1, 2, 3]]";
			List<List<Object>> predictresult = objectMapper.readValue(resultall, List.class);
			System.out.println(predictresult);
			
//			PredictResult predictresult = null;
		
		return predictresult;
		
	}


	private String refineJson(String resultall) {
//		if(resultall.startsWith("\"") ) {
//			resultall = resultall.substring(1,resultall.length()-1);
//			System.out.println("----------refineJson resultall--------- : " + resultall);
//		} 
		resultall = resultall.replaceAll("\\\\", "");
	//	resultall = resultall.replaceAll("\"[", "\"");
		//resultall = resultall.replaceAll("\\\\\\", "\"");
		System.out.println("----------resultall--------- : " + resultall);
		return resultall;
	}
	
	
//	Reader in = new FileReader("path/to/file.csv");
//	Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
//	for (CSVRecord record : records) {
//	    String lastName = record.get("Last Name");
//	    String firstName = record.get("First Name");
//	}
	
//	public PredictResult predict(String networkId , String filePath) throws Exception{
		
		
		
//		List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();
//		
//		Reader in = new FileReader(filePath);
//		Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
//		for (CSVRecord record : records) {
//			System.out.println("----------resultall222--------- : " + record.toString());
//			
//			
//			Map<String, String> recordMap = record.toMap();
//			dataList.add(recordMap);
//			System.out.println("----------resultall3--------- : " + dataList);
//			
//		}
//		
//		//getInfo();
//
//		return predict(networkId, dataList);

		
//	}
	
	public void getInfo() throws ClientProtocolException, IOException, ParseException {
		
//		String networkId = "nn0000010";
//		String url = host + "/api/v1/type/common/nninfo/" + networkId + "/category//subcate//";
//		
//
//		HttpClient client = new DefaultHttpClient();
//		HttpGet get = new HttpGet(url);
//		
//        HttpResponse response = client.execute(get);
//		
//		
//		
//		System.out.println("\nSending 'POST' request to URL : " + url);
//	
//		System.out.println("Response Code : " +
//                                    response.getStatusLine().getStatusCode());
//		
//		
//		String content  =  getResponseContent(response);
//		System.out.println("content : " + content);
//		Map fromJson = new Gson().fromJson(refineJson(content), Map.class);
//		System.out.println(fromJson);
//		JSONParser jsonPaser = new JSONParser();
//		String escape = StringEscapeUtils.unescapeJson(refineJson(content));
//		System.out.println("unescape : " + escape);
//		JSONObject jsonOb = (JSONObject) jsonPaser.parse(escape);
//		String result = (String) jsonOb.get("result");
//		System.out.println("result : " + result);
//		System.out.println("rr : " + StringEscapeUtils.unescapeJson(refineJson(result)));
//		JSONObject datadesc = (JSONObject) jsonPaser.parse(StringEscapeUtils.unescapeJson(refineJson(result)));
//		System.out.print("readVale : " + datadesc);

		
		
		
	}


	private String getResponseContent(HttpResponse response) throws IOException {
		BufferedReader rd = new BufferedReader(
	            new InputStreamReader(response.getEntity().getContent()));
			
			
			
		StringBuffer result = new StringBuffer();
		
		String line = "\n";
		while ((line = rd.readLine()) != null) {
			result.append(line);
			//System.out.println("---------result --------- : " + result);
		}
		
		return result.toString();
	}
	
	
}
