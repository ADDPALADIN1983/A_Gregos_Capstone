package com.example.a_gregos_capstone;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CareerViewModel extends AndroidViewModel {
    private CareerViewModel.CareerRepository repository;

    public CareerViewModel(@NonNull Application application) {
        super(application);
        repository = new CareerViewModel.CareerRepository(application);
    }

    public void insert(Career career) {
        repository.insert(career);
    }

    public void update(Career career) {
        repository.update(career);
    }

    public void delete(Career career) {
        repository.delete(career);
    }

    public Career getCareer(int careerID) {
        return repository.getCareer(careerID);
    }

    public int getCareerID(int personID, String careerCategory, String specialization, String companyName) {
        return repository.getCareerID(personID, careerCategory, specialization, companyName);
    }

    public List<Career> searchByCareerSpecialization(String careerSpecialization) {
        return repository.searchByCareerSpecialization("%" + careerSpecialization + "%");
    }

    public List<Career> searchCompanyName(String companyName) {
        return repository.searchCompanyName("%" + companyName + "%");
    }

    public List<Career> searchByCareerCategoryName(String categoryName) {
        return repository.searchByCareerCategoryName(categoryName);
    }

    public LiveData<List<Career>> searchByPersonID(int personID) {
        return repository.searchByPersonID(personID);
    }

    public class CareerRepository {
        private CareerDatabase.CareerDao careerDao;

        CareerRepository(Application application) {
            CareerDatabase database = CareerDatabase.getInstance(application);
            careerDao = database.careerDao();
        }

        public void insert(Career career) {
            careerDao.insert(career);
        }

        public void update(Career career) {
            careerDao.update(career);
        }

        public void delete(Career career) {
            careerDao.delete(career);
        }

        public Career getCareer(int careerID) {
            return careerDao.getCareer(careerID);
        }

        public int getCareerID(int personID, String careerCategory, String specialization, String companyName) {
            return careerDao.getCareerID(personID, careerCategory, specialization, companyName);
        }

        public List<Career> searchByCareerSpecialization(String careerSpecialization) {
            return careerDao.searchByCareerSpecialization(careerSpecialization);
        }

        public List<Career> searchCompanyName(String companyName) {
            return careerDao.searchCompanyName(companyName);
        }

        public List<Career> searchByCareerCategoryName(String categoryName) {
            return careerDao.searchByCareerCategoryName(categoryName);
        }

        public LiveData<List<Career>> searchByPersonID(int personID) {
            return careerDao.searchByPersonID(personID);
        }
    }
}