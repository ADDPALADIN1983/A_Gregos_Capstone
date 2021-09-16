package com.example.a_gregos_capstone;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "person_date_table")
public class PersonDate {
    @PrimaryKey(autoGenerate = true)
    private int dateID;
    private int personID;
    private String dateType;
    private int year;
    private int month;
    private int day;
    private String dateDescription;

    public PersonDate() {
    }

    @Ignore
    public PersonDate(int personID, String dateType, int year, int month, int day, String dateDescription) {
        this.personID = personID;
        this.dateType = dateType;
        this.year = year;
        this.month = month;
        this.day = day;
        this.dateDescription = dateDescription;
    }

    @Ignore
    public PersonDate(int dateID, int personID, String dateType, int year, int month, int day, String dateDescription) {
        this.dateID = dateID;
        this.personID = personID;
        this.dateType = dateType;
        this.year = year;
        this.month = month;
        this.day = day;
        this.dateDescription = dateDescription;
    }

    public int getDateID() {
        return dateID;
    }

    public void setDateID(int dateID) {
        this.dateID = dateID;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDateDescription() {
        return dateDescription;
    }

    public void setDateDescription(String dateDescription) {
        this.dateDescription = dateDescription;
    }

    @Override
    public String toString() {
        String s = month + "/" + day + "/" + year;
        return s;
    }
}
