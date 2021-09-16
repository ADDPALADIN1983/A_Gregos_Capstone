package com.example.a_gregos_capstone;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NoteViewModel extends AndroidViewModel {
    private NoteViewModel.NoteRepository repository;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteViewModel.NoteRepository(application);
    }

    public void insert(Note note) {
        repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }

    public Note getNote(int noteID) {
        return repository.getNote(noteID);
    }

    public int getNoteID(int personID, String noteTitle, String noteDetails, int importance) {
        return repository.getNoteID(personID, noteTitle, noteDetails, importance);
    }

    public LiveData<List<Note>> getAllNotes(int personID) {
        return repository.getAllNotes(personID);
    }

    public class NoteRepository {
        private NoteDatabase.NoteDao noteDao;

        NoteRepository(Application application) {
            NoteDatabase database = NoteDatabase.getInstance(application);
            noteDao = database.noteDao();
        }

        public void insert(Note note) {
            noteDao.insert(note);
        }

        public void update(Note note) {
            noteDao.update(note);
        }

        public void delete(Note note) {
            noteDao.delete(note);
        }

        public Note getNote(int noteID) {
            return noteDao.getNote(noteID);
        }

        public int getNoteID(int personID, String noteTitle, String noteDetails, int importance) {
            return noteDao.getNoteID(personID, noteTitle, noteDetails, importance);
        }

        public LiveData<List<Note>> getAllNotes(int personID) {
            return noteDao.getAllNotes(personID);
        }
    }
}
