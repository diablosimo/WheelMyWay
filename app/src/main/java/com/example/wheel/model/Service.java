package com.example.wheel.model;

public class Service {
    long id;
    String nom;
    boolean estAccessible;

    public Service() {
    }

    public Service(long id, String nom, boolean estAccessible) {
        this.id = id;
        this.nom = nom;
        this.estAccessible = estAccessible;
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

    public boolean isEstAccessible() {
        return estAccessible;
    }

    public void setEstAccessible(boolean estAccessible) {
        this.estAccessible = estAccessible;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", estAccessible=" + estAccessible +
                '}';
    }
}
