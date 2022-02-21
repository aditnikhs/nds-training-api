package com.training.api.global;

import java.sql.Timestamp;

import com.training.api.model.ResponseModel;

public class GlobalFunction {
    public static String returnResponse(ResponseModel responseModel) {
		
		responseModel.setResponseTime(new Timestamp(System.currentTimeMillis()));
		
		return GlobalConstant.gson.toJson(responseModel);
		
	}
}
