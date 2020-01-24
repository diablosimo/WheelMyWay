package com.example.wheel.ui.foundation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.wheel.R;
import com.example.wheel.model.CategorieEtablissement;
import com.example.wheel.model.Etablissement;
import com.example.wheel.model.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchEtablissementFragment extends Fragment {
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mCatRef = mRootRef.child("categorieEtablissement");
    private DatabaseReference mEtabRef = mRootRef.child("etablissement");
    private Spinner category;
    private EditText foundationName;
    private ListView listViewFoundations;
    private ArrayAdapter<CategorieEtablissement> adapter;
    private List<CategorieEtablissement> categorieEtablissements = new ArrayList<>();
    private CategorieEtablissement selected;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.search_etablissement_fragment, container, false);
        category = root.findViewById(R.id.sp_category);
        foundationName = root.findViewById(R.id.et_foundation_name);
        Button search = root.findViewById(R.id.btn_search);
        listViewFoundations = root.findViewById(R.id.list_etablissement);

        mCatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for (DataSnapshot catSnapshot : dataSnapshot.getChildren()) {
                    CategorieEtablissement categorieEtablissement = catSnapshot.getValue(CategorieEtablissement.class);
                    assert categorieEtablissement != null;
                    categorieEtablissement.setId(Long.parseLong(Objects.requireNonNull(catSnapshot.getKey())));
                    categorieEtablissements.add(categorieEtablissement);
                }

                adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.spinner, categorieEtablissements);
                category.setAdapter(adapter);
                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selected = categorieEtablissements.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Query query = mEtabRef.orderByChild("categorieEtablissement_id").equalTo(selected.getId());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!foundationName.getText().toString().isEmpty()) {
                            Query query1 = mEtabRef.orderByChild("nom").startAt(foundationName.getText().toString()).endAt(foundationName.getText().toString() + "\uf8ff");
                            query1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    getEtablissement(dataSnapshot);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        } else {
                            getEtablissement(dataSnapshot);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        listViewFoundations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Etablissement etablissement = (Etablissement) parent.getItemAtPosition(position);
                FragmentTransaction t = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                FoundationFragment mFrag = new FoundationFragment();
                mFrag.setEtablissement(etablissement);
                t.replace(R.id.nav_host_fragment, mFrag);
                t.addToBackStack(null);
                t.commit();
            }
        });

        return root;
    }

    private void getEtablissement(DataSnapshot dataSnapshot) {
        ArrayList<Etablissement> etablissements = new ArrayList<>();
        Etablissement etablissement;
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            etablissement = snapshot.getValue(Etablissement.class);
            List<Service> services = new ArrayList<>();
            for (DataSnapshot snapshot1 : snapshot.child("service").getChildren()) {
                Service service = snapshot1.getValue(Service.class);
                assert service != null;
                service.setId(Long.parseLong(Objects.requireNonNull(snapshot1.getKey())));
                services.add(service);
            }
            assert etablissement != null;
            etablissement.setId(Long.parseLong(Objects.requireNonNull(snapshot.getKey())));
            etablissement.setServices(services);
            etablissements.add(etablissement);
            System.out.println(etablissement);
        }
        FoundationAdapter adapter = new FoundationAdapter(etablissements, getContext());
        listViewFoundations.setAdapter(adapter);
    }



}
