package com.stdev.deketin.utils;

import androidx.room.TypeConverter;

import com.stdev.deketin.models.LocationModel;

public class LocationConverter {
    @TypeConverter
    public LocationModel fromLocation(String value) {
        if(value == null) return null;

        String[] loc = value.split(",");
        LocationModel model = new LocationModel();
        model.setLat(Double.parseDouble(loc[0]));
        model.setLng(Double.parseDouble(loc[1]));

        return model;
    }

    @TypeConverter
    public String locationModelToLocation(LocationModel value) {
        return value == null ? null : value.toString();
    }
}
