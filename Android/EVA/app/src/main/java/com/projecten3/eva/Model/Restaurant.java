package com.projecten3.eva.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Restaurant implements Parcelable {



    private String naam;
    private int foto;
    private String omschrijving;
    private String telefoonNr;
    private String location;


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTelefoonNr() {
        return telefoonNr;
    }

    public void setTelefoonNr(String telefoonNr) {
        this.telefoonNr = telefoonNr;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getNaam() { return naam; }

    public void setNaam(String naam) { this.naam = naam; }


    public Restaurant(String naam, int foto, String omschrijving, String telefoonNr, String location){
        this.naam = naam;
        this.foto = foto;
        this.omschrijving = omschrijving;
        this.telefoonNr = telefoonNr;
        this.location = location;

    }

    /**
     * Parcelable methods
     * @param in
     */
    public Restaurant(Parcel in){
       naam = in.readString();
       foto = in.readInt();
       omschrijving = in.readString();
       telefoonNr = in.readString();
       location = in.readString();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags){
        parcel.writeString(naam);
        parcel.writeInt(foto);
        parcel.writeString(omschrijving);
        parcel.writeString(telefoonNr);
        parcel.writeString(location);
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
