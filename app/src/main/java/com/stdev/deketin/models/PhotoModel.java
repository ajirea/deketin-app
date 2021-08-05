package com.stdev.deketin.models;

import com.google.gson.annotations.SerializedName;
import com.stdev.deketin.api.ApiConfig;

public class PhotoModel {
    private int height;
    private int width;
    private int maxWidth;
    private int maxHeight;

    @SerializedName("photo_reference")
    private String photoReference;

    @SerializedName("html_attributes")
    private String[] htmlAttributes;

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public String[] getHtmlAttributes() {
        return htmlAttributes;
    }

    public void setHtmlAttributes(String[] htmlAttributes) {
        this.htmlAttributes = htmlAttributes;
    }

    public String getPhotoUrl() {
        return String.format("%s%s?maxwidth=%s&photoreference=%s&key=%s",
                ApiConfig.BASE_URL, ApiConfig.PATH_PHOTO, maxWidth, photoReference, ApiConfig.API_KEY);
    }
}
