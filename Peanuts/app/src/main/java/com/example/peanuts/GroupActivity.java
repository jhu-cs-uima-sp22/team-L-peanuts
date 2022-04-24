package com.example.peanuts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        setTitle(name);
        boolean isHost = intent.getBooleanExtra("isHost", false);
        ConstraintLayout placeholder = (ConstraintLayout) findViewById(R.id.PlaceHolder);
        HorizontalScrollView mealPlan = (HorizontalScrollView) findViewById(R.id.MealPlanSection);
        HorizontalScrollView restrictionsView = (HorizontalScrollView) findViewById(R.id.RestrictionsInGroups);
        ConstraintLayout response = (ConstraintLayout) findViewById(R.id.MealPlanResponse);
        Bundle args = intent.getBundleExtra("bundle");
        ArrayList<FoodItem> foods = (ArrayList<FoodItem>) args.getSerializable("foods");
        ArrayList<String> restrictions = (ArrayList<String>) args.getSerializable("restrictions");
        ArrayList<NewAccount.User> members = (ArrayList<NewAccount.User>) args.getSerializable("members");
        if (isHost) {
            placeholder.setVisibility(View.INVISIBLE);
            mealPlan.setVisibility(View.VISIBLE);
            restrictionsView.setVisibility(View.VISIBLE);
        } else {
            if (foods.isEmpty()) { //check if meal plan is empty
                //if meal plan is empty
                placeholder.setVisibility(View.VISIBLE);
                mealPlan.setVisibility(View.INVISIBLE);
                response.setVisibility(View.INVISIBLE);
                restrictionsView.setVisibility(View.INVISIBLE);
            } else {
                //if meal plan exists
                placeholder.setVisibility(View.INVISIBLE);
                mealPlan.setVisibility(View.VISIBLE);
                response.setVisibility(View.VISIBLE);
                ImageButton addMealPlanButton = (ImageButton) findViewById(R.id.AddToMealPlan);
                addMealPlanButton.setVisibility(View.INVISIBLE);
            }
        }
        Context ctx = getApplicationContext();
        GroupMemberAdapter memberAdapter = new GroupMemberAdapter(ctx, R.layout.group_users_layout, members);
        ListView membersList = (ListView) this.findViewById(R.id.MembersList);
        membersList.setAdapter(memberAdapter);
        registerForContextMenu(membersList);
        memberAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void toAddFoodInGroup(View v) {
        Intent intent = new Intent(this, GroupAddFood.class);
        startActivity(intent);
    }
}