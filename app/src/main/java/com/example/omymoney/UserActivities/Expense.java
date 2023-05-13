package com.example.omymoney.UserActivities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.omymoney.Adapters.H_Adapter;
import com.example.omymoney.Models.model_history;
import com.example.omymoney.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Expense extends AppCompatActivity {

    int totalPrice, Totalcash;
    String  month_name , Year , Expense ,saving;
    TextView Income , Amount, Saving, delete,Empty;
    FirebaseAuth firebaseAuth;
    RecyclerView hsitory_recycler;
    H_Adapter h_adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    LottieAnimationView lottieAnimationView, lottieAnimationView1;
    ArrayList<model_history> historyArrayList;
    ArrayList<model_history> item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        Amount = findViewById(R.id.expense);
        Saving = findViewById(R.id.e);
        Empty = findViewById(R.id.empty);
        lottieAnimationView1 = findViewById(R.id.animation_view1);
        swipeRefreshLayout= findViewById(R.id.swiperefresh);
        hsitory_recycler = findViewById(R.id.recycler_his);
        Income = findViewById(R.id.Income);
        lottieAnimationView1.setVisibility(View.GONE);
        delete = findViewById(R.id.delete);
        lottieAnimationView = findViewById(R.id.animation_view);
        List<model_history> item = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        month_name = getIntent().getStringExtra("month");
        Expense = getIntent().getStringExtra("Income");
        Year = getIntent().getStringExtra("Year");


        try {
            int number = Integer.parseInt(Expense);
            if(number==0){
                checker();
            }
            retrive_history();
            check();
        }catch (Exception e){

        }


               //Clicks
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                retrive_history();
                check();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        //
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Expense.this);
                builder.setTitle("Delete").setMessage("Are you sure you want to delete Product")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    delete();
                                } catch (Exception e) {
                                    Toast.makeText(Expense.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();

                            }
                        }).show();
            }
        });

        //
       lottieAnimationView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onBackPressed();
           }
            });

    }
    // FOR RETERIVING DATA
    private void retrive_history() {
        historyArrayList = new ArrayList<>();
        hsitory_recycler.setLayoutManager(new LinearLayoutManager(this));
        // Database reference getting order
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User Data")
                .child(firebaseAuth.getUid()).child(Year);
        databaseReference.child(month_name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                historyArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    model_history Model = dataSnapshot.getValue(model_history.class);
                    historyArrayList.add(Model);
                }
                totalPrice = 0;
                for (int i = 0; i < historyArrayList.size(); i++) {
                    totalPrice += Double.parseDouble(historyArrayList.get(i).getAmount());
                }
                String t = String.valueOf(totalPrice);
                H_Adapter h_adapter = new H_Adapter(Expense.this, historyArrayList);
                hsitory_recycler.setAdapter(h_adapter);
                h_adapter.notifyDataSetChanged();
                try{
                Income.setText(Expense);
                Amount.setText(t);
                Totalcash =  Integer.parseInt(Expense)-Integer.parseInt(t);
                saving= String.valueOf(Totalcash);
                Saving.setText(saving);
                check();}
                catch (Exception e){

                    Toast.makeText(Expense.this, "Please Restart the app", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //
    private void  check(){
        if( totalPrice!= 0) {

            lottieAnimationView1.setVisibility(View.GONE);
        }else {
            lottieAnimationView1.setVisibility(View.VISIBLE);
        }

    }
    // FOR DELETING DATA
    private void delete(){
        historyArrayList.clear();
        firebaseAuth =FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User Data")
                .child(firebaseAuth.getUid()).child(Year);
        databaseReference.child(month_name).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("History");
                databaseReference1.child(firebaseAuth.getUid()).removeValue();
                Toast.makeText(Expense.this,"Your data is removed",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Expense.this,e.getMessage(),Toast.LENGTH_SHORT).show();


            }
        });
    }
    // FOR CHEKCING
    private void checker(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Expense.this);
        builder.setCancelable(false);
        builder.setTitle("Note").setMessage("There is no Data Available")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Expense.this,MainActivity.class));
                        finish();
                    }
                }).show();
    }
//




}