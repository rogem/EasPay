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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditInfo extends AppCompatActivity {

     EditText etfirstname,etlastname,etgender,etage,etemployeenumber,etemail;
     String userID;
     Button btnsv;
     FirebaseUser user;
     DatabaseReference referenceStudent,referenceFaculty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);



        etfirstname =(EditText) findViewById(R.id.ETFirstName);
        etlastname =(EditText) findViewById(R.id.ETLastName);
        etgender = (EditText) findViewById(R.id.ETGender);
        etage = (EditText) findViewById(R.id.ETAge);
        etemployeenumber=(EditText) findViewById(R.id.ETEmployeeNumber);
        etemail =(EditText) findViewById(R.id.ETEmail);
        btnsv = findViewById(R.id.btnsv);

        user = FirebaseAuth.getInstance().getCurrentUser();
        referenceStudent = FirebaseDatabase.getInstance().getReference("StudentSignUpConnectFirebase");
        referenceFaculty = FirebaseDatabase.getInstance().getReference("FacultySignUpConnectFirebase");
        userID = user.getUid();

        referenceStudent.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StudentSignUpConnectFirebase userProfile = snapshot.getValue(StudentSignUpConnectFirebase.class);


                if (userProfile != null ){
                    String firstname = userProfile.FirstName;
                    String lastname = userProfile.LastName;
                    String gender = userProfile.Gender;
                    String age = userProfile.Age;
                    String employeenumber = userProfile.EmployeeNumber;
                    String email = userProfile.Email;


                    etfirstname.setText(firstname);
                    etlastname.setText(lastname);
                    etgender.setText(gender);
                    etage.setText(age);
                    etemployeenumber.setText(employeenumber);
                    etemail.setText(email);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditInfo.this,"Something Wrong Happen", Toast.LENGTH_LONG).show();
            }
        });

        referenceFaculty.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FacultySignUpConnectFirebase userProfile = snapshot.getValue(FacultySignUpConnectFirebase.class);


                if (userProfile != null ){
                    String firstname = userProfile.FirstName;
                    String lastname = userProfile.LastName;
                    String gender = userProfile.Gender;
                    String age = userProfile.Age;
                    String employeenumber = userProfile.EmployeeNumber;
                    String email = userProfile.Email;


                    etfirstname.setText(firstname);
                    etlastname.setText(lastname);
                    etgender.setText(gender);
                    etage.setText(age);
                    etemployeenumber.setText(employeenumber);
                    etemail.setText(email);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditInfo.this,"Something Wrong Happen", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void btnedtbck(View view) {
        Toast.makeText(this, "Back Clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

    public void btnsv(View view) {

        Toast.makeText(this, "Data Clicked", Toast.LENGTH_LONG).show();

    }


}
