package com.example.wheel.model;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Handicape {
    private String id;
    private String nom;
    private String email;
    private String num_tel;

    public Handicape() {
    }

    public Handicape(String id, String nom, String email, String num_tel) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.num_tel = num_tel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(String num_tel) {
        this.num_tel = num_tel;
    }

    @Override
    public String toString() {
        return "Handicape{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", num_tel='" + num_tel + '\'' +
                '}';
    }

    public void registerUser(FirebaseUser user, String phone) {
        DatabaseReference mHandicapRef = FirebaseDatabase.getInstance().getReference().child("handicape");
        if (user != null && !phone.isEmpty()) {
            this.nom = user.getDisplayName();
            this.email = user.getEmail();
            this.id = user.getUid();
            this.num_tel = phone;
            mHandicapRef.child(this.id).setValue(this);
        }
    }
}
