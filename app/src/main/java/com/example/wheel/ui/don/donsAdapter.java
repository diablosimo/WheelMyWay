package com.example.wheel.ui.don;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.wheel.model.volontaire;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheel.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class donsAdapter extends RecyclerView.Adapter<donsAdapter.MyViewHolder> {


    Context context;
    ArrayList<Don> dons;
    Dialog myDialog;
    donsAdapter.MyViewHolder myViewHolder;

    public donsAdapter(Context context, ArrayList<Don> demandes) {
        this.context = context;
        this.dons = demandes;
    }

    public void setDons(ArrayList<Don> dons) {
        this.dons = dons;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.propositionsdon, parent, false);
        myViewHolder = new MyViewHolder(v);
        myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.modify_fragment);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.date.setText(dons.get(position).getDate_don());
        holder.largeur.setText(dons.get(position).getLargeur().toString() + " cm");
        holder.poids.setText(dons.get(position).getPoids().toString() + " Kg");
        holder.diametre.setText(dons.get(position).getDiametre_roue().toString() + " cm");
        holder.modele.setText(dons.get(position).getModele());

          /*  if(dons.get(position).getEstPris()) {
                holder.estpris.setText("Chaise roulante déja prise");
                holder.estpris.setVisibility(View.VISIBLE);
            }

*/

        holder.findVolontaire(position);
        holder.onClick(position);

    }

    @Override
    public int getItemCount() {
        return dons.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        volontaire v;
        TextView largeur, diametre, modele, poids, date, estpris, username;
        ImageView userimg;

        FloatingActionButton call;

        public MyViewHolder(View itemView) {

            super(itemView);
            userimg = itemView.findViewById(R.id.img);
            username = (TextView) itemView.findViewById(R.id.username);

            largeur = (TextView) itemView.findViewById(R.id.largeur);
            diametre = (TextView) itemView.findViewById(R.id.diametre);
            modele = itemView.findViewById(R.id.modele);
            poids = (TextView) itemView.findViewById(R.id.poids);
            date = (TextView) itemView.findViewById(R.id.date);
            estpris = itemView.findViewById(R.id.estpris);
            call = itemView.findViewById(R.id.call);


        }

        public void onClick(final int position) {
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "call... ", Toast.LENGTH_SHORT).show();


                }
            });
        }

        public void findVolontaire(final int position) {


            DatabaseReference query = FirebaseDatabase.getInstance().getReference().child("volontaire");

            StorageReference mvolontaireStorageRef = FirebaseStorage.getInstance().getReference().child("volontaire");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot keyId : dataSnapshot.getChildren()) {
                        volontaire v = new volontaire();
                        if (keyId.child("volontaire_id").getValue().equals(dons.get(position).getVolontaire_id())) {
                            String a = keyId.child("nom").getValue(String.class);
                            v.setNom(keyId.child("nom").getValue(String.class));
                            username.setText(a);
                            Toast.makeText(context, v.getNom() + " ", Toast.LENGTH_SHORT).show();



                                /*            if (v.getImage() != null) {
                                                mvolontaireStorageRef = mvolontaireStorageRef.child(etablissement.getImage());
                                                System.out.println(mvolontaireStorageRef.getDownloadUrl());
                                                Glide.with(this).load(mvolontaireStorageRef).centerCrop()
                                                        .into(userimg);
                                            }*/


                            break;
                        }
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }


    }


}
