package com.example.daniel.eksperiment1.models;

public class Facebook {
    private String billede;
    private String navn;
    private String tekst;
    private String link;

    public Facebook(String billede, String navn, String tekst, String link) {
        this.billede = billede;
        this.navn = navn;
        this.tekst = tekst;
        this.link = link;
    }

    public String getBillede() {
        return billede;
    }

    public String getNavn() {
        return navn;
    }

    public String getTekst() {
        return tekst;
    }

    public String getLink() {
        return link;
    }
}
