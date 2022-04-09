package com.example.peanuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class NewAccount extends AppCompatActivity {

    private DatabaseReference myRef;
    private FirebaseDatabase database;

    private EditText nameText;
    private EditText emailText;
    private EditText passText;
    private EditText confirmText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        database = FirebaseDatabase.getInstance("https://peanuts-e9a7c-default-rtdb.firebaseio.com/");
        myRef = database.getReference("users");

        nameText = (EditText) findViewById(R.id.new_name);
        emailText = (EditText) findViewById(R.id.new_email);
        passText = (EditText) findViewById(R.id.new_password);
        confirmText = (EditText) findViewById(R.id.confirm_password);

    }

    public void onStart() {
        super.onStart();
    }

    public void launchRestrictions(View view) {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String pass = passText.getText().toString();
        String confirm = confirmText.getText().toString();

        if (pass.equals(confirm) ) {
            writeNewUser(id, name, email,pass);
            Intent intent = new Intent(this, EditRestrictions.class);
            startActivity(intent);
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Invalid input";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }

    public void launchLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public static class User {

        public String name;
        public String password;
        public String email;

        public User(String username, String email, String password) {
            this.name = username;
            this.password = password;
            this.email = email;
        }
    }

    public void writeNewUser(String userId, String name, String email, String password) {
        User user = new User(name, email, password);

        myRef.child(email).setValue(user);
    }


}