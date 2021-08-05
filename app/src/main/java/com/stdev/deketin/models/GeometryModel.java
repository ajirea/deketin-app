package com.stdev.deketin.models;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class GeometryModel {
    private LocationModel location;

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }
}
