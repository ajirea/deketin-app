package com.stdev.deketin.api;

import androidx.annotation.Nullable;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.maps.model.LatLng;
import com.stdev.deketin.models.NearbySearchResponseModel;
import com.stdev.deketin.models.PlaceResponseModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface MapApiService {

    @GET(ApiConfig.PATH_NEARBY_SEARCH)
    Call<NearbySearchResponseModel> getNearbySearch(@QueryMap Map<String, String> options);

    @GET(ApiConfig.PATH_PLACE_DETAIL)
    Call<PlaceResponseModel> getPlaceDetail(@Query("place_id") String placeId, @QueryMap Map<String, String> options);
}
