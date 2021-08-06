package com.stdev.deketin.presenters;

import android.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.stdev.deketin.R;
import com.stdev.deketin.api.ApiConfig;
import com.stdev.deketin.api.MapApiEndpoint;
import com.stdev.deketin.api.MapApiService;
import com.stdev.deketin.database.dao.VisitHistoryDao;
import com.stdev.deketin.models.LocationModel;
import com.stdev.deketin.models.NearbySearchResponseModel;
import com.stdev.deketin.models.PlaceModel;
import com.stdev.deketin.models.PlaceVisitHistoryModel;
import com.stdev.deketin.utils.Preferences;
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
    private List<PlaceVisitHistoryModel> lastVisitedPlaces = new ArrayList<>();
    private MainView view;
    private MapApiService api;
    private VisitHistoryDao visitHistoryDao;
    private static final int RECOMMENDED_PLACE_RADIUS = 200; // meters

    public MainViewPresenterImpl(MainView view, VisitHistoryDao visitHistoryDao) {
        this.view = view;
        this.visitHistoryDao = visitHistoryDao;
        api = MapApiEndpoint.getClient().create(MapApiService.class);
    }

    public void fetchRecommendedPlaces() {
        Map<String, String> options = new HashMap<>();

        LocationModel currentLocation = Preferences.getCurrentLocation(view.getBinding().getRoot().getContext());

        options.put("radius", String.valueOf(RECOMMENDED_PLACE_RADIUS));
        options.put("location", currentLocation.toString());
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

    public void fetchLastVisitedPlaces() {
        lastVisitedPlaces.addAll(visitHistoryDao.take(10));
        view.onLoadLastVisitedPlaces(lastVisitedPlaces);
    }

    @Override
    public void load() {
        lastVisitedPlaces.clear();
        recommendedPlaces.clear();
        fetchRecommendedPlaces();
        fetchLastVisitedPlaces();
    }
}
