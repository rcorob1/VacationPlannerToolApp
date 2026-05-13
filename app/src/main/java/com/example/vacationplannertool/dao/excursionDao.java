package com.example.vacationplannertool.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.vacationplannertool.entity.Excursion;
import java.util.List;

@Dao
public interface excursionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Excursion excursion);

    @Update
    void update(Excursion excursion);

    @Delete
    void delete(Excursion excursion);

    @Query("SELECT * FROM EXCURSIONS ORDER BY excId ASC")
    List<Excursion> getExcursions();

    @Query("SELECT * FROM EXCURSIONS WHERE vacId=:prod ORDER BY excId ASC")
    List<Excursion> getAssocExcursions(int prod);

}
