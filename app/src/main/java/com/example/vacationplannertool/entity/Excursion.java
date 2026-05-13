package com.example.vacationplannertool.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "excursions")
public class Excursion {
    @PrimaryKey(autoGenerate = true)
    private int excId;
    //private String excName;
    //private float price;
    private int vacId;
    //private String excDate;

    public int getExcId() {
        return excId;
    }

    public void setExcId(int excId) {
        this.excId = excId;
    }

    public int getVacId() {
        return vacId;
    }

    public void setVacId(int vacId) {
        this.vacId = vacId;
    }


}
