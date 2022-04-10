package com.example.peanuts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class FoodItemAdapterProfile extends ArrayAdapter<FoodItem> {

    int resource;

    public FoodItemAdapterProfile(Context ctx, int res, List<FoodItem> items)
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

        ImageView foodButton = (ImageView) foodItemView.findViewById(R.id.food_item_image_profile);
        foodButton.setImageDrawable(it.getImage());

        return foodItemView;
    }

}
