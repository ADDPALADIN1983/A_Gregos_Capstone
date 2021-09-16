package com.example.a_gregos_capstone;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "person_table")
public class APerson {
    @PrimaryKey(autoGenerate = true)
    private int personID;
    private int categoryID;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;
    private int birthDateID;
    private String importantDates;
    private String personalNotes;
    private String pastTimes;
    private String careerIDs;

    public APerson() {
    }

    @Ignore
    public APerson(int categoryID, String firstName, String lastName, String phoneNumber, String emailAddress, int birthDate, String importantDates, String personalNotes, String pastTimes, String careerIDs) {
        this.categoryID = categoryID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.birthDateID = birthDate;
        this.importantDates = importantDates;
        this.personalNotes = personalNotes;
        this.pastTimes = pastTimes;
        this.careerIDs = careerIDs;
    }

    @Ignore
    public APerson(int personID, int categoryID, String firstName, String lastName, String phoneNumber, String emailAddress, int birthDate, String importantDates, String personalNotes, String pastTimes, String careerIDs) {
        this.personID = personID;
        this.categoryID = categoryID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.birthDateID = birthDate;
        this.importantDates = importantDates;
        this.personalNotes = personalNotes;
        this.pastTimes = pastTimes;
        this.careerIDs = careerIDs;
    }


    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getBirthDateID() {
        return birthDateID;
    }

    public void setBirthDateID(int birthDate) {
        this.birthDateID = birthDate;
    }

    public String getImportantDates() {
        return importantDates;
    }

    public void setImportantDates(String importantDates) {
        this.importantDates = importantDates;
    }

    public String getPersonalNotes() {
        return personalNotes;
    }

    public void setPersonalNotes(String personalNotes) {
        this.personalNotes = personalNotes;
    }

    public String getPastTimes() {
        return pastTimes;
    }

    public void setPastTimes(String pastTimes) {
        this.pastTimes = pastTimes;
    }

    public String getCareerIDs() {
        return careerIDs;
    }

    public void setCareerIDs(String careerIDs) {
        this.careerIDs = careerIDs;
    }


}
