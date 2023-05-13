package com.example.omymoney.UserActivities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

import com.example.omymoney.R;
import com.example.omymoney.utils.constants;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class Add extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    EditText dateButton, amount, Note, Expense_name;
            TextView  month_name, Day, Year;
    Button button;
    LottieAnimationView lottieAnimationView;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        // Intailize iDS
        button = findViewById(R.id.Done);
        firebaseAuth = FirebaseAuth.getInstance();
        Expense_name = findViewById(R.id.expense_name);
        amount = findViewById(R.id.amount);
        lottieAnimationView = findViewById(R.id.animation_view);
        month_name = findViewById(R.id.Month);
        Day = findViewById(R.id.Day);
        Year = findViewById(R.id.year);
        Note = findViewById(R.id.Note);
        DateTime();
        // clicks
        lottieAnimationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Days();

            }
        });
        month_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                months();
            }
        });
        Year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Years();
            }
        });

        //  CLicks
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                constrains();
            }
        });
    }

    //
    private void constrains() {
        if (TextUtils.isEmpty(Expense_name.getText().toString())) {
            Toast.makeText(this, "Please Enter Expense Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(amount.getText().toString())) {
            Toast.makeText(this, "Please Enter Expense Amount", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Day.getText().toString())) {
            Toast.makeText(this, "Please Enter Expense Day", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(month_name.getText().toString())) {
            Toast.makeText(this, "Please Enter Expense Months", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Year.getText().toString())) {
            Toast.makeText(this, "Please Enter Expense Year", Toast.LENGTH_SHORT).show();
            return;
        }

        data_save();
        history();
    }

    //
    private void months() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select your Months").setItems(constants.options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String category = constants.options[i];
                month_name.setText(category);

            }
        }).show();
    }

    //
    private void Days() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select your Day").setItems(constants.DAYS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String category = constants.DAYS[i];
                Day.setText(category);

            }
        }).show();
    }

    //
    private void Years() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select your Year").setItems(constants.year, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String category = constants.year[i];
                Year.setText(category);

            }
        }).show();
    }

    // Method for saving data to firebase
    private void history() {
        String timestemp = "" + System.currentTimeMillis();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id",""+timestemp);
        hashMap.put("Date",""+Day.getText().toString()+""+ month_name.getText().toString()+""+ Year.getText().toString());
        hashMap.put("Expense_Name", "" + Expense_name.getText().toString());
        hashMap.put("Amount", "" + amount.getText().toString());
        hashMap.put("Day", "" + Day.getText().toString());
        hashMap.put("Month",""+ month_name.getText().toString());
        hashMap.put("Year",""+ Year.getText().toString());
        hashMap.put("Note", "" + Note.getText().toString());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("History");
        databaseReference.child(firebaseAuth.getUid()).child(timestemp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            }
        });

    }

    //
    private void data_save() {
        // Write a message to the database
        String timestemp = "" + System.currentTimeMillis();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id",""+timestemp);
        hashMap.put("Expense_Name", "" + Expense_name.getText().toString());
        hashMap.put("Amount", "" + amount.getText().toString());
        hashMap.put("Date",""+Day.getText().toString()+""+ month_name.getText().toString()+""+ Year.getText().toString());
        hashMap.put("Day", "" + Day.getText().toString());
        hashMap.put("Month",""+ month_name.getText().toString());
        hashMap.put("Year",""+ Year.getText().toString());
        hashMap.put("Note", "" + Note.getText().toString());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User Data")
                .child(firebaseAuth.getUid())
                .child(Year.getText().toString());
        databaseReference.child(month_name.getText().toString())
                .child(timestemp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                startActivity(new Intent(Add.this, MainActivity.class));
                finish();
                Toast.makeText(Add.this, "Your Data is Added", Toast.LENGTH_SHORT).show();
                clear_data();
            }
        });
        //
    }

    //
    private void clear_data() {
        Expense_name.setText("");
        amount.setText("");
        Day.setText("");
        month_name.setText("");
        Year.setText("");
        Note.setText("");
    }
    //


    public void DateTime() {

        Calendar c = Calendar.getInstance();
        String[]monthName={"January","February","March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};
        String month_=monthName[c.get(Calendar.MONTH)];
        int year=c.get(Calendar.YEAR);
        int date=c.get(Calendar.DATE);
        month_name.setText(month_);
      String d= String.valueOf(date);
       String y = String.valueOf(year);
       Day.setText(d);
         Year.setText(y);
    }
}