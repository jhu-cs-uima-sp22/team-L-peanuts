package com.example.peanuts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
            String email = emailText.getText().toString();
            String pass = passText.getText().toString();

            if (email.equals("") || pass.equals("")) throw new Exception();

            myRef.child(email).child("password").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("retrieve", dataSnapshot.toString());
                    String savedPass = dataSnapshot.getValue().toString();

                    if (pass.equals(savedPass)) {
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("retrieve", databaseError.toString());
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
