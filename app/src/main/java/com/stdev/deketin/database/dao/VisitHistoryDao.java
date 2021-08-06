package com.stdev.deketin.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.stdev.deketin.models.PlaceModel;
import com.stdev.deketin.models.PlaceVisitHistoryModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface VisitHistoryDao {
    @Insert(entity = PlaceVisitHistoryModel.class)
    long storeVisitHistory(PlaceVisitHistoryModel place);

    @Delete(entity = PlaceVisitHistoryModel.class)
    int deleteVisitHistory(PlaceVisitHistoryModel place);

    @Update(entity = PlaceVisitHistoryModel.class)
    int updateVisitHistory(PlaceVisitHistoryModel place);

    @Query("SELECT * FROM visit_histories ORDER BY updated_at DESC")
    List<PlaceVisitHistoryModel> getAll();

    @Query("SELECT * FROM visit_histories ORDER BY updated_at DESC LIMIT :amount")
    List<PlaceVisitHistoryModel> take(int amount);

    @Query("SELECT * FROM visit_histories WHERE placeId = :placeId")
    PlaceVisitHistoryModel findByPlaceId(String placeId);
}
