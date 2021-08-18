package com.stdev.deketin.presenters;

import android.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.stdev.deketin.R;
import com.stdev.deketin.api.MapApiEndpoint;
import com.stdev.deketin.api.MapApiService;
import com.stdev.deketin.models.PlaceModel;
import com.stdev.deketin.models.PlaceResponseModel;
import com.stdev.deketin.views.PlaceDetailView;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceDetailPresenterImpl implements PlacePresenter {
    PlaceDetailView view;
    PlaceModel place;
    MapApiService api;

    public PlaceDetailPresenterImpl(PlaceDetailView view) {
        this.view = view;
        api = MapApiEndpoint.getClient().create(MapApiService.class);
    }

    @Override
    public void load() {
        Map<String, String> options = new HashMap<>();
        options.put("language", "id");
        options.put("region", ".id");
        options.put("fields", "name,place_id,photos,vicinity,geometry,formatted_address,formatted_phone_number,url,business_status,opening_hours");
        //Log.d("detplace", String.valueOf(view.getPlaceId()));
        Call<PlaceResponseModel> call = api.getPlaceDetail(view.getPlaceId(), options);

        call.enqueue(new Callback<PlaceResponseModel>() {
            @Override
            public void onResponse(Call<PlaceResponseModel> call, Response<PlaceResponseModel> response) {
                PlaceResponseModel body = response.body();
                if(response.isSuccessful() && body != null) {
                    if(body.getStatus().equals("OK")) {
                        place = body.getResult();
                        view.onLoad(place);
                    }
                }
                Log.d("faildetail", response.toString());
            }

            @Override
            public void onFailure(Call<PlaceResponseModel> call, Throwable t) {
                new AlertDialog.Builder(null)
                        .setTitle("Kesalahan")
                        .setMessage(t.getMessage())
                        .show();
            }
        });
    }
}
