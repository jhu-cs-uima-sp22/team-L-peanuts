package com.example.peanuts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class Pop extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Debug", "IN POP");

        setContentView(R.layout.popup);

        Intent intent = getIntent();
        TextView title = findViewById(R.id.PopUpTitle);
        TextView popUpList = findViewById(R.id.pop_up_list);
        popUpList.setMovementMethod(new ScrollingMovementMethod());
        title.setText(intent.getStringExtra("foodName"));
        ArrayList<String> data = intent.getStringArrayListExtra("data");
        StringBuilder dataString = new StringBuilder();
        for (String s : data) {
            dataString.append(s).append("\n");
        }
        popUpList.setText(dataString.toString());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width * 0.5), (int)(height * 0.5));
    }
}
