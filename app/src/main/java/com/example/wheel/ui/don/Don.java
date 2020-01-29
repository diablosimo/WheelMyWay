package com.example.wheel.ui.don;

import java.util.Date;

public class Don {
    private String date_don;
    private Long diametre_roue;
    private Boolean estPris;
    private Long largeur;
    private String modele;
    private Long poids;
    private Long volontaire_id;

    public Don(String date_don, Long diametre_roue, Boolean estPris, Long largeur, String modele, Long poids, Long volontaire_id) {
        this.date_don = date_don;
        this.diametre_roue = diametre_roue;
        this.estPris = estPris;
        this.largeur = largeur;
        this.modele = modele;
        this.poids = poids;
        this.volontaire_id = volontaire_id;
    }

    public Don() {
    }

    public String getDate_don() {
        return date_don;
    }

    public void setDate_don(String date_don) {
        this.date_don = date_don;
    }

    public Long getDiametre_roue() {
        return diametre_roue;
    }

    public void setDiametre_roue(Long diametre_roue) {
        this.diametre_roue = diametre_roue;
    }

    public Boolean getEstPris() {
        return estPris;
    }

    public void setEstPris(Boolean estPris) {
        this.estPris = estPris;
    }

    public Long getLargeur() {
        return largeur;
    }

    public void setLargeur(Long largeur) {
        this.largeur = largeur;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public Long getPoids() {
        return poids;
    }

    public void setPoids(Long poids) {
        this.poids = poids;
    }

    public Long getVolontaire_id() {
        return volontaire_id;
    }

    public void setVolontaire_id(Long volontaire_id) {
        this.volontaire_id = volontaire_id;
    }

    @Override
    public String toString() {
        return "Don{" +
                "date_don='" + date_don + '\'' +
                ", diametre_roue=" + diametre_roue +
                ", estPris=" + estPris +
                ", largeur=" + largeur +
                ", modele='" + modele + '\'' +
                ", poids=" + poids +
                ", volontaire_id=" + volontaire_id +
                '}';
    }
}
