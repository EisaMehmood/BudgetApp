package com.example.omymoney.Authactivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.omymoney.R;
import com.example.omymoney.UserActivities.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserData extends AppCompatActivity {
    EditText first_name , last_name;
    Button button;
 FirebaseAuth firebaseAuth;
 SharedPreferences sharedPreferences;
    public static final  String SHARED_PERF_NAME="mypref";
   public static final  String FIRST_NAME="first name";
    private static final  String LAST_NAME="last name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (shared())
        {
            Intent intent=new Intent(UserData.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            SharedPreferences.Editor editor=getSharedPreferences("slide",MODE_PRIVATE).edit();
            editor.putBoolean("slide",true);
            editor.commit();
        }

        setContentView(R.layout.activity_user_data);
        first_name =  findViewById(R.id.FirstName);
        last_name = findViewById(R.id.LastName);
        firebaseAuth = FirebaseAuth.getInstance();
        button = findViewById(R.id.Done);
        //clicks
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(TextUtils.isEmpty(first_name.getText().toString())){
                   Toast.makeText(UserData.this, "Please enter first name Name", Toast.LENGTH_SHORT).show();
                   return;
               }
               if(TextUtils.isEmpty(last_name.getText().toString())){
                   Toast.makeText(UserData.this, "Please enter last Name", Toast.LENGTH_SHORT).show();
                   return;
               }
               save_data();

            }
        });
    }
    //function
    public void save_data(){
        // Write a message to the database
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("First name",""+first_name.getText().toString());
        hashMap.put("Last name",""+first_name.getText().toString());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User Data");
        databaseReference.child(firebaseAuth.getUid()).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(UserData.this, "Your Data is Added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserData.this, MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserData.this, e.getMessage() , Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserData.this,Signup.class));
            }
        });
    }
    public boolean shared(){
        SharedPreferences sharedPreferences=getSharedPreferences("slide",MODE_PRIVATE);
            boolean result=sharedPreferences.getBoolean("slide",false);
            return result;

    }


}