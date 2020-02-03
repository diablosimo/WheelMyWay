package com.example.wheel.ui.don;

import android.app.Dialog;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
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

public class listedonAdapter extends RecyclerView.Adapter<listedonAdapter.MyViewHolder> {

    Context context;
    ArrayList<DemandeDon> demandes;
    Dialog myDialog;
    MyViewHolder myViewHolder;

    public listedonAdapter(Context context, ArrayList<DemandeDon> demandes) {
        this.context = context;
        this.demandes = demandes;
    }

    public void setDemandes(ArrayList<DemandeDon> demandes) {
        this.demandes = demandes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardsdondemande, parent, false);
        myViewHolder = new MyViewHolder(v);
        myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.modify_fragment);


        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.datedemande.setText(demandes.get(position).getDatedemande());
        holder.titredemande.setText(demandes.get(position).getTitre());
        holder.messagedemande.setText(demandes.get(position).getMessage());
        holder.onClick(position);
        holder.onClickmodify(position);
    }

    @Override
    public int getItemCount() {
        return demandes.size();
    }

    public void deletedemandedon(final int position) {
        DemandeDon demande = new DemandeDon();
        demande = demandes.get(position);
        FirebaseDatabase bd = FirebaseDatabase.getInstance();

        Toast.makeText(context, position + demande.getTitre() + demande.getMessage() + demande.getDemandeId(), Toast.LENGTH_SHORT).show();

        Query query = bd.getReference().child("demandeDon").orderByChild("demandeId").equalTo(demande.getDemandeId());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {

                    Snapshot.getRef().removeValue();
                    if (position > 0) {
                        demandes.remove(position);
                    }
                    notifyDataSetChanged();

                    Toast.makeText(context, "demande supprimée ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
//                    Log.e(Tag, "onCancelled", databaseError.toException());
            }
        });


    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView datedemande, titredemande, messagedemande;
        Button btndelete, btnmodify;

        public MyViewHolder(View itemView) {

            super(itemView);
            datedemande = (TextView) itemView.findViewById(R.id.date);
            titredemande = (TextView) itemView.findViewById(R.id.titre);
            messagedemande = itemView.findViewById(R.id.message);
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
                    final EditText d_titredemande = (EditText) myDialog.findViewById(R.id.titre);
                    final EditText d_messagedemande = myDialog.findViewById(R.id.message);
                    Button d_btnmodify = (Button) myDialog.findViewById(R.id.modifier);
                    d_titredemande.setText(demandes.get(position).getTitre());
                    final Switch s = myDialog.findViewById(R.id.urgent);
                    final EditText largeur = myDialog.findViewById(R.id.largeur);
                    final EditText diametre = myDialog.findViewById(R.id.diametre);
                    final RadioGroup type = myDialog.findViewById(R.id.type);
                    myDialog.show();
                    Toast.makeText(context, demandes.get(position).getHandicape_id() + "aa", Toast.LENGTH_SHORT).show();

                    d_btnmodify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "ur inside ", Toast.LENGTH_SHORT).show();
                            modifieDon(position, d_titredemande, d_messagedemande, s, largeur, diametre, type);
                        }
                    });
                }
            });
        }

        public void modifieDon(int position, EditText titre, EditText message, Switch urgent, EditText largeur, EditText diametre, RadioGroup type) {
            int checkedRadioId = type.getCheckedRadioButtonId();
            String typetext = "";
            String urgence = "";
            String messagefinal = "";

            if (checkedRadioId == R.id.Manuelle) {
                typetext = "Manuelle";
            } else if (checkedRadioId == R.id.automatique)
                typetext = "Automatique";
            if (urgent.isChecked())
                urgence = "demande urgente";
            else
                urgence = "demande non urgente";
            messagefinal = "Préferences : " + largeur.getText() + " ," + diametre.getText() + " ," + typetext + " ," + urgence + " ,Message : " + message.getText();

            DatabaseReference donRef = FirebaseDatabase.getInstance().getReference().child("demandeDon");
            DateFormat df = new SimpleDateFormat("dd/MM/yy ");
            final Date dateDemande = new Date();
            DemandeDon demandeDon = new DemandeDon();
            demandeDon.setHandicape_id(demandes.get(position).getHandicape_id());
            demandeDon.setTitre(titre.getText().toString());
            demandeDon.setMessage(messagefinal);
            demandeDon.setDemandeId(demandes.get(position).getDemandeId());
            demandeDon.setDatedemande(dateDemande.toString());
            donRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            if (message.getText().toString().matches("") || titre.getText().toString().matches(""))
                Toast.makeText(context, "Vous devez saisir au mois le titre et le message", Toast.LENGTH_SHORT).show();
            else {
                donRef.child(String.valueOf(demandeDon.getDemandeId())).setValue(demandeDon);
                Toast.makeText(context, "Demande Ajouté", Toast.LENGTH_SHORT).show();

            }
        }


    }
}
