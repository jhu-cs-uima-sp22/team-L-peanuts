package com.example.peanuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
        Context context = getApplicationContext();
        CharSequence text = "Passwords do not match";
        int duration = Toast.LENGTH_SHORT;

        try {
            String name = nameText.getText().toString();
            String email = emailText.getText().toString();
            String pass = passText.getText().toString();
            String confirm = confirmText.getText().toString();

            if (name.equals("") || confirm.equals("") ||
                    email.equals("") || pass.equals("")) throw new Exception();

            if (pass.equals(confirm)) {
                writeNewUser(name, email, pass);
                Intent intent = new Intent(this, EditRestrictions.class);
                intent.putExtra("user", email);
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        } catch (Exception e) {
            Toast toast = Toast.makeText(context, "Missing input", duration);
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
        public ArrayList<String> restrictions;

        public User(String username, String email, String password, ArrayList<String> restrictions) {
            this.name = username;
            this.password = password;
            this.email = email;
            this.restrictions = restrictions;
        }

        public String getName() { return name; }

        public String getEmail() { return email; }

        public ArrayList<String> getRestrictions() { return restrictions; }

    }

    public void writeNewUser(String name, String email, String password) {
        User user = new User(name, email, password, new ArrayList<>());

        Context context = getApplicationContext();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_email", email);
        editor.putString("user_name", name);
        editor.putString("user_password", password);
        editor.apply();
        myRef.child(email).setValue(user);
    }


}