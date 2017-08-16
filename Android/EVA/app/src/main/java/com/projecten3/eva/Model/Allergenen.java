package com.projecten3.eva.Model;

public class Allergenen {
    public String naam;
    public String id;

    public Allergenen(String naam, String id) {
        this.naam = naam;
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public String getId() {
        return id;
    }
}
