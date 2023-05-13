package com.example.omymoney.Authactivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.omymoney.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {
    EditText et1, et2, et3, et4, et5, et6;
    TextView textView;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String Phone_N;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        final ProgressBar progressBar = findViewById(R.id.otpProgressBar);
        // Intailize the IDs
        textView= findViewById(R.id.resendTextView);
        et1 = findViewById(R.id.otpEditText1);
        et2 = findViewById(R.id.otpEditText2);
        et3 = findViewById(R.id.otpEditText3);
        et4 = findViewById(R.id.otpEditText4);
        et5 = findViewById(R.id.otpEditText5);
        et6 = findViewById(R.id.otpEditText6);
        final Button button = findViewById(R.id.verifyOTPBtn);
        progressBar.setVisibility(View.GONE);
        //clicks
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OTP.this,"Please wait for 1 minute and try again" , Toast.LENGTH_SHORT).show();
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        getIntent().getStringExtra("code")+getIntent().getStringExtra("phone"),
                        60,
                        TimeUnit.SECONDS,
                        OTP.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Toast.makeText(OTP.this, "Successful", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(OTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                Toast.makeText(OTP.this, "Code Sent", Toast.LENGTH_SHORT).show();


                            }
                        }

                );
            }
        });
        button.setOnClickListener(view -> {
            if (!et1.getText().toString().trim().isEmpty() && !et2.getText().toString().trim().isEmpty()
                    && !et3.getText().toString().trim().isEmpty() &&
                    !et4.getText().toString().trim().isEmpty() &&
                    !et5.getText().toString().trim().isEmpty() && !et6.getText().toString().trim().isEmpty()) {
                String entercodeotp =
                        et1.getText().toString() +
                                et2.getText().toString() +
                                et3.getText().toString() +
                                et4.getText().toString() +
                                et5.getText().toString() +
                                et6.getText().toString();

                if (getIntent().getStringExtra("backendotp") != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    button.setVisibility(View.INVISIBLE);

                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            getIntent().getStringExtra("backendotp"), entercodeotp

                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    button.setVisibility(View.VISIBLE);

                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(OTP.this, UserData.class);
                                        startActivity(intent);
                                        Toast.makeText(OTP.this, "login sucess", Toast.LENGTH_SHORT).show();

                                        SharedPreferences sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("name", "true");
                                        editor.apply();
                                    } else {
                                        Toast.makeText(OTP.this, "enter correct otp", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                } else {
                    Toast.makeText(this, "please check internet connection", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(OTP.this, "please enter otp", Toast.LENGTH_SHORT).show();
            }
        });
        // calling of Functions
        moveOTP();
    }
    // Function for moving towards the Otp Activivty
        public void moveOTP () {
            et1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    if (!s.toString().isEmpty()) {
                        et2.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            et2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    if (!s.toString().isEmpty()) {
                        et3.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            et3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    if (!s.toString().isEmpty()) {
                        et4.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            et4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    if (!s.toString().isEmpty()) {
                        et5.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            et5.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    if (!s.toString().isEmpty()) {
                        et6.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }


    }

