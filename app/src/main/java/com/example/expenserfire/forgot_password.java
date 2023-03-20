package com.example.expenserfire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class forgot_password extends AppCompatActivity {
AppCompatButton forgot_btn,login_forgot;
EditText email_forgot;
FirebaseAuth auth;
ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email_forgot=findViewById(R.id.email_forgot);
        forgot_btn=findViewById(R.id.forgot_btn);
        login_forgot=findViewById(R.id.login_forgot);

        auth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressbar_forgot);
        forgot_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email_forgot=email_forgot.getText().toString().trim();
                if(!Email_forgot.isEmpty()){
                    progressBar.setVisibility(View.VISIBLE);
                    auth.sendPasswordResetEmail(Email_forgot).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(forgot_password.this, "Reset Email sent To your Email", Toast.LENGTH_SHORT).show();
                            email_forgot.setText("");
                            progressBar.setVisibility(View.INVISIBLE);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(forgot_password.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }else{
                    email_forgot.setError("Email Cannot be empty");
                    email_forgot.setEnabled(true);
                }
            }
        });
        login_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Loginactivity.class));
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
}