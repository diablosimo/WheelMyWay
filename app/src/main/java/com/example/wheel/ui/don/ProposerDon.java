package com.example.wheel.ui.don;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wheel.MainActivity;
import com.example.wheel.R;
import com.example.wheel.model.Session;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProposerDon extends Fragment {

    FirebaseDatabase bd;
    DatabaseReference reference;
    Session session;
    Don don;
    private ProposerDonViewModel mViewModel;
    private Button ajouter;
    private EditText largeur;
    private EditText diametre;
    private EditText poids;
    private EditText modele;

    public static ProposerDon newInstance() {
        return new ProposerDon();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.proposer_don_fragment, container, false);
        bd = FirebaseDatabase.getInstance();
        reference = bd.getReference().child("don");

        ajouter = (Button) root.findViewById(R.id.modifier);
        largeur = root.findViewById(R.id.largeur);
        diametre = root.findViewById(R.id.diametre);
        poids = root.findViewById(R.id.poids);
        modele = root.findViewById(R.id.titre);


        session = new Session(getContext()); //in oncreate


        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(getContext(), "a1", Toast.LENGTH_SHORT).show();

                String pattern = "dd-MM-yyyy";
                String dateobj = new SimpleDateFormat(pattern).format(new Date());
                don = new Don();


                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });
                Toast.makeText(getContext(), "a2", Toast.LENGTH_SHORT).show();

                if (modele.getText().toString().matches("") || largeur.getText().toString().matches("") || poids.getText().toString().matches("") || diametre.getText().toString().matches(""))
                    Toast.makeText(getContext(), "Vous devez fournir plus d'informations", Toast.LENGTH_SHORT).show();
                else {
                    don.setVolontaire_id(MainActivity.user.getUid());
                    don.setLargeur(Long.parseLong(largeur.getText().toString()));
                    don.setDiametre_roue(Long.valueOf(diametre.getText().toString()));
                    don.setPoids(Long.valueOf(poids.getText().toString()));
                    don.setModele(modele.getText().toString());
                    don.setDate_don(dateobj.toString());
                    don.setEstPris("false");
                    String key = reference.push().getKey();
                    don.setDonid(key);
                    reference.child(key).setValue(don);
                    Toast.makeText(getContext(), "Demande Ajouté", Toast.LENGTH_SHORT).show();

                }
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProposerDonViewModel.class);
        // TODO: Use the ViewModel
    }

}
