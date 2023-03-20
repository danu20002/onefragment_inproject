package com.example.expenserfire;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDragHandleView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class mainscreen_expense extends AppCompatActivity {
    SharedPreferences mprefs;
    private static final String SHARED_PREFS ="sharedprefs" ;
MaterialButton button_add;
FirebaseAuth auth;
FirebaseFirestore firestore;
RecyclerView recyclerView;
FirebaseUser firebaseUser;
ArrayList<transcationmodel> transcationmodelArrayList;
TranscatonAdapter transcatonAdapter;
int sumExpense=0;
int sumIncome=0;

ImageView refresh_btn,settings_btn;
TextView toatl_amont_transact,total_income,total_expense;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen_expense);
        recyclerView=findViewById(R.id.recyclerview);

        toatl_amont_transact=findViewById(R.id.toatal_money_transaction);
        total_expense=findViewById(R.id.Expense_money_toatal);
        total_income=findViewById(R.id.income_money_total);

        refresh_btn=findViewById(R.id.refresher);

         auth=FirebaseAuth.getInstance();
         firestore=FirebaseFirestore.getInstance();
           transcationmodelArrayList=new ArrayList<>();

           recyclerView.setLayoutManager(new LinearLayoutManager(this));
           recyclerView.setHasFixedSize(true);
        button_add=findViewById(R.id.floating_add);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(mainscreen_expense.this,transaction.class));
            }
        });

  fetchdata();
  
refresh_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(mainscreen_expense.this,mainscreen_expense.class));
        finish();
    }
});







    }

    private void fetchdata() {
      firestore.collection("Expenses").document(auth.getUid()).collection("Notes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
          @Override
          public void onComplete(@NonNull Task<QuerySnapshot> task) {
              for (DocumentSnapshot ds:task.getResult()){
                transcationmodel model=new transcationmodel(ds.getId(),ds.getString("note"), ds.getString("amount"),ds.getString("type"),ds.getString("date") );

               int amount= Integer.parseInt(ds.getString("amount"));
                if(ds.getString("type").equals("Expense")){
                 sumExpense=sumExpense+amount;
                }else{
                    sumIncome=sumIncome+amount;
                }



                transcationmodelArrayList.add(model);


              }
              total_expense.setText(String.valueOf(sumExpense));
              total_income.setText(String.valueOf(sumIncome));
              toatl_amont_transact.setText(String.valueOf(sumIncome-sumExpense));
              transcatonAdapter=new TranscatonAdapter(getApplicationContext(),transcationmodelArrayList);
              recyclerView.setAdapter(transcatonAdapter);
          }
      });

    }
}