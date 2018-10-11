package com.binarycase.saudiassociation.loginRegister.network.request;

import com.google.gson.annotations.SerializedName;

public class ResendSmsData {
    @SerializedName("phone") String phone;

    public ResendSmsData(String phone, String device_id) {
        this.phone = phone;
        this.device_id = device_id;
    }

    @SerializedName("device_id") String device_id;
}
