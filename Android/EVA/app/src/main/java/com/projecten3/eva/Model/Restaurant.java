package com.projecten3.eva.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Restaurant implements Parcelable {
    public String id;
    public String naam;
    public String telefoon;
    public Adres adres;

    public Restaurant(String id, String naam, String telefoon, Adres adres) {
        this.id = id;
        this.naam = naam;
        this.telefoon = telefoon;
        this.adres = adres;
    }

    public String getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public String getTelefoon() {
        return telefoon;
    }

    public Adres getAdres() {
        return adres;
    }

    public static Creator<Restaurant> getCREATOR() {
        return CREATOR;
    }

    /**
     * Parcelable methods
     * @param in
     */
    public Restaurant(Parcel in){
       naam = in.readString();
       id = in.readString();
       telefoon = in.readString();
       //adres = in.readString();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags){
        parcel.writeString(naam);
        parcel.writeString(id);
        parcel.writeString(telefoon);
    }

    public static final Parcelable.Creator<Restaurant> CREATOR = new Parcelable.Creator<Restaurant>(){
        public Restaurant createFromParcel(Parcel in){
            return new Restaurant(in);
        }

        public Restaurant[] newArray(int size){
            return new Restaurant[size];
        }
    };


}
