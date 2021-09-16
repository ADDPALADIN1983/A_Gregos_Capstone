package com.example.a_gregos_capstone;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PersonViewModel extends AndroidViewModel {
    private PersonRepository repository;

    public PersonViewModel(@NonNull Application application) {
        super(application);
        repository = new PersonRepository(application);
    }

    public void insert(APerson aPerson) {
        repository.insert(aPerson);
    }

    public void update(APerson aPerson) {
        repository.update(aPerson);
    }

    public void delete(APerson aPerson) {
        repository.delete(aPerson);
    }

    public APerson getAPerson(int personID) {
        return repository.getAPerson(personID);
    }

    public List<APerson> searchByPartialPhone(String phoneNumber) {
        return repository.searchByPartialPhone("%" + phoneNumber + "%");
    }

    public List<APerson> searchFirstName(String firstName) {
        return repository.searchFirstName("%" + firstName + "%");
    }

    public List<APerson> searchLastName(String lastName) {
        return repository.searchLastName("%" + lastName + "%");
    }

    public LiveData<List<APerson>> searchByCategory(int categoryID) {
        return repository.searchByCategory(categoryID);
    }

    class PersonRepository {
        private PersonDatabase.PersonDao personDao;

        PersonRepository(Application application) {
            PersonDatabase database = PersonDatabase.getInstance(application);
            personDao = database.personDao();
        }

        public void insert(APerson aPerson) {
            personDao.insert(aPerson);
        }

        public void update(APerson aPerson) {
            personDao.update(aPerson);
        }

        public void delete(APerson aPerson) {
            personDao.delete(aPerson);
        }

        public APerson getAPerson(int personID) {
            return personDao.getAPerson(personID);
        }

        public List<APerson> searchByPartialPhone(String phoneNumber) {
            return personDao.searchByPartialPhone(phoneNumber);
        }

        public List<APerson> searchFirstName(String firstName) {
            return personDao.searchFirstName(firstName);
        }

        public List<APerson> searchLastName(String lastName) {
            return personDao.searchLastName(lastName);
        }

        public LiveData<List<APerson>> searchByCategory(int categoryID) {
            return personDao.searchByCategory(categoryID);
        }
    }
}
