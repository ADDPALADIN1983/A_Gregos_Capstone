package com.example.a_gregos_capstone;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int noteID;
    private int personID;
    private String noteTitle;
    private String noteDetails;
    private int noteImportance;

    public Note() {
    }

    @Ignore
    public Note(int personID, String noteTitle, String noteDetails, int noteImportance) {
        this.personID = personID;
        this.noteTitle = noteTitle;
        this.noteDetails = noteDetails;
        this.noteImportance = noteImportance;
    }

    @Ignore
    public Note(int noteID, int personID, String noteTitle, String noteDetails, int noteImportance) {
        this.noteID = noteID;
        this.personID = personID;
        this.noteTitle = noteTitle;
        this.noteDetails = noteDetails;
        this.noteImportance = noteImportance;
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDetails() {
        return noteDetails;
    }

    public void setNoteDetails(String noteDetails) {
        this.noteDetails = noteDetails;
    }

    public int getNoteImportance() {
        return noteImportance;
    }

    public void setNoteImportance(int noteImportance) {
        this.noteImportance = noteImportance;
    }
}
