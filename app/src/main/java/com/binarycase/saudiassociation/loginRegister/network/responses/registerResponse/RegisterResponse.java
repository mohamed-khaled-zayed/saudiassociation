package com.binarycase.saudiassociation.loginRegister.network.responses.registerResponse;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse{

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
			"RegisterResponse{" + 
			"user = '" + user + '\'' + 
			"}";
		}
}