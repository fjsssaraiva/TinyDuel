package com.fsaraiva.tinyduel.engine;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
//import android.util.Log;

/**
 * Created by Fernando on 24/03/2018.
 */

public class GameOptions {
    //Load game
    public void readOptions(Context ctx) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        Stage.gameOptions_soundOn = preferences.getBoolean("soundOn", false);
        Stage.gameOptions_kidModeOn = preferences.getBoolean("kidModeOn", true);
        Stage.gameOptions_devModeOn = preferences.getBoolean("devModeOn", false);
        Stage.gameOptions_blueHumanOn = preferences.getBoolean("blueHumanOn", true);
        Stage.gameOptions_redHumanOn = preferences.getBoolean("redHumanOn", true);
        Stage.gameOptions_blueUseButtons = preferences.getBoolean("blueUseButtons", true);
        Stage.gameOptions_redUseButtons = preferences.getBoolean("redUseButtons", true);
        Stage.gameOptions_blueUseSchema1 = preferences.getBoolean("blueUseSchema1", true);
        Stage.gameOptions_redUseSchema1 = preferences.getBoolean("redUseSchema1", true);
        Stage.gameOptions_language = preferences.getInt("language", 1);
        Stage.gameOptions_adCounter = preferences.getInt("adCounter", 0);
    }

    public void writeOptions(Context ctx) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("soundOn", Stage.gameOptions_soundOn);
        editor.putBoolean("kidModeOn", Stage.gameOptions_kidModeOn);
        editor.putBoolean("devModeOn", Stage.gameOptions_devModeOn);
        editor.putBoolean("blueHumanOn", Stage.gameOptions_blueHumanOn);
        editor.putBoolean("redHumanOn", Stage.gameOptions_redHumanOn);
        editor.putBoolean("blueUseButtons", Stage.gameOptions_blueUseButtons);
        editor.putBoolean("redUseButtons", Stage.gameOptions_redUseButtons);
        editor.putBoolean("blueUseSchema1", Stage.gameOptions_blueUseSchema1);
        editor.putBoolean("redUseSchema1", Stage.gameOptions_redUseSchema1);
        editor.putInt("language", Stage.gameOptions_language);
        editor.putInt("adCounter", Stage.gameOptions_adCounter);

        editor.apply();  //or commit?
    }
}
