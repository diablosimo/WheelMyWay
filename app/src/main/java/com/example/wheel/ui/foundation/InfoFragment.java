package com.example.wheel.ui.foundation;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wheel.R;
import com.example.wheel.model.Etablissement;
import com.mapbox.mapboxsdk.geometry.LatLng;

public class InfoFragment extends Fragment {
    private Etablissement etablissement;

    public InfoFragment(Etablissement etablissement) {
        this.etablissement = etablissement;
    }


    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_info, container, false);
        TextView tv_description = view.findViewById(R.id.tv_description);
        Button btnLocaliser = view.findViewById(R.id.btn_localiser);
        tv_description.setText(etablissement.getDescription());

        btnLocaliser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng=new LatLng(etablissement.getLat(),etablissement.getLng());
            }
        });
        return view;
    }
}
