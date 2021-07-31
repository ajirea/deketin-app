package com.stdev.deketin.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.stdev.deketin.PlaceDetailActivity;
import com.stdev.deketin.R;
import com.stdev.deketin.databinding.PhotoItemBinding;
import com.stdev.deketin.databinding.PlaceItemBinding;
import com.stdev.deketin.models.PlaceModel;

import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {
    private final String[] photos;

    public PhotosAdapter(String[] photos) {
        this.photos = photos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.photo_item,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItemData(photos[position]);
    }

    @Override
    public int getItemCount() {
        return photos.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final PhotoItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = PhotoItemBinding.bind(itemView);
        }

        public void setItemData(String photo) {
            Picasso.get()
                    .load(photo)
                    .placeholder(R.drawable.bg_skeleton)
                    .into(binding.thumbnail);
        }
    }
}
