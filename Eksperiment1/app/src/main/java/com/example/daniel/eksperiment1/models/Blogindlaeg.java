package com.example.daniel.eksperiment1.models;

public class Blogindlaeg {
    private String titel;
    private String udgivelsesdato;
    private String indhold;
    private String kategori;
    private String link;

    public Blogindlaeg(String titel, String udgivelsesdato, String indhold, String kategori, String link) {
        this.titel = titel;
        this.udgivelsesdato = udgivelsesdato;
        this.indhold = indhold;
        this.kategori = kategori;
        this.link = link;
    }

    public String getTitel() {
        return titel;
    }

    public String getUdgivelsesdato() {
        return udgivelsesdato;
    }

    public String getIndhold() {
        return indhold;
    }

    public String getKategori() {
        return kategori;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "(" + kategori + ") " +  titel + " | " + udgivelsesdato;
    }
}
