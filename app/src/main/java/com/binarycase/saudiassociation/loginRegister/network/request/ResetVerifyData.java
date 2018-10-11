package com.binarycase.saudiassociation.loginRegister.network.request;

import com.google.gson.annotations.SerializedName;

public class ResetVerifyData {
    @SerializedName("token") String token;
    @SerializedName("reset_code") String reset_code;
    @SerializedName("device_id") String device_id;

    public ResetVerifyData(String token, String reset_code, String device_id) {
        this.token = token;
        this.reset_code = reset_code;
        this.device_id = device_id;
    }
}
