package com.fsaraiva.tinyduel.game.scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.fsaraiva.tinyduel.R;
import com.fsaraiva.tinyduel.engine.Scene;
import com.fsaraiva.tinyduel.engine.Stage;
import com.fsaraiva.tinyduel.engine.map.Background;
import com.fsaraiva.tinyduel.engine.utils.audio.Sample;
import com.fsaraiva.tinyduel.engine.utils.gui.Button;
import com.fsaraiva.tinyduel.engine.utils.video.Sprite;
import com.fsaraiva.tinyduel.game.MainAppActivity;
import com.fsaraiva.tinyduel.game.characters.WhackMole;

import java.util.Random;

public class WhackamoleGameScene extends Scene {
    private Bitmap backgroundImg;
    public Background staticBackgroud;

    private Button buttonPause;
    private Button BACK_MAIN_MENU;
    public boolean playing = false;
    public boolean gameEnded = false;

    //private Bitmap mask;
    private Bitmap mole;
    private Bitmap moleRed;
    private Bitmap moleBlue;

    public WhackMole[] moles;
    private boolean running = false;

    public int scoreBlue = 0;
    public int scoreRed = 0;

    private int activeMole = -1;
    private boolean moleRising = true;
    private boolean moleSinking = false;
    private int moleRate = 5; //5;
    private int fingerX, fingerY;
    //private static SoundPool sounds;
    private static int whackSound;
    private static int missSound;
    private boolean moleJustHit = false;

    private Sprite redWhacker;
    private Sprite blueWhacker;

    private Sprite whack;
    private Sprite whackRed;
    private Sprite whackBlue;

    private boolean whacking = false;
    private boolean whackingRed = false;
    private boolean whackingBlue = false;
    private int molesWhacked = 0;
    private int molesMissed = 0;
    private int AIwackingX;
    private int AIwackingY;

    public boolean soundOn = true;

    public WhackamoleGameScene(View context) {
        super();
        isDirty = false;
    }

    public void initScene(Context context) {
        staticBackgroud = new Background(BitmapFactory.decodeResource(context.getResources(), R.drawable.fullmap_whackmole));
        //staticBackgroud = new Background(BitmapFactory.decodeResource(context.getResources(), R.drawable.fullmap_layer0));

       // mask = BitmapFactory.decodeResource(context.getResources(), R.drawable.mask);
        //mole = BitmapFactory.decodeResource(context.getResources(), R.drawable.mole);
        moleBlue = BitmapFactory.decodeResource(context.getResources(), R.drawable.mole_blue);
        moleRed = BitmapFactory.decodeResource(context.getResources(), R.drawable.mole_red);

        //whack = BitmapFactory.decodeResource(context.getResources(), R.drawable.whack1);
        whack = new Sprite(20, 120, 120 , context);
        //whackRed = BitmapFactory.decodeResource(context.getResources(), R.drawable.whacker_red);
        whackRed = new Sprite(21, 120, 120 , context);
        //whackBlue = BitmapFactory.decodeResource(context.getResources(), R.drawable.whacker_blue);
        whackBlue = new Sprite(22, 120, 120 , context);

        moles = new WhackMole[22];
        moles[0] = new WhackMole(1, 100,460,460,380, moleRed);
        moles[1] = new WhackMole(2, 220,460,460,380, moleBlue);
        moles[2] = new WhackMole(1, 355,460,460,380, moleRed);
        moles[3] = new WhackMole(2, 895,460,460,380, moleBlue);
        moles[4] = new WhackMole(1, 1015,460,460,380, moleRed);
        moles[5] = new WhackMole(2, 1150,460,460,380, moleBlue);
        moles[6] = new WhackMole(1, 550,460, 460,370, moleRed);
        moles[7] = new WhackMole(2, 695,460, 460,370, moleBlue);

        moles[8] = new WhackMole(2, 100,650,650,570, moleBlue);
        moles[9] = new WhackMole(1, 220,650,650,570, moleRed);
        moles[10] = new WhackMole(2, 355,650,650,570, moleBlue);
        moles[11] = new WhackMole(1, 895,650,650,570, moleRed);
        moles[12] = new WhackMole(2, 1015,650,650,570, moleBlue);
        moles[13] = new WhackMole(1, 1150,650,650,570, moleRed);
        moles[14] = new WhackMole(2, 550,650,650,570, moleBlue);
        moles[15] = new WhackMole(1, 695,650,650,570, moleRed);

        moles[16] = new WhackMole(2, 100,240,240,160, moleBlue);
        moles[17] = new WhackMole(1, 375,240,240,160, moleRed);
        moles[18] = new WhackMole(2, 845,240,240,160, moleBlue);
        moles[19] = new WhackMole(1, 1100,240,240,160, moleRed);
        moles[20] = new WhackMole(2, 550,260,260,180, moleBlue);
        moles[21] = new WhackMole(1, 695,260,260,180, moleRed);

        blueWhacker = new Sprite(11, 120, 120 , context);
        redWhacker = new Sprite(12, 120, 120 , context);

        buttonPause = new Button((Stage.viewport_current_size_X/2)-25, Stage.viewport_current_size_Y-75, 50, 50);
        buttonPause.face = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_player_obj);
        buttonPause.update();
        BACK_MAIN_MENU = new Button(80, Stage.viewport_current_size_Y-50, 300, 50);

        isDirty = true;
    }

    public void killScene() {
        staticBackgroud.image.recycle();
        buttonPause.face.recycle();
        for (int i=0; i<22; i++) {
            moles[0].image.recycle();
        }
        //mask.recycle();
        //mole.recycle();
        moleBlue.recycle();
        moleRed.recycle();
        whack.image.recycle();
        whackRed.image.recycle();
        whackBlue.image.recycle();
        blueWhacker.image.recycle();
        redWhacker.image.recycle();
        isDirty = false;
    }

    public void checkInput(MotionEvent event) {
        touchX = event.getX();
        touchY = event.getY();
        Rect touchArea = new Rect((int) ((touchX - 10) / Stage.zoomplusX), (int) ((touchY - 10) / Stage.zoomplusY), (int) ((touchX + 10) / Stage.zoomplusX), (int) ((touchY + 10) / Stage.zoomplusY));

        int eventaction = event.getAction();
        if (playing) {
            switch (eventaction) {
                case MotionEvent.ACTION_DOWN:
                        //fingerX = (int) touchX;
                        //fingerY = (int) touchY;
                        if (detectMoleContact(touchArea)) {
                            whacking = true;
                            //if (soundOn) {
                            //	AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
                            //	float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                            //    sounds.play(whackSound, volume, volume, 1, 0, 1);
                            //}
                            if (Stage.gameOptions_soundOn) {
                                Sample.playSound(0);
                            }

                            AIwackingX = moles[activeMole].x;
                            AIwackingY = moles[activeMole].y;
                            if (moles[activeMole].ID == 1) {
                                scoreBlue++;
                                whackingBlue = true;
                            } else {
                                scoreRed++;
                                whackingRed = true;
                            }
                            molesWhacked++; //still needed?
                            pickActiveMole();
                        }
                    //}
                    if (Rect.intersects(touchArea, buttonPause.box)) {
                        playing = false;
                        //Stage.currScene = Stage.pauseScene;
                        //Stage.currentScene = Stage.GameState.PAUSE_GAME;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    whacking = false;
                    whackingBlue = false;
                    whackingRed = false;
                    break;
            }
        } else {
            // game paused
            int maskedAction = event.getActionMasked();
            touchX = event.getX();
            touchY = event.getY();
            //touchArea = new Rect((int) touchX - 10, (int) touchY - 10, (int) touchX + 10, (int) touchY + 10);
            touchArea = new Rect((int) ((touchX - 10) / Stage.zoomplusX), (int) ((touchY - 10) / Stage.zoomplusY), (int) ((touchX + 10) / Stage.zoomplusX), (int) ((touchY + 10) / Stage.zoomplusY));
            switch (maskedAction) {
                case MotionEvent.ACTION_DOWN: {
                    if (/*gameEnded ||*/ Rect.intersects(touchArea, BACK_MAIN_MENU.box)) {
                        //Sample.resumeMusic();
                        if (Stage.gameOptions_adCounter > 2) {
                            if (MainAppActivity.mInterstitialAd.isLoaded()) {
                                MainAppActivity.mInterstitialAd.show();
                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }
                            Stage.gameOptions_adCounter = 0;
                            Stage.snapshotHandler.writeOptions(Stage.context1);
                        }

                        Stage.titleScene.initScene(Stage.context1);
                        Stage.currScene = Stage.titleScene;
                        //killScene();
                        //gameEnded = false;
                        playing = false;
                    } else {
                        playing = true;
                    }
                    break;
                }
            }
        }
    }

    public void update() {
        if (playing) {
            //Log.d("mole","playing, active: " + activeMole);
            if (activeMole < 0) {
                pickActiveMole();
            }

            int rndcolor = 0;
            if (!Stage.gameOptions_blueHumanOn) {
                whackingBlue = false;
                whacking = false;
            }
            if (!Stage.gameOptions_redHumanOn) {
                whackingRed = false;
                whacking = false;
            }
            if (!Stage.gameOptions_blueHumanOn && moles[activeMole].moleSinking && moles[activeMole].ID == 1) {
                rndcolor = new Random().nextInt(30);

                // todo: add dificulty here
                if (rndcolor < 2) {
                    moles[activeMole].y = moles[activeMole].maxPos;
                    moles[activeMole].moleJustHit = true;
                    whacking = true;
                    if (Stage.gameOptions_soundOn) {
                        Sample.playSound(0);
                    }
                    AIwackingX = moles[activeMole].x;
                    AIwackingY = moles[activeMole].y;
                    //if (soundOn) {
                    //	AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
                    //	float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    //    sounds.play(whackSound, volume, volume, 1, 0, 1);
                    //}
                    scoreBlue++;
                    whackingBlue = true;
                    molesWhacked++; //still needed?
                    pickActiveMole();
                }
            }
            if (!Stage.gameOptions_redHumanOn && moles[activeMole].moleSinking && moles[activeMole].ID == 2) {
                rndcolor = new Random().nextInt(30);
                if (rndcolor < 2) {
                    moles[activeMole].y = moles[activeMole].maxPos;
                    moles[activeMole].moleJustHit = true;
                    whacking = true;
                    if (Stage.gameOptions_soundOn) {
                        Sample.playSound(0);
                    }
                    AIwackingX = moles[activeMole].x;
                    AIwackingY = moles[activeMole].y;
                    scoreRed++;
                    whackingRed = true;
                    molesWhacked++; //still needed?
                    pickActiveMole();
                }
            }
            animateMoles();
        }
    }

    public boolean handleBackButton() {
        playing = false;
        return false;
    }

    private void animateMoles() {
        moles[activeMole].update(moleRate);
    }

    public void pickActiveMole() {
        //Log.d("mole","picking Active Mole");
        if (activeMole >= 0 && !moles[activeMole].moleJustHit) {
            //if (soundOn) {
            //	AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
            //	float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            //	sounds.play(missSound, volume, volume, 1, 0, 1);
            //}
            molesMissed++;
        }
        activeMole = new Random().nextInt(22);
        moles[activeMole].moleRising = true;
        moles[activeMole].moleSinking = false;
        moles[activeMole].moleJustHit = false;
        //moleRate = 5 + (int) (molesWhacked / 10);

        // todo: add gameOptions_kidModeOn
        if (Stage.gameOptions_kidModeOn) {
            moleRate = 2;
        } else {
            moleRate = 5 + (molesWhacked / 10);
        }
    }

    private boolean detectMoleContact(Rect touch) {
        boolean contact = false;

        /*if (fingerX >= moles[activeMole].x &&
                fingerX < moles[activeMole].x + (int) (88 * Stage.zoomplusX) &&
                fingerY > moles[activeMole].y &&
                fingerY < (int) (moles[activeMole].maxPos-25) * Stage.zoomplusY)
        */
        if (touch.intersect(moles[activeMole].getBounds()))
        {
            //Log.d("mole","contact");
            moles[activeMole].y = moles[activeMole].maxPos;
            contact = true;
            moles[activeMole].moleJustHit = true;
        }
        return contact;
    }

    public void reset() {
        molesWhacked = 0;
        molesMissed = 0;
        scoreRed = 0;
        scoreBlue = 0;
        activeMole = -1;
        gameEnded = false;
        whackingRed = false;
        whackingBlue = false;
        whacking = false;
    }

    public void drawScene(Canvas canvas) {
        try {
            //canvas.drawBitmap(backgroundImg, 0, 0, null);
            pt2.setColor(Color.BLACK);
            canvas.drawRect(0,0, Stage.DeviceWIDTH,Stage.DeviceHEIGHT, pt2);

            //staticBackgroud.draw(canvas, true, 0, 0,0,0);

            //could just draw active mole
            for (int i=0; i<22; i++) {
                moles[i].draw(canvas);
            }
            staticBackgroud.draw(canvas, true, 0, 0, 0, 0);

            bitmapText.drawString(canvas, scoreBlue + ":" + scoreRed, (int)(Stage.viewport_current_size_X/2.6), 10, 6);

            if (scoreBlue == 10 || scoreRed == 10) gameEnded = true;
            if (gameEnded) {
                playing = false;
                if (scoreBlue == 10) {
                    pt2.setColor(Color.parseColor("#800000FF"));
                    pt2.setStyle(Paint.Style.FILL);
                    GUImask = new Rect(0, 0, Stage.DeviceWIDTH, Stage.DeviceHEIGHT);
                    canvas.drawRect(GUImask, pt2);
                }
                if (scoreRed == 10) {
                    pt2.setColor(Color.parseColor("#80FF0000"));
                    pt2.setStyle(Paint.Style.FILL);
                    GUImask = new Rect(0, 0, Stage.DeviceWIDTH, Stage.DeviceHEIGHT);
                    canvas.drawRect(GUImask, pt2);
                }
            }

            if (whackingBlue) {
                /*if (!Stage.gameOptions_blueHumanOn) {
                    fingerX = AIwackingX;
                    fingerY = AIwackingY;
                }*/
                //canvas.drawBitmap(whack, (fingerX - (whack.getWidth() / 2))/Stage.zoomplusX, (fingerY - (whack.getHeight() / 2))/Stage.zoomplusY, null);
                //canvas.drawBitmap(whackBlue, (fingerX - (whack.getWidth() / 2))/Stage.zoomplusX, (fingerY - (whack.getHeight() / 2))/Stage.zoomplusY, null);
                whack.setX(AIwackingX);
                whack.setY(AIwackingY);
                whack.drawCentered(canvas);
                whackBlue.setX(AIwackingX);
                whackBlue.setY(AIwackingY);
                whackBlue.drawCentered(canvas);
            } else {
                blueWhacker.setX(20);
                blueWhacker.setY(Stage.viewport_current_size_Y - 120);
                blueWhacker.draw(canvas);
            }
            if (whackingRed) {
                /*if (!Stage.gameOptions_redHumanOn) {
                    fingerX = AIwackingX;
                    fingerY = AIwackingY;
                }*/
                whack.setX(AIwackingX);
                whack.setY(AIwackingY);
                whack.drawCentered(canvas);
                whackRed.setX(AIwackingX);
                whackRed.setY(AIwackingY);
                whackRed.drawCentered(canvas);
                //canvas.drawBitmap(whack, (fingerX - (whack.getWidth() / 2))/Stage.zoomplusX, (fingerY - (whack.getHeight() / 2))/Stage.zoomplusY, null);
                //canvas.drawBitmap(whackRed, (fingerX - (whack.getWidth() / 2))/Stage.zoomplusX, (fingerY - (whack.getHeight() / 2))/Stage.zoomplusY, null);
            } else {
                redWhacker.setX(Stage.viewport_current_size_X - 20 - 80);
                redWhacker.setY(Stage.viewport_current_size_Y - 120);
                redWhacker.draw(canvas);
            }

            if (playing) {
                buttonPause.draw(canvas);
            } else if (!gameEnded){
                bitmapText.drawString(canvas,"PRESS ANYWHERE TO RESUME", 80,Stage.viewport_current_size_Y/2, 3);
                bitmapText.drawString(canvas, "MAIN MENU", BACK_MAIN_MENU.x, BACK_MAIN_MENU.y, 2);
                //BACK_MAIN_MENU.draw(canvas);
            }

            if (gameEnded) {
                if (scoreBlue == 10) {
                    bitmapText.drawString(canvas, "BLUE WINS", 70, Stage.viewport_current_size_Y / 2 - 50, 8);
                    playing = false;
                }
                if (scoreRed == 10) {
                    bitmapText.drawString(canvas, "RED WINS ", 130, Stage.viewport_current_size_Y / 2 - 50, 8);
                    playing = false;
                }
                bitmapText.drawString(canvas, "MAIN MENU", BACK_MAIN_MENU.x, BACK_MAIN_MENU.y, 2);
                //BACK_MAIN_MENU.draw(canvas);
            }
        } catch (Exception e) {
            Log.d("ERRDRAW","" + e.toString());
        }
    }
}

