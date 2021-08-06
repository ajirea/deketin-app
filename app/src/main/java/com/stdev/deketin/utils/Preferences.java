package com.stdev.deketin.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;

import com.stdev.deketin.models.LocationModel;

public class Preferences {
    private static final String PREF_SESSION = "com.stdev.deketin.session";
    private static final String CURRENT_LOCATION = "CURRENT_LOCATION";
    private static final String CURRENT_ADDRESS_SHORT = "CURRENT_ADDRESS_SHORT";
    private static final String CURRENT_ADDRESS_LONG = "CURRENT_ADDRESS_LONG";
    private static final String WALKED_STATUS = "WALKED_STATUS";

    private Context context;

    public static void setCurrentLocationPreferences(Context context, LocationModel location, Address address) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CURRENT_LOCATION, location.toString());
        editor.putString(CURRENT_ADDRESS_SHORT, address.getLocality());
        editor.putString(CURRENT_ADDRESS_LONG,address.getAddressLine(0));

        editor.apply();
    }

    public static LocationModel getCurrentLocation(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);

        String prefLoc = preferences.getString(CURRENT_LOCATION, null);
        LocationModel location = new LocationModel();

        if(prefLoc != null) {
            String[] splittedLoc = prefLoc.split(",");
            location.setLat(Double.parseDouble(splittedLoc[0]));
            location.setLng(Double.parseDouble(splittedLoc[1]));
        }

        return location;
    }

    public static String getCurrentAddress(Context context, boolean isShort) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);

        return preferences.getString(isShort ? CURRENT_ADDRESS_SHORT : CURRENT_ADDRESS_LONG, null);
    }

    public static void setWalkedStatus(Context context, boolean status) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(WALKED_STATUS, status);
        editor.apply();
    }

    public static boolean getWalkedStatus(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);

        return preferences.getBoolean(WALKED_STATUS, false);
    }
}
