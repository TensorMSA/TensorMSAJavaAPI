package com.posco.tensormsa.predict;

import java.util.List;
import java.util.Map;

public class PredictRequest {
	
	//{ "nn_id": "string" , 
//	  "nn_type" : "string", 
//	  "run_type" : "string", 
//	  "epoch" : "number", 
//	  "testset" : "number" , 
//	  "predict_data":"List -Object"}

	private String nn_id;
	private String nn_type;
	private String run_type;
	private Double epoch;
	private Double testset;
	private List<Map<String,String>> predict_data;
	public String getNn_id() {
		return nn_id;
	}
	public void setNn_id(String nn_id) {
		this.nn_id = nn_id;
	}
	public String getNn_type() {
		return nn_type;
	}
	public void setNn_type(String nn_type) {
		this.nn_type = nn_type;
	}
	public String getRun_type() {
		return run_type;
	}
	public void setRun_type(String run_type) {
		this.run_type = run_type;
	}
	public Double getEpoch() {
		return epoch;
	}
	public void setEpoch(Double epoch) {
		this.epoch = epoch;
	}
	public Double getTestset() {
		return testset;
	}
	public void setTestset(Double testset) {
		this.testset = testset;
	}
	public List<Map<String, String>> getPredict_data() {
		return predict_data;
	}
	public void setPredict_data(List<Map<String, String>> predict_data) {
		this.predict_data = predict_data;
	}

}
