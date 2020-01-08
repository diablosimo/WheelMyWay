package com.example.wheel.ui.foundation;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.wheel.R;
import com.example.wheel.model.CategorieEtablissement;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchEtablissementFragment extends Fragment {
    private SearchEtablissementViewModel mViewModel;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mCatRef = mRootRef.child("categorieEtablissement");
    DatabaseReference mEtabRef = mRootRef.child("etablissement");
    Spinner category;
    EditText foundationName;
    Button search;
    ArrayAdapter<CategorieEtablissement> adapter;
    public List<CategorieEtablissement> categorieEtablissements = new ArrayList<>();
    CategorieEtablissement selected;

    public static SearchEtablissementFragment newInstance() {
        return new SearchEtablissementFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.search_etablissement_fragment, container, false);
        category = root.findViewById(R.id.sp_category);
        foundationName = root.findViewById(R.id.et_foundation_name);
        search = root.findViewById(R.id.btn_search);

        mCatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot catSnapshot : dataSnapshot.getChildren()) {
                    CategorieEtablissement categorieEtablissement = catSnapshot.getValue(CategorieEtablissement.class);
                    categorieEtablissement.setId(Long.parseLong(catSnapshot.getKey()));
                    categorieEtablissements.add(categorieEtablissement);
                }

                adapter = new ArrayAdapter<CategorieEtablissement>(getContext(), R.layout.spinner, categorieEtablissements);
                category.setAdapter(adapter);
                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selected = categorieEtablissements.get(position);
                        long k = selected.getId();
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
//                query.addChildEventListener(new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                        System.out.println(dataSnapshot.getChildren());
//                    }
//
//                    @Override
//                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                        System.out.println(dataSnapshot.getChildren());
//
//                    }
//
//                    @Override
//                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                    }
//
//                    @Override
//                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (foundationName.getText().toString() != null && !foundationName.getText().toString().isEmpty()) {
                            Query query1 = mEtabRef.orderByChild("nom").equalTo(foundationName.getText().toString());
                            query1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    System.out.println(dataSnapshot.getChildren());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            // System.out.println(dataSnapshot.getChildren());
                        } else {
                            System.out.println(dataSnapshot.getChildren());

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchEtablissementViewModel.class);
        // TODO: Use the ViewModel
    }


}
