package com.segula.rohitpawar.gateofishtar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Rohit Pawar on 02-02-2018.
 */

public class Utils {


    /*  calculate amount of health remained for a champion at the end of day.
        @param champion - type of the champion (e.g. 'Wizard', 'human')
        @param date_string_intervals - list of date intervals strings when a champion passing the gate (e.g. ['XXXX-XX-XX XX:XX'])*/

    public int calculate_champion_health(String champion, ArrayList<String> intervals) {
        int total_damage = 0;
        Date date = null, datenext = null;


        for (int i = 0; i < intervals.size(); i++) {
            String str_date = intervals.get(i);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                date = format.parse(str_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                datenext = format.parse(intervals.get(i + 1));
            } catch (Exception e) {
                datenext = date;
            }

            int next_damage = calculate_damage_taken(date, champion);
            long interval = (datenext.getTime() - date.getTime()) / 1000;

            if (interval >= 3600 || i == (intervals).size() - 1) {
                total_damage += next_damage;
            }
        }
        return total_damage;
    }

    private int calculate_damage_taken(Date date, String champion) {

        if (holly_day(date) || invincible_champion(champion)) {
            return 0;
            //"Janna" demon of Wind spawned
        } else if (date.getHours() == 6 && date.getMinutes() >= 0 && date.getMinutes() <= 29) {
            return 7;
        }
        //"Tiamat" goddess of Oceans spawned
        else if (date.getHours() == 6 && date.getMinutes() >= 30 && date.getMinutes() <= 59) {
            return 18;
        }
        //"Mithra" goddess of sun spawned
        else if (date.getHours() == 7 && date.getMinutes() >= 0 && date.getMinutes() <= 59) {
            return 25;
        }
        //"Warwick" God of war spawned
        else if (date.getHours() == 8 && date.getMinutes() >= 0 && date.getMinutes() <= 29) {
            return 18;
        }
        //"Kalista" demon of agony spawned
        else if (date.getHours() == 8 && date.getHours() <= 14 && date.getMinutes() >= 30 && date.getMinutes() <= 59) {
            return 7;
        }
        //"Ahri" goddess of wisdom spawned
        else if (date.getHours() == 15 && date.getMinutes() >= 0 && date.getMinutes() <= 29) {
            return 13;
        }
        //"Brand" god of fire spawned
        else if (date.getHours() == 15 && date.getMinutes() >= 0 || date.getHours() == 16 && date.getMinutes() <= 59) {
            return 25;
        }
        //"Rumble" god of lightning spawned
        else if (date.getHours() == 17 && date.getMinutes() >= 0 && date.getMinutes() <= 59) {
            return 18;
        }

        //"Skarner" the scorpion demon spawned
        else if (date.getHours() >= 18 && date.getHours() <= 19 && date.getMinutes() >= 0 && date.getMinutes() <= 59) {
            return 7;
        }

        //"Luna" The goddess of the moon spawned
        else if (date.getHours() == 20 && date.getMinutes() <= 59) {
            return 13;
        } else {
            return 0;
        }

    }


    private boolean holly_day(Date date) {
        return false;
    }

    private boolean invincible_champion(String champion) {

        if (champion.equals("Wizard")) {
            return true;
        } else if (champion.equals("Spirit")) {
            return true;
        } else if (champion.equals("human") || champion.equals("giant") || champion.equals("vampire")) {
            return false;
        } else {
            return false;
        }
    }
}



