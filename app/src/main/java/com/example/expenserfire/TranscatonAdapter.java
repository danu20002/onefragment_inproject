package com.example.expenserfire;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TranscatonAdapter extends RecyclerView.Adapter<TranscatonAdapter.ViewHolder> {
    Context context;
    ArrayList<transcationmodel> transcationmodelArrayList;

public TranscatonAdapter(Context context, ArrayList<transcationmodel> transcationmodelArrayList){
    this.context=context;
    this.transcationmodelArrayList=transcationmodelArrayList;
}






    @NonNull
    @Override
    public TranscatonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_recycler_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TranscatonAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
     transcationmodel model=transcationmodelArrayList.get(position);
     String priority=model.getType();
     if(priority.equals("Expense")){
holder.prioritycolor.setBackgroundResource(R.drawable.redshape);
     }else{
       holder.prioritycolor.setBackgroundResource(R.drawable.greenshape);
     }
     holder.amount_adder.setText(model.getAmount());
     holder.date_adder.setText(model.getDate());
     holder.note_adder.setText(model.getNote());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context,update_trsact.class);
            intent.putExtra("id",transcationmodelArrayList.get(position).getId());
            intent.putExtra("amount",transcationmodelArrayList.get(position).getAmount());
            intent.putExtra("note",transcationmodelArrayList.get(position).getNote());
            intent.putExtra("type",transcationmodelArrayList.get(position).getType());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           context.startActivity(intent);
        }
    });






    }

    @Override
    public int getItemCount() {
        return transcationmodelArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
      TextView note_adder,amount_adder,date_adder;
      View prioritycolor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            note_adder=itemView.findViewById(R.id.note_one);
            amount_adder=itemView.findViewById(R.id.amount_one);
            date_adder=itemView.findViewById(R.id.date_one);
          prioritycolor=itemView.findViewById(R.id.priorityid);

        }
    }
}
