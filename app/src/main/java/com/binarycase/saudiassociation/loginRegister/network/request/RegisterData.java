package com.binarycase.saudiassociation.loginRegister.network.request;

import com.google.gson.annotations.SerializedName;

public class RegisterData {
    @SerializedName("name") String name ;

    public RegisterData(String name, String phone, String email, String password, String device_id) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.device_id = device_id;
    }

    @SerializedName("phone") String phone;
    @SerializedName("email") String email;
    @SerializedName("password") String password;
    @SerializedName("device_id") String device_id;
}
