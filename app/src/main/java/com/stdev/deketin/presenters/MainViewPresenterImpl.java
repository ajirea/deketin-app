package com.stdev.deketin.presenters;

import android.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.stdev.deketin.R;
import com.stdev.deketin.api.ApiConfig;
import com.stdev.deketin.api.MapApiEndpoint;
import com.stdev.deketin.api.MapApiService;
import com.stdev.deketin.models.NearbySearchResponseModel;
import com.stdev.deketin.models.PlaceModel;
import com.stdev.deketin.views.MainView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewPresenterImpl implements PlacePresenter {
    private final List<PlaceModel> recommendedPlaces = new ArrayList<>();
    private List<PlaceModel> lastVisitedPlaces = new ArrayList<>();
    private MainView view;
    private MapApiService api;
    private static final int RECOMMENDED_PLACE_RADIUS = 200; // meters

    public MainViewPresenterImpl(MainView view) {
        this.view = view;
        api = MapApiEndpoint.getClient().create(MapApiService.class);
    }

    private void generateDummyData() {
        String thumb = "https://images.unsplash.com/photo-1519494026892-80bbd2d6fd0d?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=140&q=80";

        PlaceModel place1 = new PlaceModel();
        place1.setPlaceName("AWI Creative");
        place1.setDistance(0.1);
        place1.setThumbnail(thumb);

        PlaceModel place2 = new PlaceModel();
        place2.setPlaceName("Warung Ana Teh Eni");
        place2.setDistance(0.1);
        place2.setThumbnail(thumb);

        PlaceModel place3 = new PlaceModel();
        place3.setPlaceName("Toko Pupuk Tani Sejahtera");
        place3.setDistance(0.5);
        place3.setThumbnail(thumb);

        PlaceModel place4 = new PlaceModel();
        place4.setPlaceName("Al-Husaeniyah");
        place4.setDistance(0.3);
        place4.setThumbnail(thumb);

        PlaceModel place5 = new PlaceModel();
        place5.setPlaceName("Koperasi Palagan Makmur");
        place5.setDistance(0.7);
        place5.setThumbnail(thumb);

        PlaceModel place6 = new PlaceModel();
        place6.setPlaceName("Al-Husaeniyah");
        place6.setDistance(0.3);
        place6.setThumbnail(thumb);

        PlaceModel place7 = new PlaceModel();
        place7.setPlaceName("Koperasi Palagan Makmur");
        place7.setDistance(0.7);
        place7.setThumbnail(thumb);

        recommendedPlaces.add(place1);
        recommendedPlaces.add(place2);
        recommendedPlaces.add(place3);
        recommendedPlaces.add(place4);
        recommendedPlaces.add(place5);

        lastVisitedPlaces.add(place6);
        lastVisitedPlaces.add(place7);
    }

    public void fetchRecommendedPlaces() {
        Map<String, String> options = new HashMap<>();
        options.put("radius", String.valueOf(RECOMMENDED_PLACE_RADIUS));
        options.put("location", ApiConfig.DUMMY_LOCATION); // todo: change to gps coordinate
        options.put("language", "id");

        Call<NearbySearchResponseModel> call = api.getNearbySearch(options);
        call.enqueue(new Callback<NearbySearchResponseModel>() {
            @Override
            public void onResponse(Call<NearbySearchResponseModel> call, Response<NearbySearchResponseModel> response) {
                NearbySearchResponseModel body = response.body();

                if(response.isSuccessful() && body != null) {
                    if(body.getStatus().equals("OK")) {
                        view.getBinding().recommendedLoadingText.setVisibility(View.GONE);
                        recommendedPlaces.addAll(body.results);
                        view.onLoadRecommendedPlaces(body.results);
                    }
                }
            }

            @Override
            public void onFailure(Call<NearbySearchResponseModel> call, Throwable t) {
                t.printStackTrace();

                new AlertDialog.Builder(null)
                        .setTitle("Kesalahan")
                        .setMessage(t.getMessage())
                        .show();

                view.getBinding().recommendedLoadingText.setText(R.string.failed_loading_data);
            }
        });
    }

    @Override
    public void load() {
        lastVisitedPlaces.clear();
        recommendedPlaces.clear();
        generateDummyData();
        fetchRecommendedPlaces();
    }
}
