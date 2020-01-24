package com.example.wheel.ui.foundation;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.QuickViewConstants;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wheel.R;
import com.example.wheel.model.Etablissement;
import com.example.wheel.model.Reclamation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FoundationFragment extends Fragment {

    private FoundationViewModel mViewModel;
    public Etablissement etablissement;
    TextView tvNom,tvAdresse,tvCat;
    ImageView imvFoundation;
    Button btnAvis, btnAccess, btnInfo;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    StorageReference mFoundationStorageRef= FirebaseStorage.getInstance().getReference().child("etablissement");
    String categorie="";


    public static FoundationFragment newInstance() {
        return new FoundationFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.foundation_fragment, container, false);

        btnAvis = v.findViewById(R.id.btn_avis);
        btnAccess = v.findViewById(R.id.btn_access);
        btnInfo = v.findViewById(R.id.btn_info);
        tvNom=v.findViewById(R.id.tv_nom);
        tvAdresse=v.findViewById(R.id.tv_adresse);
        tvCat=v.findViewById(R.id.tv_categorie);
        imvFoundation=v.findViewById(R.id.imv_foundation);

        tvNom.setText(etablissement.getNom());
        tvAdresse.setText(etablissement.getAdresse());
        System.out.println(imvFoundation.getMaxWidth()+" "+imvFoundation.getMaxHeight());
        System.out.println(imvFoundation.getWidth()+" "+imvFoundation.getHeight());
        if(etablissement.getImage()!=null){
            mFoundationStorageRef=mFoundationStorageRef.child(etablissement.getImage());
            System.out.println(mFoundationStorageRef.getDownloadUrl());
            Glide.with(this).load(mFoundationStorageRef).centerCrop()
                    .into(imvFoundation);
        }

        final Query query = mRootRef.child("categorieEtablissement").orderByKey().equalTo(etablissement.getCategorieEtablissement_id()+"");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categorie=dataSnapshot.child(etablissement.getCategorieEtablissement_id()+"").child("nom").getValue(String.class);
                tvCat.setText(categorie);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });






        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        AvisFragment mFrag = new AvisFragment(etablissement.getId());
        t.replace(R.id.content_fragment, mFrag);
        t.commit();
        switchSubFragments(btnAvis, new AvisFragment(etablissement.getId()));
        switchSubFragments(btnAccess, new AccessibilityFragment(etablissement));
        switchSubFragments(btnInfo, new InfoFragment(etablissement));


        return v;
    }

    public void switchSubFragments(Button button, final Fragment fragment) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
                t.replace(R.id.content_fragment, fragment);
                t.commit();
            }
        });
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FoundationViewModel.class);
        // TODO: Use the ViewModel
    }

}
