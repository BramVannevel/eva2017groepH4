package com.projecten3.eva.Model;

/**
 * Created by Ben on 15/08/2017.
 */

public class DatabaseEntry {
    public String challenge;
    public String day;
    public String state;

    public DatabaseEntry(){};

    public DatabaseEntry(String challenge, String day, String state) {
        this.challenge = challenge;
        this.day = day;
        this.state = state;
    }
}
