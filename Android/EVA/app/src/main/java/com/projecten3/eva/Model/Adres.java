package com.projecten3.eva.Model;

public class Adres {
    public String straat;
    public String huisnummer;
    public String stad;
    public String postcode;

    public Adres(String straat, String huisnummer, String stad, String postcode) {
        this.straat = straat;
        this.huisnummer = huisnummer;
        this.stad = stad;
        this.postcode = postcode;
    }

    public String getStraat() {
        return straat;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public String getStad() {
        return stad;
    }

    public String getPostcode() {
        return postcode;
    }
}
