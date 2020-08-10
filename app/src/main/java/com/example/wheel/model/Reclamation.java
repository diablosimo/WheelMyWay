package com.example.wheel.model;

public class Reclamation {
    String id;
    long etablissement_id;
    String handicape_id;
    String message;
    String objet;

    public Reclamation() {
    }

    public Reclamation(long etablissement_id, String handicape_id, String message, String objet) {
        this.etablissement_id = etablissement_id;
        this.handicape_id = handicape_id;
        this.message = message;
        this.objet = objet;
    }

    public Reclamation(String id, long etablissement_id, String handicape_id, String message, String objet) {
        this.id = id;
        this.etablissement_id = etablissement_id;
        this.handicape_id = handicape_id;
        this.message = message;
        this.objet = objet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getEtablissement_id() {
        return etablissement_id;
    }

    public void setEtablissement_id(long etablissement_id) {
        this.etablissement_id = etablissement_id;
    }

    public String getHandicape_id() {
        return handicape_id;
    }

    public void setHandicape_id(String handicape_id) {
        this.handicape_id = handicape_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", etablissement_id=" + etablissement_id +
                ", handicape_id=" + handicape_id +
                ", message='" + message + '\'' +
                ", objet='" + objet + '\'' +
                '}';
    }
}
