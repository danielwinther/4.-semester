package com.example.daniel.eksperiment1.models;

public class Kategori {
    private String titel, link;

    public Kategori(String titel, String link) {
        this.titel = titel;
        this.link = link;
    }

    public String getTitel() {
        return titel;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return titel;
    }
}
