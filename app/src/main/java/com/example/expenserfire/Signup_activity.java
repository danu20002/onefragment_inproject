package com.example.expenserfire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup_activity extends AppCompatActivity {
Button login_btn,Signup_btn;
ProgressBar progressbar_sign;

TextInputLayout Email_signup,password_signup;
FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        login_btn=findViewById(R.id.login_btn_sign);
        password_signup=findViewById(R.id.password_signup);
        Email_signup=findViewById(R.id.email_signup);
        Signup_btn=findViewById(R.id.signupn_btn_sign);
        progressbar_sign=findViewById(R.id.progressbar_signup);
        auth=FirebaseAuth.getInstance();
        Signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=Email_signup.getEditText().getText().toString().trim();
                String password=password_signup.getEditText().getText().toString().trim();

                if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Email_signup.setError(null);
                    Email_signup.setEnabled(false);
                    if (!password.isEmpty()){
                       password_signup.setError(null);
                        password_signup.setEnabled(false);
                        progressbar_sign.setVisibility(View.VISIBLE);
                                  auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                      @Override
                                      public void onComplete(@NonNull Task<AuthResult> task) {
                                          if(task.isSuccessful()){

                                               auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                   @Override
                                                   public void onComplete(@NonNull Task<Void> task) {
                                                       if(task.isSuccessful()){
                                                           Signup_btn.setText("Check your Email");
                                                           Signup_btn.setEnabled(false);
                                                           progressbar_sign.setVisibility(View.INVISIBLE);
                                                           Toast.makeText(Signup_activity.this, "User Account is Created Successfully PLEASE VERIFY EMAIL SENT ", Toast.LENGTH_SHORT).show();
                                                       }
                                                   }
                                               }).addOnFailureListener(new OnFailureListener() {
                                                   @Override
                                                   public void onFailure(@NonNull Exception e) {
                                                       Toast.makeText(Signup_activity.this, "Something went wrong with server and Client please be notified"+task.getException(), Toast.LENGTH_SHORT).show();
                                                   }
                                               });
                                          }
                                      }
                                  });





                    }else{
                        password_signup.setError("Email is required");

                        Toast.makeText(Signup_activity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                }else{

                    Email_signup.setError("Email is required");

                    Toast.makeText(Signup_activity.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                }


            }
        });


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup_activity.this,Loginactivity.class));
                finish();
            }
        });





    }

}