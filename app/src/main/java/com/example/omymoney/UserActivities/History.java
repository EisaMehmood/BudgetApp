package com.example.omymoney.UserActivities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.omymoney.Adapters.H_Adapter;
import com.example.omymoney.Models.model_history;
import com.example.omymoney.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class History extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    RecyclerView hsitory_recycler;
    H_Adapter h_adapter;
    TextView Empty;
    SwipeRefreshLayout swipeRefreshLayout;
    LottieAnimationView lottieAnimationView;
    ArrayList<model_history> historyArrayList;
    ArrayList<model_history> item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Empty = findViewById(R.id.empty);
        swipeRefreshLayout= findViewById(R.id.swiperefresh);
        hsitory_recycler = findViewById(R.id.recycler_his);
        lottieAnimationView = findViewById(R.id.animation_view);
        List<model_history> item = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        Empty.setVisibility(View.GONE);

        retrive_history();
        //clicks
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                retrive_history();

                swipeRefreshLayout.setRefreshing(false);
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

    //
    public class MonthComparator implements Comparator<model_history> {
        @Override
        public int compare(model_history item1, model_history item2) {
            SimpleDateFormat format = new SimpleDateFormat("MMMM", Locale.ENGLISH);
            try {
                Date date1 = format.parse(item1.getMonth());
                Date date2 = format.parse(item2.getMonth());
                return date2.compareTo(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        }
    }
//
    private void retrive_history() {
        historyArrayList = new ArrayList<>();
        hsitory_recycler.setLayoutManager(new LinearLayoutManager(History.this));


        // Database reference getting order
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("History");
        databaseReference.child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                historyArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    model_history Model = dataSnapshot.getValue(model_history.class);
                    historyArrayList.add(Model);
                }
                Collections.sort(historyArrayList, new Comparator<model_history>() {
                    @Override
                    public int compare(model_history Model, model_history t1) {
                        return t1.getDay().compareToIgnoreCase(Model.getDay());
                    }
                });
                Collections.sort(historyArrayList, new MonthComparator());
                H_Adapter h_adapter = new H_Adapter(History.this, historyArrayList);
                hsitory_recycler.setAdapter(h_adapter);
                h_adapter.notifyDataSetChanged();
                check();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

private  void  check(){
        if( historyArrayList.isEmpty()){
              Empty.setVisibility(View.VISIBLE);
        }
}
}


      //
