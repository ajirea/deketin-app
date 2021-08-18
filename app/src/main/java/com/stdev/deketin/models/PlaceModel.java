package com.stdev.deketin.models;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.SphericalUtil;
import com.stdev.deketin.api.ApiConfig;
import com.stdev.deketin.utils.Preferences;

public class PlaceModel {

    @SerializedName("name")
    private String placeName;

    private double distance;

    @SerializedName("place_id")
    private String placeId;

    @SerializedName("photos")
    private PhotoModel[] photos;

    @SerializedName("vicinity")
    private String vicinity;

    @SerializedName("geometry")
    private GeometryModel geometry;

    @SerializedName("formatted_address")
    private String formattedAddress;

    @SerializedName("formatted_phone_number")
    private String formattedPhoneNumber;

    @SerializedName("url")
    private String url;

    @SerializedName("business_status")
    private String businessStatus;

    @SerializedName("opening_hours")
    private OpeningHourModel openingHours;

    private String thumbnail;
    private String address;
    private String date;

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public double getDistance(Context context) {
        if(geometry == null) return 0D;

        LocationModel currentLocation = Preferences.getCurrentLocation(context);

        String[] dummyLocation = ApiConfig.DUMMY_LOCATION.split(",");
        // set dummy location
        LatLng current = new LatLng(currentLocation.getLat(), currentLocation.getLng());

        LatLng destination = new LatLng(geometry.getLocation().getLat(), geometry.getLocation().getLng());

        return SphericalUtil.computeDistanceBetween(current, destination);
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public PhotoModel[] getPhotos() {
        return photos;
    }

    public void setPhotos(PhotoModel[] photos) {
        this.photos = photos;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public GeometryModel getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryModel geometry) {
        this.geometry = geometry;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getFormattedPhoneNumber() {
        return formattedPhoneNumber;
    }

    public void setFormattedPhoneNumber(String formattedPhoneNumber) {
        this.formattedPhoneNumber = formattedPhoneNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
    }

    public OpeningHourModel getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHourModel openingHours) {
        this.openingHours = openingHours;
    }
}
