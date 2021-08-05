package com.stdev.deketin.dialogs;


import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.stdev.deketin.R;
import com.stdev.deketin.databinding.DialogLocationBinding;

public class UpdateLocationDialog {
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private Context context;
    private DialogLocationBinding binding;

    public UpdateLocationDialog(Context context) {
        this.context = context;
        builder = new AlertDialog.Builder(context);
        buildView();
    }

    public void buildView() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_location, null);
        binding = DialogLocationBinding.bind(view);
        binding.indicatorUpdate.setVisibility(View.GONE);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textUpdate.setVisibility(View.GONE);
                binding.indicatorUpdate.setVisibility(View.VISIBLE);
            }
        });

        builder.setView(binding.getRoot());
        dialog = builder.create();
    }

    public AlertDialog getDialog() {
        return dialog;
    }

    public AlertDialog.Builder getBuilder() {
        return builder;
    }

    public void show() {
        dialog.show();
    }

    public void hide() {
        dialog.hide();
    }
}
