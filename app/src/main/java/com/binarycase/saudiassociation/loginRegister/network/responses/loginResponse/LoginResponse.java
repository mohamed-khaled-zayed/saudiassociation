package com.binarycase.saudiassociation.loginRegister.network.responses.loginResponse;

import com.google.gson.annotations.SerializedName;

public class LoginResponse{

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
			"LoginResponse{" + 
			"user = '" + user + '\'' + 
			"}";
		}
}