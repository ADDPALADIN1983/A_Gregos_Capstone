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

@Database(entities = Career.class, version = 1, exportSchema = false)
abstract class CareerDatabase extends RoomDatabase {

    private static CareerDatabase instance;

    public abstract CareerDatabase.CareerDao careerDao();

    public static synchronized CareerDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), CareerDatabase.class,
                    "career_database").fallbackToDestructiveMigration().addCallback(roomCallback).allowMainThreadQueries().build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new CareerDatabase.PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private CareerDatabase.CareerDao careerDao;

        private PopulateDBAsyncTask(CareerDatabase db) {
            careerDao = db.careerDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }

    @Dao
    public interface CareerDao {

        @Insert
        void insert(Career career);

        @Update
        void update(Career career);

        @Delete
        void delete(Career career);

        @Query("SELECT * FROM career_table where careerID = :careerID")
        Career getCareer(int careerID);

        @Query("SELECT * FROM career_table where personID = :personID AND companyName =:companyName AND careerSpecialization= :specialization AND careerCategory = :careerCategory ORDER BY careerID desc")
        int getCareerID(int personID, String careerCategory, String specialization, String companyName);

        @Query("SELECT * FROM career_table where careerSpecialization LIKE :careerSpecialization ORDER BY careerCategory ASC")
        List<Career> searchByCareerSpecialization(String careerSpecialization);

        @Query("SELECT * FROM career_table where companyName LIKE :companyName ORDER BY careerCategory ASC")
        List<Career> searchCompanyName(String companyName);

        @Query("SELECT * FROM career_table where careerCategory =:careerCategory ORDER BY companyName ASC")
        List<Career> searchByCareerCategoryName(String careerCategory);

        @Query("SELECT * FROM career_table where personID =:personID ORDER BY companyName ASC")
        LiveData<List<Career>> searchByPersonID(int personID);
    }
}