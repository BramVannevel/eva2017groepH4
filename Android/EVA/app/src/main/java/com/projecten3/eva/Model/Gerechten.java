package com.projecten3.eva.Model;

import java.util.ArrayList;

public class Gerechten {
    public String id;
    public String naam;
    public String omschrijving;
    public ArrayList<Allergenen> allergenen = null;
    public Categorieen categorie;

    public Gerechten(String id, String naam, String omschrijving, ArrayList<Allergenen> allergenen, Categorieen categorie) {
        this.id = id;
        this.naam = naam;
        this.omschrijving = omschrijving;
        this.allergenen = allergenen;
        this.categorie = categorie;
    }

    public String getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public ArrayList<Allergenen> getAllergenen() {
        return allergenen;
    }

    public Categorieen getCategorie() {
        return categorie;
    }
}
