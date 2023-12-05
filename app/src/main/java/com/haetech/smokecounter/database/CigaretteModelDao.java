package com.haetech.smokecounter.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import java.sql.Date;

import java.util.List;

@Dao
@TypeConverters(Converters.class)
public interface CigaretteModelDao {
    @Query("SELECT * FROM CigaretteModel")
    List<CigaretteModel> getAllCigarettes();
    @Query("SELECT * FROM CigaretteModel ORDER BY ID DESC LIMIT 1")
    CigaretteModel getLastCigarette();
    @Query("SELECT * FROM CigaretteModel WHERE cigaretteModel.smoked_date > :date")
    List<CigaretteModel> getAllCigarettesByDate(Date date);

    @Insert
    void insertOneCigarette(CigaretteModel cigaretteModel);
    @Delete
    void delete(CigaretteModel cigaretteModel);
}
