package com.stdev.deketin.views;

import com.stdev.deketin.models.PlaceModel;

import java.util.List;

public interface MainView {
    void onLoad(List<PlaceModel> recommendedPlaces, List<PlaceModel> lastVisitedPlaces);
}
