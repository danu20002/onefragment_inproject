package com.example.expenserfire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class update_trsact extends AppCompatActivity {
EditText amount_text,note_text;
String newtype;
AppCompatButton update_btn,delete_btn;
CheckBox expense_checked,Income_checked;


FirebaseFirestore firestore;
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_trsact);
        amount_text=findViewById(R.id.amount_adding_thing);
        note_text=findViewById(R.id.note_adding_thing);
         expense_checked=findViewById(R.id.expense_checkbox_thing);
         Income_checked=findViewById(R.id.income_checkbox_thing);
          update_btn=findViewById(R.id.update_btn);
          delete_btn=findViewById(R.id.delete_btn);

              firestore=FirebaseFirestore.getInstance();
               auth=FirebaseAuth.getInstance();



        String id=getIntent().getStringExtra("id");
        String amount=getIntent().getStringExtra("amount");
        String note=getIntent().getStringExtra("note");
        String type=getIntent().getStringExtra("type");

       amount_text.setText(amount);
       note_text.setText(note);
       switch (type){
           case "Income":
               newtype="Income";
               Income_checked.setChecked(true);
               break;
           case "Expense":
               newtype="Expense";
               expense_checked.setChecked(true);
               break;

       }
       Income_checked.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                newtype="Income";
                Income_checked.setChecked(true);
                expense_checked.setChecked(false);

           }
       });
       expense_checked.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               newtype="Expense";
               Income_checked.setChecked(false);
               expense_checked.setChecked(true);
           }
       });
       update_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String amount=amount_text.getText().toString();
               String notes=note_text.getText().toString();


       firestore.collection("Expenses").document(auth.getUid()).collection("Notes").document(id).update("amount",amount,"note",notes,"type",newtype).addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void unused) {
               onBackPressed();
               Toast.makeText(update_trsact.this, "UPDATED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Toast.makeText(update_trsact.this, e.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });
           }
       });


       delete_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
          firestore.collection("Expenses").document(auth.getUid()).collection("Notes").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
              @Override
              public void onSuccess(Void unused) {
                  onBackPressed();
                  Toast.makeText(update_trsact.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
              }
          }).addOnFailureListener(new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception e) {
                  Toast.makeText(update_trsact.this, e.getMessage(), Toast.LENGTH_SHORT).show();
              }
          });
           }
       });


    }
}