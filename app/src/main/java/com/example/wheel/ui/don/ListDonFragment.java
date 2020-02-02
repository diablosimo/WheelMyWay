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

import com.example.wheel.R;
import com.example.wheel.model.Session;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListDonFragment extends Fragment {

    Query query;
    RecyclerView recyclerView;
    ArrayList<DemandeDon> list;
    listedonAdapter adapter;
    Session session;
    private ListDonViewModel mViewModel;

    public static ListDonFragment newInstance() {
        return new ListDonFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        session = new Session(getContext());
        View root = inflater.inflate(R.layout.list_don_fragment, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.myRecycler);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        query = FirebaseDatabase.getInstance().getReference().child("demandeDon").orderByChild("handicape_id").equalTo(Integer.parseInt(session.getusename()));

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list = new ArrayList<DemandeDon>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String titre = dataSnapshot1.child("titre").getValue(String.class);
                    String message = dataSnapshot1.child("message").getValue(String.class);
                    long did = dataSnapshot1.child("demandeId").getValue(Long.class);

                    String date = dataSnapshot1.child("datedemande").getValue(String.class);
                    DemandeDon d = new DemandeDon();
                    d.setMessage(message);
                    d.setDemandeId(did);
                    d.setHandicape_id(Long.valueOf(session.getusename()));

                    d.setDatedemande(date);
                    d.setTitre(titre);
                    list.add(d);
                    //  Toast.makeText(getContext(), "aa2", Toast.LENGTH_SHORT).show();


                }

             /* list.get(1).setDemandeId(Long.valueOf(0));
               list.get(1).setDemandeId(Long.valueOf(1));
*/
                //   Toast.makeText(getContext(), session.getusename(), Toast.LENGTH_SHORT).show();
                adapter = new listedonAdapter(getContext(), list);
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
        mViewModel = ViewModelProviders.of(this).get(ListDonViewModel.class);
        // TODO: Use the ViewModel
    }

}
