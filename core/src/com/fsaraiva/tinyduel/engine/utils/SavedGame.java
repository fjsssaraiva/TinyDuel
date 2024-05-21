package com.fsaraiva.tinyduel.engine.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.fsaraiva.tinyduel.TinyDuel;

public class SavedGame {
    public Preferences prefs;

    public SavedGame(String savedGameName) {
        prefs = Gdx.app.getPreferences(savedGameName);
        //addValues();
    }

    public String getStringValue(String property) {
        return prefs.getString(property, "No name stored");
    }

    public void getValues(TinyDuel game) {
        int lifes = prefs.getInteger("lifes", 5);
        int ammo = prefs.getInteger("ammo", 10);
    }

    public void addValues() {
        //prefs.putString("name", "Donald Duck");
        //prefs.putBoolean("soundOn", true);
        prefs.putInteger("lifes", 10);
        prefs.putInteger("ammo", 5);

        // bulk update your preferences
        prefs.flush();
    }
}
