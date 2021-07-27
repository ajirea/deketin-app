package com.stdev.deketin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.stdev.deketin.R;
import com.stdev.deketin.databinding.PlaceItemBinding;
import com.stdev.deketin.models.PlaceModel;

import java.util.List;

public class RecommendedPlacesAdapter extends RecyclerView.Adapter<RecommendedPlacesAdapter.RecommendedPlacesViewHolder> {
    private final List<PlaceModel> places;

    public RecommendedPlacesAdapter(List<PlaceModel> places) {
        this.places = places;
    }

    @NonNull
    @Override
    public RecommendedPlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecommendedPlacesViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.place_item,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedPlacesAdapter.RecommendedPlacesViewHolder holder, int position) {
        holder.setItemData(places.get(position));
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    static class RecommendedPlacesViewHolder extends RecyclerView.ViewHolder {

        private final PlaceItemBinding binding;

        public RecommendedPlacesViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = PlaceItemBinding.bind(itemView);
        }

        public void setItemData(PlaceModel data) {
            binding.placeName.setText(data.getPlaceName());
            binding.placeDistance.setText(String.valueOf(data.getDistance()));
            Picasso.get()
                    .load(data.getThumbnail())
                    .placeholder(R.drawable.img_tools_hospital)
                    .into(binding.thumbnail);
        }
    }
}
