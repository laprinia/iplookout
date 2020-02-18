package com.example.iplookout;

public class LookoutFirebaseDB {
    String id;
    String lookupIP;
    String country;
    String city;
    String lookupDate;
    String flagCode;

    public LookoutFirebaseDB()
    {

    }

    public LookoutFirebaseDB(String id,String lookupIP, String country, String flagCode, String lookupDate) {
        this.id=id;
        this.lookupIP = lookupIP;
        this.country = country;

        this.lookupDate = lookupDate;
        this.flagCode=flagCode;
    }

    public String getLookupIP() {
        return lookupIP;
    }

    public String getFlagCode() {
        return flagCode;
    }

    public void setFlagCode(String flagCode) {
        this.flagCode = flagCode;
    }

    public void setLookupIP(String lookupIP) {
        this.lookupIP = lookupIP;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLookupDate() {
        return lookupDate;
    }

    public void setLookupDate(String lookupDate) {
        this.lookupDate = lookupDate;
    }

}
