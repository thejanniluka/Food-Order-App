package com.example.foodorderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp_Form extends AppCompatActivity {
        EditText txt_fullName,txt_username,txt_email,txt_password;
        Button btn_register;
        RadioButton radioGenderMale,getRadioGenderFemale;
        DatabaseReference databaseReference;
        String gender="";
        FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__form);
        getSupportActionBar().setTitle("Signup Form");

        //Casting View
        txt_fullName = (EditText) findViewById(R.id.txt_full_name);
        txt_username = (EditText) findViewById(R.id.txt_username);
        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_password = (EditText) findViewById(R.id.txt_password);
        btn_register = (Button) findViewById(R.id.btn_register);
        radioGenderMale = (RadioButton) findViewById(R.id.radio_male);
        getRadioGenderFemale = (RadioButton) findViewById(R.id.radio_female);

        databaseReference = FirebaseDatabase.getInstance().getReference("Food");
        firebaseAuth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String fullName = txt_fullName.getText().toString();
                final String userName = txt_username.getText().toString();
                final String email = txt_email.getText().toString();
                String password = txt_password.getText().toString();

                if (radioGenderMale.isChecked()) {
                    gender = "Male";
                }
                if (getRadioGenderFemale.isChecked()) {
                    gender = "Female";
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(SignUp_Form.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUp_Form.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(fullName)) {
                    Toast.makeText(SignUp_Form.this, "Please Enter FullName", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(SignUp_Form.this, "Please Enter UserName", Toast.LENGTH_SHORT).show();
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUp_Form.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Food information = new Food(
                                            fullName,
                                            userName,
                                            email,
                                            gender
                                    );

                                    FirebaseDatabase.getInstance().getReference("Food")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(SignUp_Form.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                        }
                                    });

                                } else {

                                }
                                //...
                            }
                        });

            }
        });
    }
            public void btn_loginForm(View view) {
            }
        }




