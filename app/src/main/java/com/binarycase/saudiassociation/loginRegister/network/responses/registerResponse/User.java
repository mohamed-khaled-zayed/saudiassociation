package com.binarycase.saudiassociation.loginRegister.network.responses.registerResponse;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("phone")
	private String phone;

	@SerializedName("sms_code")
	private String smsCode;

	@SerializedName("api_token")
	private String apiToken;

	@SerializedName("name")
	private String name;

	@SerializedName("sms")
	private Sms sms;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setSmsCode(String smsCode){
		this.smsCode = smsCode;
	}

	public String getSmsCode(){
		return smsCode;
	}

	public void setApiToken(String apiToken){
		this.apiToken = apiToken;
	}

	public String getApiToken(){
		return apiToken;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setSms(Sms sms){
		this.sms = sms;
	}

	public Sms getSms(){
		return sms;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"User{" + 
			"updated_at = '" + updatedAt + '\'' + 
			",phone = '" + phone + '\'' + 
			",sms_code = '" + smsCode + '\'' + 
			",api_token = '" + apiToken + '\'' + 
			",name = '" + name + '\'' + 
			",sms = '" + sms + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}