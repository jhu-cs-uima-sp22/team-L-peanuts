package com.example.peanuts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private DatabaseReference myRef;
    private FirebaseDatabase database;

    private EditText passText;
    private EditText emailText;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = FirebaseDatabase.getInstance("https://peanuts-e9a7c-default-rtdb.firebaseio.com/");
        myRef = database.getReference("users");

        passText = (EditText) findViewById(R.id.login_pass);
        emailText = (EditText) findViewById(R.id.login_email);
    }

    /** Called when the user clicks the login button */
    public void launchMain(View view) {
        Context context = getApplicationContext();
        CharSequence text = "Invalid input";
        int duration = Toast.LENGTH_SHORT;

        try {
            email = emailText.getText().toString();
            String pass = passText.getText().toString();

            if (email.equals("") || pass.equals("")) throw new Exception();

            if (email.contains(".")) {
                int index = email.indexOf(".");
                email = email.substring(0, index) + email.substring(index + 1);
            }

            myRef.child(email).child("password").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String savedPass = (String) dataSnapshot.getValue();

                    if (pass.equals(savedPass)) {
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        Context context = getApplicationContext();
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("user_email", email);
                        editor.putString("user_password", pass);
                        editor.apply();
                        startActivity(intent);
                    } else {
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } catch (Exception e) {
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }

    public void launchNewAccount(View view) {
        Intent intent = new Intent(this, NewAccount.class);
        startActivity(intent);
    }
}
