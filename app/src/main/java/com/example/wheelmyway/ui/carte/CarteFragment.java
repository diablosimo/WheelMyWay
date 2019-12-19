package com.example.wheelmyway.ui.carte;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wheelmyway.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CarteFragment extends Fragment implements OnMapReadyCallback {

    private CarteViewModel mViewModel;
    private GoogleMap mMap;
    public static CarteFragment newInstance() {
        return new CarteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v;
        v= inflater.inflate(R.layout.carte_fragment, container, false);

        return v;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CarteViewModel.class);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(getContext(),"hey",Toast.LENGTH_LONG).show();

        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(0, 0);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
