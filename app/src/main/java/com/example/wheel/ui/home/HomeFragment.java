package com.example.wheel.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.wheel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    TextView conditon;
    Button foggy,sunny;

    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();
    DatabaseReference mCondRef= mRootRef.child("condition");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        conditon= root.findViewById(R.id.textviewCondition);
        sunny= root.findViewById(R.id.btnsunny);
        foggy= root.findViewById(R.id.btnfoggy);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
mCondRef.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        String text= dataSnapshot.getValue(String.class);
        conditon.setText(text);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});
    sunny.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mCondRef.setValue("sunny");
        }
    });
        foggy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCondRef.setValue("foggy");

            }
        });
    }
}