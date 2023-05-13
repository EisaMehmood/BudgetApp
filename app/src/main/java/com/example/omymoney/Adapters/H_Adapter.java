package com.example.omymoney.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.omymoney.Models.model_history;
import com.example.omymoney.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class H_Adapter extends RecyclerView.Adapter<H_Adapter.viewholder> {
    FirebaseAuth firebaseAuth;
    public H_Adapter(Context context, ArrayList<model_history> history_list) {
        this.context = context;
        this.history_list = history_list;
    }

    Context context;
    ArrayList<model_history> history_list;
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_for_history,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
     model_history Model_history = history_list.get(position);
     String ID = Model_history.getId();
     String Year = Model_history.getYear();
     String Month = Model_history.getMonth();
     holder.expense_name.setText("Expense Name :"+Model_history.getExpense_Name());
     holder.expense_amount.setText("Expense Amount :"+Model_history.getAmount());
     holder.expense_note.setText("Expense Note :"+Model_history.getNote());
     holder.expense_date.setText("Expense Date :"+Model_history.getDate());
     holder.delete.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            DELETE(ID,Year,Month);
         }
     });


    }

    private void DELETE(String id, String year, String month) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth =FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User Data")
                .child(firebaseAuth.getUid()).child(year);
        databaseReference.child(month).child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("History");
                databaseReference1.child(firebaseAuth.getUid()).child(id).removeValue();
                Toast.makeText(context,"Your data is removed",Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();

            }
        });

    }

    @Override
    public int getItemCount() {
        return history_list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView expense_name,expense_amount,expense_note,expense_date,delete;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.delete);
            expense_name= itemView.findViewById(R.id.expense_name);
            expense_amount= itemView.findViewById(R.id.expense_amount);
            expense_note= itemView.findViewById(R.id.expense_note);
            expense_date = itemView.findViewById(R.id.expense_Date);

        }

    }

}
