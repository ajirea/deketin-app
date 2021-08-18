package com.stdev.deketin.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
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
    public static LocationRequest locationRequest;

    private static void initLocationRequest() {
        locationRequest = LocationRequest.create()
                .setInterval(1000 * 30)
                .setFastestInterval(1000 * 5)
                .setMaxWaitTime(1000 * 30)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @SuppressLint("MissingPermission")
    public static void getLastKnownLocation(Context context, @Nullable OnSuccessListener<Address> address) {
        initLocationRequest();
        FusedLocationProviderClient flpClient = LocationServices.getFusedLocationProviderClient(context);

        flpClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();

                mLastLocation = location;
                try {
                    Locale locale = new Locale("id", "ID");
                    Geocoder geocoder = new Geocoder(context, locale);

                    List<Address> addresses = geocoder
                            .getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    Address firstAddress = addresses.get(0);
                    LocationModel locModel = new LocationModel();
                    locModel.setLat(location.getLatitude());
                    locModel.setLng(location.getLongitude());

                    Preferences.setCurrentLocationPreferences(context, locModel, firstAddress);

                    if (address != null) address.onSuccess(firstAddress);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, Looper.getMainLooper());
    }
}
