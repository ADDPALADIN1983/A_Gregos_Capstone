package com.example.a_gregos_capstone;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PastTimesViewModel extends AndroidViewModel {
    private PastTimesViewModel.PastTimesRepository repository;

    public PastTimesViewModel(@NonNull Application application) {
        super(application);
        repository = new PastTimesViewModel.PastTimesRepository(application);
    }

    public void insert(PastTimes pastTimes) {
        repository.insert(pastTimes);
    }

    public void update(PastTimes pastTimes) {
        repository.update(pastTimes);
    }

    public void delete(PastTimes pastTimes) {
        repository.delete(pastTimes);
    }

    public PastTimes getPastTimes(int pastTimeID) {
        return repository.getPastTimes(pastTimeID);
    }

    public int getPastTimeID(int personID, String pastTimeName, String details) {
        return repository.getPastTimeID(personID, pastTimeName, details);
    }

    public List<PastTimes> searchByPastTime(String pastTimeName) {
        return repository.searchByPastTime("%"+pastTimeName+"%");
    }

    public LiveData<List<PastTimes>> getAllPastTimes(int personID) {
        return repository.getAllPastTimes(personID);
    }

    class PastTimesRepository {
        private PastTimesDatabase.PastTimeDao pastTimeDao;

        PastTimesRepository(Application application) {
            PastTimesDatabase database = PastTimesDatabase.getInstance(application);
            pastTimeDao = database.pastTimeDao();
        }

        public void insert(PastTimes pastTimes) {
            pastTimeDao.insert(pastTimes);
        }

        public void update(PastTimes pastTimes) {
            pastTimeDao.update(pastTimes);
        }

        public void delete(PastTimes pastTimes) {
            pastTimeDao.delete(pastTimes);
        }

        public PastTimes getPastTimes(int pastTimeID) {
            return pastTimeDao.getPastTimes(pastTimeID);
        }

        public int getPastTimeID(int personID, String pastTimeName, String details) {
            return pastTimeDao.getPastTimeID(personID, pastTimeName, details);
        }

        public List<PastTimes> searchByPastTime(String pastTimeName) {
            return pastTimeDao.searchByPastTime(pastTimeName);
        }

        public LiveData<List<PastTimes>> getAllPastTimes(int personID) {
            return pastTimeDao.getAllPastTimes(personID);
        }

    }
}
