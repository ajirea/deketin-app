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
            int margin = (int) (8F*dpRatio);

            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.setMargins(margin, margin, margin,margin);
            itemView.setLayoutParams(layoutParams);

            binding.placeName.setMaxWidth((int)(110*dpRatio));
            binding.placeName.setMaxLines(2);
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
