package com.fsaraiva.tinyduel.engine.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.fsaraiva.tinyduel.TinyDuel;

public class Settings {

    public Preferences prefs;

    public Settings(String settingsName) {
        prefs = Gdx.app.getPreferences(settingsName);
        //addValues();
    }

    public String getStringValue(String property) {
        return prefs.getString(property, "No name stored");
    }

    public void getValues(TinyDuel game) {
        game.input.useOnScreenCtrl1 = prefs.getBoolean("useOnScreenCtrl1", false);
        game.input.useOnScreenCtrl2 = prefs.getBoolean("useOnScreenCtrl2", false);
        game.DebugMode = prefs.getBoolean("DebugMode", false);
        game.DebugMode2 = prefs.getBoolean("DebugMode2", false);
        game.musicOn = prefs.getBoolean("musicOn", true);
        game.soundOn = prefs.getBoolean("soundOn", true);
        game.musicLvl = prefs.getInteger("musicLvl", 5);
        game.soundLvl = prefs.getInteger("soundLvl", 5);
    }

    public void addValues() {
        prefs.putString("name", "Donald Duck");
        prefs.putBoolean("soundOn", true);
        prefs.putInteger("highscore", 10);

        // bulk update your preferences
        prefs.flush();
    }
}
