package com.binarycase.saudiassociation.loginRegister.network.responses.verifyResponse;

import com.google.gson.annotations.SerializedName;

public class VerifyResponse{

	@SerializedName("user")
	private User user;

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	@Override
 	public String toString(){
		return 
			"VerifyResponse{" + 
			"user = '" + user + '\'' + 
			"}";
		}
}