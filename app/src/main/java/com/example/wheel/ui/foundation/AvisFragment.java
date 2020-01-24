package com.example.wheel.ui.foundation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.wheel.R;
import com.example.wheel.model.Reclamation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AvisFragment extends Fragment {
    ArrayList<Reclamation> reclamations;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRecRef = mRootRef.child("reclamation");
    long foundationId;
    ListView lv_reclamations;

    public AvisFragment() {
    }

    public AvisFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    public AvisFragment(long foundationId) {
        this.foundationId = foundationId;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        reclamations=new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_avis, container, false);
        lv_reclamations=view.findViewById(R.id.lv_reclamation);
        final Query query = mRecRef.orderByChild("etablissement_id").equalTo(foundationId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.getChildrenCount());
                reclamations = new ArrayList<>();
                Reclamation reclamation;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    reclamation = snapshot.getValue(Reclamation.class);
                    reclamation.setId(Long.parseLong(snapshot.getKey()));
                    reclamations.add(reclamation);
                }
                ReclamationAdapter reclamationAdapter=new ReclamationAdapter(reclamations,getContext());
                lv_reclamations.setAdapter(reclamationAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        return view;
    }


}