package com.example.wheel.ui.foundation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.wheel.R;
import com.example.wheel.model.Etablissement;
import com.google.gson.Gson;

public class FoundationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foundation_fragment);
        Gson gson = new Gson();
        Etablissement etablissement = gson.fromJson(getIntent().getStringExtra("etablissement"), Etablissement.class);
        System.out.println(etablissement);
    }
}
