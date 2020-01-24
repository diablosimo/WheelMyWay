package com.example.wheel.ui.foundation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wheel.R;
import com.example.wheel.model.Service;

import java.util.List;
import java.util.Objects;

public class ServiceAdapter extends ArrayAdapter<Service> {
    private List<Service> services;
    private Context context;

    ServiceAdapter(List<Service> data, Context context) {
        super(context, R.layout.installation_row_item, data);
        this.services = data;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String nom = Objects.requireNonNull(getItem(position)).getNom();
        boolean estAccessible = Objects.requireNonNull(getItem(position)).isEstAccessible();
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.service_row_item, parent, false);
        TextView viewNom = convertView.findViewById(R.id.tv_service);
        ImageView viewAccess = convertView.findViewById(R.id.imv_access);
        viewNom.setText(nom);
        if(estAccessible)
            viewAccess.setImageResource(R.drawable.ic_check_circle);
        else
            viewAccess.setImageResource(R.drawable.ic_remove_circle_outline);
        return convertView;
    }
}
