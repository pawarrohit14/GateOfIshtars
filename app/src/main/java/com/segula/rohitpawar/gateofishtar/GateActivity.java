package com.segula.rohitpawar.gateofishtar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class GateActivity extends AppCompatActivity {

    private String champion_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate);
        String champion_name = getIntent().getStringExtra("CHAMPION");
        Log.i("ROHIT", champion_name);
    }
}
