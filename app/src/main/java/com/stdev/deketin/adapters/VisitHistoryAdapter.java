package com.stdev.deketin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.stdev.deketin.R;
import com.stdev.deketin.databinding.HorizontalPlaceItemBinding;
import com.stdev.deketin.databinding.PlaceItemBinding;
import com.stdev.deketin.models.PlaceModel;

import java.util.List;

public class VisitHistoryAdapter extends RecyclerView.Adapter<VisitHistoryAdapter.ViewHolder> {
    private final List<PlaceModel> places;

    public VisitHistoryAdapter(List<PlaceModel> places) {
        this.places = places;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.horizontal_place_item,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItemData(places.get(position));
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final HorizontalPlaceItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = HorizontalPlaceItemBinding.bind(itemView);
        }

        public void setItemData(PlaceModel data) {
            binding.placeName.setText(data.getPlaceName());
            binding.address.setText(data.getAddress());
            binding.date.setText(data.getDate());
            Picasso.get()
                    .load(data.getThumbnail())
                    .placeholder(R.drawable.bg_skeleton)
                    .into(binding.thumbnail);
        }
    }
}
