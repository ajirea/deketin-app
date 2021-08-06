package com.stdev.deketin.dialogs;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.stdev.deketin.R;
import com.stdev.deketin.databinding.DialogLocationBinding;
import com.stdev.deketin.utils.Preferences;
import com.stdev.deketin.utils.UserLocation;

public class UpdateLocationDialog {
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private Context context;
    private DialogLocationBinding binding;
    private boolean isLoading = false;

    public UpdateLocationDialog(Context context) {
        this.context = context;
        builder = new AlertDialog.Builder(context);
        buildView();
    }

    public void buildView() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_location, null);
        binding = DialogLocationBinding.bind(view);
        binding.indicatorUpdate.setVisibility(View.GONE);

        binding.btnUpdateLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoading) return;

                isLoading = true;

                binding.textUpdate.setVisibility(View.GONE);
                binding.indicatorUpdate.setVisibility(View.VISIBLE);
                UserLocation.getLastKnownLocation(context, new OnSuccessListener<Address>() {
                    @Override
                    public void onSuccess(Address address) {
                        updateAddress();
                        binding.indicatorUpdate.setVisibility(View.GONE);
                        binding.textUpdate.setVisibility(View.VISIBLE);
                        isLoading = false;
                    }
                });
            }
        });

        builder.setView(binding.getRoot());
        dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                updateAddress();
            }
        });
    }

    private void updateAddress() {
        String address = Preferences.getCurrentAddress(context, false);
        binding.address.setText(address == null ? context.getString(R.string.loading) : address);
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
