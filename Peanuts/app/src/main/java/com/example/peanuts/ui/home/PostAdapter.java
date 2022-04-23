package com.example.peanuts.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peanuts.FoodItem;
import com.example.peanuts.R;
import com.example.peanuts.UsersAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

//public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> implements Filterable {
public class PostAdapter extends ArrayAdapter<FoodItem> {
    int resource;
    protected FirebaseDatabase database;
    protected DatabaseReference myRef;
    private SharedPreferences sp;
    private ArrayList<FoodItem> usersPost;
    private StorageReference storageReference;
    private final List<FoodItem> fullPosts = new ArrayList<>();
    private List<FoodItem> posts;
    private final UsersAdapter.Listener listener = new UsersAdapter.Listener();



    public PostAdapter(Context ctx, int res, List<FoodItem> items) {
        super(ctx, res, items);
        Log.d("debug", "in post constructor");
        resource = res;
        this.posts = items;
        fullPosts.addAll(items);


    }

    /*private final View.OnClickListener onClickListener = (view) -> {
        FoodItem it = (FoodItem) view.getTag();
        Log.d("USER", String.valueOf(it));

        //TODO go to food item page
    };*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("debug", "in getView");
        LinearLayout itemView;
        FoodItem it = getItem(position);

        if (convertView == null) {
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, itemView, true);
        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView textView = (TextView) itemView.findViewById(R.id.name_text);
        ImageView image = (ImageView) itemView.findViewById(R.id.image);
        TextView allergenText = (TextView) itemView.findViewById(R.id.allergens);

        //Title
        String name = it.getName();
        if (name != null) {
            textView.setText(name);
            Log.d("debug", "set name");
        } else {
            name = "Untitled";
            textView.setText(name);
        }

        //Image
        //it.getHasImage()

            String path = it.getImageUri();
            Log.d("debug", "got path");
            Log.d("debug", path);
        if (path != null && !path.equals("")) {
            //get the storage reference
            storageReference = FirebaseStorage.getInstance("gs://peanuts-e9a7c.appspot.com").getReference().child(path);
            Log.d("debug", "got storage ref");

            //create temp file for image
            File file = null;
            try {
                file = File.createTempFile("images", "jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }

            File finalLocalFile = file;
            Log.d("debug", "got file");

            //store to storage
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.d("debug", "in on success for retrieving image");
                    Bitmap bitmap = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());
                    image.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("debug", "in on failure for retrieving image");

                }
            });
        }

        Log.d("debug", "didn't have image");
        return itemView;
    }

    /*public static Bitmap stringToBitmap(String encodedString){
        try{
            //String base64Image = encodedString.split(",")[1];
            String base64Image = encodedString.substring(8);
            byte [] encodeByte = Base64.decode(base64Image,Base64.URL_SAFE);

            Log.d("debug", "converted");
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        }
        catch(Exception e){
            Log.d("App", "Failed to decode image " + e.getMessage());
            return null;
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<FoodItem> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(fullPosts);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (FoodItem item : fullPosts) {
                        if (item.getName().toLowerCase().contains(filterPattern)
                                || item.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;

                Log.d("results", String.valueOf(results));

                return results;
            }
            @Override
            protected void publishResults(final CharSequence constraint,
                                          FilterResults results) {
                posts.clear();
                posts.addAll((ArrayList) results.values);
                notifyDataSetChanged();

            }

            @Override
            public boolean equals(@Nullable Object obj) {
                return super.equals(obj);
            }
        };
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.explore_posts, parent, false);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        holder.textView.setText(posts.get(position).getName());

        //holder.image.setImageBitmap(posts.get(position).getEmail());
        setImage(holder, posts.get(position).getImageUri(), holder.image);
        holder.itemView.setTag(posts.get(position));
        holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView textView;
        final ImageView image;

        ViewHolder(View view) {
            super(view);
            textView = (TextView) itemView.findViewById(R.id.name_text);
            image = (ImageView) itemView.findViewById(R.id.image);
        }

    }

    public void setImage (@NonNull PostAdapter.ViewHolder holder, String path, ImageView image) {
        storageReference = FirebaseStorage.getInstance("gs://peanuts-e9a7c.appspot.com").getReference().child(path);
        Log.d("debug", "got storage ref");
        try {
            String prefix = path.substring(6, path.length() - 4);
            Log.d("debug", "prefix: " + prefix);
            // it.getRandomID().toString();
            Log.d("debug", "got random");
            final File file = File.createTempFile(prefix, "png");
            Log.d("debug", "got fileEm");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.d("debug", "in on success for retrieving image");
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    holder.image.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("debug", "in on failure for retrieving image");

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}