package com.example.a_gregos_capstone;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "career_table")
public class Career {
    @PrimaryKey(autoGenerate = true)
    private int careerID;
    private int personID;
    private String careerCategory;
    private String careerSpecialization;
    private String companyName;

    public Career() {
    }

    @Ignore
    public Career(int personID, String careerCategory, String careerSpecialization, String companyName) {

        this.personID = personID;
        this.careerCategory = careerCategory;
        this.careerSpecialization = careerSpecialization;
        this.companyName = companyName;
    }

    @Ignore
    public Career(int careerID, int personID, String careerCategory, String careerSpecialization, String companyName) {
        this.careerID = careerID;
        this.personID = personID;
        this.careerCategory = careerCategory;
        this.careerSpecialization = careerSpecialization;
        this.companyName = companyName;
    }

    public int getCareerID() {
        return careerID;
    }

    public void setCareerID(int careerID) {
        this.careerID = careerID;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getCareerCategory() {
        return careerCategory;
    }

    public void setCareerCategory(String careerCategory) {
        this.careerCategory = careerCategory;
    }

    public String getCareerSpecialization() {
        return careerSpecialization;
    }

    public void setCareerSpecialization(String careerSpecialization) {
        this.careerSpecialization = careerSpecialization;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


}
