package com.stdev.deketin.views;

import com.stdev.deketin.databinding.ActivityPlaceDetailBinding;
import com.stdev.deketin.models.PlaceModel;

public interface PlaceDetailView {
    void onLoad(PlaceModel place);
    ActivityPlaceDetailBinding getBinding();

    String getPlaceId();
    String getPlaceName();
}
