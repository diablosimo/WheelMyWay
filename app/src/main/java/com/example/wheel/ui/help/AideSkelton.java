package com.example.wheel.ui.help;

public class AideSkelton {
    public boolean estUrgent, estConfirmer, estAccepter;
    public String message;
    public String helptype;
    public double latitude, longtitude;
    public String handicap_id;
    public int phoneContact;
    public long timestamp;

    public AideSkelton(Boolean urge, boolean estConfirme, boolean estAccepter, String clmsg, double lat, double longt, String uid, int phone, long temps, String helpty) {
        this.estUrgent = urge;
        this.estConfirmer = estConfirme;
        this.estAccepter = estAccepter;
        this.message = clmsg;
        this.latitude = lat;
        this.longtitude = longt;
        this.handicap_id = uid;
        this.phoneContact = phone;
        this.timestamp = temps;
        this.helptype = helpty;

    }

    public AideSkelton(boolean estUrgent, boolean estConfirme, boolean estAccepter, String msg, double lat, double longt, String userID, int phone, long temps, String helpty) {
        this.estUrgent = estUrgent;
        this.estConfirmer = estConfirme;
        this.estAccepter = estAccepter;
        this.message = msg;
        this.latitude = lat;
        this.longtitude = longt;
        this.handicap_id = userID;
        this.phoneContact = phone;
        this.timestamp = temps;
        this.helptype = helpty;
    }
}
