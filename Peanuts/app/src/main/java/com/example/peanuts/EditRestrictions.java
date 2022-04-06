package com.example.peanuts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class EditRestrictions extends AppCompatActivity {

    protected ArrayList<Item> restrictions;
    protected ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restrictions);

        // create temp ArrayList of items
        restrictions = new ArrayList<Item>();
        restrictions.add(new Item("peanuts", true));
        restrictions.add(new Item("dairy", false));
        restrictions.add(new Item("seafood", true));
    }
}