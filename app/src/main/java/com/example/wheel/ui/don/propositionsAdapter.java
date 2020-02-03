package com.example.wheel.ui.don;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class propositionsAdapter extends RecyclerView.Adapter<propositionsAdapter.MyViewHolder> {
    Context context;
    ArrayList<Don> dons;
    Dialog myDialog;
    propositionsAdapter.MyViewHolder myViewHolder;

    public propositionsAdapter(Context context, ArrayList<Don> dons) {
        this.context = context;
        this.dons = dons;
    }

    public void setDemandes(ArrayList<Don> dons) {
        this.dons = dons;
    }

    @NonNull
    @Override
    public propositionsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardsproposition, parent, false);
        myViewHolder = new propositionsAdapter.MyViewHolder(v);
        myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.modify_proposition);

        return new propositionsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.largeur.setText(dons.get(position).getLargeur().toString());
        holder.poids.setText(dons.get(position).getPoids().toString());

        holder.modele.setText(dons.get(position).getModele().toString());
        holder.diametre.setText(dons.get(position).getDiametre_roue().toString());
        holder.estpris.setText(dons.get(position).getEstPris().toString());
        holder.date_don.setText(dons.get(position).getDate_don().toString());

        holder.onClick(position);
        holder.onClickmodify(position);

    }

    @Override
    public int getItemCount() {
        return dons.size();
    }

    public void deletedemandedon(final int position) {

        Toast.makeText(context, dons.get(position) + "in 111111 delete function", Toast.LENGTH_SHORT).show();

        Don don = new Don();
        don = dons.get(position);
        FirebaseDatabase bd = FirebaseDatabase.getInstance();

        Toast.makeText(context, position + "in delete function", Toast.LENGTH_SHORT).show();


        Query query = FirebaseDatabase.getInstance().getReference().child("don").orderByChild("donid").equalTo(dons.get(position).getDonid());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {

                    Snapshot.getRef().removeValue();
                    if (position >= 0) {
                        dons.remove(position);
                    }
                    notifyDataSetChanged();

                    Toast.makeText(context, "don supprimé ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
//                    Log.e(Tag, "onCancelled", databaseError.toException());
            }
        });


    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView largeur, poids, modele, diametre, estpris, date_don;
        Button btndelete, btnmodify;

        public MyViewHolder(View itemView) {

            super(itemView);
            largeur = (TextView) itemView.findViewById(R.id.largeur);
            poids = (TextView) itemView.findViewById(R.id.poids);
            modele = (TextView) itemView.findViewById(R.id.titre);
            diametre = (TextView) itemView.findViewById(R.id.diametre);
            estpris = (TextView) itemView.findViewById(R.id.estpris);
            date_don = (TextView) itemView.findViewById(R.id.date);

            btndelete = (Button) itemView.findViewById(R.id.buttondelete);
            btnmodify = (Button) itemView.findViewById(R.id.buttonmodify);
        }

        public void onClick(final int position) {
            btndelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, position + " is clicked", Toast.LENGTH_SHORT).show();

                    deletedemandedon(position);

                }
            });
        }

        public void onClickmodify(final int position) {
            btnmodify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, position + " is clicked", Toast.LENGTH_SHORT).show();
                    final EditText ed_largeur = (EditText) myDialog.findViewById(R.id.largeur);
                    final EditText ed_diametre = myDialog.findViewById(R.id.diametre);
                    final EditText ed_modele = (EditText) myDialog.findViewById(R.id.titre);
                    final EditText ed_poids = myDialog.findViewById(R.id.poids);
                    Button d_btnmodify = (Button) myDialog.findViewById(R.id.modifier);
                    final Switch s = myDialog.findViewById(R.id.estpris);

                    myDialog.show();
                    Toast.makeText(context, position + "aa", Toast.LENGTH_SHORT).show();


                    d_btnmodify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "ur inside ", Toast.LENGTH_SHORT).show();
                            modifieDon(position, ed_largeur, ed_diametre, s, ed_modele, ed_poids);


                        }
                    });


                }
            });
        }

        public void modifieDon(int position, EditText largeur, EditText diametre, Switch etat, EditText modele, EditText poids) {
            Toast.makeText(context, "modify", Toast.LENGTH_SHORT).show();


            String etate = "";
            String model = "";
            Long largeure;
            Long diam;
            Long poid;


            if (etat.isChecked())
                etate = "true";
            else
                etate = "fals";


            DatabaseReference donRef = FirebaseDatabase.getInstance().getReference().child("don");
            DateFormat df = new SimpleDateFormat("dd/MM/yy ");
            final Date dateDemande = new Date();
            df.format(dateDemande);
            Don d = new Don();
            d.setVolontaire_id(dons.get(position).getVolontaire_id());
            if (!largeur.getText().toString().matches(""))
                d.setLargeur(Long.valueOf(largeur.getText().toString()));
            else
                d.setLargeur(Long.valueOf("0"));


            if (!poids.getText().toString().matches(""))
                d.setPoids(Long.valueOf(poids.getText().toString()));
            else
                d.setPoids(Long.valueOf("0"));

            d.setModele(modele.getText().toString());
            d.setDonid(Long.valueOf(position));
            if (etat.isChecked())
                d.setEstPris("true");
            else
                d.setEstPris("false");
            if (!diametre.getText().toString().matches(""))
                d.setDiametre_roue(Long.valueOf(diametre.getText().toString()));
            else
                d.setDiametre_roue(Long.valueOf("0"));

            d.setDate_don(df.format(dateDemande));
            d.setDonid(dons.get(position).getDonid());
            d.setVolontaire_id(dons.get(position).getVolontaire_id());
            Toast.makeText(context, "" + position + ":sd " + d.getDonid() + d.getDate_don() + d.getVolontaire_id() + d.getEstPris() + d.getModele(), Toast.LENGTH_SHORT).show();

            donRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            if (modele.getText().toString().matches(""))
                Toast.makeText(context, "Vous devez saisir au mois le modéle", Toast.LENGTH_SHORT).show();
            else {
                donRef.child(String.valueOf(d.getDonid())).setValue(d);
                notifyDataSetChanged();
                Toast.makeText(context, "Don modifé", Toast.LENGTH_SHORT).show();

            }
        }


    }
}
