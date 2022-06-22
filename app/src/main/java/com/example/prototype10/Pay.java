package com.example.prototype10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Pay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
    }

    public void btnpyback(View view) {
        Toast.makeText(this, "Back Clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

    public void btnupldqr(View view) {
        Toast.makeText(this, "Upload QR Clicked ", Toast.LENGTH_SHORT).show();
    }

    public void btnscnqr(View view) {
        Toast.makeText(this, "Scan QR Clicked ", Toast.LENGTH_SHORT).show();
    }

}