package com.projecten3.eva.Model;

public class Challenge {
    public String id;
    public String titel;
    public String omschrijving;
    public int dag;
    public String reward;
    public Gerechten gerecht;
    public Restaurant restaurant;

    public Challenge(String id, String titel, String omschrijving, int dag, String reward, Gerechten gerecht, Restaurant restaurant) {
        this.id = id;
        this.titel = titel;
        this.omschrijving = omschrijving;
        this.dag = dag;
        this.reward = reward;
        this.gerecht = gerecht;
        this.restaurant = restaurant;
    }

    public String getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public int getDag() {
        return dag;
    }

    public String getReward() {
        return reward;
    }

    public Gerechten getGerecht() {
        return gerecht;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }
}
