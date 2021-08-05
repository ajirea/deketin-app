package com.stdev.deketin.dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.stdev.deketin.R;
import com.stdev.deketin.SearchResultsActivity;
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
        binding.searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String keyword = v.getText().toString().trim();

                    if(keyword.length() < 1) return false;

                    Intent intent = new Intent(getContext(), SearchResultsActivity.class);
                    intent.putExtra("keyword", v.getText().toString().trim());
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        return binding.getRoot();
    }
}
