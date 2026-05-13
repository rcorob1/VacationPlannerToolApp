package com.example.vacationplannertool.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.vacationplannertool.entity.Vacation;
import java.util.List;

@Dao
public interface vacationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Vacation vacation);

    @Update
    void update(Vacation vacation);

    @Delete
    void delete(Vacation vacation);

    @Query("SELECT * FROM VACATIONS ORDER BY vacId ASC")
    List<Vacation> getVacations();

}
