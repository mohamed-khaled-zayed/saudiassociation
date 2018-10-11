package com.binarycase.saudiassociation.loginRegister.network.responses.verifyResponse;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("settings")
	private Object settings;

	@SerializedName("sms_code")
	private String smsCode;

	@SerializedName("api_token")
	private String apiToken;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("avatar")
	private String avatar;

	@SerializedName("reset_code")
	private Object resetCode;

	@SerializedName("phone_verified")
	private int phoneVerified;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("role_id")
	private Object roleId;

	@SerializedName("phone")
	private String phone;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	public void setSettings(Object settings){
		this.settings = settings;
	}

	public Object getSettings(){
		return settings;
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

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setAvatar(String avatar){
		this.avatar = avatar;
	}

	public String getAvatar(){
		return avatar;
	}

	public void setResetCode(Object resetCode){
		this.resetCode = resetCode;
	}

	public Object getResetCode(){
		return resetCode;
	}

	public void setPhoneVerified(int phoneVerified){
		this.phoneVerified = phoneVerified;
	}

	public int getPhoneVerified(){
		return phoneVerified;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setRoleId(Object roleId){
		this.roleId = roleId;
	}

	public Object getRoleId(){
		return roleId;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
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
			"settings = '" + settings + '\'' + 
			",sms_code = '" + smsCode + '\'' + 
			",api_token = '" + apiToken + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",avatar = '" + avatar + '\'' + 
			",reset_code = '" + resetCode + '\'' + 
			",phone_verified = '" + phoneVerified + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",role_id = '" + roleId + '\'' + 
			",phone = '" + phone + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}