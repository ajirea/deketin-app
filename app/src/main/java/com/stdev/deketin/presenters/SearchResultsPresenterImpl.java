package com.stdev.deketin.presenters;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stdev.deketin.api.ApiConfig;
import com.stdev.deketin.api.MapApiEndpoint;
import com.stdev.deketin.api.MapApiService;
import com.stdev.deketin.models.LocationModel;
import com.stdev.deketin.models.NearbySearchResponseModel;
import com.stdev.deketin.models.PlaceModel;
import com.stdev.deketin.utils.Preferences;
import com.stdev.deketin.views.MainView;
import com.stdev.deketin.views.SearchResultsView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsPresenterImpl implements PlacePresenter {
    private List<PlaceModel> places = new ArrayList<>();
    private List<PlaceModel> lastVisitedPlaces = new ArrayList<>();
    private SearchResultsView view;
    private Context context;
    private MapApiService api;
    private boolean isLoading = true;

    public static NearbySearchResponseModel body;

    public SearchResultsPresenterImpl(SearchResultsView view) {
        this.view = view;

        api = MapApiEndpoint.getClient().create(MapApiService.class);

        view.getBinding().searchResultsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(body == null) return;

                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading && body.getNextPageToken() != null) {
                    if (layoutManager != null && layoutManager
                            .findLastCompletelyVisibleItemPosition() == places.size() - 1) {
                        fetchData(body.getNextPageToken());
                    }
                }
            }
        });
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private void fetchData(@Nullable String nextPageToken) {
        Map<String, String> options = new HashMap<>();
        LocationModel currentLocation = Preferences.getCurrentLocation(view.getBinding().getRoot().getContext());

        options.put("location", currentLocation.toString());
        options.put("rankby", "distance");
        options.put("language", "id");

        if(view.getType() != null) {
            options.put("type", view.getType());
        }

        if(view.getKeyword() != null) {
            options.put("keyword", view.getKeyword());
        }

        if (nextPageToken != null) {
            options.put("pagetoken", nextPageToken);
        }

        Call<NearbySearchResponseModel> call = api.getNearbySearch(options);
        call.enqueue(new Callback<NearbySearchResponseModel>() {
            @Override
            public void onResponse(Call<NearbySearchResponseModel> call, Response<NearbySearchResponseModel> response) {
                body = response.body();
                if (body.getStatus().equals("OK")) {
                    setLoadingState(false);

                    // add results to places list
                    places.addAll(body.results);
                    view.onLoad(places);
                }
            }

            @Override
            public void onFailure(Call<NearbySearchResponseModel> call, Throwable t) {
                t.printStackTrace();
                setLoadingState(false);

                if (context != null) {
                    new AlertDialog.Builder(context)
                            .setTitle("Kesalahan")
                            .setMessage(t.getMessage())
                            .show();
                }
            }
        });
    }


    private void setLoadingState(boolean state) {
        if (state) {
            view.getBinding().swipeRefresh.setRefreshing(true);
        } else {
            view.getBinding().swipeRefresh.setRefreshing(false);
        }
        isLoading = state;
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

        places.add(place1);
        places.add(place2);
        places.add(place3);
        places.add(place4);
        places.add(place5);
        places.add(place2);
        places.add(place3);
        places.add(place4);
        places.add(place5);
    }

    @Override
    public void load() {
        places.clear();
        fetchData(null);
    }
}
