package com.example.vacationplannertool.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vacations")
public class Vacation {
    @PrimaryKey(autoGenerate = true)

    private int vacId;
    private String vacName;

    public int getVacId() {
        return vacId;
    }

    public void setVacId(int vacId) {
        this.vacId = vacId;
    }

    public String getVacName() {
        return vacName;
    }

    public void setVacName(String vacName) {
        this.vacName = vacName;
    }
}
