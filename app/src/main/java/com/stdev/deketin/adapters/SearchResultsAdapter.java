package com.stdev.deketin.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.stdev.deketin.PlaceDetailActivity;
import com.stdev.deketin.R;
import com.stdev.deketin.databinding.PlaceItemBinding;
import com.stdev.deketin.models.PhotoModel;
import com.stdev.deketin.models.PlaceModel;

import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchResultsViewHolder> {
    private final List<PlaceModel> places;

    public SearchResultsAdapter(List<PlaceModel> places) {
        this.places = places;
    }

    @NonNull
    @Override
    public SearchResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchResultsViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.place_item,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultsAdapter.SearchResultsViewHolder holder, int position) {
        holder.setItemData(places.get(position));
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    static class SearchResultsViewHolder extends RecyclerView.ViewHolder {

        private final PlaceItemBinding binding;

        public SearchResultsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = PlaceItemBinding.bind(itemView);
            float dpRatio = itemView.getResources().getDisplayMetrics().density;
            int margin = (int) (8 * dpRatio);

            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.setMargins(margin, margin, margin, margin);
            itemView.setLayoutParams(layoutParams);

//            binding.placeName.setMaxWidth((int) (90 * dpRatio));
//            binding.placeName.setMaxLines(2);
//            binding.placeName.setEllipsize(TextUtils.TruncateAt.END);
        }

        @SuppressLint("DefaultLocale")
        public void setItemData(PlaceModel data) {
            binding.placeName.setText(data.getPlaceName());
            binding.placeDistance.setText(String.format("%.1fkm", data.getDistance(itemView.getContext())/1000));

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(binding.getRoot().getContext(), PlaceDetailActivity.class);
                    intent.putExtra("place_id", data.getPlaceId());
                    intent.putExtra("place_name", data.getPlaceName());

                    if(data.getPhotos() != null) {
                        PhotoModel photo = data.getPhotos()[0];
                        intent.putExtra("place_photo_url", photo.getPhotoUrl());
                    }

                    binding.getRoot().getContext().startActivity(intent);
                }
            });

            if (data.getPhotos() != null && data.getPhotos().length > 0) {
                PhotoModel photo = data.getPhotos()[0];
                photo.setMaxWidth(320);

                Picasso.get()
                        .load(photo.getPhotoUrl())
                        .placeholder(R.drawable.bg_skeleton)
                        .into(binding.thumbnail);
            } else {
                binding.thumbnail.setImageResource(R.drawable.bg_no_image);
            }
        }
    }
}
