package com.example.omymoney.Authactivities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.omymoney.R;
import com.example.omymoney.UserActivities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity {
       FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        firebaseAuth = FirebaseAuth.getInstance();
                 check_user();
    }
    // Function for the check users
    private void check_user(){
        if(firebaseAuth.getCurrentUser()==null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(Splash.this, Signup.class));
                    finish();

                }
            }, 5000);
        }

        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Splash.this, MainActivity.class));
                    finish();

                }
            }, 5000);
        }
    }
}