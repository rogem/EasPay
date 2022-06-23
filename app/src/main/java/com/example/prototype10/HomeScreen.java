package com.example.prototype10;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    FirebaseAuth mAuth;
    private NavigationView nav_view;

    private TextView nav_email, nav_fname, nav_lname;
    private DatabaseReference userRef, userReff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        nav_view = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        nav_email = nav_view.getHeaderView(0).findViewById(R.id.nav_user_email);
        nav_fname = nav_view.getHeaderView(0).findViewById(R.id.nav_user_fname);
        nav_lname = nav_view.getHeaderView(0).findViewById(R.id.nav_user_lname);
        userRef = FirebaseDatabase.getInstance().getReference().child("StudentSignUpConnectFirebase").child(
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String email = snapshot.child("Email").getValue().toString();
                    nav_email.setText(email);
                    String fname = snapshot.child("FirstName").getValue().toString();
                    nav_fname.setText(fname);
                    String lname = snapshot.child("LastName").getValue().toString();
                    nav_lname.setText(lname);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        userReff = FirebaseDatabase.getInstance().getReference().child("FacultySignUpConnectFirebase").child(
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );
        userReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String email = snapshot.child("Email").getValue().toString();
                    nav_email.setText(email);
                    String fname = snapshot.child("FirstName").getValue().toString();
                    nav_fname.setText(fname);
                    String lname = snapshot.child("LastName").getValue().toString();
                    nav_lname.setText(lname);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void btnsend(View view) {
        Toast.makeText(this, "Send Clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, SendMoney.class);
        startActivity(intent);
    }

    public void btnpay(View view) {
        Toast.makeText(this, "Pay Clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Pay.class);
        startActivity(intent);
    }

    public void btntrnsctn(View view) {
        Toast.makeText(this, "Transaction Clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, TransactionHistory.class);
        startActivity(intent);
    }


    public void btnpoints(View view) {
        Toast.makeText(this, "Points Clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Points.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_editprofile:
                Intent intentep=new Intent(HomeScreen.this,EditInfo.class);
                startActivity(intentep);
                break;
            case R.id.nav_print:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PrintFragment()).commit();
                break;
            case R.id.nav_settings:
                Intent intents=new Intent(HomeScreen.this,Setting.class);
                startActivity(intents);
                break;
            case R.id.nav_signout:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent=new Intent(HomeScreen.this,LogIn.class);
                startActivity(intent);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void nav_signout () {
        FirebaseAuth mAuth = FirebaseAuth.getInstance ();
        finish ();
        startActivity ( new Intent ( this, LogIn.class ) );
    }

}