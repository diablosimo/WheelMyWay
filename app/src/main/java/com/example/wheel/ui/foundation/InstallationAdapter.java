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
import com.example.wheel.model.Installation;

import java.util.List;
import java.util.Objects;

public class InstallationAdapter extends ArrayAdapter<Installation> {
    private List<Installation> installations;
    private Context context;

    public InstallationAdapter(List<Installation> data, Context context) {
        super(context, R.layout.installation_row_item, data);
        this.installations = data;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String nom = Objects.requireNonNull(getItem(position)).getNom();
        String emplacement = Objects.requireNonNull(getItem(position)).getEmplacement();
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.installation_row_item, parent, false);
        TextView viewNomAccess = convertView.findViewById(R.id.tv_access_nom);
        TextView viewEmpl = convertView.findViewById(R.id.tv_empl_access);
        viewNomAccess.setText(nom);
        viewEmpl.setText(emplacement);
        return convertView;
    }
}
