package com.stdev.deketin.views;

import com.stdev.deketin.adapters.RecommendedPlacesAdapter;
import com.stdev.deketin.databinding.ActivityMainBinding;
import com.stdev.deketin.models.PlaceModel;

import java.util.List;

public interface MainView {
    void onLoadRecommendedPlaces(List<PlaceModel> recommendedPlaces);
    void onLoadLastVisitedPlaces(List<PlaceModel> lastVisitedPlaces);
    ActivityMainBinding getBinding();
}
