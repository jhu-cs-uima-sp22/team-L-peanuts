package com.example.peanuts.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.peanuts.FoodDetail;
import com.example.peanuts.FoodItem;
import com.example.peanuts.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PostAdapter extends ArrayAdapter<FoodItem> {
    int resource;
    protected FirebaseDatabase database;
    protected DatabaseReference myRef;
    private CardView cardView;

    public PostAdapter(Context ctx, int res, List<FoodItem> items) {
        super(ctx, res, items);
        resource = res;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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

        String name = it.getName();
        if (textView != null) {
            if (name != null) {
                textView.setText(name);
            } else {
                name = "Untitled";
                textView.setText(name);
            }
        }

        String path = it.getImageUri();

        if (image != null) {
            it.setImage(path, image);
        }

        cardView = (CardView) itemView.findViewById(R.id.card_view);
        if (cardView != null) {
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = getContext();
                    Intent intent = new Intent(context, FoodDetail.class);
                    intent.putExtra("name", it.getName());
                    intent.putExtra("allergens", it.getAllergens());
                    intent.putExtra("image", it.getImageUri());
                    context.startActivity(intent);
                    notifyDataSetChanged();
                }
            });
        }
        return itemView;
    }
}