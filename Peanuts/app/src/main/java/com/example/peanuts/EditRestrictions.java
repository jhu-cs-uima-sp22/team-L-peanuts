package com.example.peanuts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class EditRestrictions extends AppCompatActivity {

    protected ArrayList<Item> restrictions;
    protected ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restrictions);

        // create temp ArrayList of items
        restrictions = new ArrayList<>();
        restrictions.add(new Item("Peanuts", false));
        restrictions.add(new Item("Dairy", false));
        restrictions.add(new Item("Seafood", false));
        restrictions.add(new Item("Soy", false));
        restrictions.add(new Item("Strawberries", false));
        restrictions.add(new Item("Shellfish", false));
        restrictions.add(new Item("Eggs", false));
        restrictions.add(new Item("Tree Nuts", false));
        restrictions.add(new Item("Wheat", false));
        restrictions.add(new Item("Gluten", false));
        restrictions.add(new Item("Avocado", false));
        restrictions.add(new Item("Sesame", false));

        adapter = new ItemAdapter(this, R.layout.item_restriction, restrictions);

        ListView myList = (ListView) this.findViewById(R.id.restrictions_list);
        myList.setAdapter(adapter);
        registerForContextMenu(myList);
        // refresh view
        adapter.notifyDataSetChanged();


    }
}