package com.binarycase.saudiassociation.loginRegister.network.request;

import com.google.gson.annotations.SerializedName;

public class ShrootData {
    @SerializedName("device_id") String device_id;

    public ShrootData(String device_id, String content_slug) {
        this.device_id = device_id;
        this.content_slug = content_slug;
    }

    @SerializedName("content_slug") String content_slug;
}
