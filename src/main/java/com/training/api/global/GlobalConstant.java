package com.training.api.global;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GlobalConstant {
    
	public static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();


    public static final String SUCCESS_CODE = "00";
    public static final String INTERNAL_ERROR_CODE = "01";
    public static final String EXTERNAL_ERROR_CODE = "02";

    public static final String REC_STATUS_ACTIVE = "A";
    public static final String REC_STATUS_NON_ACTIVE = "N";
    
}
