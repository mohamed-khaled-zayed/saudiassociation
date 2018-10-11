package com.binarycase.saudiassociation.loginRegister.network.responses.resendSmsResponse;

import com.google.gson.annotations.SerializedName;

public class ResendSmsResponse{

	@SerializedName("sms_code")
	private String smsCode;

	@SerializedName("MessageIs")
	private String messageIs;

	@SerializedName("Code")
	private String code;

	@SerializedName("token")
	private String token;

	public void setSmsCode(String smsCode){
		this.smsCode = smsCode;
	}

	public String getSmsCode(){
		return smsCode;
	}

	public void setMessageIs(String messageIs){
		this.messageIs = messageIs;
	}

	public String getMessageIs(){
		return messageIs;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	@Override
 	public String toString(){
		return 
			"ResendSmsResponse{" + 
			"sms_code = '" + smsCode + '\'' + 
			",messageIs = '" + messageIs + '\'' + 
			",code = '" + code + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}