package com.binarycase.saudiassociation.loginRegister.network.responses.shrootResponse;

import com.google.gson.annotations.SerializedName;

public class ShrootResponse{

	@SerializedName("title")
	private String title;

	@SerializedName("content")
	private String content;

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return content;
	}

	@Override
 	public String toString(){
		return 
			"ShrootResponse{" + 
			"title = '" + title + '\'' + 
			",content = '" + content + '\'' + 
			"}";
		}
}