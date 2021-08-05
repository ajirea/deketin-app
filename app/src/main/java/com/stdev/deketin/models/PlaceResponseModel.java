package com.stdev.deketin.models;

import com.google.gson.annotations.SerializedName;

public class PlaceResponseModel {
    @SerializedName("status")
    private String status;

    @SerializedName("info_messages")
    private String[] infoMessages;

    @SerializedName("result")
    private PlaceModel result;

    public PlaceResponseModel(String status, String[] infoMessages) {
        this.status = status;
        this.infoMessages = infoMessages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] getInfoMessages() {
        return infoMessages;
    }

    public void setInfoMessages(String[] infoMessages) {
        this.infoMessages = infoMessages;
    }

    public PlaceModel getResult() {
        return result;
    }

    public void setResult(PlaceModel result) {
        this.result = result;
    }
}
