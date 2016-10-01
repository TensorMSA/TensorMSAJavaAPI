package com.posco.tensormsa.predict;

import java.util.List;


// {"status": "number", "result": "[0.1, 0.3]"}
public class PredictResult {

	private String status;
	private List<List<Double>> result;
	
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<List<Double>> getResult() {
		return result;
	}
	public void setResult(List<List<Double>> result) {
		this.result = result;
	}
	
	
	
	
}
