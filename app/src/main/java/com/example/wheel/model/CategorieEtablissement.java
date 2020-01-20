package com.example.wheel.model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
        return  id+" "+ nom ;
    }



}
