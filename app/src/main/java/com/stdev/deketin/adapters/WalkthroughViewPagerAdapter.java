package com.stdev.deketin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stdev.deketin.R;
import com.stdev.deketin.databinding.WalkthroughItemBinding;
import com.stdev.deketin.models.WalkthroughModel;

import java.util.List;

public class WalkthroughViewPagerAdapter extends  RecyclerView.Adapter<WalkthroughViewPagerAdapter.WalkthroughViewHolder> {

    private final List<WalkthroughModel> walkthroughs;
    private static WalkthroughItemBinding binding;

    public WalkthroughViewPagerAdapter(List<WalkthroughModel> walkthroughs) {
        this.walkthroughs = walkthroughs;
    }

    @NonNull
    @Override
    public WalkthroughViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.walkthrough_item,
                parent,
                false
        );

        binding = WalkthroughItemBinding.bind(view);

        return new WalkthroughViewHolder(
                binding.getRoot()
        );
    }

    @Override
    public void onBindViewHolder(@NonNull WalkthroughViewHolder holder, int position) {
        holder.setWalkthroughItemData(walkthroughs.get(position));
    }

    @Override
    public int getItemCount() {
        return walkthroughs.size();
    }

    static class WalkthroughViewHolder extends RecyclerView.ViewHolder {

        public WalkthroughViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setWalkthroughItemData(WalkthroughModel data) {
            binding.wtTitle.setText(data.getTitle());
            binding.wtDescription.setText(data.getDescription());
            binding.wtImage.setImageResource(data.getImageDrawable());
        }
    }
}