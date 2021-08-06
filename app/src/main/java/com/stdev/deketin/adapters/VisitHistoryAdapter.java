package com.stdev.deketin.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.stdev.deketin.PlaceDetailActivity;
import com.stdev.deketin.R;
import com.stdev.deketin.databinding.HorizontalPlaceItemBinding;
import com.stdev.deketin.databinding.PlaceItemBinding;
import com.stdev.deketin.models.PhotoModel;
import com.stdev.deketin.models.PlaceModel;
import com.stdev.deketin.models.PlaceVisitHistoryModel;

import java.text.SimpleDateFormat;
import java.util.List;

public class VisitHistoryAdapter extends RecyclerView.Adapter<VisitHistoryAdapter.ViewHolder> {
    private final List<PlaceVisitHistoryModel> places;

    public VisitHistoryAdapter(List<PlaceVisitHistoryModel> places) {
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

        @SuppressLint("SimpleDateFormat")
        public void setItemData(PlaceVisitHistoryModel data) {
            binding.placeName.setText(data.getPlaceName());
            binding.address.setText(data.getFormattedAddress());
            binding.date.setText(new SimpleDateFormat("dd MMMM yyyy").format(data.getCreatedAt()));
            Picasso.get()
                    .load(data.getPhotoUrl())
                    .placeholder(R.drawable.bg_skeleton)
                    .into(binding.thumbnail);

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(binding.getRoot().getContext(), PlaceDetailActivity.class);
                    intent.putExtra("place_id", data.getPlaceId());
                    intent.putExtra("place_name", data.getPlaceName());

                    if(data.getPhotoUrl() != null) {
                        intent.putExtra("place_photo_url", data.getPhotoUrl());
                    }

                    binding.getRoot().getContext().startActivity(intent);
                }
            });
        }
    }
}
