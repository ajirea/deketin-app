package com.stdev.deketin.views;

import com.stdev.deketin.models.PlaceModel;

import java.util.List;

public interface SearchResultsView {
    void onLoad(List<PlaceModel> places);
}
