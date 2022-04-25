package com.example.peanuts;

import android.content.Context;
import android.media.Image;
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

public class GroupMealPlanAdapter extends ArrayAdapter<FoodItem> {
    int resource;

    ArrayList<FoodItem> foods = new ArrayList<>();

    public GroupMealPlanAdapter(Context ctx, int res, List<FoodItem> food) {
        super(ctx, res, food);
        resource = res;
        this.foods.addAll(food);
        Log.d("MEMBERS", String.valueOf(foods));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout groupMealPlanView;

        if (convertView == null) {
            groupMealPlanView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, groupMealPlanView, true);
        } else {
            groupMealPlanView = (LinearLayout) convertView;
        }

        ImageView mealPlanImage = (ImageView) groupMealPlanView.findViewById(R.id.FoodImage);
        TextView mealPlanName = (TextView) groupMealPlanView.findViewById(R.id.FoodName1);

        mealPlanImage.setImageDrawable(getItem(position).getImage());
        mealPlanName.setText(getItem(position).getName());


        return groupMealPlanView;
    }
}
