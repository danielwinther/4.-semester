package com.example.daniel.eksperiment1.models;

public class Fronter {
    private String tid, fag;

    public Fronter(String tid, String fag) {
        this.tid = tid;
        this.fag = fag;
    }

    public String getTid() {
        return tid;
    }

    public String getFag() {
        return fag;
    }

    @Override
    public String toString() {
        return fag;
    }
}
