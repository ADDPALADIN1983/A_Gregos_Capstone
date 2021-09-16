package com.example.a_gregos_capstone;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PersonDateViewModel extends AndroidViewModel {
    private PersonDateViewModel.PersonDateRepository repository;

    public PersonDateViewModel(@NonNull Application application) {
        super(application);
        repository = new PersonDateViewModel.PersonDateRepository(application);
    }

    public void insert(PersonDate date) {
        repository.insert(date);
    }

    public void update(PersonDate date) {
        repository.update(date);
    }

    public void delete(PersonDate date) {
        repository.delete(date);
    }

    public PersonDate getDate(int dateID) {
        return repository.getDate(dateID);
    }

    int getDateID(int personID, int year, int month, int day, String dateDescription) {
        return repository.getDateID(personID, year, month, day, dateDescription);
    }

    public LiveData<List<PersonDate>> getAllDates(int personID) {
        return repository.getAllDates(personID);
    }

    public LiveData<List<PersonDate>> searchByPersonID(int personID) {
        return repository.searchByPersonID(personID);
    }

    public LiveData<List<PersonDate>> searchByDateType(String dateType) {
        return repository.searchByDateType(dateType);
    }

    public LiveData<List<PersonDate>> searchByMonthAndDay(int month, int day) {
        return repository.searchByMonthAndDay(month, day);
    }

    public LiveData<List<PersonDate>> searchByMonth(int month) {
        return repository.searchByMonth(month);
    }

    class PersonDateRepository {
        private PersonDateDatabase.PersonDateDao dateDao;

        PersonDateRepository(Application application) {
            PersonDateDatabase database = PersonDateDatabase.getInstance(application);
            dateDao = database.dateDao();
        }

        public void insert(PersonDate date) {
            dateDao.insert(date);
        }

        public void update(PersonDate date) {
            dateDao.update(date);
        }

        public void delete(PersonDate date) {
            dateDao.delete(date);
        }

        public PersonDate getDate(int dateID) {
            return dateDao.getDate(dateID);
        }

        int getDateID(int personID, int year, int month, int day,String dateDescription) {
            return dateDao.getDateID(personID, year, month, day, dateDescription);
        }

        public LiveData<List<PersonDate>> getAllDates(int personID) {
            return dateDao.getAllDates(personID);
        }

        public LiveData<List<PersonDate>> searchByPersonID(int personID) {
            return dateDao.searchByPersonID(personID);
        }

        public LiveData<List<PersonDate>> searchByDateType(String dateType) {
            return dateDao.searchByDateType(dateType);
        }

        public LiveData<List<PersonDate>> searchByMonthAndDay(int month, int day) {
            return dateDao.searchByMonthAndDay(month, day);
        }

        public LiveData<List<PersonDate>> searchByMonth(int month) {
            return dateDao.searchByMonth(month);
        }
    }
}