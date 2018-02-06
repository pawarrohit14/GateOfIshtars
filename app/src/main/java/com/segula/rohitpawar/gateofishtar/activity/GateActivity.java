package com.segula.rohitpawar.gateofishtar.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.segula.rohitpawar.gateofishtar.model.ChampionEntry;
import com.segula.rohitpawar.gateofishtar.model.ChampionMaster;
import com.segula.rohitpawar.gateofishtar.R;
import com.segula.rohitpawar.gateofishtar.utils.Utils;
import com.segula.rohitpawar.gateofishtar.storage.BaseStore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GateActivity extends AppCompatActivity {

    BaseStore db = new BaseStore(this);
    private String  currentDateTime;
    private List<ChampionEntry> championEntryList = new ArrayList<>();
    private ChampionMaster championMaster;
    private ArrayList<String> intervals = new ArrayList<>();
    private Date dbdate, curdate;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate);
        layout =( LinearLayout) findViewById(R.id.gate_layout);

        //Calculate Current timestamp
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            currentDateTime = dateFormat.format(new Date()); // Find todays date

        } catch (Exception e) {
            e.printStackTrace();

        }

       //Get selected champion from previous screen
        String champion_name = getIntent().getStringExtra("CHAMPION");


        //Get all last gate entries from database for selected champion
        championEntryList = db.getAllEntryByChampion(champion_name);

        //Add first entry for selected champion
        if (championEntryList.size() <= 0) {
            db.addCampionEntry(new ChampionEntry(champion_name, currentDateTime));
        }


        //Get all champion to calculate total damage received
        championEntryList = db.getAllEntryByChampion(champion_name);
        for (int i = 0; i < championEntryList.size(); i++) {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                dbdate = format.parse(championEntryList.get(championEntryList.size() - 1).get_timeStamp());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                curdate = format.parse(currentDateTime);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }

            long interval = (curdate.getTime() - dbdate.getTime()) / 1000;
            if (interval >= 3600) {
                db.addCampionEntry(new ChampionEntry(champion_name, currentDateTime));


            }

            championEntryList = db.getAllEntryByChampion(champion_name);
            intervals.add(championEntryList.get(i).get_timeStamp());

        }

        Utils utils = new Utils();
        int damage = utils.calculate_champion_health(champion_name, intervals, GateActivity.this);
        Log.i("ROHIT", String.valueOf(damage));


        //After calculating damage received , check weather Champion is alive or dead
        championMaster = db.getChampionByChampionName(champion_name);
        if (championMaster != null) {
            int championhealth = championMaster.getHealth();

            if (damage >= championhealth) {

                Toast.makeText(GateActivity.this, "Game Over, Champion is dead ", Toast.LENGTH_LONG).show();

                //Set background
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    layout.setBackgroundDrawable( getResources().getDrawable(R.drawable.loss) );
                } else {
                    layout.setBackground( getResources().getDrawable(R.drawable.loss));
                }

            } else {
                Toast.makeText(GateActivity.this, "Champion sucessfully passed the game with " +
                        String.valueOf(championhealth - damage) + " health", Toast.LENGTH_LONG).show();

                //Set background
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    layout.setBackgroundDrawable( getResources().getDrawable(R.drawable.win) );
                } else {
                    layout.setBackground( getResources().getDrawable(R.drawable.win));
                }
            }
        }


    }
}
