package com.stdev.deketin.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.stdev.deketin.R;
import com.stdev.deketin.adapters.CategoriesGridAdapter;
import com.stdev.deketin.databinding.BottomSheetCategoriesBinding;

public class CategoryBottomSheetDialog extends BottomSheetDialogFragment {
    private BottomSheetCategoriesBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_categories, container, false);
        binding = BottomSheetCategoriesBinding.bind(view);

        // Grid View
        CategoriesGridAdapter categoriesGridAdapter = new CategoriesGridAdapter(getContext());
        binding.categoriesMenuGridView.setAdapter(categoriesGridAdapter);

        return binding.getRoot();
    }
}
