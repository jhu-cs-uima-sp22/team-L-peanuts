package com.example.peanuts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.peanuts.MainActivity;
import com.example.peanuts.R;

import java.util.ArrayList;

public class EditFoods extends AppCompatActivity {

    private ListView myList;
    protected ArrayList<FoodItem> foodItems;
    protected FoodItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_foods);

        foodItems = new ArrayList<>();
        boolean[] restrictions = new boolean[12];
        restrictions[1] = true;
        restrictions[6] = true;
        restrictions[8] = true;
        restrictions[9] = true;
        Drawable image = getDrawable(R.drawable.spaghetti);
        foodItems.add(new FoodItem("Spaghetti", restrictions, image));

        myList = (ListView) this.findViewById(R.id.myFoodList);

        adapter = new FoodItemAdapter(this, R.layout.food_layout, foodItems);

        myList.setAdapter(adapter);

        registerForContextMenu(myList);

        adapter.notifyDataSetChanged();
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