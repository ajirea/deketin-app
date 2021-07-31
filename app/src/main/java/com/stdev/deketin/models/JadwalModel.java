package com.stdev.deketin.models;

public class JadwalModel {
    private String dayName;
    private String[] jadwal;

    public JadwalModel(String dayName, String[] jadwal) {
        this.dayName = dayName;
        this.jadwal = jadwal;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getJadwal() {
        if (jadwal.length > 1) {
            return String.format("%s–%s", jadwal[0], jadwal[1]);
        } else if(jadwal.length < 1) {
            return "Libur";
        }
        return jadwal[0];
    }

    public void setJadwal(String[] jadwal) {
        this.jadwal = jadwal;
    }
}
