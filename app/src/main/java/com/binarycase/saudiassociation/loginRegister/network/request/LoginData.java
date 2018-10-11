package com.binarycase.saudiassociation.loginRegister.network.request;

import com.google.gson.annotations.SerializedName;

public class LoginData {
    public LoginData(String phone, String password, String device_id) {
        this.phone = phone;
        this.password = password;
        this.device_id = device_id;
    }

    @SerializedName("phone") String phone;
    @SerializedName("password") String password;
    @SerializedName("device_id") String device_id;

}
