package com.example.peanuts.ui.add;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.peanuts.FoodItem;

import com.example.peanuts.RestrictionItem;
import com.example.peanuts.R;
import com.example.peanuts.databinding.FragmentAddBinding;
import com.example.peanuts.ui.profile.ProfileFragment;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.UUID;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class AddFragment extends Fragment implements View.OnClickListener{

    private FragmentAddBinding binding;
    private Button save;
    private Button clear;
    private Button image;
    private EditText name;
    private String nameOfFood;
    private ImageView imageView;
    private ImageView imageView2;
    private boolean setImage;
    private ActivityResultLauncher<String> getContent;
    private SharedPreferences sp;

    public ArrayList<RestrictionItem> restrictions;
    protected FoodPostAdapter adapter;
    protected FirebaseDatabase database;
    protected DatabaseReference myRef;
    private ArrayList<FoodItem> usersPost = new ArrayList<>();

    // instance for firebase storage and StorageReference

    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://peanuts-e9a7c.appspot.com");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        database = FirebaseDatabase.getInstance("https://peanuts-e397e-default-rtdb.firebaseio.com/");

        Context context = getContext();
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        String email = sp.getString("user_email", "email");
        myRef = database.getReference("Users");
        binding = FragmentAddBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        name = (EditText) root.findViewById(R.id.item_text);
        name.setHint("Name of food");
        image = root.findViewById(R.id.pick_image);
        imageView = root.findViewById(R.id.imageView);
        imageView2 = root.findViewById(R.id.imageView2);
        setImage = false;

        //allergen list
        restrictions = new ArrayList<>();
        restrictions.add(new RestrictionItem("Avocado", false, getResources().getDrawable(R.drawable.avocado_icon)));
        restrictions.add(new RestrictionItem("Dairy", false, getResources().getDrawable(R.drawable.dairy_icon)));
        restrictions.add(new RestrictionItem("Eggs", false, getResources().getDrawable(R.drawable.eggs_icon)));
        restrictions.add(new RestrictionItem("Gluten", false, getResources().getDrawable(R.drawable.gluten_icon)));
        restrictions.add(new RestrictionItem("Peanuts", false, getResources().getDrawable(R.drawable.peanuts_icon)));
        restrictions.add(new RestrictionItem("Seafood", false, getResources().getDrawable(R.drawable.seafood_icon)));
        restrictions.add(new RestrictionItem("Sesame", false, getResources().getDrawable(R.drawable.sesame_icon)));
        restrictions.add(new RestrictionItem("Shellfish", false, getResources().getDrawable(R.drawable.shellfish_icon)));
        restrictions.add(new RestrictionItem("Soy", false, getResources().getDrawable(R.drawable.soy_icon)));
        restrictions.add(new RestrictionItem("Strawberries", false, getResources().getDrawable(R.drawable.strawberries_icon)));
        restrictions.add(new RestrictionItem("Tree Nuts", false, getResources().getDrawable(R.drawable.tree_nuts_icon)));
        restrictions.add(new RestrictionItem("Wheat", false, getResources().getDrawable(R.drawable.wheat_icon)));

        adapter = new FoodPostAdapter(getContext(), R.layout.item_restriction, restrictions, restrictions);

        ListView myList = (ListView) root.findViewById(R.id.list);
        myList.setAdapter(adapter);
        registerForContextMenu(myList);
        // refresh view
        adapter.notifyDataSetChanged();

        myRef.child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    Log.d("retrieve_success", dataSnapshot.toString());
                    for (DataSnapshot posts: dataSnapshot.getChildren()) {
                        usersPost.add(posts.getValue(FoodItem.class));
                    }

                } else {
                    usersPost = new ArrayList<>();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("retrieve_fail", databaseError.toString());
            }
        });

        save = (Button) root.findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create post
                //save data in database
                Context context = getContext();
                int duration = Toast.LENGTH_LONG;
                nameOfFood = name.getText().toString();
                CharSequence text;
                if (!setImage) {
                    text = "You need to pick an image";
                }
                else if (nameOfFood.equals("")) {
                    text = "You need to name your food";
                } else {
                    text = "Food Added!";
                    myRef.child(email).setValue(new ArrayList<>());

                    imageView.setDrawingCacheEnabled(true);
                    imageView.buildDrawingCache();
                    Bitmap bitmap = imageView.getDrawingCache();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    imageView.setDrawingCacheEnabled(false);
                    byte[] data = baos.toByteArray();

                    UUID random = UUID.randomUUID();
                    String path = "images/" + random + ".jpg";
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

                    uploadTask.addOnSuccessListener(getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });

                    FoodItem post = new FoodItem(nameOfFood, path);
                    for(int i = 0; i < restrictions.size(); i++) {
                        if (restrictions.get(i).isChecked()) {
                            post.addAllergens(restrictions.get(i).getItem());
                        }
                    }
                    usersPost.add(post);

                    myRef.child(email).setValue(usersPost);

                    clearContent();
                }

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
        clear = (Button) root.findViewById(R.id.clear_button);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearContent();
            }
        });

        //IMAGE
        getContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imageView.setImageURI(result);
                setImage = true;
                imageView2.setVisibility(View.INVISIBLE);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContent.launch("image/*");
            }
        });

        return root;
    }


    private void clearContent () {
        name.setText("");
        name.setHint("Name of food");

        for(int i = 0; i < restrictions.size(); i++) {
            if (restrictions.get(i).isChecked()) {
                restrictions.get(i).changeChecked(false);
            }
        }
        adapter.notifyDataSetChanged();
        imageView.setImageResource(android.R.color.transparent);
        imageView2.setVisibility(View.VISIBLE);
        setImage = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onClick(View view) {
        //create post
        //save data in database

        Fragment fragment = new ProfileFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_view, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}