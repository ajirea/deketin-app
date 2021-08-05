package com.stdev.deketin.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NearbySearchResponseModel {
    public List<PlaceModel> results;

    @SerializedName("status")
    private String status;

    @SerializedName("info_messages")
    private String[] infoMessages;

    @SerializedName("next_page_token")
    private String nextPageToken;

    public NearbySearchResponseModel(String status, String[] infoMessages) {
        this.status = status;
        this.infoMessages = infoMessages;
    }

    public void setResults(List<PlaceModel> results) {
        this.results = results;
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

    public List<PlaceModel> getResults() {
        return results;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }
}
