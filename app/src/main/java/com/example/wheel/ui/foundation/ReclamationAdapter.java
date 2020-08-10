package com.example.wheel.ui.foundation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wheel.R;
import com.example.wheel.model.Reclamation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ReclamationAdapter extends ArrayAdapter<Reclamation> {
    private ArrayList<Reclamation> reclamations;
    private Context context;
    private String handicapeName = "";


    public ReclamationAdapter(ArrayList<Reclamation> data, Context context) {
        super(context, R.layout.reclamation_row_item, data);
        this.reclamations = data;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String objet = Objects.requireNonNull(getItem(position)).getObjet();
        String message = Objects.requireNonNull(getItem(position)).getMessage();
        final String handicapId = Objects.requireNonNull(getItem(position)).getHandicape_id();
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.reclamation_row_item, parent, false);
        TextView tvObjet = convertView.findViewById(R.id.tv_objet);
        TextView tvMessage = convertView.findViewById(R.id.tv_message);
        final TextView tvNomHandicape = convertView.findViewById(R.id.tv_handi_nom);
        final Query query = FirebaseDatabase.getInstance().getReference().child("handicape").orderByKey().equalTo(handicapId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                handicapeName = dataSnapshot.child(handicapId).child("nom").getValue(String.class);
                if (handicapeName == null || handicapeName.isEmpty())
                    handicapeName = "Anonymous";
                tvNomHandicape.setText(handicapeName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        tvObjet.setText(objet);
        tvMessage.setText(message);


        return convertView;

    }
}