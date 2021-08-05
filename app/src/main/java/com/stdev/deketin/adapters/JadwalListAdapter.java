package com.stdev.deketin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.stdev.deketin.R;
import com.stdev.deketin.databinding.JadwalItemBinding;
import com.stdev.deketin.databinding.MenuItemBinding;
import com.stdev.deketin.models.JadwalModel;

import java.util.ArrayList;

public class JadwalListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> jadwal;

    public JadwalListAdapter(Context context, ArrayList<String> jadwal) {
        this.context = context;
        this.jadwal = jadwal;
    }

    @Override
    public int getCount() {
        return jadwal.size();
    }

    @Override
    public Object getItem(int position) {
        return jadwal.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.jadwal_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String jadwal = this.jadwal.get(position);
        String[] splitted = jadwal.split(" ");

        if(splitted.length >= 2) {
            viewHolder.getBinding().dayName.setText(splitted[0]);
            viewHolder.getBinding().jadwal.setText(splitted[1]);
        } else {
            viewHolder.getBinding().dayName.setText("-");
            viewHolder.getBinding().jadwal.setText("-");
        }

        return convertView;
    }

    private class ViewHolder {
        private JadwalItemBinding binding;

        public ViewHolder(View view) {
            binding = JadwalItemBinding.bind(view);
        }

        public JadwalItemBinding getBinding() {
            return binding;
        }
    }
}
