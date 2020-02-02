package com.example.wheel.ui.foundation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.wheel.R;
import com.example.wheel.model.Etablissement;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;


public class FoundationFragment extends Fragment {

    private Etablissement etablissement;
    private TextView tvCat;

    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private StorageReference mFoundationStorageRef = FirebaseStorage.getInstance().getReference().child("etablissement");
    private String categorie = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.foundation_fragment, container, false);

        Button btnAvis = v.findViewById(R.id.btn_avis);
        Button btnAccess = v.findViewById(R.id.btn_access);
        Button btnInfo = v.findViewById(R.id.btn_info);
        TextView tvNom = v.findViewById(R.id.tv_nom);
        TextView tvAdresse = v.findViewById(R.id.tv_adresse);
        tvCat = v.findViewById(R.id.tv_categorie);
        ImageView imvFoundation = v.findViewById(R.id.imv_foundation);

        tvNom.setText(etablissement.getNom());
        tvAdresse.setText(etablissement.getAdresse());
        System.out.println(imvFoundation.getMaxWidth() + " " + imvFoundation.getMaxHeight());
        System.out.println(imvFoundation.getWidth() + " " + imvFoundation.getHeight());
        if (etablissement.getImage() != null) {
            mFoundationStorageRef = mFoundationStorageRef.child(etablissement.getImage());
            System.out.println(mFoundationStorageRef.getDownloadUrl());
            Glide.with(this).load(mFoundationStorageRef).centerCrop()
                    .into(imvFoundation);
        }

        final Query query = mRootRef.child("categorieEtablissement").orderByKey().equalTo(etablissement.getCategorieEtablissement_id() + "");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categorie = dataSnapshot.child(etablissement.getCategorieEtablissement_id() + "").child("nom").getValue(String.class);
                tvCat.setText(categorie);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        FragmentTransaction t = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        AvisFragment mFrag = new AvisFragment(etablissement.getId());
        t.replace(R.id.content_fragment, mFrag);
        t.commit();
        switchSubFragments(btnAvis, new AvisFragment(etablissement.getId()));
        switchSubFragments(btnAccess, new AccessibilityFragment(etablissement));
        switchSubFragments(btnInfo, new InfoFragment(etablissement));


        return v;
    }

    private void switchSubFragments(Button button, final Fragment fragment) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction t = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                t.replace(R.id.content_fragment, fragment);
                t.commit();
            }
        });
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }
}
