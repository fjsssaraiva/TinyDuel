package com.fsaraiva.tinyduel.game.scenes;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.fsaraiva.tinyduel.R;
import com.fsaraiva.tinyduel.engine.Scene;
import com.fsaraiva.tinyduel.engine.map.Background;
import com.fsaraiva.tinyduel.engine.Stage;
import com.fsaraiva.tinyduel.engine.utils.audio.Sample;
import com.fsaraiva.tinyduel.engine.utils.gui.Button;
import com.fsaraiva.tinyduel.engine.utils.video.Animation;
import com.fsaraiva.tinyduel.game.MainAppActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import com.fsaraiva.tinyduel.R;
import com.fsaraiva.tinyduel.engine.Scene;
import com.fsaraiva.tinyduel.engine.map.Background;
import com.fsaraiva.tinyduel.engine.Stage;
import com.fsaraiva.tinyduel.engine.utils.audio.Sample;
import com.fsaraiva.tinyduel.engine.utils.gui.Button;
import com.fsaraiva.tinyduel.engine.utils.video.Animation;
import com.fsaraiva.tinyduel.game.MainAppActivity;
import com.google.firebase.analytics.FirebaseAnalytics;

public class TitleScene extends Scene {

    private Button START_PONG_GAME;
    private Button START_WHACK_GAME;
    private Button START_FIGHT_GAME;
    private Button OPTIONS;
    private Button HELP;
    private Background menu_image;
    private Animation anim;

    public TitleScene(View context) {
        super();
        isDirty = false;
        //Sample.playSoundLoop(6);
    }

    public void killScene() {
        menu_image.image.recycle();
        anim.recycleAnimation();
        isDirty = false;
    }

    public void initScene(Context context) {
        menu_image = new Background(BitmapFactory.decodeResource(context.getResources(), R.drawable.menu_image3));

        START_PONG_GAME = new Button(20, 465, 220, 220);
        START_PONG_GAME.face = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu_tennis);
        START_PONG_GAME.update();

        START_WHACK_GAME = new Button(275, 465, 220, 220);
        START_WHACK_GAME.face = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu_whacker);
        START_WHACK_GAME.update();

        START_FIGHT_GAME = new Button(527, 465, 220, 220);
        START_FIGHT_GAME.face = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu_gunfight);
        START_FIGHT_GAME.update();

        OPTIONS = new Button(783, 465, 220, 220);
        OPTIONS.face = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu_options);
        OPTIONS.update();

        HELP = new Button(1035, 465, 220, 220);
        HELP.face = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu_help);
        HELP.update();

        anim = new Animation(BitmapFactory.decodeResource(context.getResources(), R.drawable.title_anim),300,300,12);
        anim.setDelay(35);

        if (Stage.gameOptions_soundOn) {
            if (!Sample.musicPlaying) {
                if (Sample.musicID == -1) {
                    Sample.playSoundLoop(6);
                } else {
                    Sample.resumeMusic();
                }
            }
        }
        isDirty = true;
    }

    public void checkInput(MotionEvent event) { //View context
        // get touch screen coordinates
        int maskedAction = event.getActionMasked();

        touchX = event.getX();
        touchY = event.getY();
        // rescale touch coords from screen size to viewport size
        Rect touchArea = new Rect((int) ((touchX-10)/Stage.zoomplusX), (int) ((touchY-10)/Stage.zoomplusY), (int) ((touchX+10)/Stage.zoomplusX), (int) ((touchY+10)/Stage.zoomplusY));

        // Check for Action_DOWN only, to prevent option cycling
        switch (maskedAction) {
            case MotionEvent.ACTION_DOWN:
                if (Rect.intersects(touchArea, START_PONG_GAME.box)) {
                    Sample.pauseMusic();
                    if (Stage.gameOptions_soundOn) {
                        Sample.playSound(5);
                    }
                    Stage.snapshotHandler.readOptions(Stage.context1);
                    Stage.fullmap = true;

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "GAME1");
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "TENNIS");
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "enter tennis");
                    MainAppActivity.mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                    Stage.gameOptions_adCounter++;
                    Stage.snapshotHandler.writeOptions(Stage.context1);

                    Stage.mainGameScene.initScene(Stage.context1);
                    Stage.mainGameScene.reset();
                    Stage.currentScene = Stage.GameState.MAIN_GAME;
                    Stage.currScene = Stage.mainGameScene;
                }

                if (Rect.intersects(touchArea, START_FIGHT_GAME.box)) {
                    Sample.pauseMusic();
                    if (Stage.gameOptions_soundOn) {
                        Sample.playSound(5);
                    }
                    Stage.snapshotHandler.readOptions(Stage.context1);
                    Stage.fullmap = true;

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "GAME3");
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "GUNFIGHT");
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "enter gunfight");
                    MainAppActivity.mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                    Stage.gameOptions_adCounter++;
                    Stage.snapshotHandler.writeOptions(Stage.context1);

                    Stage.fightGameScene.initScene(Stage.context1);
                    Stage.fightGameScene.reset();
                    Stage.currentScene = Stage.GameState.FIGHT_GAME;
                    Stage.currScene = Stage.fightGameScene;
                }
                if (Rect.intersects(touchArea, START_WHACK_GAME.box)) {
                    Sample.pauseMusic();
                    if (Stage.gameOptions_soundOn) {
                        Sample.playSound(5);
                    }
                    Stage.snapshotHandler.readOptions(Stage.context1);
                    Stage.fullmap = true;

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "GAME2");
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "WHACKER");
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "enter whacker");
                    MainAppActivity.mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                    Stage.gameOptions_adCounter++;
                    Stage.snapshotHandler.writeOptions(Stage.context1);

                    Stage.whackGameScene.initScene(Stage.context1);
                    Stage.whackGameScene.reset();
                    Stage.currentScene = Stage.GameState.WHACK_GAME;
                    Stage.currScene = Stage.whackGameScene;
                }
                if (Rect.intersects(touchArea, OPTIONS.box)) {
                    if (Stage.gameOptions_soundOn) {
                        Sample.playSound(5);
                    }
                    Stage.optionsScene.initScene(Stage.context1);
                    Stage.currScene = Stage.optionsScene;
                    Stage.currentScene = Stage.GameState.OPTIONS_MENU;

                }

                if (Rect.intersects(touchArea, HELP.box)) {
                    if (Stage.gameOptions_soundOn) {
                        Sample.playSound(5);
                    }
                    Stage.helpScene.initScene(Stage.context1);
                    Stage.currScene = Stage.helpScene;
                    Stage.currentScene = Stage.GameState.HELP_MENU;
                }
                break;
        }
    }

    public void update() {
        //clear other scenes (should not be here)
/*        if (Stage.mainGameScene.isDirty) {
            Stage.mainGameScene.killScene();
            //Log.d("KILL SCENE", "pong");
        }
        if (Stage.fightGameScene.isDirty) {
            Stage.fightGameScene.killScene();
            //Log.d("KILL SCENE", "fight");
        }
        if (Stage.whackGameScene.isDirty) {
            Stage.whackGameScene.killScene();
            //Log.d("KILL SCENE", "whack");
        }
        if (Stage.optionsScene.isDirty) {
            Stage.optionsScene.killScene();
            //Log.d("KILL SCENE", "whack");
        }
        if (Stage.helpScene.isDirty) {
            Stage.helpScene.killScene();
            //Log.d("KILL SCENE", "whack");
        }*/
        anim.update();
    }

    public void drawScene(Canvas canvas) {
        menu_image.draw(canvas, true, 0, 0, 0, 0);
        //bitmapText.drawString(canvas, "321 GO...",190, 40, 7);

        //canvas.drawBitmap(anim.getImage(), anim.src_full, anim.getDrawingBoundsXYZoom(980,305,1), null);

        START_WHACK_GAME.draw(canvas);

        String debug = "Musicloaded: " + Sample.loaded + " id:" + Sample.musicID + " play:" + Sample.musicPlaying;
        Log.d("music: ", debug);

        bitmapText.drawString(canvas, "WHACKER", START_WHACK_GAME.x+20, START_WHACK_GAME.y+20, 1);
        START_FIGHT_GAME.draw(canvas);
        bitmapText.drawString(canvas, "GUNFIGHT", START_FIGHT_GAME.x+20, START_FIGHT_GAME.y+20, 1);
        START_PONG_GAME.draw(canvas);
        bitmapText.drawString(canvas, "TENNIS", START_PONG_GAME.x+20, START_PONG_GAME.y+20, 1);
        OPTIONS.draw(canvas);
        bitmapText.drawString(canvas, "OPTIONS", OPTIONS.x+20, OPTIONS.y+20, 1);
        HELP.draw(canvas);
        bitmapText.drawString(canvas, "HELP", HELP.x+20, HELP.y+20, 1);

        pt2.setStyle(Paint.Style.STROKE); // .FILL_AND_STROKE); //.STROKE);
        //pt2.setStrokeJoin(Paint.Join.ROUND);
        //pt2.setAlpha(10);

    }
}

