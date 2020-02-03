package com.example.wheel.model;

public class CategorieEtablissement {
    long id;
    String nom;

    public CategorieEtablissement() {
    }

    public CategorieEtablissement(long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public CategorieEtablissement(String nom) {
        this.nom = nom;
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

    @Override
    public String toString() {
        return nom;
    }



}
