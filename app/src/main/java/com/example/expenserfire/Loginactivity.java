package com.example.expenserfire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Loginactivity extends AppCompatActivity {

    private SharedPreferences mprefs;
    Button signup_btn,login_btn;
 EditText Email_login,password_login;
 FirebaseAuth auth;
 CheckBox mycheckbox;
 TextView forgot_passwords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        signup_btn=findViewById(R.id.signup_btn_login);
        login_btn=findViewById(R.id.login_btn_page);
        Email_login=findViewById(R.id.email_login);
        mycheckbox=findViewById(R.id.stayloggedin);
        password_login=findViewById(R.id.password_login);
        forgot_passwords=findViewById(R.id.forgotpassword);
          auth=FirebaseAuth.getInstance();


        forgot_passwords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(Loginactivity.this, forgot_password.class));

            }
        });
      auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
          @Override
          public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
              if(auth.getCurrentUser()!=null){
                  startActivity(new Intent(Loginactivity.this,mainscreen_expense.class));
                  finish();
              }else{
                  Toast.makeText(Loginactivity.this, "Login please", Toast.LENGTH_SHORT).show();
              }
          }
      });







        auth=FirebaseAuth.getInstance();
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_login=Email_login.getText().toString();
                String password_logn=password_login.getText().toString();


                if(!email_login.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(email_login).matches()){
                    if(!password_logn.isEmpty()){

                        auth.signInWithEmailAndPassword(email_login,password_logn).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                if(auth.getCurrentUser().isEmailVerified()){

                                    Toast.makeText(Loginactivity.this, "SignIn Successful", Toast.LENGTH_SHORT).show();
                                   startActivity(new Intent(Loginactivity.this,mainscreen_expense.class));
                                   finish();





                                }else{
                                    Toast.makeText(Loginactivity.this, "EMAIL NOT VERIFIED YET", Toast.LENGTH_LONG).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Loginactivity.this, "USERNAME AND PASSWORD NOT MATCHING", Toast.LENGTH_LONG).show();
                            }
                        });







                    }else {
                       password_login.setError("password is Required");


                    }
                }else {
                    Email_login.setError("Email Cannot be Empty");


                }
            }
        });





        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Loginactivity.this,Signup_activity.class));
                finish();
            }
        });
    }





}