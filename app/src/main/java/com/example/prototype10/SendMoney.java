package com.example.prototype10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.ValueEventListener;

public class SendMoney extends AppCompatActivity {

    EditText etfirstname,etlastname,etgender,etage,etemployeenumber,etemail,etpassword,etuserstatus;

    String FirstName,LastName,Gender,Age,EmployeeNumber,ContactNumber,Email,Password,Balance,AUserStatus;

    EditText mobilenumber,amount;
    String balance,phonenumber;

    FirebaseAuth auth;
    DatabaseReference referenceProfile;
    String userIDofRegistered;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);

        mobilenumber = findViewById(R.id.EditTextMobileNumber);
        amount = findViewById(R.id.EditAmount);

        etfirstname =(EditText) findViewById(R.id.ETFirstName);
        etlastname =(EditText) findViewById(R.id.ETLastName);
        etgender = (EditText) findViewById(R.id.ETGender);
        etage = (EditText) findViewById(R.id.ETAge);
        etemployeenumber=(EditText) findViewById(R.id.ETEmployeeNumber);
        etemail =(EditText) findViewById(R.id.ETEmail);
        etpassword =(EditText) findViewById(R.id.ETPassword);
        etuserstatus=(EditText) findViewById(R.id.ETUserStatus);

        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        userIDofRegistered = firebaseUser.getUid();

        referenceProfile = FirebaseDatabase.getInstance().getReference("User");

        showProfile(firebaseUser);

        Button buttonUpdateProfile = findViewById(R.id.btnSendMoney);
        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMoney(firebaseUser);
            }
        });
    }

    private void sendMoney(FirebaseUser firebaseUser) {


        String Amount = amount.getText().toString();
        Integer cash = Integer.parseInt(Amount);
        Integer availbalance = Integer.parseInt(balance);
        Integer total = availbalance-cash;

        referenceProfile.child(userIDofRegistered).child("Balance").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //balance of the user
                referenceProfile.child(userIDofRegistered).child("Balance").setValue(total);
                //send money to another user
                referenceProfile.child(phonenumber).child("Balance").setValue(cash);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SendMoney.this,"Something Wrong Happen", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showProfile(@NonNull FirebaseUser firebaseUser) {

        referenceProfile.child(userIDofRegistered).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User readUserDetails = snapshot.getValue(User.class);

                if (readUserDetails != null ){
                    FirstName = readUserDetails.FirstName;
                    LastName = readUserDetails.LastName;
                    Gender = readUserDetails.Gender;
                    Age = readUserDetails.Age;
                    EmployeeNumber = readUserDetails.EmployeeNumber;
                    ContactNumber = readUserDetails.ContactNumber;
                    Email = readUserDetails.Email;
                    Password = readUserDetails.Password;
                    Balance = readUserDetails.Balance;
                    AUserStatus = readUserDetails.AUserStatus;

                    phonenumber = ContactNumber;
                    balance = Balance;

//                    mobilenumber.setText(ContactNumber);
//                    amount.setText(balance);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SendMoney.this,"Something Wrong Happen", Toast.LENGTH_LONG).show();
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