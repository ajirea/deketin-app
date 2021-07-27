package com.stdev.deketin.presenters;

import com.stdev.deketin.models.PlaceModel;
import com.stdev.deketin.views.MainView;

import java.util.ArrayList;
import java.util.List;

public class MainViewPresenterImpl implements PlacePresenter {
    private List<PlaceModel> recommendedPlaces = new ArrayList<>();
    private List<PlaceModel> lastVisitedPlaces = new ArrayList<>();
    private MainView view;

    public MainViewPresenterImpl(MainView view) {
        this.view = view;
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

        recommendedPlaces.add(place1);
        recommendedPlaces.add(place2);
        recommendedPlaces.add(place3);
        recommendedPlaces.add(place4);
        recommendedPlaces.add(place5);

        lastVisitedPlaces.add(place6);
        lastVisitedPlaces.add(place7);
    }

    @Override
    public void load() {
        recommendedPlaces.clear();
        lastVisitedPlaces.clear();
        generateDummyData();
        view.onLoad(recommendedPlaces, lastVisitedPlaces);
    }
}
