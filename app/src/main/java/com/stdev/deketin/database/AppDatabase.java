package com.stdev.deketin.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.stdev.deketin.database.dao.VisitHistoryDao;
import com.stdev.deketin.models.PlaceVisitHistoryModel;
import com.stdev.deketin.utils.LocationConverter;
import com.stdev.deketin.utils.TimestampConverter;

@Database(entities = {PlaceVisitHistoryModel.class}, version = 1, exportSchema = false)
@TypeConverters({TimestampConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract VisitHistoryDao visitHistoryDao();

    public static AppDatabase getDatabase(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "deketin")
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}
