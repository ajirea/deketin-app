package com.stdev.deketin.presenters;

import com.stdev.deketin.models.PlaceVisitHistoryModel;

public interface VisitHistoryPresenter {
    void load();
    void store(PlaceVisitHistoryModel place);
    void update(PlaceVisitHistoryModel place);
    void delete(PlaceVisitHistoryModel place);
}
