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
import android.widget.Toast;

import com.example.wheel.MainActivity;
import com.example.wheel.R;
import com.example.wheel.model.Session;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.util.ArrayList;

public class MyPropositions extends Fragment {
    Query query;
    RecyclerView recyclerView;
    ArrayList<Don> list;
    propositionsAdapter adapter;
    Session session;

    private MyPropositionsViewModel mViewModel;

    public static MyPropositions newInstance() {
        return new MyPropositions();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        session = new Session(getContext());
        View root = inflater.inflate(R.layout.my_propositions_fragment, container, false);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView) root.findViewById(R.id.myRecycler);

        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        query = FirebaseDatabase.getInstance().getReference().child("don").orderByChild("volontaire_id").equalTo(MainActivity.user.getUid());
        list = new ArrayList<Don>();


        Toast.makeText(getContext(), "on create", Toast.LENGTH_SHORT).show();

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                list.clear();


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Don d = new Don();
                    Long largeur = dataSnapshot1.child("largeur").getValue(Long.class);
                    String id = dataSnapshot1.child("volontaire_id").getValue(String.class);
                    String donid = dataSnapshot1.child("donid").getValue(String.class);

                    Long diametre = dataSnapshot1.child("diametre_roue").getValue(Long.class);
                    Long poids = dataSnapshot1.child("poids").getValue(Long.class);
                    String modele = dataSnapshot1.child("modele").getValue(String.class);
                    String date = dataSnapshot1.child("date_don").getValue(String.class);
                    String estpris = dataSnapshot1.child("estPris").getValue(String.class);
                    String etat;
                    if (estpris == "true")
                        etat = "Chaise est déja prise";
                    else
                        etat = "Chaise n'est pas encore prise ";
                    d.setDonid(donid);
                    d.setVolontaire_id(id);
                    d.setDate_don(date);
                    d.setDiametre_roue(diametre);
                    d.setEstPris(etat);
                    d.setModele(modele);
                    d.setPoids(poids);
                    d.setLargeur(largeur);
                    list.add(d);


                }


                //      Toast.makeText(getContext(), session.getusename(), Toast.LENGTH_SHORT).show();
                adapter = new propositionsAdapter(getContext(), list);
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
        mViewModel = ViewModelProviders.of(this).get(MyPropositionsViewModel.class);
        // TODO: Use the ViewModel
    }

}
