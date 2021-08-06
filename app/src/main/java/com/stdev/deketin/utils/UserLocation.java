package com.stdev.deketin.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import androidx.annotation.Nullable;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.stdev.deketin.MainActivity;
import com.stdev.deketin.models.LocationModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

public class UserLocation {
    public static Location mLastLocation;

    @SuppressLint("MissingPermission")
    public static void getLastKnownLocation(Context context, @Nullable OnSuccessListener<Address> address) {
        FusedLocationProviderClient flpClient = LocationServices.getFusedLocationProviderClient(context);

        flpClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    mLastLocation = location;
                    Locale locale = new Locale("id", "ID");
                    Geocoder geocoder = new Geocoder(context, locale);
                    try {
                        List<Address> addresses = geocoder
                                .getFromLocation(location.getLatitude(), location.getLongitude(), 2);
                        Address firstAddress = addresses.get(0);
                        LocationModel locModel = new LocationModel();
                        locModel.setLat(location.getLatitude());
                        locModel.setLng(location.getLongitude());

                        Preferences.setCurrentLocationPreferences(context, locModel, firstAddress);

                        if(address != null)  address.onSuccess(firstAddress);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
