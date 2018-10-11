package com.binarycase.saudiassociation.loginRegister.network.request;

import com.google.gson.annotations.SerializedName;

public class ResetData {
    @SerializedName("token") String token;
    @SerializedName("password") String password;
    @SerializedName("device_id") String device_id;

    public ResetData(String token, String password, String device_id) {
        this.token = token;
        this.password = password;
        this.device_id = device_id;
    }
}
