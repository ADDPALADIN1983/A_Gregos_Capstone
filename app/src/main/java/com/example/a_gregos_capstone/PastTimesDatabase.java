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

@Database(entities = PastTimes.class, version = 1, exportSchema = false)
abstract class PastTimesDatabase extends RoomDatabase {

    private static PastTimesDatabase instance;

    public abstract PastTimeDao pastTimeDao();

    public static synchronized PastTimesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), PastTimesDatabase.class,
                    "past_times_database").fallbackToDestructiveMigration().addCallback(roomCallback).allowMainThreadQueries().build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PastTimesDatabase.PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private PastTimeDao pastTimeDao;

        private PopulateDBAsyncTask(PastTimesDatabase db) {
            pastTimeDao = db.pastTimeDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }

    @Dao
    public interface PastTimeDao {

        @Insert
        void insert(PastTimes pastTimes);

        @Update
        void update(PastTimes pastTimes);

        @Delete
        void delete(PastTimes pastTimes);

        @Query("SELECT * FROM past_times_table where pastTimeID = :pastTimeID")
        PastTimes getPastTimes(int pastTimeID);

        @Query("SELECT * FROM past_times_table where personID = :personID AND pastTimeName =:pastTimeName and pastTimeDetails = :details ORDER BY pastTimeID desc")
        int getPastTimeID(int personID, String pastTimeName, String details);

        @Query("SELECT * FROM past_times_table where pastTimeName LIKE :pastTimeName ORDER BY pastTimeName")
        List<PastTimes> searchByPastTime(String pastTimeName);

        @Query("SELECT * FROM past_times_table where personID =:personID ORDER BY pastTimeName ASC")
        LiveData<List<PastTimes>> getAllPastTimes(int personID);

    }
}