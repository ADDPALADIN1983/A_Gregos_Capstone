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

@Database(entities = Note.class, version = 1, exportSchema = false)
abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class,
                    "note_database").fallbackToDestructiveMigration().addCallback(roomCallback).allowMainThreadQueries().build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new NoteDatabase.PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private PopulateDBAsyncTask(NoteDatabase db) {
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }

    @Dao
    public interface NoteDao {

        @Insert
        void insert(Note note);

        @Update
        void update(Note note);

        @Delete
        void delete(Note note);

        @Query("SELECT * FROM note_table where noteID = :noteID")
        Note getNote(int noteID);

        @Query("SELECT * FROM note_table where noteTitle = :noteTitle and personID = :personID AND noteDetails = :noteDetails AND noteImportance = :importance ORDER BY noteID desc")
        int getNoteID(int personID, String noteTitle, String noteDetails, int importance);

        @Query("SELECT * FROM note_table where personID =:personID ORDER BY noteImportance ASC")
        LiveData<List<Note>> getAllNotes(int personID);
    }
}