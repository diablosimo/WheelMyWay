package com.example.wheel.ui.foundation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.wheel.R;
import com.example.wheel.model.Etablissement;
import com.example.wheel.model.Installation;
import com.example.wheel.model.Reclamation;
import com.example.wheel.model.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AccessibilityFragment extends Fragment {
    private Etablissement etablissement;
    private ListView lv_services;
    private ListView lv_access;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mInstRef = mRootRef.child("installation");
    DatabaseReference mAcsRef = mRootRef.child("accessibilite");
    List<Installation> installations;


    public AccessibilityFragment() {
    }

    public AccessibilityFragment(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accessibility, container, false);
        lv_services = view.findViewById(R.id.lv_services);
        lv_access = view.findViewById(R.id.lv_access);
        ServiceAdapter serviceAdapter = new ServiceAdapter(etablissement.getServices(), getContext());
        lv_services.setAdapter(serviceAdapter);
        long etablissementId=etablissement.getId();
        final Query query = mInstRef.orderByChild("etablissement_id").equalTo(etablissementId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                installations = new ArrayList<>();
                Installation installation;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    installation = snapshot.getValue(Installation.class);
                    installation.setId(Long.parseLong(snapshot.getKey()));
                    final Installation finalInstallation = installation;
                    mAcsRef.orderByKey().equalTo(installation.getAccessibilite_id()+"").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            finalInstallation.setNom(dataSnapshot.child(finalInstallation.getAccessibilite_id()+"").child("nom").getValue(String.class));
                            installations.add(finalInstallation);
                            System.out.println(finalInstallation);
                            lv_access.setAdapter(new InstallationAdapter(installations,getContext()));

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });



        return view;
    }


}
