package com.example.a_gregos_capstone;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "category_table")
public class PersonCategory {

    @PrimaryKey(autoGenerate = true)
    private int categoryID;
    private String categoryName;

    public PersonCategory() {
    }

    @Ignore
    public PersonCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    @Ignore
    public PersonCategory(int categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
