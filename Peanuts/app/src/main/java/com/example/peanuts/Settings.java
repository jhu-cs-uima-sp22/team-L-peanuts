package com.example.peanuts;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class Settings extends AppCompatActivity {

    private SharedPreferences preferences;
    private TextView photo;
    private ImageView image;
    private ActivityResultLauncher<String> getContent;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://peanuts-e9a7c.appspot.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Context context = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        photo = (TextView) findViewById(R.id.profile_photo_text);
        image = (ImageView) findViewById(R.id.imageView2);
        TextView name_text = (TextView) findViewById(R.id.name_text);
        name_text.setText(preferences.getString("user_name", ""));
        TextView email_text = (TextView) findViewById(R.id.email_text);
        email_text.setText(preferences.getString("user_email", ""));
        TextView password_text = (TextView) findViewById(R.id.password_text);
        password_text.setText(preferences.getString("user_password", ""));

        database = FirebaseDatabase.getInstance("https://peanuts-e9a7c-default-rtdb.firebaseio.com/");
        myRef = database.getReference("users");

        getContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                image.setImageURI(result);
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContent.launch("image/*");
            }
        });
    }

    public void save(View view) {
        TextView textViewName = (TextView) findViewById(R.id.name_text);
        String name = textViewName.getText().toString();
        TextView textViewPassword = (TextView) findViewById(R.id.password_text);
        String password = textViewPassword.getText().toString();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_name", name);
        editor.putString("user_password", password);
        editor.apply();

        image.setDrawingCacheEnabled(true);
        image.buildDrawingCache();
        Bitmap bitmap = image.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        image.setDrawingCacheEnabled(false);
        byte[] data = baos.toByteArray();

        UUID random = UUID.randomUUID();
        String path = "images/" + random + ".jpg";
        String email = preferences.getString("user_email", "");
        myRef.child(email).child("profile_image").setValue(path);
        StorageReference fireRef = storage.getReference(path);

        // Create file metadata including the content type
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpg")
                .setCustomMetadata("myCustomProperty", "myValue")
                .build();

        // Update metadata properties
        fireRef.updateMetadata(metadata)
                .addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                    @Override
                    public void onSuccess(StorageMetadata storageMetadata) {
                        // Updated metadata is in storageMetadata
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Uh-oh, an error occurred!
                    }
                });

        UploadTask uploadTask = fireRef.putBytes(data, metadata);

        uploadTask.addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });

        finish();
    }

    public void logout(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}