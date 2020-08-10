package com.example.wheel.ui.don;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class DemandeDon {
    private String demandeId;
    private String handicape_id;
    private String datedemande;
    private String message;
    private String titre;

    public DemandeDon() {
    }

    public DemandeDon(String id, String titre, String message) {
        this.handicape_id = id;
        this.titre = titre;
        this.message = message;
    }

    public String getDemandeId() {
        return demandeId;
    }

    public void setDemandeId(String a) {
        this.demandeId = a;
    }

    public String getDatedemande() {
        return datedemande;
    }

    public void setDatedemande(String datedemande) {
        this.datedemande = datedemande;
    }

    public String getHandicape_id() {
        return handicape_id;
    }

    public void setHandicape_id(String id) {
        this.handicape_id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
