package com.example.omymoney.Authactivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.omymoney.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class Signup extends AppCompatActivity {
    CountryCodePicker ccp;
    String countrycode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // Intailize the IDs
         ccp =  findViewById(R.id.editTextNumber2);
         ProgressBar progressBar = findViewById(R.id.phoneProgressBar);
        EditText editText = findViewById(R.id.phoneEditTextNumber);
        Button button= findViewById(R.id.sendOTPBtn);
        progressBar.setVisibility(View.GONE);
        //clicks
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
               countrycode="+"+selectedCountry.getPhoneCode();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editText.getText().toString().trim().isEmpty()){
                    if (editText.getText().toString().length() != 10){
                        Toast.makeText(Signup.this, "please enter correct number", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Signup.this,countrycode+editText.getText().toString(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.VISIBLE);
                        button.setVisibility(View.INVISIBLE);


                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                countrycode + editText.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                Signup.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        button.setVisibility(View.VISIBLE);

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        button.setVisibility(View.VISIBLE);

                                        Toast.makeText(Signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        Intent intent = new Intent(Signup.this, OTP.class);
                                        intent.putExtra("backendotp", s);
                                        intent.putExtra("code",countrycode);
                                        intent.putExtra("phone",editText.getText().toString());
                                        startActivity(intent);
                                        finish();
                                        progressBar.setVisibility(View.GONE);
                                        button.setVisibility(View.VISIBLE);

                                    }
                                }

                        );

                    }
                }else{
                    Toast.makeText(Signup.this, "please enter phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
//fun
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}