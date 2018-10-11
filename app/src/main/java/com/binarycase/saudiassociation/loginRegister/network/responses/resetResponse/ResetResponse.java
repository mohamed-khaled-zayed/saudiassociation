package com.binarycase.saudiassociation.loginRegister.network.responses.resetResponse;

import com.google.gson.annotations.SerializedName;

public class ResetResponse{

	@SerializedName("success")
	private String success;

	public void setSuccess(String success){
		this.success = success;
	}

	public String getSuccess(){
		return success;
	}

	@Override
 	public String toString(){
		return 
			"ResetResponse{" + 
			"success = '" + success + '\'' + 
			"}";
		}
}