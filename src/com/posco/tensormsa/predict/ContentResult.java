package com.posco.tensormsa.predict;

public class ContentResult {

	private String status;
	private String result;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "ContentResult [status=" + status + ", result=" + result + "]";
	}
	
	
}
