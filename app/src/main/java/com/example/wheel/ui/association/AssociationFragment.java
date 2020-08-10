package com.example.wheel.ui.association;

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

import com.example.wheel.R;
import com.example.wheel.ui.don.listedonAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AssociationFragment extends Fragment {

    DatabaseReference ref;
    RecyclerView recyclerView;
    ArrayList<association> list;
    listeassociationAdapter adapter;


    public static AssociationFragment newInstance() {
        return new AssociationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.association_fragment, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.myRecycler);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        ref = FirebaseDatabase.getInstance().getReference().child("association");

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list = new ArrayList<association>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                   /* String titre = dataSnapshot1.child("titre").getValue(String.class);
                    String message = dataSnapshot1.child("message").getValue(String.class);
                    long did = dataSnapshot1.child("demandeId").getValue(Long.class);

                    String date = dataSnapshot1.child("datedemande").getValue(String.class);
                    DemandeDon d = new DemandeDon();
                    d.setMessage(message);
                    d.setDemandeId(did);
                    d.setHandicape_id(Long.valueOf(session.getusename()));

                    d.setDatedemande(date);
                    d.setTitre(titre);*/
                    association association = new association();
                    association = dataSnapshot1.getValue(com.example.wheel.ui.association.association.class);
                    list.add(association);
                    //  Toast.makeText(getContext(), "aa2", Toast.LENGTH_SHORT).show();


                }

             /* list.get(1).setDemandeId(Long.valueOf(0));
               list.get(1).setDemandeId(Long.valueOf(1));
*/
                //   Toast.makeText(getContext(), session.getusename(), Toast.LENGTH_SHORT).show();
                adapter = new listeassociationAdapter(getContext(), list);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return root;
    }

}
