package com.example.wheel.model;

public class volontaire {
    private long volontaire_id;
    private String nom;

    public volontaire() {
    }

    public volontaire(long volontaire_id) {
        this.volontaire_id = volontaire_id;
    }

    public long getVolontaire_id() {
        return volontaire_id;
    }

    public void setVolontaire_id(long volontaire_id) {
        this.volontaire_id = volontaire_id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
