package com.example.wheel.ui.accessibility;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wheel.R;
import com.example.wheel.model.AccessibiliteRoute;
import com.example.wheel.model.TypeAccessibilite;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class add_accessibility extends AppCompatActivity {
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mtypeRef = mRootRef.child("typeAccessibilite");
    DatabaseReference mtypeRef2 = mRootRef.child("accessibiliteRoute");
    ArrayAdapter<String> adapter;
    Spinner spinner;
    TypeAccessibilite acc;
    Button add, cancel;
    Intent returnIntent;
    private List<String> typeaccessibility = new ArrayList<>();
    private List<String> IDtypeaccessibility = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_accessibility);
        add = findViewById(R.id.btnSend);
        cancel = findViewById(R.id.btncancel);
        spinner = findViewById(R.id.spinner);
        returnIntent = getIntent();


        mtypeRef.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                for (DataSnapshot catSnapshot : dataSnapshot.getChildren()) {
                    String type = (String) catSnapshot.child("nom").getValue();
                    String tidype = catSnapshot.getKey();
                    IDtypeaccessibility.add(tidype);
                    typeaccessibility.add(type);
                }
                adapter = new ArrayAdapter<>(
                        getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        typeaccessibility);
                spinner.setAdapter(adapter);
                // spinner.setSelection();
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                      @Override
                                                      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                          acc = new TypeAccessibilite(typeaccessibility.get(position));
                                                          acc.setId(Long.parseLong(IDtypeaccessibility.get(position)));

                                                      }

                                                      @Override
                                                      public void onNothingSelected(AdapterView<?> parent) {
                                                          acc = null;

                                                      }
                                                  }

                );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                acc = null;
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a1 = returnIntent.getStringExtra("coordinates");
                assert a1 != null;
                a1 = a1.substring(1, a1.length() - 1);
                String[] aa = a1.split(",");
                AccessibiliteRoute a = new AccessibiliteRoute(false, Float.parseFloat(aa[1]), Float.parseFloat(aa[0]), acc.getNom(), acc.getId());
                mtypeRef2.push().setValue(a);
                setResult(Activity.RESULT_OK, returnIntent);
                Toast.makeText(getApplicationContext(), getString(R.string.access_ajoutee), Toast.LENGTH_LONG).show();
                finish();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                returnIntent.putExtra("result", acc.getNom());

                setResult(Activity.RESULT_CANCELED, returnIntent);

                finish();
            }
        });

    }
}



























































