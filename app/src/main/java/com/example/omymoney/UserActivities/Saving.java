package com.example.omymoney.UserActivities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.omymoney.R;

public class Saving extends AppCompatActivity {
    LottieAnimationView lottieAnimationView, lottieAnimationView1,lottieAnimationView2;
    TextView Saving;
    String amout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving);
        lottieAnimationView = findViewById(R.id.animation_view);
        lottieAnimationView1= findViewById(R.id.animation_view1);
        lottieAnimationView2 = findViewById(R.id.animation_view2);
        Saving = findViewById(R.id.saving_amount);
        amout = getIntent().getStringExtra("Saving");

        Integer NUM2 = Integer.parseInt(amout);
        try {
            Saving.setText(amout);
            change();

            if (NUM2 == 0) {
                checker();

            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        //clicks
        lottieAnimationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void checker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Saving.this);
        builder.setCancelable(false);
        builder.setTitle("Note").setMessage("There is no Data Available")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Saving.this, MainActivity.class));
                        finish();
                    }
                }).show();
    }

    //
    private void change() {
        Integer num =  Integer.parseInt(amout);
        if (num<0) {
            lottieAnimationView2.setVisibility(View.VISIBLE);
        }else{
            lottieAnimationView2.setVisibility(View.GONE);
        }

    }
}