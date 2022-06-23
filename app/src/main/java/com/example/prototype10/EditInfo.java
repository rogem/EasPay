package com.example.prototype10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditInfo extends AppCompatActivity {

     EditText etfirstname,etlastname,etgender,etage,etemployeenumber,etcontactnumber,etemail,etpassword,etbalance;
//     String userID;
//     Button btnsv;
//     FirebaseUser user;
//     DatabaseReference referenceStudent,referenceFaculty;

    String FirstName,LastName,Gender,Age,EmployeeNumber,ContactNumber,Email,Password,Balance;

    FirebaseAuth authProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);



        etfirstname =(EditText) findViewById(R.id.ETFirstName);
        etlastname =(EditText) findViewById(R.id.ETLastName);
        etgender = (EditText) findViewById(R.id.ETGender);
        etage = (EditText) findViewById(R.id.ETAge);
        etemployeenumber=(EditText) findViewById(R.id.ETEmployeeNumber);
        etcontactnumber =(EditText) findViewById(R.id.ETContactNumber);
        etemail =(EditText) findViewById(R.id.ETEmail);
        etpassword =(EditText) findViewById(R.id.ETPassword);
        etbalance =(EditText) findViewById(R.id.ETBalance);



        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        showProfile(firebaseUser);

        Button buttonUpdateProfile = findViewById(R.id.btnsave);
        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(firebaseUser);
            }
        });

//        user = FirebaseAuth.getInstance().getCurrentUser();
//        referenceStudent = FirebaseDatabase.getInstance().getReference("StudentSignUpConnectFirebase");
//        referenceFaculty = FirebaseDatabase.getInstance().getReference("FacultySignUpConnectFirebase");
//        userID = user.getUid();
//
//        referenceStudent.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                StudentSignUpConnectFirebase userProfile = snapshot.getValue(StudentSignUpConnectFirebase.class);
//
//
//                if (userProfile != null ){
//                     firstname = userProfile.FirstName;
//                     lastname = userProfile.LastName;
//                     gender = userProfile.Gender;
//                     age = userProfile.Age;
//                     employeenumber = userProfile.EmployeeNumber;
//                     email = userProfile.Email;
//
//
//                    etfirstname.setText(firstname);
//                    etlastname.setText(lastname);
//                    etgender.setText(gender);
//                    etage.setText(age);
//                    etemployeenumber.setText(employeenumber);
//                    etemail.setText(email);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(EditInfo.this,"Something Wrong Happen", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        referenceFaculty.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                FacultySignUpConnectFirebase userProfile = snapshot.getValue(FacultySignUpConnectFirebase.class);
//
//
//                if (userProfile != null ){
//                    String firstname = userProfile.FirstName;
//                    String lastname = userProfile.LastName;
//                    String gender = userProfile.Gender;
//                    String age = userProfile.Age;
//                    String employeenumber = userProfile.EmployeeNumber;
//                    String email = userProfile.Email;
//
//
//                    etfirstname.setText(firstname);
//                    etlastname.setText(lastname);
//                    etgender.setText(gender);
//                    etage.setText(age);
//                    etemployeenumber.setText(employeenumber);
//                    etemail.setText(email);
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(EditInfo.this,"Something Wrong Happen", Toast.LENGTH_LONG).show();
//            }
//        });


    }

    private void updateProfile(FirebaseUser firebaseUser) {
        if (TextUtils.isEmpty(FirstName)){
            etfirstname.setError("First Name is required");
            etfirstname.requestFocus();
        }else if (TextUtils.isEmpty(LastName)){
            etlastname.setError("First Name is required");
            etlastname.requestFocus();
        }else if(TextUtils.isEmpty(Gender)){
            etgender.setError("Gender is required");
            etgender.requestFocus();
        }else if(TextUtils.isEmpty(Age)){
           etage.setError("Age is required");
            etage.requestFocus();
        }else if(TextUtils.isEmpty(EmployeeNumber)){
            etemployeenumber.setError("Employee Number is required");
            etemployeenumber.requestFocus();
        }else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            etemail.setError("Please valid email");
            etemail.requestFocus();
        }else {
            FirstName = etfirstname.getText().toString();
            LastName = etlastname.getText().toString();
            Gender = etgender.getText().toString();
            Age = etage.getText().toString();
            EmployeeNumber = etemployeenumber.getText().toString();
            ContactNumber = etcontactnumber.getText().toString();
            Email = etemail.getText().toString();
            Password = etpassword.getText().toString();
            Balance = etbalance.getText().toString();

            StudentSignUpConnectFirebase writeUserDetails = new StudentSignUpConnectFirebase(FirstName,LastName,Gender,Age,EmployeeNumber,ContactNumber,Email,Password,Balance);

            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("StudentSignUpConnectFirebase");

            String userID = firebaseUser.getUid();

            referenceProfile.child(userID).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){

                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().
                                setDisplayName(Email).build();
                        firebaseUser.updateProfile(profileUpdates);

                        Toast.makeText(EditInfo.this,"Edit Successful", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(EditInfo.this,HomeScreen.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }else {
                        try {
                            throw task.getException();
                        }catch (Exception e){
                            Toast.makeText(EditInfo.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
    }

    private void showProfile(@NonNull FirebaseUser firebaseUser) {
        String userIDofRegistered = firebaseUser.getUid();

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("StudentSignUpConnectFirebase");

        referenceProfile.child(userIDofRegistered).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StudentSignUpConnectFirebase readUserDetails = snapshot.getValue(StudentSignUpConnectFirebase.class);

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


                    etfirstname.setText(FirstName);
                    etlastname.setText(LastName);
                    etgender.setText(Gender);
                    etage.setText(Age);
                    etemployeenumber.setText(EmployeeNumber);
                    etcontactnumber.setText(ContactNumber);
                    etemail.setText(Email);
                    etpassword.setText(Password);
                    etbalance.setText(Balance);

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

//    public void btnsv(View view) {

//        if(isFirstName() || isLastName()){
//            Toast.makeText(this, "Data Edited", Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(this, HomeScreen.class);
//            startActivity(intent);
//        }else {
//            Toast.makeText(this, "Data is te same and can not be edited", Toast.LENGTH_LONG).show();
//        }
//    }

//    private boolean isFirstName() {
//        if (!firstname.equals(etfirstname.getEditableText().toString())){
//            referenceStudent.child(firstname).child("FirstName").setValue(etfirstname.getEditableText().toString());
//            firstname = etfirstname.getText().toString();
//            return true;
//        }else {
//            return  false;
//        }
//    }
//
//    private boolean isLastName() {
//        if (!lastname.equals(etlastname.getEditableText().toString())){
//            referenceStudent.child(lastname).child("LastName").setValue(etlastname.getEditableText().toString());
//            lastname = etlastname.getText().toString();
//            return true;
//        }else {
//            return  false;
//        }
//    }


}
