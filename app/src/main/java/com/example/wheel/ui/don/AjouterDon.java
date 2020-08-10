package com.example.wheel.ui.don;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.wheel.MainActivity;
import com.example.wheel.R;
import com.example.wheel.model.Session;

import com.google.firebase.analytics.FirebaseAnalytics;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AjouterDon extends Fragment {

    FirebaseDatabase bd;
    DatabaseReference reference;
    DatabaseReference donRef;
    DemandeDon demandeDon;
    Long maxid;
    private AjouterDonViewModel mViewModel;
    private Button ajouter;
    private EditText titre;
    private EditText largeur;
    private EditText diametre;
    private RadioGroup type;
    private EditText message;
    private String messagefinal;
    private String typetext;
    private Switch urgent;
    private String urgence;
    private RadioButton Manuelle;
    private RadioButton automatique;
    private Session session;//global variable


    public static AjouterDon newInstance() {
        return new AjouterDon();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.ajouter_don_fragment, container, false);
        bd = FirebaseDatabase.getInstance();
        reference = bd.getReference();
        donRef = reference.child("demandeDon");
        ajouter = (Button) root.findViewById(R.id.modifier);
        titre = root.findViewById(R.id.titre);
        largeur = root.findViewById(R.id.largeur);
        diametre = root.findViewById(R.id.diametre);

        type = root.findViewById(R.id.type);
        message = root.findViewById(R.id.message);
        Manuelle = root.findViewById(R.id.Manuelle);
        automatique = root.findViewById(R.id.automatique);
        urgent = root.findViewById(R.id.urgent);
        session = new Session(getContext()); //in oncreate
        session.setusename(String.valueOf(1));

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedRadioId = type.getCheckedRadioButtonId();

                if (checkedRadioId == R.id.Manuelle) {
                    typetext = "Manuelle";
                } else if (checkedRadioId == R.id.automatique)
                    typetext = "Automatique";
                if (urgent.isChecked())
                    urgence = "demande urgente";
                else
                    urgence = "demande non urgente";

                messagefinal = "Préferences : " + largeur.getText() + " ," + diametre.getText() + " ," + typetext + " \n " + urgence + " \n Message : " + message.getText();
                // Toast.makeText(getContext(), "message :"+messagefinal, Toast.LENGTH_SHORT).show();

                // DateFormat df = new SimpleDateFormat("dd/MM/yy ");
                // final Date dateobj = new Date();
                String pattern = "dd-MM-yyyy";
                String dateobj = new SimpleDateFormat(pattern).format(new Date());

                demandeDon = new DemandeDon();
                demandeDon.setHandicape_id(MainActivity.user.getUid());
                demandeDon.setTitre(titre.getText().toString());
                demandeDon.setMessage(messagefinal);
                demandeDon.setDatedemande(dateobj.toString());


                donRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                            maxid = dataSnapshot.getChildrenCount();
                            /*String a = String.valueOf(maxid)+String.valueOf(Math.random());
                            a = a.replace(".","");*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });
                if (message.getText().toString().matches("") || titre.getText().toString().matches(""))
                    Toast.makeText(getContext(), "Vous devez saisir au mois le titre et le message", Toast.LENGTH_SHORT).show();
                else {
                    String key = donRef.push().getKey();
                    demandeDon.setDemandeId(key);
                    donRef.child(key).setValue(demandeDon);
                    Toast.makeText(getContext(), "Demande Ajoutée", Toast.LENGTH_SHORT).show();

                }
            }
        });

        return root;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AjouterDonViewModel.class);
        // TODO: Use the ViewModel
    }


    public int ajouterDon() {

        int checkedRadioId = type.getCheckedRadioButtonId();

        if (checkedRadioId == R.id.Manuelle) {
            typetext = "Manuelle";
        } else if (checkedRadioId == R.id.automatique)
            typetext = "Automatique";
        if (urgent.isChecked())
            urgence = "demande urgente";
        else
            urgence = "demande non urgente";

        messagefinal = "Préferences : " + largeur.getText() + " ," + diametre.getText() + " ," + typetext + " /n" + urgence + " /n Message : " + message.getText();
        Toast.makeText(getContext(), "message :" + message, Toast.LENGTH_SHORT).show();


        return 0;
    }

}
