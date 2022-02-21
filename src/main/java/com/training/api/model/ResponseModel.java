package com.training.api.model;

import java.sql.Timestamp;

import com.training.api.global.GlobalConstant;

public class ResponseModel {
    private String responseCode;  	
	private String responseMessage;	
	private String responseDescription;
	private Timestamp responseTime;     
	private Object responseData;
	
	public ResponseModel() {
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public Timestamp getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Timestamp responseTime) {
		this.responseTime = responseTime;
	}

	public Object getResponseData() {
		return responseData;
	}

	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	} 
	
	public String toGsonString() {
		return GlobalConstant.gson.toJson(this);
	}

}
