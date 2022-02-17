package com.example.springboot.constants;

import com.example.springboot.controller.response.ResultInfo;

public class ResultInfoConstants {
    public static final ResultInfo SUCCESS = new ResultInfo("SUCCESS");
    public static final ResultInfo SITE_NOT_FOUND = new ResultInfo("SITE_NOT_FOUND");
    public static final ResultInfo FOLDER_NOT_FOUND = new ResultInfo("FOLDER_NOT_FOUND");
    public static final ResultInfo DUPLICATE_NAME = new ResultInfo("NAME ALREADY USED");
    public static final ResultInfo DUPLICATE_NUMBER = new ResultInfo("NUMBER ALREADY LOGGED IN");
    public static final ResultInfo NUMBER_DOES_NOT_EXIST = new ResultInfo("USER NOT FOUND,SIGNUP REQUIRED");
    public static final ResultInfo INVALID_MOBILE = new ResultInfo("MOBILE_NUMBER_INVALID");
    public static final ResultInfo INVALID_MPIN = new ResultInfo("MPIN_INVALID");
    public static final ResultInfo INVALID_OTP = new ResultInfo("OTP_INVALID");
    public static final ResultInfo BAD_CREDENTIAL = new ResultInfo("BAD CREDENTIAL");
    public static final ResultInfo USER_NOT_FOUND = new ResultInfo("USER_NOT_FOUND");
    public static final ResultInfo FOLDER_EXISTS = new ResultInfo("THIS FOLDER ALREADY EXISTS");
    public static final ResultInfo NOT_LOGGED_IN = new ResultInfo("USER NOT LOGGED IN");
    public static final ResultInfo FAILED = new ResultInfo("FAILED");

}
