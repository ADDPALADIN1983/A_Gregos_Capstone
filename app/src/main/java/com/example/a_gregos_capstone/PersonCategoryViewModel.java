package com.example.a_gregos_capstone;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PersonCategoryViewModel extends AndroidViewModel {
    private PersonCategoryViewModel.PersonCategoryRepository repository;

    public PersonCategoryViewModel(@NonNull Application application) {
        super(application);
        repository = new PersonCategoryViewModel.PersonCategoryRepository(application);
    }

    public void insert(PersonCategory category) {
        repository.insert(category);
    }

    public void update(PersonCategory category) {
        repository.update(category);
    }

    public void delete(PersonCategory category) {
        repository.delete(category);
    }

    public PersonCategory getCategory(int categoryID) {
        return repository.getCategory(categoryID);
    }

    public int getCategoryID(String categoryName) {
        return repository.getCategoryID(categoryName);
    }

    public LiveData<List<PersonCategory>> searchCategoryName(String categoryName) {
        return repository.searchCategoryName(categoryName);
    }

    public List<String> getCategoryNames() {
        return repository.getCategoryNames();
    }

    class PersonCategoryRepository {
        private PersonCategoryDatabase.PersonCategoryDao categoryDao;

        PersonCategoryRepository(Application application) {
            PersonCategoryDatabase database = PersonCategoryDatabase.getInstance(application);
            categoryDao = database.categoryDao();
        }

        public void insert(PersonCategory category) {
            categoryDao.insert(category);
        }

        public void update(PersonCategory category) {
            categoryDao.update(category);
        }

        public void delete(PersonCategory category) {
            categoryDao.delete(category);
        }

        public PersonCategory getCategory(int categoryID) {
            return categoryDao.getCategory(categoryID);
        }

        public int getCategoryID(String categoryName) {
            return categoryDao.getCategoryID(categoryName);
        }

        public LiveData<List<PersonCategory>> searchCategoryName(String categoryName) {
            return categoryDao.searchCategoryName(categoryName);
        }

        public List<String> getCategoryNames() {
            return categoryDao.getCategoryNames();
        }
    }
}
