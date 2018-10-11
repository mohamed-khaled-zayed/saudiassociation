package com.binarycase.saudiassociation.loginRegister.network.responses.registerResponse;

import com.google.gson.annotations.SerializedName;

public class Sms{

	@SerializedName("MessageIs")
	private String messageIs;

	@SerializedName("Code")
	private String code;

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

	@Override
 	public String toString(){
		return 
			"Sms{" + 
			"messageIs = '" + messageIs + '\'' + 
			",code = '" + code + '\'' + 
			"}";
		}
}