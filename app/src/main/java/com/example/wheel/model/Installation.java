package com.example.wheel.model;

public class Installation {
    long id;
    String emplacement;
    String nom;
    long etablissement_id;
    long accessibilite_id;

    public Installation() {
    }

    public Installation(long id, String emplacement, long etablissement_id, long accessibilite_id) {
        this.id = id;
        this.emplacement = emplacement;
        this.etablissement_id = etablissement_id;
        this.accessibilite_id = accessibilite_id;
    }

    public Installation(long id, String emplacement, String nom, long etablissement_id, long accessibilite_id) {
        this.id = id;
        this.emplacement = emplacement;
        this.nom = nom;
        this.etablissement_id = etablissement_id;
        this.accessibilite_id = accessibilite_id;
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

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public long getEtablissement_id() {
        return etablissement_id;
    }

    public void setEtablissement_id(long etablissement_id) {
        this.etablissement_id = etablissement_id;
    }

    public long getAccessibilite_id() {
        return accessibilite_id;
    }

    public void setAccessibilite_id(long accessibilite_id) {
        this.accessibilite_id = accessibilite_id;
    }

    @Override
    public String toString() {
        return "Installation{" +
                "id=" + id +
                ", emplacement='" + emplacement + '\'' +
                ", nom='" + nom + '\'' +
                ", etablissement_id=" + etablissement_id +
                ", accessibilite_id=" + accessibilite_id +
                '}';
    }
}
