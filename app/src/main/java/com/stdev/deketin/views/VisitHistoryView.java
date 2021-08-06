package com.stdev.deketin.views;

import com.stdev.deketin.models.PlaceModel;
import com.stdev.deketin.models.PlaceVisitHistoryModel;

import java.util.List;

public interface VisitHistoryView {
    void onStore();
    void onUpdate();
    void onDelete();
    void onLoad(List<PlaceVisitHistoryModel> places);
}
