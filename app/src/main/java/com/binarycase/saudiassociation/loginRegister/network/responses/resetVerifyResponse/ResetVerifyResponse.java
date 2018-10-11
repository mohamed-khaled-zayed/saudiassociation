package com.binarycase.saudiassociation.loginRegister.network.responses.resetVerifyResponse;

import com.google.gson.annotations.SerializedName;

public class ResetVerifyResponse{

	@SerializedName("token")
	private String token;

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	@Override
 	public String toString(){
		return 
			"ResetVerifyResponse{" + 
			"token = '" + token + '\'' + 
			"}";
		}
}