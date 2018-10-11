package com.binarycase.saudiassociation.loginRegister.network.responses.passResetResponse;

import com.google.gson.annotations.SerializedName;

public class PassResetResponse{

	@SerializedName("reset_code")
	private String resetCode;

	@SerializedName("MessageIs")
	private String messageIs;

	@SerializedName("Code")
	private String code;

	@SerializedName("token")
	private String token;

	public void setResetCode(String resetCode){
		this.resetCode = resetCode;
	}

	public String getResetCode(){
		return resetCode;
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
			"PassResetResponse{" + 
			"reset_code = '" + resetCode + '\'' + 
			",messageIs = '" + messageIs + '\'' + 
			",code = '" + code + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}