package com.example.iplookout;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class DomainLookup {
    @PrimaryKey(autoGenerate = true) public int id;
    public String date;
    public String domain_name;


    public DomainLookup() {

    }
    @Ignore
    public DomainLookup( String date,String domain_name) {

        this.date = date;
        this.domain_name = domain_name;
    }
    public DomainLookup(int id, String date, String domain_name)
    {
        this.id=id;
        this.date=date;
        this.domain_name=domain_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDomain_name() {
        return domain_name;
    }

    public void setDomain_id(int domain_id) {
        this.domain_name = domain_name;
    }

    @Override
    public String toString() {
        return "{" + " " + id + " " + date +" " + domain_name + '}';
    }
}
