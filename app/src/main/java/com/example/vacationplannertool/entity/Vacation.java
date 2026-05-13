package com.example.vacationplannertool.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vacations")
public class Vacation {
    @PrimaryKey(autoGenerate = true)

    private int vacId;
    private String vacName;
    private String vacStartDate;
    private String vacEndDate;
    private String vacHotel;

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

    public String getVacStartDate() {
        return vacStartDate;
    }

    public void setVacStartDate(String vacStartDate) {
        this.vacStartDate = vacStartDate;
    }

    public String getVacEndDate() {
        return vacEndDate;
    }

    public void setVacEndDate(String vacEndDate) {
        this.vacEndDate = vacEndDate;
    }

    public String getVacHotel() {
        return vacHotel;
    }

    public void setVacHotel(String vacHotel) {
        this.vacHotel = vacHotel;
    }
}
