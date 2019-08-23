package com.fsaraiva.tinyduel.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Debug;
//import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.fsaraiva.tinyduel.game.scenes.GunfightGameScene;
import com.fsaraiva.tinyduel.game.scenes.HelpScene;
import com.fsaraiva.tinyduel.game.scenes.OptionsScene;
import com.fsaraiva.tinyduel.game.scenes.PongGameScene;
import com.fsaraiva.tinyduel.game.scenes.TitleScene;
import com.fsaraiva.tinyduel.engine.utils.audio.Sample;
import com.fsaraiva.tinyduel.game.scenes.WhackamoleGameScene;

public class Stage extends SurfaceView implements SurfaceHolder.Callback
{
    //private static final String TAG = GamePanel.class.getName();
    private String debug = "";

    // State machine
    // todo: replace with current_scene
    public enum GameState { //create enum class?
        PAUSE_GAME,
        OPTIONS_MENU,
        HELP_MENU,
        TITLE_MENU,
        MAIN_GAME,
        FIGHT_GAME,
        WHACK_GAME
    }

    public static Context context1;

/*    private Context myContext;
    private SurfaceHolder mySurfaceHolder;
    private Bitmap backgroundImg;
    public int backgroundOrigW;
    public int backgroundOrigH;
    public float scaleW;
    public float scaleH;
    public float drawScaleW;
    public float drawScaleH;
*/
    public static int touchThreshold;

    public static OptionsScene optionsScene;
    public static HelpScene helpScene;
    public static TitleScene titleScene;
    public static PongGameScene mainGameScene;
    public static GunfightGameScene fightGameScene;
    public static WhackamoleGameScene whackGameScene;

    // shared preferences
    public static boolean gameOptions_soundOn;
    public static boolean gameOptions_kidModeOn;
    public static boolean gameOptions_devModeOn;
    public static boolean gameOptions_blueHumanOn;
    public static boolean gameOptions_redHumanOn;
    public static boolean gameOptions_blueUseButtons;
    public static boolean gameOptions_redUseButtons;
    public static boolean gameOptions_blueUseSchema1;
    public static boolean gameOptions_redUseSchema1;
    public static int gameOptions_language;
    public static int gameOptions_adCounter;

    public boolean resumeGame;

    //public Sample soundManager;

    public static Scene currScene;
    public static GameState currentScene = GameState.TITLE_MENU; //TITLE_MENU, MAIN_GAME, PAUSE_GAME, OPTIONS_MENU, CREDITS

    public static GameOptions snapshotHandler;

    private static final String TAG = Stage.class.getSimpleName();
    // device settings
    public static int DeviceWIDTH;
    public static int DeviceHEIGHT;

    float scaleFactorX=0;
    float scaleFactorY=0;
    // changeable by zoom applied
    public static int viewport_current_size_X=1280;
    public static int viewport_current_size_Y=720;

    public double FPS;
    public MainThread thread;

    public static boolean fullmap;        // fullmap flag
    public int direction_scroll; //0 stop, 1 nw, 2 ne, 3 sw, 4 se : ENUM DIRECTION
    //public static float zoom;
    //public static float zoomplus; //scale between viewport and actual screen resolution (1780 / 320)x
    public static float zoomplusX;
    public static float zoomplusY;
    //private Background fullGameMap; // original full map

    public Stage(Context context)
    {
        super(context);

        context1 = this.getContext();

        //add callback to the surface holder to intercept events
        getHolder().addCallback(this);
        // run thread
        thread = new MainThread(getHolder(), this);
        // make gamePanel focusable so it can handle events
        setFocusable(true);

        // device info
        DeviceHEIGHT = getResources().getDisplayMetrics().heightPixels;
        DeviceWIDTH = getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //Log.d("STAGE","surface changed");
        DeviceWIDTH = width;
        DeviceHEIGHT = height;
        //backgroundImg = Bitmap.createScaledBitmap(backgroundImg, width, height, true);
        zoomplusX = (float) DeviceWIDTH / viewport_current_size_X;
        zoomplusY = (float) DeviceHEIGHT / viewport_current_size_Y;

        touchThreshold = 50; //*zoomplusX
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        int counter = 0;

        //save game state
        //writeToFile();
        // Save preferences (move to optionsPanel)
        resumeGame = true;

        snapshotHandler.writeOptions(context1);

        // bitmap clean up
        // currScene.releaseResources();
        // release sounds
        //Sample.cleanUpIfEnd();
        while(retry && counter<10000)
        {
            // use counter?
            counter++;
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
                //Log.d(TAG, "Thread destroyed");
            }catch(InterruptedException e) {e.printStackTrace();}
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        //String strTime = "";
        long startTime = System.nanoTime();
        //Sample.InitSound(getContext());
        //scenes
        optionsScene = new OptionsScene(this);
        helpScene = new HelpScene(this);
        mainGameScene = new PongGameScene(this);
        fightGameScene = new GunfightGameScene(this);
        whackGameScene = new WhackamoleGameScene(this);
        titleScene = new TitleScene(this);

        // todo: add bitmap pool

        // initial game state
        //readfromFile();
        // Load preferences
        snapshotHandler = new GameOptions();
        snapshotHandler.readOptions(context1);

        titleScene.initScene(context1);
        currentScene = GameState.TITLE_MENU;
        currScene = titleScene;
        //Sample.playSoundLoop(6);
        //Sample.playSound(6);
        //zoomplusX = (float)DeviceWIDTH / (float)viewport_current_size_X;
        //zoomplusY = (float)DeviceHEIGHT / (float)viewport_current_size_Y;

        // these affect overall screen size
        scaleFactorX = (float)getWidth()/(DeviceWIDTH*1.f);
        scaleFactorY = (float)getHeight()/(DeviceHEIGHT*1.f);

        // start main cycle thread
        thread.setRunning(true);
        thread.start();
    }

    @Override
    // todo: add View.performClick, event should be handled in currentScene.handleInput(event)
    public boolean onTouchEvent(MotionEvent event) {
        currScene.checkInput(event);
        return true;
        // todo: send event to currentScene.handleEvent(event)
    }

    public boolean pressedBackButton() {
        if (currScene == titleScene) {
            return true;
        } else {
            currScene.handleBackButton();
            return false; //set to false to prevent exit from app
        }
    }

// todo: add timeDelta arg to update()
    public void update() {
        currScene.update();

        //clear other scenes (should not be here)
        if (mainGameScene.isDirty && currScene != mainGameScene) {
            mainGameScene.killScene();
            //Log.d("KILL SCENE", "pong");
        }
        if (fightGameScene.isDirty && currScene != fightGameScene) {
            fightGameScene.killScene();
            //Log.d("KILL SCENE", "fight");
        }
        if (whackGameScene.isDirty && currScene != whackGameScene) {
            whackGameScene.killScene();
            //Log.d("KILL SCENE", "whack");
        }
        if (optionsScene.isDirty && currScene != optionsScene) {
            optionsScene.killScene();
            //Log.d("KILL SCENE", "options");
        }
        if (helpScene.isDirty && currScene != helpScene) {
            helpScene.killScene();
            //Log.d("KILL SCENE", "help");
        }
        if (titleScene.isDirty && currScene != titleScene) {
            titleScene.killScene();
            //Log.d("KILL SCENE", "title");
        }
    }

    @Override
    // todo: drawing should be done in currentScene.draw(canvas)
    public void draw(Canvas canvas)
    {
        super.draw(canvas);

        if(canvas!= null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);  //really necessary?

            //clear the screen
            canvas.drawColor(Color.WHITE);

            currScene.drawScene(canvas);

            if (Stage.gameOptions_devModeOn) {
        /*        float density = getResources().getDisplayMetrics().density;
                Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
                Debug.getMemoryInfo(memoryInfo);
                final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
                final int freeMemory = (int) (Runtime.getRuntime().freeMemory() / 1024);
                String memMessage =
                        "Free= "+freeMemory+" kB / MaxMem ="+maxMemory+" kB (Memory: Pss= "+(int)(memoryInfo.getTotalPss() / 1024.0) +
                                " Mb, Private= "+(int)(memoryInfo.getTotalPrivateDirty() / 1024.0)+" Mb, Shared= "+(int)(memoryInfo.getTotalSharedDirty() / 1024.0)+" Mb)";
          */
                Paint p = new Paint();
                p.setTextSize(18);
                p.setTextAlign(Paint.Align.LEFT);
                p.setColor(Color.WHITE);
                p.setTypeface(Typeface.create("Arial", Typeface.BOLD));
                p.setAntiAlias(true);
                p.setSubpixelText(true);
                p.setShadowLayer(3, 0, 0, Color.BLACK);

                canvas.drawText("FPS:" + (int) FPS, 10, 20, p);
                canvas.drawText("Ad:" + gameOptions_adCounter, 10, 40, p);
            }

            // to turn back or it will keep on scaling
            canvas.restoreToCount(savedState);
        }
    }

    public void setAvgFps(double avgFps) {
        FPS = avgFps;
    }
}



/*
https://codereview.stackexchange.com/questions/27197/pong-game-in-java/27211
https://github.com/OEP/android-pong/blob/master/src/org/oep/pong/InputHandler.java
http://androidgameprogramming.com/programming-a-pong-game/
*/
