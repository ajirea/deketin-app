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

public class LastVisitedPlacesAdapter extends RecyclerView.Adapter<LastVisitedPlacesAdapter.LastVisitedViewHolder> {
    private final List<PlaceModel> places;

    public LastVisitedPlacesAdapter(List<PlaceModel> places) {
        this.places = places;
    }

    @NonNull
    @Override
    public LastVisitedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LastVisitedViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.place_item,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull LastVisitedPlacesAdapter.LastVisitedViewHolder holder, int position) {
        holder.setItemData(places.get(position));
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    static class LastVisitedViewHolder extends RecyclerView.ViewHolder {

        private final PlaceItemBinding binding;

        public LastVisitedViewHolder(@NonNull View itemView) {
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
