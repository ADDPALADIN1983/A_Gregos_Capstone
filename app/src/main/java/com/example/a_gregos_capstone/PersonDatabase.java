package com.example.a_gregos_capstone;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = APerson.class, version = 1, exportSchema = false)
abstract class PersonDatabase extends RoomDatabase {

    private static PersonDatabase instance;

    public abstract PersonDao personDao();

    public static synchronized PersonDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), PersonDatabase.class,
                    "person_database").fallbackToDestructiveMigration().addCallback(roomCallback).allowMainThreadQueries().build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PersonDatabase.PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private PersonDao personDao;

        private PopulateDBAsyncTask(PersonDatabase db) {
            personDao = db.personDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }

    @Dao
    public interface PersonDao {

        @Insert
        void insert(APerson aPerson);

        @Update
        void update(APerson aPerson);

        @Delete
        void delete(APerson aPerson);

        @Query("SELECT * FROM person_table where personID = :personID")
        APerson getAPerson(int personID);

        @Query("SELECT * FROM PERSON_TABLE where phoneNumber LIKE :phoneNumber ORDER BY lastName ASC")
        List<APerson> searchByPartialPhone(String phoneNumber);

        @Query("SELECT * FROM PERSON_TABLE where firstName LIKE :firstName ORDER BY lastName ASC")
        List<APerson> searchFirstName(String firstName);

        @Query("SELECT * FROM PERSON_TABLE where lastName LIKE :lastName ORDER BY firstName ASC")
        List<APerson> searchLastName(String lastName);

        @Query("SELECT * FROM PERSON_TABLE where categoryID =:categoryID ORDER BY lastName ASC")
        LiveData<List<APerson>> searchByCategory(int categoryID);
    }
}