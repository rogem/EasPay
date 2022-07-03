package com.example.prototype10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class SendMoney extends AppCompatActivity {

    EditText mobilenumber,amount,message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);

        mobilenumber = findViewById(R.id.EditTextMobileNumber);
        amount = findViewById(R.id.EditAmount);
        message = findViewById(R.id.EditTextMessage);

        Button buttonUpdateProfile = findViewById(R.id.btnSendMoney);
        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getmobilenumber = mobilenumber.getText().toString();
                String money = amount.getText().toString();
                Integer moneyToAdd = Integer.parseInt(money);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                        .child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Balance");

                ref.runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        Object currentMoney = currentData.getValue();
                        int totalMoney = 0;
                        if (currentMoney == null){
                            totalMoney = moneyToAdd;
                        }else {
                            totalMoney = Integer.parseInt(String.valueOf(currentMoney)) - moneyToAdd;

                            Intent intent = new Intent(SendMoney.this,HomeScreen.class);
                            startActivity(intent);
                            finish();
                        }
                        currentData.setValue(totalMoney);
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

                    }
                });
            }
        });
    }

    public void btnbchme(View view) {
        Toast.makeText(this, "Back Clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

//    public void sndcontinue(View view) {
//        Toast.makeText(this, "Continue Clicked", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, SendMoney2.class);
//        startActivity(intent);
//    }
}