package com.example.peanuts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class NewGroup extends AppCompatActivity {

    private DatabaseReference usersDB;
    private DatabaseReference groupsDB;
    private FirebaseDatabase database;

    protected ArrayList<NewAccount.User> users;
    protected String uuid;
    protected String groupName;
    protected String user;

    protected UsersAdapter adapter;
    protected Context context;
    protected RecyclerView myList;

    private SharedPreferences preferences;
    private final Listener listener = new Listener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("debug", "in new group");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        Log.d("FRAG", "new group activity");

        context = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.user = preferences.getString("user_email", "");

        database = FirebaseDatabase.getInstance("https://peanuts-e9a7c-default-rtdb.firebaseio.com/");
        usersDB = database.getReference("users");
        groupsDB = database.getReference("groups");

        uuid = String.valueOf(UUID.randomUUID());
        users = new ArrayList<>();
        myList = (RecyclerView) findViewById(R.id.users_list);
        myList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UsersAdapter(context, users, uuid);
        myList.setAdapter(adapter);

        handleSearch();

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Query usersQuery = usersDB.orderByChild("name");
        usersQuery.addValueEventListener(listener);
    }

    public class Listener implements ValueEventListener {
        private ArrayList<String> groups;
        @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot userItem: dataSnapshot.getChildren()) {
                String email = (String) userItem.child("email").getValue();

                if (!email.equals(user)) {
                    String name = (String) userItem.child("name").getValue();
                    ArrayList<String> restrictions = (ArrayList<String>) userItem.child("restrictions").getValue();

                    NewAccount.User item = new NewAccount.User(name, email, restrictions);
                    users.add(item);
                }

                adapter = new UsersAdapter(context, users, uuid);
                myList.setAdapter(adapter);
                // refresh view
                adapter.notifyDataSetChanged();
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.d("users_retrieve_error", databaseError.toString());
        }

    }

    private void handleSearch() {
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                findViewById(R.id.group_search);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        // attach setOnQueryTextListener
        // to search view defined above
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    // Override onQueryTextSubmit method
                    // which is call
                    // when submitquery is searched

                    @Override
                    public boolean onQueryTextSubmit(String query)
                    {
                        // If the list contains the search query
                        // than filter the adapter
                        // using the filter method
                        // with the query as its argument
                        adapter.getFilter().filter(query);
                        searchView.clearFocus();

                        return true;
                    }

                    // This method is overridden to filter
                    // the adapter according to a search query
                    // when the user is typing search
                    @Override
                    public boolean onQueryTextChange(String newText)
                    {
                        adapter.getFilter().filter(newText);
                        return false;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_done) {
            groupName = ((EditText) findViewById(R.id.groupName)).getText().toString();

            List<NewAccount.User> member = adapter.getMembers();
            List<String> restrictions = adapter.getRestrictions();

            GroupItem group = new GroupItem(groupName, member, restrictions, user);
            groupsDB.child(uuid).setValue(group);

            DatabaseReference userGroup = usersDB.child(user).child("groups").push();
            String userKey = userGroup.getKey();
            Map<String, Object> map = new HashMap<>();
            map.put(userKey, uuid);
            usersDB.child(user).child("groups").updateChildren(map);


            for (NewAccount.User groupMember : member) {
//                Map<String, Object> groupMap = new HashMap<>();
//                groupMap.put("0", uuid);

                Map<String, Object> notifications = new HashMap<>();
                //group as id, true for group notification
                notifications.put(uuid, true);
                usersDB.child(groupMember.getEmail()).child("groups").updateChildren(map);
                usersDB.child(groupMember.getEmail()).child("notifications").updateChildren(notifications);
            }

            finish();

            return true;
        } else {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            return super.onOptionsItemSelected(item);
        }
    }
}

