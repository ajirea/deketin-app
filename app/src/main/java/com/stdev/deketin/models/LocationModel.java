package com.stdev.deketin.models;

import androidx.annotation.NonNull;

public class LocationModel {
    private double lat;
    private double lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s,%s", String.valueOf(lat), String.valueOf(lng));
    }
}
