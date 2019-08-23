package com.fsaraiva.tinyduel.game;

import android.content.Context;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.fsaraiva.tinyduel.engine.Stage;
import com.fsaraiva.tinyduel.engine.utils.audio.Sample;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.android.gms.ads.AdRequest;

public class MainAppActivity extends Activity {
    public Stage stage;
    public static FirebaseAnalytics mFirebaseAnalytics;
    public static InterstitialAd mInterstitialAd;

    private static final String TAG = "tinyduel-v0.8"; //tinyduel.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // turn title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set initial screen
        stage = new Stage(this);
        setContentView(stage);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Init ads
        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");

        mInterstitialAd = new InterstitialAd(this);
        //mInterstitialAd.setAdUnitId("ca-app-pub-6429528320074964/6172590444");
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        // Set an AdListener.
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Log.d("ADMOB: ", "closed add, starting new one");
                AdRequest adRequest = new AdRequest.Builder().addTestDevice("ca-app-pub-3940256099942544/1033173712").build();
                mInterstitialAd.loadAd(adRequest);
            }
        });
    }

    @Override
    protected void onStart() {
        //reload bitmaps in onStart()
        super.onStart();}

    @Override
    protected void onResume() {
        super.onResume();
        Sample.InitSound(this.getApplicationContext());
        stage = new Stage(this);
        setContentView(stage);
        if (stage.thread.isAlive()) {
            stage.thread.setRunning(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (stage.thread.isAlive()) {
            stage.thread.setRunning(false);
        }
        Sample.cleanUpIfEnd();
    }

    @Override
    protected void onStop() {
        //Clear and recycle bitmaps in onStop()
        super.onStop();}

    @Override
    protected void onDestroy() {
        super.onDestroy();}

    @Override
    public void onBackPressed() {
        if (stage.pressedBackButton()) {
            super.onBackPressed();
        }
    }

}
