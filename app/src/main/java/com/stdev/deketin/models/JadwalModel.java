package com.stdev.deketin.models;

import com.google.gson.annotations.SerializedName;

public class JadwalModel {
    @SerializedName("day")
    private String day;

    @SerializedName("time")
    private String time;

    private String[] dayName = new String[]{"Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};

    public JadwalModel(String day, String time) {
        this.day = day;
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
