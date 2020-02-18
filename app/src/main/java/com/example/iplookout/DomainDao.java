package com.example.iplookout;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.iplookout.Domain;

import java.util.List;

@Dao
public interface DomainDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertDomain(Domain domain);

    @Insert
    public void insertDomains(List<Domain> domains);

    @Query("Select * from Domain")
    public List<Domain> getAllDomains();





    //fol
    @Transaction
    @Query("SELECT * FROM Domain")
    public List<DomainWithLookups> getDomainWithLookups();

    @Query("Delete from DomainLookup ")
    public void deleteAllLookups();

    @Query("Delete from Domain")
    public void deleteAllDomains();

    @Query("Select * from DomainLookup")
    public List<DomainLookup> getAllDomainLookups();

    @Insert
    public void insertDomainLookups(DomainLookup lookup);

}
