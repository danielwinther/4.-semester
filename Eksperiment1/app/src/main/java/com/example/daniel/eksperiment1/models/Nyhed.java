package com.example.daniel.eksperiment1.models;

public class Nyhed {
    private String titel;
    private String udgivelsesdato;
    private String broedtekst;
    private String billede;
    private String link;

    public Nyhed(String titel, String udgivelsesdato, String broedtekst, String billede, String link) {
        this.titel = titel;
        this.udgivelsesdato = udgivelsesdato;
        this.broedtekst = broedtekst;
        this.billede = billede;
        this.link = link;
    }

    public String getTitel() {
        return titel;
    }

    public String getUdgivelsesdato() {
        return udgivelsesdato;
    }

    public String getBroedtekst() {
        return broedtekst;
    }

    public String getBillede() {
        return billede;
    }

    public String getLink() {
        return link;
    }

}
