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

import java.util.ArrayList;
import java.util.Objects;

public class NewAccount extends AppCompatActivity {

    private DatabaseReference myRef;
    private FirebaseDatabase database;

    private EditText nameText;
    private EditText emailText;
    private EditText passText;
    private EditText confirmText;
    private boolean isSignUp;
    private String email;

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

        isSignUp = true;
        try {
            String name = nameText.getText().toString();
            email = emailText.getText().toString();
            String pass = passText.getText().toString();
            String confirm = confirmText.getText().toString();

            if (email.contains(".")) {
                int index = email.indexOf(".");
                email = email.substring(0, index) + email.substring(index + 1);
            }

            if (name.equals("") || confirm.equals("") ||
                    email.equals("") || pass.equals("")) throw new Exception();

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean isNewEmail = true;

                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        String currEmail = user.getKey();
                        if (Objects.requireNonNull(currEmail).equals(email)) {
                            isNewEmail = false;
                        }
                    }
                    if (isNewEmail && isSignUp) {
                        isSignUp = false;
                        if (pass.equals(confirm)) {
                            writeNewUser(name, email, pass);
                            Intent intent = new Intent(context, EditRestrictions.class);
                            intent.putExtra("user", email);
                            startActivity(intent);
                        } else {
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    } else if (isSignUp) {
                        Toast toast = Toast.makeText(context, "Email already in use", duration);
                        toast.show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

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
        public String path;
        public long response;

        User(String username, String email, String password, ArrayList<String> restrictions) {
            this.name = username;
            this.password = password;
            this.email = email;
            this.restrictions = restrictions;
        }

        public User(String username, String email, ArrayList<String> restrictions) {
            this.name = username;
            this.email = email;
            this.restrictions = restrictions;
        }

        public User(String username, String email, long response, ArrayList<String> restrictions) {
            this.name = username;
            this.email = email;
            this.response = response;
            this.restrictions = restrictions;
        }


        public String getName() { return name; }

        public String getEmail() { return email; }

        public void setPath(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }

        public ArrayList<String> getRestrictions() { return restrictions; }

        public long getResponse() {
            return response;
        }

        public void setResponse(int response) {
            this.response = response;
        }

    }

    public void writeNewUser(String name, String email, String password) {
        User user = new User(name, email, password, new ArrayList<>());

        Context context = getApplicationContext();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        if (email.contains(".")) {
            int index = email.indexOf(".");
            email = email.substring(0, index) + email.substring(index + 1);
        }
        editor.putString("user_email", email);
        editor.putString("user_name", name);
        editor.putString("user_password", password);
        editor.apply();
        myRef.child(email).setValue(user);

    }
}