package com.example.a_gregos_capstone;

import android.content.Context;
import android.os.AsyncTask;

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

@Database(entities = PersonDate.class, version = 1, exportSchema = false)
abstract class PersonDateDatabase extends RoomDatabase {

    private static PersonDateDatabase instance;

    public abstract PersonDateDao dateDao();

    public static synchronized PersonDateDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), PersonDateDatabase.class,
                    "person_date_database").fallbackToDestructiveMigration().addCallback(roomCallback).allowMainThreadQueries().build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PersonDateDatabase.PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private PersonDateDao dateDao;

        private PopulateDBAsyncTask(PersonDateDatabase db) {
            dateDao = db.dateDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }

    @Dao
    public interface PersonDateDao {

        @Insert
        void insert(PersonDate date);

        @Update
        void update(PersonDate date);

        @Delete
        void delete(PersonDate date);

        @Query("SELECT * FROM person_date_table where dateID = :dateID")
        PersonDate getDate(int dateID);

        @Query("SELECT * FROM person_date_table where personID = :personID")
        LiveData<List<PersonDate>> getAllDates(int personID);

        @Query("SELECT dateID FROM person_date_table where personID = :personID AND year = :year AND month= :month AND day = :day AND dateDescription = :dateDescription")
        int getDateID(int personID, int year, int month, int day, String dateDescription);

        @Query("SELECT * FROM person_date_table where personID =:personID ORDER BY year, month, day ASC")
        LiveData<List<PersonDate>> searchByPersonID(int personID);

        @Query("SELECT * FROM person_date_table where dateType =:dateType ORDER BY year, month, day ASC")
        LiveData<List<PersonDate>> searchByDateType(String dateType);

        @Query("SELECT * FROM person_date_table where month =:month & day =:day  ORDER BY dateType ASC")
        LiveData<List<PersonDate>> searchByMonthAndDay(int month, int day);

        @Query("SELECT * FROM person_date_table where month =:month ORDER BY dateType asc")
        LiveData<List<PersonDate>> searchByMonth(int month);
    }
}