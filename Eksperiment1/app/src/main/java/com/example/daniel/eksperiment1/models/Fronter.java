package com.example.daniel.eksperiment1.models;

public class Fronter {
    private String fag, tid, lokale, underviser;

    public Fronter(String fag, String tid, String lokale, String underviser) {
        this.fag = fag;
        this.tid = tid;
        this.lokale = lokale;
        this.underviser = underviser;
    }

    public String getFag() {
        return fag;
    }

    public String getTid() {
        return tid;
    }

    public String getLokale() {
        return lokale;
    }

    public String getUnderviser() {
        return underviser;
    }

}
