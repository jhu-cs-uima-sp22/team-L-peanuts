package com.example.peanuts;import android.content.Context;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.CheckBox;import android.widget.CompoundButton;import android.widget.Filter;import android.widget.Filterable;import android.widget.LinearLayout;import android.widget.TextView;import androidx.annotation.NonNull;import androidx.annotation.Nullable;import androidx.recyclerview.widget.RecyclerView;import com.google.firebase.database.DataSnapshot;import com.google.firebase.database.DatabaseError;import com.google.firebase.database.DatabaseReference;import com.google.firebase.database.FirebaseDatabase;import com.google.firebase.database.ValueEventListener;import java.util.ArrayList;import java.util.List;import java.util.Locale;public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> implements Filterable {    private List<NewAccount.User> fullUsers = new ArrayList<>();    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://peanuts-e9a7c-default-rtdb.firebaseio.com/");    private DatabaseReference myRef = database.getReference("groups");    private String groupId;    private List<NewAccount.User> users;    private CheckBox checkBox;    private Context context;    private Listener listener = new Listener();    UsersAdapter(Context ctx, List<NewAccount.User> users, String uuid) {        context = ctx;        this.groupId = uuid;        this.users = users;        fullUsers.addAll(users);        myRef.child(groupId).addValueEventListener(listener);    }    private final View.OnClickListener onClickListener = (view) -> {        NewAccount.User it = (NewAccount.User) view.getTag();        Log.d("USER", String.valueOf(it));        checkBox = view.findViewById(R.id.user_checkbox);        boolean isChecked = checkBox.isChecked();//        if (!isChecked) {            listener.add(it);            checkBox.setChecked(true);    };    public List<NewAccount.User> getMembers() {        Log.d("MEMBERS", String.valueOf(listener.retrieveMembers().size()));        return listener.retrieveMembers();    }    public List<String> getRestrictions() {        Log.d("RESTRICTIONS", String.valueOf(listener.retrieveRestrictions().size()));        return listener.retrieveRestrictions();    }    public class Listener implements ValueEventListener{        private List<String> groupRestrictions;        private List<NewAccount.User> members;        @Override        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {            if (dataSnapshot.getValue() != null) {                Log.d("retrieve_success", dataSnapshot.toString());                members = (ArrayList<NewAccount.User>) dataSnapshot.child("members").getValue();                groupRestrictions = (ArrayList<String>) dataSnapshot.child("restrictions").getValue();            } else {                members = new ArrayList<>();                groupRestrictions = new ArrayList<>();            }        }        @Override        public void onCancelled(@NonNull DatabaseError databaseError) {            Log.d("retrieve_fail", databaseError.toString());            members = new ArrayList<>();            groupRestrictions = new ArrayList<>();        }        public List<NewAccount.User> retrieveMembers() {            return members;        }        public List<String> retrieveRestrictions() {            return groupRestrictions;        }        public void add(NewAccount.User user) {            members.add(user);            Log.d("members", String.valueOf(members));            if (user.restrictions != null) {                for (String item : user.getRestrictions()) {                    if (!groupRestrictions.contains(item)) {                        groupRestrictions.add(item);                    }                }            }        }        public void remove(NewAccount.User user) {                //do something                }    }    @Override    public Filter getFilter() {        Log.d("debug", "IN FILTER");        return new Filter() {            @Override            protected FilterResults performFiltering(CharSequence constraint) {                List<NewAccount.User> filteredList = new ArrayList<>();                if (constraint == null || constraint.length() == 0) {                    filteredList.addAll(fullUsers);                } else {                    String filterPattern = constraint.toString().toLowerCase().trim();                    for (NewAccount.User item : fullUsers) {                        if (item.getEmail().toLowerCase().contains(filterPattern)                                || item.getName().toLowerCase().contains(filterPattern)) {                            filteredList.add(item);                        }                    }                }                FilterResults results = new FilterResults();                results.values = filteredList;                Log.d("results", String.valueOf(results));                return results;            }            @Override            protected void publishResults(final CharSequence constraint,                                          FilterResults results) {                Log.d("UsersList", String.valueOf(users));                Log.d("FullList", String.valueOf(fullUsers));                users.clear();                users.addAll((ArrayList) results.values);                notifyDataSetChanged();            }            @Override            public boolean equals(@Nullable Object obj) {                return super.equals(obj);            }        };    }    @Override    public UsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {        View view = LayoutInflater.from(parent.getContext())                .inflate(R.layout.item_user, parent, false);        return new UsersAdapter.ViewHolder(view);    }    @Override    public void onBindViewHolder(@NonNull UsersAdapter.ViewHolder holder, int position) {        holder.userName.setText(users.get(position).getName());        holder.userEmail.setText(users.get(position).getEmail());        holder.itemView.setTag(users.get(position));        holder.itemView.setOnClickListener(onClickListener);    }    @Override    public int getItemCount() {        Log.d("users_retrieve", String.valueOf(users.size()));        return users.size();    }    public class ViewHolder extends RecyclerView.ViewHolder {        final TextView userName;        final TextView userEmail;        ViewHolder(View view) {            super(view);            userName = (TextView) view.findViewById(R.id.user_name);            userEmail = (TextView) view.findViewById(R.id.user_email);        }    }}