package com.example.peanuts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.media.session.PlaybackState;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupMealPlanAdapter extends RecyclerView.Adapter<GroupMealPlanAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<FoodItem> foodItems;

    public GroupMealPlanAdapter(Context context,List<FoodItem> foodItems) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.foodItems = foodItems;
    }

    @Override
    public GroupMealPlanAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mealplan_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(foodItems.get(position).getName());
        String path = foodItems.get(position).getImageUri();
        foodItems.get(position).setImage(path, holder.image);
        //holder.image.setImageDrawable(foodItems.get(position).getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Pop.class);
                String foodName = foodItems.get(holder.getAdapterPosition()).getName();
                boolean[] data = foodItems.get(holder.getAdapterPosition()).getRestrictions();
                ArrayList<String> allergens = foodItems.get(holder.getAdapterPosition()).getAllergens();
                ArrayList<String> stringData = new ArrayList<>();
                stringData.add("\nAllergens/Restrictions:\n");
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                if (allergens != null) {
                    stringData.addAll(allergens);
                }
                /*for (int i = 0; i < 12; i++) {
                    if (data[i]) {
                        stringData.add(preferences.getString("" + i, ""));
                    }
                }*/
                intent.putExtra("foodName", foodName);
                intent.putExtra("data", stringData);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        final TextView name;
        final ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.FoodName1);
            image = (ImageView)itemView.findViewById(R.id.FoodImage);
        }
    }
}

