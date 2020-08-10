package com.example.wheel.model;

public class volontaire {
    private long volontaire_id;
    private String nom;
    private String image;
    private String num_tel;
    private String adresse;
    private long coins;
    private long email;
    private float lat;
    private float lng;


    public volontaire() {
    }

    public volontaire(long volontaire_id) {
        this.volontaire_id = volontaire_id;
    }

    public String getNum_tel() {
        return num_tel;
    }

    public long getVolontaire_id() {
        return volontaire_id;
    }

    public void setNum_tel(String num_tel) {
        this.num_tel = num_tel;
    }

    public void setVolontaire_id(long volontaire_id) {
        this.volontaire_id = volontaire_id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

    public long getEmail() {
        return email;
    }

    public void setEmail(long email) {
        this.email = email;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }
}
