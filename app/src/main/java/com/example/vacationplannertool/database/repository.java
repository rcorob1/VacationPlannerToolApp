package com.example.vacationplannertool.database;

import android.app.Application;

import com.example.vacationplannertool.dao.excursionDao;
import com.example.vacationplannertool.dao.vacationDao;
import com.example.vacationplannertool.entity.Excursion;
import com.example.vacationplannertool.entity.Vacation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class repository {
    private excursionDao mExcDao;
    private vacationDao mVacDao;

    private List<Vacation> mVacs;
    private List<Excursion> mExcs;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public repository(Application application) {
        vacationDatabaseBuilder db = vacationDatabaseBuilder.getDB(application);
        mExcDao=db.eDao();
        mVacDao=db.vDao();
    }

    public void insert(Excursion excursion) {
        databaseExecutor.execute(()->{
            mExcDao.insert(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void insert(Vacation vacation) {
        databaseExecutor.execute(()->{
            mVacDao.insert(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Excursion excursion) {
        databaseExecutor.execute(()->{
            mExcDao.delete(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Vacation vacation) {
        databaseExecutor.execute(()->{
            mVacDao.delete(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Excursion excursion) {
        databaseExecutor.execute(()->{
            mExcDao.update(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Vacation vacation) {
        databaseExecutor.execute(()->{
            mVacDao.update(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Excursion> getExcursions() {
        databaseExecutor.execute(()->{
            mExcs = mExcDao.getExcursions();
        });
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mExcs;
    }

    public List<Excursion> getAssocExcursions(int vacId) {
        databaseExecutor.execute(()->{
            mExcs = mExcDao.getAssocExcursions(vacId);
        });
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mExcs;
    }
    public List<Vacation> getVacations() {
        databaseExecutor.execute(()->{
            mVacs = mVacDao.getVacations();
        });
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mVacs;
    }


}
