package com.example.peanuts.ui.add;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.peanuts.R;

import java.util.ArrayList;
import java.util.List;

import com.example.peanuts.RestrictionItem;

public class FoodPostAdapter extends ArrayAdapter<RestrictionItem> {
    int resource;
    private ArrayList<RestrictionItem> checkedRestrictionItem;

    public FoodPostAdapter(Context ctx, int res, List<RestrictionItem> restrictionItems, ArrayList<RestrictionItem> checkedRestrictionItem) {
        super(ctx, res, restrictionItems);
        resource = res;
        this.checkedRestrictionItem = checkedRestrictionItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout itemView;
        RestrictionItem it = getItem(position);

        if (convertView == null) {
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, itemView, true);
        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView textView = (TextView) itemView.findViewById(R.id.restriction_name);
        CheckBox checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);


        textView.setText(it.getItem());
        Boolean checked = it.isChecked();
        checkBox.setChecked(checked);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                it.changeChecked(b);
            }
        });

        return itemView;
    }

}
