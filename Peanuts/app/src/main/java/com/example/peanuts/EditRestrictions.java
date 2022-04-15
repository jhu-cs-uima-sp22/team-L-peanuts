package com.example.peanuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
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
        Log.d("debug", "in edit restrictions");


        Intent intent = getIntent();
        String user = intent.getStringExtra("user");

        // create temp ArrayList of items
        restrictions = new ArrayList<>();
        restrictions.add(new Item("Avocado", false, getResources().getDrawable(R.drawable.avocado_icon)));
        restrictions.add(new Item("Dairy", false, getResources().getDrawable(R.drawable.dairy_icon)));
        restrictions.add(new Item("Eggs", false, getResources().getDrawable(R.drawable.eggs_icon)));
        restrictions.add(new Item("Gluten", false, getResources().getDrawable(R.drawable.gluten_icon)));
        restrictions.add(new Item("Peanuts", false, getResources().getDrawable(R.drawable.peanuts_icon)));
        restrictions.add(new Item("Seafood", false, getResources().getDrawable(R.drawable.seafood_icon)));
        restrictions.add(new Item("Sesame", false, getResources().getDrawable(R.drawable.sesame_icon)));
        restrictions.add(new Item("Shellfish", false, getResources().getDrawable(R.drawable.shellfish_icon)));
        restrictions.add(new Item("Soy", false, getResources().getDrawable(R.drawable.soy_icon)));
        restrictions.add(new Item("Strawberries", false, getResources().getDrawable(R.drawable.strawberries_icon)));
        restrictions.add(new Item("Tree Nuts", false, getResources().getDrawable(R.drawable.tree_nuts_icon)));
        restrictions.add(new Item("Wheat", false, getResources().getDrawable(R.drawable.wheat_icon)));
        Log.d("debug", "set array");

        adapter = new ItemAdapter(this, R.layout.item_restriction, restrictions, user);
        Log.d("debug", "set adapter");

        ListView myList = (ListView) this.findViewById(R.id.restrictions_list);
        Log.d("debug", "set list");

        myList.setAdapter(adapter);
        Log.d("debug", "connected list and adapter");

        registerForContextMenu(myList);
        // refresh view
        adapter.notifyDataSetChanged();
        Log.d("debug", "done");

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
