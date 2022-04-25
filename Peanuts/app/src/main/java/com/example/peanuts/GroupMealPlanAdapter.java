package com.example.peanuts;

import android.content.Context;
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
        LinearLayout groupMemberView;

        if (convertView == null) {
            groupMemberView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, groupMemberView, true);
        } else {
            groupMemberView = (LinearLayout) convertView;
        }

        TextView memberName = (TextView) groupMemberView.findViewById(R.id.UserName);
        TextView memberEmail = (TextView) groupMemberView.findViewById(R.id.UserEmail);
        ImageView memberImage = (ImageView) groupMemberView.findViewById(R.id.UserImage);

        Map<String, String> user = (Map<String, String>) getItem(position);

        String name = user.get("name");
        String email = user.get("email");

        memberName.setText(name);
        memberEmail.setText(email);

        Context context = getContext();
        memberImage.setImageDrawable(context.getDrawable(R.drawable.baseline_account_circle_24));

        return groupMemberView;
    }
}
