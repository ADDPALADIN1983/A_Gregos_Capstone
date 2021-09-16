package com.example.a_gregos_capstone;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "past_times_table")
public class PastTimes {
    @PrimaryKey(autoGenerate = true)
    private int pastTimeID;
    private int personID;
    private String pastTimeName;
    private String pastTimeDetails;

    public PastTimes() {
    }

    @Ignore
    public PastTimes(int personID, String pastTimeName, String pastTimeDetails) {
        this.personID = personID;
        this.pastTimeName = pastTimeName;
        this.pastTimeDetails = pastTimeDetails;
    }

    @Ignore
    public PastTimes(int pastTimeID, int personID, String pastTimeName, String pastTimeDetails) {
        this.pastTimeID = pastTimeID;
        this.personID = personID;
        this.pastTimeName = pastTimeName;
        this.pastTimeDetails = pastTimeDetails;
    }

    public int getPastTimeID() {
        return pastTimeID;
    }

    public void setPastTimeID(int pastTimeID) {
        this.pastTimeID = pastTimeID;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getPastTimeName() {
        return pastTimeName;
    }

    public void setPastTimeName(String pastTimeName) {
        this.pastTimeName = pastTimeName;
    }

    public String getPastTimeDetails() {
        return pastTimeDetails;
    }

    public void setPastTimeDetails(String pastTimeDetails) {
        this.pastTimeDetails = pastTimeDetails;
    }
}
