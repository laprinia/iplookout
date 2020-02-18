package com.example.iplookout;

import androidx.room.ColumnInfo;

public class DomainId {

    @ColumnInfo(name = "id")
    public int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
