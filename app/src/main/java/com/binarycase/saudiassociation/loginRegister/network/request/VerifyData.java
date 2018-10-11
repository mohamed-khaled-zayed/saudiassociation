package com.binarycase.saudiassociation.loginRegister.network.request;

import com.google.gson.annotations.SerializedName;

public class VerifyData {
    @SerializedName("token") String token;
    @SerializedName("sms_code") String sms_code;
    @SerializedName("device_id") String device_id;

    public VerifyData(String token, String sms_code, String device_id) {
        this.token = token;
        this.sms_code = sms_code;
        this.device_id = device_id;
    }
}
