package com.stdev.deketin.models;

import com.google.gson.annotations.SerializedName;

public class OpeningHourModel {
    @SerializedName("open_now")
    private boolean openNow;

    @SerializedName("weekday_text")
    private String[] weekdayText;

    public boolean isOpenNow() {
        return openNow;
    }

    public void setOpenNow(boolean openNow) {
        this.openNow = openNow;
    }

    public String[] getWeekdayText() {
        return weekdayText;
    }

    public void setWeekdayText(String[] weekdayText) {
        this.weekdayText = weekdayText;
    }

//    public static class Period {
//        @SerializedName("open")
//        private JadwalModel open;
//
//        @SerializedName("close")
//        private JadwalModel close;
//
//        public JadwalModel getOpen() {
//            return open;
//        }
//
//        public void setOpen(JadwalModel open) {
//            this.open = open;
//        }
//
//        public JadwalModel getClose() {
//            return close;
//        }
//
//        public void setClose(JadwalModel close) {
//            this.close = close;
//        }
//    }
}
