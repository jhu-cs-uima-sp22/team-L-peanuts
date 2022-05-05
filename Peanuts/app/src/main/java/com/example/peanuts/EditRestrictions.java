package com.example.peanuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import java.util.ArrayList;

public class EditRestrictions extends AppCompatActivity {

    protected ArrayList<RestrictionItem> restrictions;
    protected RestrictionsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restrictions);

        Intent intent = getIntent();
        String user = intent.getStringExtra("user");

        restrictions = new ArrayList<>();
        restrictions.add(new RestrictionItem("Avocado", false, getResources().getDrawable(R.drawable.avocado_icon)));
        restrictions.add(new RestrictionItem("Dairy", false, getResources().getDrawable(R.drawable.dairy_icon)));
        restrictions.add(new RestrictionItem("Eggs", false, getResources().getDrawable(R.drawable.eggs_icon)));
        restrictions.add(new RestrictionItem("Gluten", false, getResources().getDrawable(R.drawable.gluten_icon)));
        restrictions.add(new RestrictionItem("Peanuts", false, getResources().getDrawable(R.drawable.peanuts_icon)));
        restrictions.add(new RestrictionItem("Seafood", false, getResources().getDrawable(R.drawable.seafood_icon)));
        restrictions.add(new RestrictionItem("Sesame", false, getResources().getDrawable(R.drawable.sesame_icon)));
        restrictions.add(new RestrictionItem("Shellfish", false, getResources().getDrawable(R.drawable.shellfish_icon)));
        restrictions.add(new RestrictionItem("Soy", false, getResources().getDrawable(R.drawable.soy_icon)));
        restrictions.add(new RestrictionItem("Strawberries", false, getResources().getDrawable(R.drawable.strawberries_icon)));
        restrictions.add(new RestrictionItem("Tree Nuts", false, getResources().getDrawable(R.drawable.tree_nuts_icon)));
        restrictions.add(new RestrictionItem("Wheat", false, getResources().getDrawable(R.drawable.wheat_icon)));

        adapter = new RestrictionsAdapter(this, R.layout.item_restriction, restrictions, user);

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
