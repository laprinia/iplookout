package com.example.iplookout;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import androidx.room.PrimaryKey;

@Entity
public class Domain {
@PrimaryKey(autoGenerate = false)
@NonNull
   @ColumnInfo(name="name")
    private  String name;
    @ColumnInfo(name="registrar")
    private  String registrar;

    public Domain(String name,String registrar) {

        this.name=name;
        this.registrar=registrar;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrar() {
        return registrar;
    }

    public void setRegistrar(String registrar) {
        this.registrar = registrar;
    }



    @Override
    public String toString() {
        return   name + "---" + registrar ;
    }
}





