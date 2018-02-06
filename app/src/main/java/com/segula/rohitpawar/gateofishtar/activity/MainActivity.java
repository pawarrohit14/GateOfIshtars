package com.segula.rohitpawar.gateofishtar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.segula.rohitpawar.gateofishtar.model.ChampionMaster;
import com.segula.rohitpawar.gateofishtar.R;
import com.segula.rohitpawar.gateofishtar.storage.BaseStore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Spinner sp_champion_names;
    private Button btn_enter;
    private String selected_champion = "";
    BaseStore db = new BaseStore(this);
    private ArrayList<ChampionMaster> championMasterArrayList = new ArrayList<>();
    private ArrayList<String> champion_names = new ArrayList<>();

    private static final String HUMAN = "Human";
    private static final String WIZARD = "Wizard";
    private static final String SPIRIT = "Spirit";
    private static final String GIANT = "Giant";
    private static final String VAMPIRE = "Vampire";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp_champion_names = (Spinner) findViewById(R.id.sp_champion);
        btn_enter = (Button) findViewById(R.id.btn_enter);

        //add champion list on spinner
        loadChampionData();

        //Button CLick Listner
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), GateActivity.class);
                i.putExtra("CHAMPION", selected_champion);
                startActivity(i);
            }
        });

        //Spinner selection
        sp_champion_names.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selected_champion = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    //Add Champions to dropdown spinner
    private void loadChampionData() {

        if (db.getAllChampions().size() <= 0) {
            db.addCampionMaster(new ChampionMaster(HUMAN, 100));
            db.addCampionMaster(new ChampionMaster(WIZARD, 100));
            db.addCampionMaster(new ChampionMaster(SPIRIT, 100));
            db.addCampionMaster(new ChampionMaster(GIANT, 150));
            db.addCampionMaster(new ChampionMaster(VAMPIRE, 110));

        }

        championMasterArrayList = db.getAllChampions();
        for (int i = 0; i < championMasterArrayList.size(); i++) {
            champion_names.add(championMasterArrayList.get(i).get_name());
        }

        ArrayAdapter dateAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, champion_names);
        sp_champion_names.setAdapter(dateAdapter);

    }
}
