package com.example.wheel.ui.foundation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wheel.R;
import com.example.wheel.model.Etablissement;

import java.util.ArrayList;
import java.util.List;

public class FoundationAdapter extends ArrayAdapter<Etablissement>{

    private ArrayList<Etablissement> etablissements;
    Context context;

    public FoundationAdapter(ArrayList<Etablissement> data, Context context) {
        super(context, R.layout.foundation_row_item, data);
        this.etablissements=data;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String nom=getItem(position).getNom();
        String adresse=getItem(position).getAdresse();

        Etablissement etablissement=new Etablissement(getItem(position).getId(),nom,adresse);
        LayoutInflater inflater= LayoutInflater.from(context);
        convertView=inflater.inflate(R.layout.foundation_row_item,parent,false);
        TextView viewNom=convertView.findViewById(R.id.tv_nom);
        TextView viewAdresse=convertView.findViewById(R.id.tv_adresse);
        viewNom.setText(nom);
        viewAdresse.setText(adresse);

        return convertView;

    }
}
