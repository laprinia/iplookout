package com.example.iplookout;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Domain.class, DomainLookup.class},version = 3,exportSchema = false)
public abstract class DomainDB extends RoomDatabase{

    public final static String DB_NAME = "my_db";
    private static DomainDB instanta;

    public static DomainDB getInstance(Context context)
    {
        if(instanta==null){
            instanta = Room.databaseBuilder(context, DomainDB.class, DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
     return instanta;
    }

 public abstract DomainDao getDomainDao();


}
