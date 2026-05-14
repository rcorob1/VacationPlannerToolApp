package com.example.vacationplannertool.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.vacationplannertool.dao.excursionDao;
import com.example.vacationplannertool.dao.vacationDao;
import com.example.vacationplannertool.entity.Excursion;
import com.example.vacationplannertool.entity.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version = 9, exportSchema = false)
public abstract class vacationDatabaseBuilder extends RoomDatabase {
    public abstract vacationDao vDao();
    public abstract excursionDao eDao();
    private static volatile vacationDatabaseBuilder INSTANCE;

    static vacationDatabaseBuilder getDB(final Context context) {
        if(INSTANCE == null) {
            synchronized (vacationDatabaseBuilder.class) {
                if(INSTANCE==null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            vacationDatabaseBuilder.class,
                            "vpt.db").fallbackToDestructiveMigration().build();
                }
            }
        }

        return INSTANCE;
    }


}
