package com.stdev.deketin.presenters;

import com.stdev.deketin.VisitHistoryActivity;
import com.stdev.deketin.database.AppDatabase;
import com.stdev.deketin.database.dao.VisitHistoryDao;
import com.stdev.deketin.models.PlaceModel;
import com.stdev.deketin.views.SearchResultsView;
import com.stdev.deketin.views.VisitHistoryView;

import java.util.ArrayList;
import java.util.List;

public class VisitHistoryPresenterImpl implements VisitHistoryPresenter {
    private List<PlaceModel> places = new ArrayList<>();
    private List<PlaceModel> visitedPlaces = new ArrayList<>();
    private VisitHistoryView view;
    private VisitHistoryDao dao;

    public VisitHistoryPresenterImpl(VisitHistoryView view, VisitHistoryDao dao) {
        this.view = view;
        this.dao = dao;
    }

    private void generateDummyData() {
        String thumb = "https://images.unsplash.com/photo-1519494026892-80bbd2d6fd0d?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=140&q=80";

        PlaceModel place1 = new PlaceModel();
        place1.setPlaceName("AWI Creative");
        place1.setDistance(0.1);
        place1.setThumbnail(thumb);
        place1.setAddress("Jl. Jend. Sudirman 50");
        place1.setDate("28 Juni 2021");

        PlaceModel place2 = new PlaceModel();
        place2.setPlaceName("Al Husaeniyah");
        place2.setDistance(0.1);
        place2.setThumbnail(thumb);
        place2.setAddress("Jl. Selakopi 150");
        place2.setDate("18 Juni 2021");

        places.add(place1);
        places.add(place2);
    }

    @Override
    public void load() {
        places.clear();
        generateDummyData();
        view.onLoad(dao.getAll());
    }
}
