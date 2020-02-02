package com.example.wheel.ui.don;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.wheel.R;
import com.example.wheel.model.Session;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Dons extends Fragment {


    Query query;
    RecyclerView recyclerView;
    ArrayList<Don> list;
    donsAdapter adapter;


    private DonsViewModel mViewModel;

    public static Dons newInstance() {
        return new Dons();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.dons_fragment, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.myRecycler);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        query = FirebaseDatabase.getInstance().getReference().child("don");
        list = new ArrayList<Don>();
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Don d = new Don();
                    Long largeur = dataSnapshot1.child("largeur").getValue(Long.class);
                    Long id = dataSnapshot1.child("volontaire_id").getValue(Long.class);

                    Long diametre = dataSnapshot1.child("diametre_roue").getValue(Long.class);
                    Long poids = dataSnapshot1.child("poids").getValue(Long.class);
                    String modele = dataSnapshot1.child("modele").getValue(String.class);
                    String date = dataSnapshot1.child("date_don").getValue(String.class);
                    String estpris = dataSnapshot1.child("estPris").getValue(String.class);


                    d.setVolontaire_id(id);
                    d.setDate_don(date);
                    d.setDiametre_roue(diametre);
                    d.setEstPris(estpris);
                    d.setModele(modele);
                    d.setPoids(poids);
                    d.setLargeur(largeur);
                    list.add(d);


                }


                //   Toast.makeText(getContext(), session.getusename(), Toast.LENGTH_SHORT).show();
                adapter = new donsAdapter(getContext(), list);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();

            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DonsViewModel.class);
        // TODO: Use the ViewModel
    }

}
