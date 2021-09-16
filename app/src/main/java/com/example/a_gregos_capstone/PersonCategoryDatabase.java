package com.example.a_gregos_capstone;

import android.content.Context;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;

@Database(entities = PersonCategory.class, version = 1, exportSchema = false)
abstract class PersonCategoryDatabase extends RoomDatabase {

    private static PersonCategoryDatabase instance;

    public abstract PersonCategoryDao categoryDao();

    public static synchronized PersonCategoryDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), PersonCategoryDatabase.class,
                    "category_database").fallbackToDestructiveMigration().allowMainThreadQueries().build();
            populateDB(instance);
        }

        return instance;
    }

    private static void populateDB(PersonCategoryDatabase db) {
        PersonCategoryDao categoryDao = db.categoryDao();
        if (categoryDao.getTableRows() == 0) {
            categoryDao.insert(new PersonCategory("Family"));
            categoryDao.insert(new PersonCategory("Friends"));
            categoryDao.insert(new PersonCategory("Co-workers"));
            categoryDao.insert(new PersonCategory("Acquaintances"));
        }
    }

    @Dao
    public interface PersonCategoryDao {

        @Insert
        void insert(PersonCategory category);

        @Update
        void update(PersonCategory category);

        @Delete
        void delete(PersonCategory category);

        @Query("SELECT categoryID from category_table where categoryName=:categoryName")
        int getCategoryID(String categoryName);

        @Query("SELECT * FROM category_table where categoryID = :categoryID")
        PersonCategory getCategory(int categoryID);

        @Query("SELECT * FROM category_table where categoryName =:categoryName ORDER BY categoryName ASC")
        LiveData<List<PersonCategory>> searchCategoryName(String categoryName);

        @Query("SELECT categoryName FROM category_table ORDER BY categoryID asc")
        List<String> getCategoryNames();

        @Query("SELECT COUNT(categoryID) FROM category_table")
        int getTableRows();
    }
}
