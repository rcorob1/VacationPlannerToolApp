package com.example.vacationplannertool.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "excursions")
public class Excursion {
    @PrimaryKey(autoGenerate = true)
    private int excId;
    private String excName;
    private int vacId;
    private String excDate;

    public int getExcId() {
        return excId;
    }

    public void setExcId(int excId) {
        this.excId = excId;
    }

    public String getExcName() { return excName; }

    public void setExcName(String excName) {
        this.excName = excName;
    }

    public int getVacId() {
        return vacId;
    }

    public void setVacId(int vacId) {
        this.vacId = vacId;
    }

    public String getExcDate() { return excDate; }

    public void setExcDate(String excDate) { this.excDate = excDate; }


}
