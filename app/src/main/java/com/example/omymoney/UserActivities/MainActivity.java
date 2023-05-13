package com.example.omymoney.UserActivities;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.omymoney.Adapters.H_Adapter;
import com.example.omymoney.Authactivities.Signup;
import com.example.omymoney.Models.model_history;
import com.example.omymoney.R;
import com.example.omymoney.utils.constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    PendingIntent pendingIntent1;
    AlarmManager alarmManager;
      CardView cardView1,cardView2,cardView3,cardView4;
      TextView ADD , Expense, Month , year, EXPENSE_A ,Remain ;
      FirebaseAuth firebaseAuth;
      ImageButton imageButton;
    int totalPrice;
    SwipeRefreshLayout swipeRefreshLayout;
    H_Adapter h_adapter;
    ArrayList<model_history> historyArrayList;
    ArrayList<model_history> item1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ADD = findViewById(R.id.Addmonthly);
        swipeRefreshLayout= findViewById(R.id.swiperefresh);
        imageButton= findViewById(R.id.logout);
        Remain = findViewById(R.id.f);
        Month = findViewById(R.id.month);
        year = findViewById(R.id.year);
        EXPENSE_A= findViewById(R.id.d);
        cardView1 = findViewById(R.id.card1);
        Expense = findViewById(R.id.b);
        cardView2 = findViewById(R.id.card2);
        cardView3 = findViewById(R.id.card3);
        cardView4 = findViewById(R.id.card4);
        firebaseAuth = FirebaseAuth.getInstance();
        //
        /*notification_channel();
        pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(this, AlarmReceiver.class), PendingIntent.FLAG_IMMUTABLE);
        alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
         set_notification_alarm(1*60*1000);*/
        // calling functions
        DateTime();
        getexpense();
        retrive_history();





        Month.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                retrive_history();
                getexpense();

            }catch (Exception e){
                Toast.makeText(MainActivity.this, "Please Check Your Internet Connection & restart application", Toast.LENGTH_SHORT).show();
            }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });
        year.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    retrive_history();
                    getexpense();

                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Please Check Your Internet Connection & restart application", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });
        // click listeners
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getexpense();
                retrive_history();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        //
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(MainActivity.this, Signup.class));
            }
        });
        //
        Month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                months();
            }
        });
        //
        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Year();
            }
        });
        //
        ADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Monthly_expense.class));
            }
        });
        //
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, Expense.class);
            intent.putExtra("month",Month.getText().toString());
                intent.putExtra("Year",year.getText().toString());
                intent.putExtra("Income",Expense.getText().toString());
                startActivity(intent);

            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Saving.class);
                intent.putExtra("Saving",Remain.getText().toString());
                intent.putExtra("Income",Expense.getText().toString());
                startActivity(intent);

            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Add.class));
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, History.class));
            }
        });
    }

    private void months() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select your Month").setItems(constants.options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String category = constants.options[i];
                    Month.setText(category);

                }
            }).show();

        }
    // this show the dialogue with Year
    private void Year() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select your Year").setItems(constants.year, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String category = constants.year[i];
                year.setText(category);
            }
        }).show();

    }
    //
    public void DateTime() {

        Calendar c = Calendar.getInstance();
        String[]monthName={"January","February","March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};
        String month_=monthName[c.get(Calendar.MONTH)];
        int year_=c.get(Calendar.YEAR);
        int date=c.get(Calendar.DATE);
        Month.setText(month_);
        String d= String.valueOf(date);
        String y = String.valueOf(year_);

        year.setText(y);

    }

    //
    private  void getexpense() {
        item1= new ArrayList<>();
        item1.clear();
        // Database reference getting order
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User Data")
                .child(firebaseAuth.getUid())
                .child("income")
                .child(year.getText().toString())
                .child(Month.getText().toString());
                 databaseReference.
                addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    model_history Model = dataSnapshot.getValue(model_history.class);
                    item1.add(Model);
                }
                int totalamount= 0;
                for (int i = 0; i < item1.size(); i++) {
                    totalamount+= Double.parseDouble(item1.get(i).getAmount());
                }
                    try {

                      String  Amount = String.valueOf(totalamount);
                        Expense.setText(Amount);
                    } catch (Exception e){

              }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //
    public void retrive_history() {
        historyArrayList = new ArrayList<>();
        // Database reference getting order
        // Database reference getting order
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User Data")
                .child(firebaseAuth.getUid())
                .child(year.getText().toString())
                .child(Month.getText().toString());
        databaseReference.addValueEventListener(new ValueEventListener() {
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
                H_Adapter h_adapter = new H_Adapter(MainActivity.this, historyArrayList);
                h_adapter.notifyDataSetChanged();
                String t = String.valueOf(totalPrice);
                EXPENSE_A.setText(t);
               try {
                   int Totalcash= Integer.parseInt( Expense.getText().toString()) - totalPrice;
                    String saving= String.valueOf(Totalcash);
                   Remain.setText(saving);

               }catch (Exception e){}



                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //

    public void set_notification_alarm(long interval) {
        long triggerAtMillis = System.currentTimeMillis() + interval;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);

        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval,  pendingIntent1);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis,  pendingIntent1);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis,  pendingIntent1);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent1);
        }
    }
    private void notification_channel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Reminder";
            String description = "Notification Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Notification", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
