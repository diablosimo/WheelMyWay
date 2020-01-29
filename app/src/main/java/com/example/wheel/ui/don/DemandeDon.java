package com.example.wheel.ui.don;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class DemandeDon {
    private static long demandeIdCounter = 0;
    private Long demandeId;
    private Long handicape_id;
    private String datedemande;
    private String message;
    private String titre;

    public DemandeDon() {
    }

    public DemandeDon(Long id, String titre, String message) {

        this.handicape_id = id;
        this.titre = titre;
        this.message = message;
    }

    public void setId() {
        demandeIdCounter++;
        this.demandeId = demandeIdCounter;
    }

    public Long getDemandeId() {
        return demandeId;
    }

    public void setDemandeId(Long a) {
        this.demandeId = a;
    }

    public String getDatedemande() {
        return datedemande;
    }

    public void setDatedemande(String datedemande) {
        this.datedemande = datedemande;
    }

    public Long getHandicape_id() {
        return handicape_id;
    }

    public void setHandicape_id(Long id) {
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
