package com.example.prototype10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class Points extends AppCompatActivity {
    private DatabaseReference userRef, userReff;
    EditText mobilenumber,amount,message;
    DatabaseReference reference;
    String userID, fname,key,points;
    TextView userpoints;
    Bundle bundle,userbundle;
    FirebaseUser user;
    DatabaseReference referenceStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);
        mobilenumber = findViewById(R.id.EditTextMobileNumber);
        amount = findViewById(R.id.EditPoints);
        message = findViewById(R.id.EditTextMessage);

        bundle = getIntent().getExtras();
        userID = bundle.getString("Email");
        fname = bundle.getString("FirstName");
        points = bundle.getString("Points");
        userbundle = new Bundle();
        userbundle.putString("FirstName", fname);
        userbundle.putString("Email", userID);

        userpoints = (TextView) findViewById(R.id.nav_Points);

        user = FirebaseAuth.getInstance().getCurrentUser();
        referenceStudent = FirebaseDatabase.getInstance().getReference("User");
        userID = user.getUid();

        referenceStudent.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile =snapshot.getValue(User.class);


                if (userProfile != null ){
                    String points = userProfile.Points;
                    userpoints.setText(points);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Points.this,"Something Wrong Happen", Toast.LENGTH_LONG).show();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("User").orderByChild("Email").equalTo(userID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    key = ds.getKey();

                    Button buttonUpdateProfile = findViewById(R.id.btnRedeem);
                    buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String money = amount.getText().toString();
                            Integer moneyToAdd = Integer.parseInt(money);
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                                    .child("User").child(key).child("Points");

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

                                        Intent intent = new Intent(getApplicationContext(),HomeScreen.class);
                                        intent.putExtras(userbundle);
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void btnbchme(View view) {
        Toast.makeText(this, "Back Clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),HomeScreen.class);
        intent.putExtras(userbundle);
        startActivity(intent);
    }

}