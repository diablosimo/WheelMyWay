package com.example.wheel.ui.foundation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.wheel.R;
import com.example.wheel.model.Reclamation;
import com.example.wheel.ui.dialog.ReclamationDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Objects;

public class AvisFragment extends Fragment implements ReclamationDialog.ReclamationDialogListener {
    private static final String TAG = "REC";
    private ArrayList<Reclamation> reclamations;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mRecRef = mRootRef.child("reclamation");
    private long foundationId;
    private ListView lv_reclamations;
    private FloatingActionButton btn_rec;

    public AvisFragment() {
    }

    public AvisFragment(long foundationId) {
        this.foundationId = foundationId;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        reclamations=new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_avis, container, false);
        lv_reclamations=view.findViewById(R.id.lv_reclamation);
        btn_rec = view.findViewById(R.id.btn_rec);
        btn_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        final Query query = mRecRef.orderByChild("etablissement_id").equalTo(foundationId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.getChildrenCount());
                reclamations = new ArrayList<>();
                Reclamation reclamation;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    reclamation = snapshot.getValue(Reclamation.class);
                    assert reclamation != null;
                    reclamation.setId(Objects.requireNonNull(snapshot.getKey()));
                    reclamations.add(reclamation);
                }
                ReclamationAdapter reclamationAdapter=new ReclamationAdapter(reclamations,getContext());
                lv_reclamations.setAdapter(reclamationAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        return view;
    }

    public void openDialog() {
        ReclamationDialog reclamationDialog = new ReclamationDialog();
        reclamationDialog.setTargetFragment(AvisFragment.this, 1);
        reclamationDialog.show(getFragmentManager(), "Ajouter rclamation/avis");
    }

    @Override
    public void applyTexts(String objet, String message) {
        System.out.println(objet + " " + message);
        if (objet.isEmpty() || message.isEmpty()) {
            Toast.makeText(getContext(), "Veuillez saisir l'objet et le message de la réclamation", Toast.LENGTH_LONG).show();
        } else {
            Reclamation reclamation = new Reclamation(foundationId, 0, message, objet);
            String key = mRecRef.push().getKey();
            mRecRef.child(key).setValue(reclamation);

            Toast.makeText(getContext(), "ok", Toast.LENGTH_LONG).show();

        }
    }
}
