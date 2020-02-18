package com.example.iplookout;

import androidx.room.Embedded;
import androidx.room.Relation;



import java.util.List;

public class DomainWithLookups {
    @Embedded public Domain domain;
    @Relation(
            parentColumn = "name",
            entityColumn = "domain_name"
    )
    public List<DomainLookup> domainlookups;


    @Override
    public String toString() {
        //return "" + "DOMAIN NAME=" + domain.getName();
        String rez="";
        rez+=("\nDOMAIN NAME=" + domain.getName()+"\n");
        for(DomainLookup l : domainlookups)
        {
            rez+=("acc on date: "+l.getDate()+"\n");

        }
        rez+=("----------------");




        return rez;

    }
}
