package com.example.peanuts;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class FoodItemAdapter extends ArrayAdapter<FoodItem> {

    int resource;

    public FoodItemAdapter(Context ctx, int res, List<FoodItem> items)
    {
        super(ctx, res, items);
        resource = res;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout foodItemView;
        FoodItem it = getItem(position);

        if (convertView == null) {
            foodItemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, foodItemView, true);
        } else {
            foodItemView = (LinearLayout) convertView;
        }

        TextView name = (TextView) foodItemView.findViewById(R.id.food_item_name);
        name.setText(it.getName());

        ImageView image = (ImageView) foodItemView.findViewById(R.id.food_item_image);
        image.setImageDrawable(it.getImage());

        return foodItemView;
    }

}
