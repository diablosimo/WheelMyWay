package com.example.wheel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Etablissement implements Serializable {
    private long id;
    private String nom;
    private String adresse;
    private String description;
    private boolean estApprouve;
    private float label;
    private float lng;
    private float lat;
    private long categorieEtablissement_id;
    private String image;
    private List<Service> services;

    public Etablissement() {
    }

    public Etablissement(long id, String nom, String adresse) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
    }

    public Etablissement(String nom, String adresse, String description, boolean estApprouve, float label, float lng, float lat, long categorieEtablissement_id, List<Service> service,String image) {
        this.nom = nom;
        this.adresse = adresse;
        this.description = description;
        this.estApprouve = estApprouve;
        this.label = label;
        this.lng = lng;
        this.lat = lat;
        this.categorieEtablissement_id = categorieEtablissement_id;
        this.services = service;
        this.image=image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEstApprouve() {
        return estApprouve;
    }

    public void setEstApprouve(boolean estApprouve) {
        this.estApprouve = estApprouve;
    }

    public float getLabel() {
        return label;
    }

    public void setLabel(float label) {
        this.label = label;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getCategorieEtablissement_id() {
        return categorieEtablissement_id;
    }

    public void setCategorieEtablissement_id(long categorieEtablissement_id) {
        this.categorieEtablissement_id = categorieEtablissement_id;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

//    @Override
//    public String toString() {
//        return nom;
//    }


    @Override
    public String toString() {
        return "Etablissement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", description='" + description + '\'' +
                ", estApprouve=" + estApprouve +
                ", label=" + label +
                ", lng=" + lng +
                ", lat=" + lat +
                ", categorieEtablissement_id=" + categorieEtablissement_id +
                ", services=" + services +
                '}';
    }
}
