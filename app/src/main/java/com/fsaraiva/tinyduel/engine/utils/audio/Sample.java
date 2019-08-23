package com.fsaraiva.tinyduel.engine.utils.audio;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
//import android.util.Log;

import com.fsaraiva.tinyduel.R;

//https://www.101apps.co.za/articles/using-android-s-soundpool-class-a-tutorial.html

// turn to singleton
public class Sample
{
    static SoundPool soundPool;
    static int[] sm;
    //static AudioManager amg;
    public static int musicID = -1;

    public static void InitSound(Context c) {

        int maxStreams = 10;


        Context mContext = c; //getApplicationContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(maxStreams)
                    .build();
        } else {
            soundPool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0);
        }


        musicID = -1;

        sm = new int[9];
        // fill your sounds
        sm[0] = soundPool.load(mContext, R.raw.whack, 1);
        sm[1] = soundPool.load(mContext, R.raw.gunner_aahhh, 1);
        sm[2] = soundPool.load(mContext, R.raw.racket_hit, 1);
        sm[3] = soundPool.load(mContext, R.raw.applause, 1);
        sm[4] = soundPool.load(mContext, R.raw.gunshot, 1);
        sm[5] = soundPool.load(mContext, R.raw.switch_1, 1);
        sm[6] = soundPool.load(mContext, R.raw.music, 1);
        sm[7] = soundPool.load(mContext, R.raw.cactus_tearing, 1);
        sm[8] = soundPool.load(mContext, R.raw.racket_hit_wall, 1);
        //amg = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
    }

    // todo check if soud is loaded (music not playing)

    //AssetFileDescriptor aMan.getAssets();
/*
    public void loadSound (String strSound, int stream) {

        boolean loaded = false;
        mSoundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
                loaded = true;
            }
        });
        try {
            stream= mSoundPool.load(aMan.openFd(strSound), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Is the sound loaded already?
        if (loaded) {
            mSoundPool.play(stream, streamVolume, streamVolume, 1, LOOP_1_TIME, 1f);
        }
    }*/

    public static void playSound(int sound) {
        soundPool.play(sm[sound], 1, 1, 1, 0, 1f);
    }

    public static int playSoundLoop(int sound) {
        //musicID = soundPool.play(sm[sound], 1, 1, 1, -1, 1f); //-1 for loop
        //Log.d("SOUND", "start bkg music - " + musicID);
        musicID = soundPool.play(sm[sound], 1, 1, 1, 0, 1f);
        return musicID;
    }

    public static void pauseSound(int sound) {
        soundPool.pause(sound);
    }

    public static void pauseMusic() {
        soundPool.pause(musicID);
    }

    public static void pauseAll() {
        soundPool.autoPause();
    }

    public static void resumeMusic() {
        soundPool.resume(musicID);
    }

    public static void resumeAll() {
        soundPool.autoResume();
    }

    public static final void cleanUpIfEnd() {
        sm = null;
        soundPool.release();
        soundPool = null;
    }
}
