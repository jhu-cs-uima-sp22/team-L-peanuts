package com.example.peanuts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;

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
        HorizontalScrollView restrictions = (HorizontalScrollView) findViewById(R.id.RestrictionsInGroups);
        ConstraintLayout response = (ConstraintLayout) findViewById(R.id.MealPlanResponse);
        if (isHost) {
            placeholder.setVisibility(View.INVISIBLE);
            mealPlan.setVisibility(View.VISIBLE);
            restrictions.setVisibility(View.VISIBLE);
        } else {
            if (true) { //check if meal plan is empty
                //if meal plan is empty
                placeholder.setVisibility(View.VISIBLE);
                mealPlan.setVisibility(View.INVISIBLE);
                response.setVisibility(View.INVISIBLE);
                restrictions.setVisibility(View.INVISIBLE);
            } else {
                //if meal plan exists
                placeholder.setVisibility(View.INVISIBLE);
                mealPlan.setVisibility(View.VISIBLE);
                response.setVisibility(View.VISIBLE);
                ImageButton addMealPlanButton = (ImageButton) findViewById(R.id.AddToMealPlan);
                addMealPlanButton.setVisibility(View.INVISIBLE);
            }
        }
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