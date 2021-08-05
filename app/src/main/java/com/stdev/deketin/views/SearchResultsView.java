package com.stdev.deketin.views;

import com.stdev.deketin.databinding.ActivitySearchResultsBinding;
import com.stdev.deketin.models.PlaceModel;

import java.util.List;

public interface SearchResultsView {
    ActivitySearchResultsBinding getBinding();
    String getKeyword();
    String getType();
    void onLoad(List<PlaceModel> places);
}
