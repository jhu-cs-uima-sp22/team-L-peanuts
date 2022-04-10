package com.example.peanuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import java.util.ArrayList;

public class EditRestrictions extends AppCompatActivity {

    protected ArrayList<Item> restrictions;
    protected ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restrictions);

        Intent intent = getIntent();
        String user = intent.getStringExtra("user");

        // create temp ArrayList of items
        restrictions = new ArrayList<>();
        restrictions.add(new Item("Avocado", false));
        restrictions.add(new Item("Dairy", false));
        restrictions.add(new Item("Eggs", false));
        restrictions.add(new Item("Gluten", false));
        restrictions.add(new Item("Peanuts", false));
        restrictions.add(new Item("Seafood", false));
        restrictions.add(new Item("Sesame", false));
        restrictions.add(new Item("Shellfish", false));
        restrictions.add(new Item("Soy", false));
        restrictions.add(new Item("Strawberries", false));
        restrictions.add(new Item("Tree Nuts", false));
        restrictions.add(new Item("Wheat", false));

        adapter = new ItemAdapter(this, R.layout.item_restriction, restrictions, user);

        ListView myList = (ListView) this.findViewById(R.id.restrictions_list);
        myList.setAdapter(adapter);
        registerForContextMenu(myList);
        // refresh view
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_done) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        } else {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            return super.onOptionsItemSelected(item);
        }
    }
}
