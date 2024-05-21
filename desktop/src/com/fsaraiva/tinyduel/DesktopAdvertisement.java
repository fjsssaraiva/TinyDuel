package com.fsaraiva.tinyduel;

import com.badlogic.gdx.Gdx;
import com.fsaraiva.tinyduel.engine.utils.Advertisement;

public class DesktopAdvertisement implements Advertisement {

    public void submitScore(String user, int score) {
        // Ignore the user name, because Google Play reports the score for the currently signed-in player
        // See https://developers.google.com/games/services/android/signin for more information on this
        //PlayGames.getLeaderboardsClient(activity).submitScore(getString(R.string.leaderboard_id), score);
        Gdx.app.log("DesktopAd", "would have submitted score for user " + user + ": " + score);
    }
}
