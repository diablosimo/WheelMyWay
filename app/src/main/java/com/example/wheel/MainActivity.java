package com.example.wheel;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.wheel.model.Handicape;
import com.example.wheel.ui.dialog.InputSenderDialog;

import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //recuperer l'element du menu langue
        MenuItem menuItem = menu.findItem(R.id.langue);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showChangeLanguageDialog();
                return false;
            }
        });

        return true;
    }

    /**
     * afficher le choix de langue dans un dialog
     * le chox d'une langue permet de redemarer l'application et de modifier la langue
     */
    private void showChangeLanguageDialog() {
        final String[] listItems = {"francais", "العربية"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("choisir la langue...");
        builder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        setLocale("fr");
                        recreate();
                        break;
                    case 1:
                        setLocale("ar");
                        recreate();
                        break;
                }
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * @param langue langue choisie (ex:fr,ar,...)
     *               modifier la langue d'affichage et enregistrer le choix.
     */
    private void setLocale(String langue) {
        Locale locale = new Locale(langue);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", langue);
        editor.apply();
    }

    //charger la langue choisie
    public void loadLocale() {
        SharedPreferences preferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String langue = preferences.getString("My_Lang", "");
        setLocale(langue);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {//si l'authentification reussit
                //recuperation de l'utilisateur
                user = data.getParcelableExtra("current_user");
                //chargement de l' activity_main
                setContentView(R.layout.activity_main);
                Toolbar toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                NavigationView navigationView = findViewById(R.id.nav_view);
                if (navigationView != null) {
                    LinearLayout mParent = (LinearLayout) navigationView.getHeaderView(0);
                    if (mParent != null) {
                        //charger les informations de l'utilisateur courant dans le menu
                        TextView username = (TextView) mParent.findViewById(R.id.nav_name);
                        TextView email = (TextView) mParent.findViewById(R.id.nav_email);
                        ImageView photo = (ImageView) mParent.findViewById(R.id.nav_pic);
                        username.setText(user.getDisplayName());
                        email.setText(user.getEmail());
                        Glide.with(this).load(user.getPhoneNumber()).apply(new RequestOptions().override(220, 220)).circleCrop().into(photo);

                    }
                }

                // Passing each menu ID as a set of Ids because each
                // menu should be considered as top level destinations.
                mAppBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.nav_map,
                        R.id.nav_search_foundation,
                        R.id.nav_ask_help,
                        R.id.nav_demandedon,
                        R.id.nav_list_don,
                        R.id.nav_list_demande_don,
                        R.id.nav_list_association,
                        R.id.nav_access)
                        .setDrawerLayout(drawer)
                        .build();
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
                NavigationUI.setupWithNavController(navigationView, navController);
                //s'il s'agit d'un nouvel utilisateur on l'ajoute a la BD
                System.out.println(user.getMetadata().getCreationTimestamp());
                System.out.println(user.getMetadata().getLastSignInTimestamp());
                if (verifyNewUser(user)) {
                    registerUser(user);
                }
            }
        }
    }

    private boolean verifyNewUser(FirebaseUser user) {
        long creationTime = user.getMetadata().getCreationTimestamp();
        long lastSignInTime = user.getMetadata().getLastSignInTimestamp();
        if (creationTime == lastSignInTime || (creationTime + 10 > lastSignInTime))
            return true;
        else return false;
    }

    /**
     * @param user l'utilisateur venant de s'authentifier
     */
    public void registerUser(FirebaseUser user) {
        showPhoneDialog(user);
    }


    /**
     * @param user l'utilisateur venant de s'authentifier
     */
    public void showPhoneDialog(final FirebaseUser user) {
        new InputSenderDialog(this, new InputSenderDialog.InputSenderDialogListener() {
            @Override
            public void onOK(final String number) {
                new Handicape().registerUser(user, number);
            }

            @Override
            public void onCancel(String number) {
            }
        }).setNumber("+2126").show();
    }

}
