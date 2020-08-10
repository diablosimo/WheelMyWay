package com.example.wheel.ui.association;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheel.R;

import java.util.ArrayList;

public class listeassociationAdapter extends RecyclerView.Adapter<listeassociationAdapter.MyViewHolder> {

    Context context;
    ArrayList<association> associations;
    Dialog myDialog;
    MyViewHolder myViewHolder;

    public listeassociationAdapter(Context context, ArrayList<association> associations) {
        this.context = context;
        this.associations = associations;
    }

    public void setDemandes(ArrayList<association> associations) {
        this.associations = associations;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardassociation, parent, false);
        myViewHolder = new MyViewHolder(v);
        myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.cardassociation);


        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nom.setText(associations.get(position).getNom());
        holder.email.setText(associations.get(position).getEmail());
        holder.adresse.setText(associations.get(position).getAdresse());
        holder.tel.setText(associations.get(position).getNum_tel());
        holder.onClickCall(position);
        holder.onClickShow(position);

    }

    @Override
    public int getItemCount() {
        return associations.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nom, email, adresse, tel;
        ImageView call;

        public MyViewHolder(View itemView) {

            super(itemView);
            nom = (TextView) itemView.findViewById(R.id.nom);
            email = (TextView) itemView.findViewById(R.id.email);
            adresse = (TextView) itemView.findViewById(R.id.adresse);
            tel = (TextView) itemView.findViewById(R.id.tel);
            call = (ImageView) itemView.findViewById(R.id.call);
        }

        public void onClickCall(final int position) {
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel: " + associations.get(position).getNum_tel()));
                    itemView.getContext().startActivity(callIntent);

                }
            });
        }

        public void onClickShow(final int position) {


        }

    }
}

