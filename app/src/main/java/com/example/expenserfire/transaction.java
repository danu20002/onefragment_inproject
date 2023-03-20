package com.example.expenserfire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import kotlin.jvm.internal.MagicApiIntrinsics;

public class transaction extends AppCompatActivity {
AppCompatButton add_btn;
EditText amount_add,note_add;
CheckBox expense_check,income_check;
String userchecktype="";
FirebaseFirestore firestore;
FirebaseAuth auth;
ProgressBar progressBar;
FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        add_btn=findViewById(R.id.add_to_list_bt);
        amount_add=findViewById(R.id.amount_adding);
        note_add=findViewById(R.id.note_adding);
        expense_check=findViewById(R.id.expense_checkbox);
        income_check=findViewById(R.id.income_checkbox);
        progressBar=findViewById(R.id.progressbar_transcation_save);
        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        firebaseUser=auth.getCurrentUser();

        expense_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userchecktype="Expense";
                expense_check.setChecked(true);
                income_check.setChecked(false);
            }
        });
       income_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userchecktype="Income";
                expense_check.setChecked(false);
                income_check.setChecked(true);
            }
        });
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount=amount_add.getText().toString().trim();
                String notes=note_add.getText().toString().trim();
                 if(amount.length()<=0){
                     return;
                 }
if(userchecktype.length()<=0){
    Toast.makeText(transaction.this, "select transaction type please", Toast.LENGTH_SHORT).show();
}

                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd MM yyyy HH:mm:ss", Locale.getDefault());
                 String currentdateandtime=simpleDateFormat.format(new Date());
                Map<String,Object> transaction_ss=new HashMap<>();
                 String id= UUID.randomUUID().toString();
                 transaction_ss.put("id",id);
                 transaction_ss.put("amount",amount);
                   transaction_ss.put("note",notes);
                   transaction_ss.put("type",userchecktype);
                   transaction_ss.put("date",currentdateandtime);

               progressBar.setVisibility(View.VISIBLE);
              firestore.collection("Expenses").document(auth.getUid()).collection("Notes").document(id).set(transaction_ss).addOnSuccessListener(new OnSuccessListener<Void>() {
                  @Override
                  public void onSuccess(Void unused) {
                      Toast.makeText(transaction.this, "Added succesfully", Toast.LENGTH_SHORT).show();
                      amount_add.setText("");
                      note_add.setText("");
                      expense_check.setChecked(false);
                      income_check.setChecked(false);
                      progressBar.setVisibility(View.INVISIBLE);
                  }
              }).addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {
                      Toast.makeText(transaction.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                  }
              });


            }
        });

    }
}