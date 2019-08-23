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
import com.fsaraiva.tinyduel.engine.GuiObject;
import com.fsaraiva.tinyduel.engine.Scene;
import com.fsaraiva.tinyduel.engine.Stage;
import com.fsaraiva.tinyduel.engine.map.Background;
import com.fsaraiva.tinyduel.engine.utils.audio.Sample;
import com.fsaraiva.tinyduel.engine.utils.gui.Button;
import com.fsaraiva.tinyduel.engine.utils.video.Animation;
import com.fsaraiva.tinyduel.engine.utils.video.Sprite;
import com.fsaraiva.tinyduel.game.MainAppActivity;
import com.fsaraiva.tinyduel.game.characters.GunBullet;
import com.fsaraiva.tinyduel.game.characters.GunCactus;
import com.fsaraiva.tinyduel.game.characters.GunSlinger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GunfightGameScene extends Scene {
    private Background staticBackgroud; //not in use for now

    private Button playerObjBtn1Up;
    private Button playerObjBtn1Down;
    private Button playerObjBtn1Left;
    private Button playerObjBtn1Right;
    private Button playerObjBtn1Fire;

    private Button playerObjBtn1UpLeft;
    private Button playerObjBtn1DownLeft;
    private Button playerObjBtn1UpRight;
    private Button playerObjBtn1DownRight;

    private Button playerObjBtn2Up;
    private Button playerObjBtn2Down;
    private Button playerObjBtn2Left;
    private Button playerObjBtn2Right;
    private Button playerObjBtn2Fire;

    private Button playerObjBtn2UpLeft;
    private Button playerObjBtn2DownLeft;
    private Button playerObjBtn2UpRight;
    private Button playerObjBtn2DownRight;

    private int btn1firePointerId;
    private int btn2firePointerId;

    private Button playerObjBtn1Area;
    private Button playerObjBtn2Area;

    private Button buttonPause;

    public GunCactus cactus1;
    public GunCactus cactus2;
    public GunCactus cactus3;
    public GunCactus cactus4;

    private Sprite gui_heart;
    private Sprite gui_bullet;
    private Sprite controller;
    private Sprite controller2;
    private Sprite fire_button1;
    private Sprite fire_button2;
    private Sprite carroca;
    private Sprite shadow1;
    private Sprite shadow2;
    private Animation particles_gun_hit;
    private Animation particles_gun_hit2;
    public Animation particles_gun_shot;
    public Animation particles_gun_shot2;
    private Animation particles_cactus_hit;
    private Animation particles_cactus_hit2;
    private Animation particles_cactus_hit3;
    private Animation particles_cactus_hit4;

    private Button BACK_MAIN_MENU;
    public GunBullet bullet;
    public GunSlinger gunner1;
    public GunSlinger gunner2;
    public Bitmap bullet_image;


    private int carrocaPosY;
    //public GunBullet[] bullets;  //ArrayList arrayList = new ArrayList();
    public ArrayList<GunBullet> bullets = new ArrayList<GunBullet>();

    private ArrayList<GuiObject> sortedOjectsToDraw;

    public boolean playing = false;
    public boolean gameEnded = false;

    public boolean preventAction1 = false;
    public boolean preventAction2 = false;

    public GunfightGameScene(View context) {
        super();
        isDirty = false;
    }

    public void initScene(Context context) {
        staticBackgroud = new Background(BitmapFactory.decodeResource(context.getResources(), R.drawable.fullmap_gunfight));
        //GUI
        controller = new Sprite(10, 150, 150 , context);
        controller2 = new Sprite(10, 150, 150 , context);
        fire_button1 = new Sprite(15, 100, 100, context);
        fire_button2 = new Sprite(15, 100, 100, context);
        //bullet_image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet), Stage.DeviceWIDTH, Stage.DeviceHEIGHT, true);
        bullet_image = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);
        gui_heart = new Sprite(1, 50, 40, context);
        gui_bullet = new Sprite(2, 25, 40, context);

        carroca = new Sprite(23, 150, 150, context);
        carrocaPosY = 700;
        shadow1 = new Sprite(70, 80, 110, context);
        shadow2 = new Sprite(71, 80, 110, context);

        bullets.clear();

        cactus1 = new GunCactus(370,530, context);
        cactus2 = new GunCactus(1015,190, context);
        cactus3 = new GunCactus(270,250, context);
        cactus4 = new GunCactus(835,390, context);
        //bullet = new GunBullet();
        gunner1 = new GunSlinger(1,80, context);
        gunner2 = new GunSlinger(2, Stage.viewport_current_size_X-80, context);

        /*playerObjBtn1Up = new Button(20, 20, 150, 150);
        playerObjBtn1Down = new Button(20, Stage.viewport_current_size_Y - 170, 150, 150);
        playerObjBtn1Left = new Button(20, 200, 150, 150);
        playerObjBtn1Right = new Button(20, Stage.viewport_current_size_Y - 370, 150, 150);
        playerObjBtn1Fire = new Button (20, (Stage.viewport_current_size_Y/2)-80, 150, 150);*/
/*        playerObjBtn1Up = new Button(50+50, Stage.viewport_current_size_Y - 150-50, 50, 50);
        playerObjBtn1Down = new Button(50+50, Stage.viewport_current_size_Y - 50-50, 50, 50);
        playerObjBtn1Left = new Button(0+50, Stage.viewport_current_size_Y - 100-50, 50, 50);
        playerObjBtn1Right = new Button(100+50, Stage.viewport_current_size_Y - 100-50, 50, 50);
        playerObjBtn1Fire = new Button(200+50, Stage.viewport_current_size_Y - 100-50, 100, 100);

        playerObjBtn1UpLeft = new Button(0+50, Stage.viewport_current_size_Y - 150-50, 50, 50);
        playerObjBtn1DownLeft = new Button(0+50, Stage.viewport_current_size_Y - 50-50, 50, 50);
        playerObjBtn1UpRight = new Button(100+50, Stage.viewport_current_size_Y - 150-50, 50, 50);
        playerObjBtn1DownRight = new Button(100+50, Stage.viewport_current_size_Y - 50-50, 50, 50);

        playerObjBtn2Up = new Button((Stage.viewport_current_size_X / 2)+ 50+250, Stage.viewport_current_size_Y - 150-50, 50, 50);
        playerObjBtn2Down = new Button((Stage.viewport_current_size_X / 2)+ 50+250, Stage.viewport_current_size_Y - 50-50, 50, 50);
        playerObjBtn2Left = new Button((Stage.viewport_current_size_X / 2)+ 0+250, Stage.viewport_current_size_Y - 100-50, 50, 50);
        playerObjBtn2Right = new Button((Stage.viewport_current_size_X / 2)+ 100+250, Stage.viewport_current_size_Y - 100-50, 50, 50);
        playerObjBtn2Fire = new Button((Stage.viewport_current_size_X / 2)+ 200+250, Stage.viewport_current_size_Y - 100-50, 100, 100);

        playerObjBtn2UpLeft = new Button((Stage.viewport_current_size_X / 2)+ 0+250, Stage.viewport_current_size_Y - 150-50, 50, 50);
        playerObjBtn2DownLeft = new Button((Stage.viewport_current_size_X / 2)+ 0+250, Stage.viewport_current_size_Y - 50-50, 50, 50);
        playerObjBtn2UpRight = new Button((Stage.viewport_current_size_X / 2)+ 100+250, Stage.viewport_current_size_Y - 150-50, 50, 50);
        playerObjBtn2DownRight = new Button((Stage.viewport_current_size_X / 2)+ 100+250, Stage.viewport_current_size_Y - 50-50, 50, 50);
*/
        if (Stage.gameOptions_blueUseSchema1) {
            playerObjBtn1Up = new Button(50 + 50, Stage.viewport_current_size_Y - 150 - 50, 50, 50);
            playerObjBtn1Down = new Button(50 + 50, Stage.viewport_current_size_Y - 50 - 50, 50, 50);
            playerObjBtn1Left = new Button(0 + 50, Stage.viewport_current_size_Y - 100 - 50, 50, 50);
            playerObjBtn1Right = new Button(100 + 50, Stage.viewport_current_size_Y - 100 - 50, 50, 50);
            playerObjBtn1Fire = new Button(200 + 50, Stage.viewport_current_size_Y - 100 - 50, 100, 100);

            playerObjBtn1UpLeft = new Button(0 + 50, Stage.viewport_current_size_Y - 150 - 50, 50, 50);
            playerObjBtn1DownLeft = new Button(0 + 50, Stage.viewport_current_size_Y - 50 - 50, 50, 50);
            playerObjBtn1UpRight = new Button(100 + 50, Stage.viewport_current_size_Y - 150 - 50, 50, 50);
            playerObjBtn1DownRight = new Button(100 + 50, Stage.viewport_current_size_Y - 50 - 50, 50, 50);
        } else {
            playerObjBtn1Up = new Button(50 + 50, (Stage.viewport_current_size_Y/2) - 50, 50, 50);
            playerObjBtn1Down = new Button(50 + 50, (Stage.viewport_current_size_Y/2) + 50, 50, 50);
            playerObjBtn1Left = new Button(0 + 50, (Stage.viewport_current_size_Y/2) - 0, 50, 50);
            playerObjBtn1Right = new Button(100 + 50, (Stage.viewport_current_size_Y/2) - 0, 50, 50);

            playerObjBtn1Fire = new Button(0 + 50, (Stage.viewport_current_size_Y/2) + 100, 100, 100);

            playerObjBtn1UpLeft = new Button(0 + 50, (Stage.viewport_current_size_Y/2) - 50, 50, 50);
            playerObjBtn1DownLeft = new Button(0 + 50, (Stage.viewport_current_size_Y/2) + 50, 50, 50);
            playerObjBtn1UpRight = new Button(100 + 50, (Stage.viewport_current_size_Y/2) - 50, 50, 50);
            playerObjBtn1DownRight = new Button(100 + 50, (Stage.viewport_current_size_Y/2) + 50, 50, 50);
        }
        /*playerObjBtn1Up = new Button(20, 20, 150, 150);
        playerObjBtn1Down = new Button(20, Stage.viewport_current_size_Y - 170, 150, 150);
        playerObjBtn1Left = new Button(20, 200, 150, 150);
        playerObjBtn1Right = new Button(20, Stage.viewport_current_size_Y - 370, 150, 150);
        playerObjBtn1Fire = new Button(20, (Stage.viewport_current_size_Y / 2) - 80, 150, 150);*/

        /*playerObjBtn2Up = new Button(Stage.viewport_current_size_X - 170, 20, 150, 150);
        playerObjBtn2Down = new Button(Stage.viewport_current_size_X - 170, Stage.viewport_current_size_Y - 170, 150, 150);
        playerObjBtn2Left = new Button(Stage.viewport_current_size_X - 170, 200, 150, 150);
        playerObjBtn2Right = new Button(Stage.viewport_current_size_X - 170, Stage.viewport_current_size_Y - 370, 150, 150);
        playerObjBtn2Fire = new Button(Stage.viewport_current_size_X - 170, (Stage.viewport_current_size_Y / 2) - 80, 150, 150);
        */
        if (Stage.gameOptions_redUseSchema1) {
            playerObjBtn2Up = new Button((Stage.viewport_current_size_X / 2) + 50 + 250, Stage.viewport_current_size_Y - 150 - 50, 50, 50);
            playerObjBtn2Down = new Button((Stage.viewport_current_size_X / 2) + 50 + 250, Stage.viewport_current_size_Y - 50 - 50, 50, 50);
            playerObjBtn2Left = new Button((Stage.viewport_current_size_X / 2) + 0 + 250, Stage.viewport_current_size_Y - 100 - 50, 50, 50);
            playerObjBtn2Right = new Button((Stage.viewport_current_size_X / 2) + 100 + 250, Stage.viewport_current_size_Y - 100 - 50, 50, 50);
            playerObjBtn2Fire = new Button((Stage.viewport_current_size_X / 2)+ 200+250, Stage.viewport_current_size_Y - 100-50, 100, 100);

            playerObjBtn2UpLeft = new Button((Stage.viewport_current_size_X / 2) + 0 + 250, Stage.viewport_current_size_Y - 150 - 50, 50, 50);
            playerObjBtn2DownLeft = new Button((Stage.viewport_current_size_X / 2) + 0 + 250, Stage.viewport_current_size_Y - 50 - 50, 50, 50);
            playerObjBtn2UpRight = new Button((Stage.viewport_current_size_X / 2) + 100 + 250, Stage.viewport_current_size_Y - 150 - 50, 50, 50);
            playerObjBtn2DownRight = new Button((Stage.viewport_current_size_X / 2) + 100 + 250, Stage.viewport_current_size_Y - 50 - 50, 50, 50);
        } else {
            playerObjBtn2Up = new Button((Stage.viewport_current_size_X - 200) + 50, (Stage.viewport_current_size_Y/2) - 50, 50, 50);
            playerObjBtn2Down = new Button((Stage.viewport_current_size_X - 200) + 50, (Stage.viewport_current_size_Y/2) + 50, 50, 50);
            playerObjBtn2Left = new Button((Stage.viewport_current_size_X - 200) + 0, (Stage.viewport_current_size_Y/2) - 0, 50, 50);
            playerObjBtn2Right = new Button((Stage.viewport_current_size_X - 200) + 100, (Stage.viewport_current_size_Y/2) - 0, 50, 50);
            playerObjBtn2Fire = new Button((Stage.viewport_current_size_X - 200) + 150, Stage.viewport_current_size_Y - 100-50, 100, 100);

            playerObjBtn2UpLeft = new Button((Stage.viewport_current_size_X - 200) + 0, (Stage.viewport_current_size_Y/2) - 50, 50, 50);
            playerObjBtn2DownLeft = new Button((Stage.viewport_current_size_X - 200) + 0, (Stage.viewport_current_size_Y/2) + 50, 50, 50);
            playerObjBtn2UpRight = new Button((Stage.viewport_current_size_X - 200) + 100, (Stage.viewport_current_size_Y/2) - 50, 50, 50);
            playerObjBtn2DownRight = new Button((Stage.viewport_current_size_X - 200) + 100, (Stage.viewport_current_size_Y/2) + 50, 50, 50);
        }

        playerObjBtn1Area = new Button (0, 0, Stage.viewport_current_size_X/2, Stage.viewport_current_size_Y);
        playerObjBtn2Area = new Button (Stage.viewport_current_size_X/2, 0, Stage.viewport_current_size_X/2, Stage.viewport_current_size_Y);

        buttonPause = new Button((Stage.viewport_current_size_X/2)-25, Stage.viewport_current_size_Y-75, 50, 50);
        buttonPause.face = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_player_obj);
        buttonPause.update();

        particles_gun_hit = new Animation(BitmapFactory.decodeResource(context.getResources(), R.drawable.particles_gun_hit),100,100,6);
        particles_gun_hit.playedOnce = true;
        particles_gun_hit2 = new Animation(BitmapFactory.decodeResource(context.getResources(), R.drawable.particles_gun_hit),100,100,6);
        particles_gun_hit2.playedOnce = true;

        particles_gun_shot = new Animation(BitmapFactory.decodeResource(context.getResources(), R.drawable.gunfire_anim),84,42,6);
        particles_gun_shot.playedOnce = true;
        particles_gun_shot2 = new Animation(BitmapFactory.decodeResource(context.getResources(), R.drawable.gunfire_anim2),84,42,6);
        particles_gun_shot2.playedOnce = true;

        //particles_cactus_hit = new Animation(BitmapFactory.decodeResource(context.getResources(), R.drawable.particles_gun_hit),80,100,4);
        particles_cactus_hit = new Animation(BitmapFactory.decodeResource(context.getResources(), R.drawable.cactus_anim),100,100,6);
        particles_cactus_hit.playedOnce = true;
        particles_cactus_hit2 = new Animation(BitmapFactory.decodeResource(context.getResources(), R.drawable.cactus_anim),100,100,6);
        particles_cactus_hit2.playedOnce = true;
        particles_cactus_hit3 = new Animation(BitmapFactory.decodeResource(context.getResources(), R.drawable.cactus_anim),100,100,6);
        particles_cactus_hit3.playedOnce = true;
        particles_cactus_hit4 = new Animation(BitmapFactory.decodeResource(context.getResources(), R.drawable.cactus_anim),100,100,6);
        particles_cactus_hit4.playedOnce = true;

        BACK_MAIN_MENU = new Button(80, Stage.viewport_current_size_Y-50, 300, 50);

        sortedOjectsToDraw = new ArrayList<GuiObject>();

        isDirty = true;
    }

    public void killScene() {
        staticBackgroud.image.recycle();
        buttonPause.face.recycle();
        bullet_image.recycle();
        gui_heart.image.recycle();
        gui_bullet.image.recycle();
        carroca.image.recycle();

        shadow1.image.recycle();
        shadow2.image.recycle();
        gunner1.image.recycle();
//        gunner1.image_action.recycle();
        gunner1.hitting.recycleAnimation();
        gunner1.running.recycleAnimation();
        gunner2.image.recycle();
  //      gunner2.image_action.recycle();
        gunner2.hitting.recycleAnimation();
        gunner2.running.recycleAnimation();
        controller.image.recycle();
        controller2.image.recycle();
        fire_button1.image.recycle();
        fire_button2.image.recycle();

        cactus1.image_full.recycle();
        cactus1.image_half.recycle();
        cactus1.image_empty.recycle();
        cactus2.image_full.recycle();
        cactus2.image_half.recycle();
        cactus2.image_empty.recycle();
        cactus3.image_full.recycle();
        cactus3.image_half.recycle();
        cactus3.image_empty.recycle();
        cactus4.image_full.recycle();
        cactus4.image_half.recycle();
        cactus4.image_empty.recycle();
        particles_gun_hit.recycleAnimation();
        particles_gun_hit2.recycleAnimation();
        particles_gun_shot.recycleAnimation();
        particles_gun_shot2.recycleAnimation();
        particles_cactus_hit.recycleAnimation();
        particles_cactus_hit2.recycleAnimation();
        particles_cactus_hit3.recycleAnimation();
        particles_cactus_hit4.recycleAnimation();

        isDirty = false;
    }

    public void checkInput(MotionEvent event) {
        touchX = event.getX();
        touchY = event.getY();
        Rect touchArea = new Rect((int) touchX - 10, (int) touchY - 10, (int) touchX + 10, (int) touchY + 10);

        // reset button states
        playerObjBtn1Up.bActive = false;
        playerObjBtn1Down.bActive = false;
        playerObjBtn1Left.bActive = false;
        playerObjBtn1Right.bActive = false;
        playerObjBtn1Fire.bActive = false;

        playerObjBtn1UpLeft.bActive = false;
        playerObjBtn1DownLeft.bActive = false;
        playerObjBtn1UpRight.bActive = false;
        playerObjBtn1DownRight.bActive = false;

        playerObjBtn2Up.bActive = false;
        playerObjBtn2Down.bActive = false;
        playerObjBtn2Left.bActive = false;
        playerObjBtn2Right.bActive = false;
        playerObjBtn2Fire.bActive = false;

        playerObjBtn2UpLeft.bActive = false;
        playerObjBtn2DownLeft.bActive = false;
        playerObjBtn2UpRight.bActive = false;
        playerObjBtn2DownRight.bActive = false;

        playerObjBtn1Area.bActive = false;
        playerObjBtn2Area.bActive = false;

        if (playing) {
            // get pointer index from the event object
            int pointerIndex = event.getActionIndex();
            // get pointer ID
            int pointerId = event.getPointerId(pointerIndex);

            int maskedAction; // = 0; //event.getActionMasked(i);
            for (int i = 0; i < event.getPointerCount(); i++) {
                touchX = event.getX(i);
                touchY = event.getY(i);
                //touchArea = new Rect((int) touchX - 10, (int) touchY - 10, (int) touchX + 10, (int) touchY + 10);
                touchArea = new Rect((int) ((touchX - 10) / Stage.zoomplusX), (int) ((touchY - 10) / Stage.zoomplusY), (int) ((touchX + 10) / Stage.zoomplusX), (int) ((touchY + 10) / Stage.zoomplusY));
                maskedAction = event.getActionMasked();

                switch (maskedAction) {
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP: {
                        //if (Stage.gameOptions_blueHumanOn) {
                        if (pointerId == btn2firePointerId) {btn2firePointerId = -1;}
                        if (pointerId == btn1firePointerId) {btn1firePointerId = -1;}
                        gunner1.ya = 0;
                        gunner2.ya = 0;
                        gunner1.xa = 0;
                        gunner2.xa = 0;
                        preventAction1 = false;
                        preventAction2 = false;
                        //gunner1.isShooting = false;
                        //gunner2.isShooting = false;
                        break;
                    }

                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN: {
                        if (Stage.gameOptions_blueHumanOn) {
                            if (Stage.gameOptions_blueUseButtons) {
                                if (Rect.intersects(touchArea, playerObjBtn1Fire.box) && !gunner1.isShooting && btn1firePointerId == -1 && gunner1.numBullets>0) {
                                    playerObjBtn1Fire.bActive = true;
                                    bullets.add(new GunBullet(gunner1.x + gunner1.width, gunner1.y, 50, Stage.context1));
                                    gunner1.isShooting = true;

                                    if (!Stage.gameOptions_kidModeOn) {
                                        gunner1.numBullets--;
                                    }
                                    gunner1.startTime = System.nanoTime();
                                    btn1firePointerId = pointerId;
                                    if (Stage.gameOptions_soundOn) {
                                        Sample.playSound(4);
                                    }
                                    particles_gun_shot.setFrame(0);
                                    particles_gun_shot.playedOnce = false;
                                }
                            } else {
                                if (Rect.intersects(touchArea, gunner1.getCenteredBounds()) && !gunner1.isShooting && btn1firePointerId == -1 && !preventAction1 && gunner1.numBullets>0) {
                                    bullets.add(new GunBullet(gunner1.x + gunner1.width, gunner1.y, 50, Stage.context1));
                                    gunner1.isShooting = true;

                                    if (!Stage.gameOptions_kidModeOn) {
                                        gunner1.numBullets--;
                                    }
                                    gunner1.startTime = System.nanoTime();
                                    //if (btn1firePointerId == -1) {
                                    btn1firePointerId = pointerId;
                                    if (Stage.gameOptions_soundOn) {
                                        Sample.playSound(4);
                                    }
                                    particles_gun_shot.setFrame(0);
                                    particles_gun_shot.playedOnce = false;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn1Area.box)) {
                                    gunner1.player_targetX = (int)(event.getX(i) / Stage.zoomplusX);
                                    gunner1.player_targetY = (int)(event.getY(i) / Stage.zoomplusY);
                                }
                            }
                        }
                        if (Stage.gameOptions_redHumanOn) {
                            if (Stage.gameOptions_redUseButtons) {
                                if (Rect.intersects(touchArea, playerObjBtn2Fire.box) && !gunner2.isShooting && btn2firePointerId == -1 && gunner2.numBullets>0) {
                                    playerObjBtn2Fire.bActive = true;
                                    bullets.add(new GunBullet(gunner2.x - gunner2.width, gunner2.y, -50, Stage.context1));
                                    gunner2.isShooting = true;

                                    if (!Stage.gameOptions_kidModeOn) {
                                        gunner2.numBullets--;
                                    }
                                    gunner2.startTime = System.nanoTime();
                                    btn2firePointerId = pointerId;
                                    if (Stage.gameOptions_soundOn) {
                                        Sample.playSound(4);
                                    }
                                    particles_gun_shot2.setFrame(0);
                                    particles_gun_shot2.playedOnce = false;
                                }
                            } else {
                                if (Rect.intersects(touchArea, gunner2.getCenteredBounds()) && !gunner2.isShooting && btn2firePointerId == -1 && !preventAction2 && gunner2.numBullets>0) {
                                    bullets.add(new GunBullet(gunner2.x - gunner2.width, gunner2.y, -50, Stage.context1));
                                    gunner2.isShooting = true;

                                    if (!Stage.gameOptions_kidModeOn) {
                                        gunner2.numBullets--;
                                    }
                                    gunner2.startTime = System.nanoTime();
                                    //if (btn2firePointerId == -1) {
                                    btn2firePointerId = pointerId;
                                    if (Stage.gameOptions_soundOn) {
                                        Sample.playSound(4);
                                    }
                                    particles_gun_shot2.setFrame(0);
                                    particles_gun_shot2.playedOnce = false;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn2Area.box)) {
                                    gunner2.player_targetX = (int)(event.getX(i) / Stage.zoomplusX);
                                    gunner2.player_targetY = (int)(event.getY(i) / Stage.zoomplusY);
                                }
                            }
                        }
                        break;
                    }

                    //case MotionEvent.ACTION_DOWN:
                    //case MotionEvent.ACTION_POINTER_DOWN:
                    case MotionEvent.ACTION_MOVE: {
                        if (Stage.gameOptions_blueHumanOn) {
                            if (Stage.gameOptions_blueUseButtons) {
                                if (Rect.intersects(touchArea, playerObjBtn1UpLeft.box)) {
                                    //Log.d("Update Racket","start");
                                    playerObjBtn1UpLeft.bActive = true;
                                    gunner1.ya = -15;
                                    gunner1.xa = -15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn1UpRight.box)) {
                                    //Log.d("Update Racket","start");
                                    playerObjBtn1UpRight.bActive = true;
                                    gunner1.ya = -15;
                                    gunner1.xa = 15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn1DownLeft.box)) {
                                    //Log.d("Update Racket","start");
                                    playerObjBtn1DownLeft.bActive = true;
                                    gunner1.ya = 15;
                                    gunner1.xa = -15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn1DownRight.box)) {
                                    //Log.d("Update Racket","start");
                                    playerObjBtn1DownRight.bActive = true;
                                    gunner1.ya = 15;
                                    gunner1.xa = 15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn1Up.box)) {
                                    //Log.d("Update Racket","start");
                                    playerObjBtn1Up.bActive = true;
                                    gunner1.ya = -15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn1Down.box)) {
                                    //Log.d("Update Racket","start");
                                    playerObjBtn1Down.bActive = true;
                                    gunner1.ya = 15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn1Left.box)) {
                                    playerObjBtn1Left.bActive = true;
                                    gunner1.xa = -15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn1Right.box)) {
                                    playerObjBtn1Right.bActive = true;
                                    gunner1.xa = 15;
                                }
                                // reset direction vector values
                                if (!playerObjBtn1Up.bActive && !playerObjBtn1Down.bActive
                                        && !playerObjBtn1UpLeft.bActive && !playerObjBtn1DownLeft.bActive
                                        && !playerObjBtn1UpRight.bActive && !playerObjBtn1DownRight.bActive){
                                    gunner1.ya = 0;
                                }
                                if (!playerObjBtn1Left.bActive && !playerObjBtn1Right.bActive
                                        && !playerObjBtn1UpLeft.bActive && !playerObjBtn1DownLeft.bActive
                                        && !playerObjBtn1UpRight.bActive && !playerObjBtn1DownRight.bActive) {
                                    gunner1.xa = 0;
                                }
                            } else {
                                if (Rect.intersects(touchArea, gunner1.getCenteredBounds())) {
                                    preventAction1 = true;
                                } else {
                                    preventAction1 = false;
                                    if (Rect.intersects(touchArea, playerObjBtn1Area.box) && Stage.gameOptions_blueUseSchema1) {
                                        playerObjBtn1Area.bActive = true;
                                        if (gunner1.y > (touchY + Stage.touchThreshold) / Stage.zoomplusY) {
                                            gunner1.ya = -15;
                                        } else {
                                            if (gunner1.y < (touchY - Stage.touchThreshold) / Stage.zoomplusY) {
                                                gunner1.ya = 15;
                                            } else {
                                                gunner1.ya = 0;
                                            }
                                        }
                                        if (gunner1.x > (touchX + Stage.touchThreshold) / Stage.zoomplusX) {
                                            gunner1.xa = -15;
                                        } else {
                                            if (gunner1.x < (touchX - Stage.touchThreshold) / Stage.zoomplusX) {
                                                gunner1.xa = 15;
                                            } else {
                                                gunner1.xa = 0;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (Stage.gameOptions_redHumanOn) {
                            if (Stage.gameOptions_redUseButtons) {
                                if (Rect.intersects(touchArea, playerObjBtn2UpLeft.box)) {
                                    playerObjBtn2UpLeft.bActive = true;
                                    gunner2.ya = -15;
                                    gunner2.xa = -15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn2UpRight.box)) {
                                    playerObjBtn2UpRight.bActive = true;
                                    gunner2.ya = -15;
                                    gunner2.xa = 15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn2DownLeft.box)) {
                                    playerObjBtn2DownLeft.bActive = true;
                                    gunner2.ya = 15;
                                    gunner2.xa = -15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn2DownRight.box)) {
                                    playerObjBtn2DownRight.bActive = true;
                                    gunner2.ya = 15;
                                    gunner2.xa = 15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn2Up.box)) {
                                    playerObjBtn2Up.bActive = true;
                                    gunner2.ya = -15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn2Down.box)) {
                                    playerObjBtn2Down.bActive = true;
                                    gunner2.ya = 15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn2Left.box)) {
                                    playerObjBtn2Left.bActive = true;
                                    gunner2.xa = -15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn2Right.box)) {
                                    playerObjBtn2Right.bActive = true;
                                    gunner2.xa = 15;
                                }
                                if (!playerObjBtn2Up.bActive && !playerObjBtn2Down.bActive
                                        && !playerObjBtn2UpLeft.bActive && !playerObjBtn2DownLeft.bActive
                                        && !playerObjBtn2UpRight.bActive && !playerObjBtn2DownRight.bActive){
                                    gunner2.ya = 0;
                                }
                                if (!playerObjBtn2Left.bActive && !playerObjBtn2Right.bActive
                                        && !playerObjBtn2UpLeft.bActive && !playerObjBtn2DownLeft.bActive
                                        && !playerObjBtn2UpRight.bActive && !playerObjBtn2DownRight.bActive) {
                                    gunner2.xa = 0;
                                }
                                /*if (!playerObjBtn2Up.bActive && !playerObjBtn2Down.bActive) {
                                    gunner2.ya = 0;
                                }
                                if (!playerObjBtn2Left.bActive && !playerObjBtn2Right.bActive) {
                                    gunner2.xa = 0;
                                }*/
                            } else {
                                if (Rect.intersects(touchArea, gunner2.getCenteredBounds())) {
                                    preventAction2 = true;
                                } else {
                                    preventAction2 = false;
                                    if (Rect.intersects(touchArea, playerObjBtn2Area.box) && Stage.gameOptions_redUseSchema1) {
                                        playerObjBtn2Area.bActive = true;
                                        if (gunner2.y > (touchY + Stage.touchThreshold) / Stage.zoomplusY) {
                                            gunner2.ya = -15;
                                        } else {
                                            if (gunner2.y < (touchY - Stage.touchThreshold) / Stage.zoomplusY) {
                                                gunner2.ya = 15;
                                            } else {
                                                gunner2.ya = 0;
                                            }
                                        }
                                        if (gunner2.x > (touchX + Stage.touchThreshold) / Stage.zoomplusX) {
                                            gunner2.xa = -15;
                                        } else {
                                            if (gunner2.x < (touchX - Stage.touchThreshold) / Stage.zoomplusX) {
                                                gunner2.xa = 15;
                                            } else {
                                                gunner2.xa = 0;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (Rect.intersects(touchArea, buttonPause.box)) {
                            playing = false;
                            //Stage.currScene = Stage.pauseScene;
                            //Stage.currentScene = Stage.GameState.PAUSE_GAME;
                        }
                        break;
                    }
                }
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
                        //Stage.currentScene = Stage.GameState.TITLE_MENU;
                        Stage.currScene = Stage.titleScene;
                        //killScene();
                        //gameEnded = false;

                        playing = false;
                    } else if (!gameEnded) {
                        playing = true;
                    }
                    break;
                }
            }
        }
    }

    public void update() {
        // find better way to test collision
        if (playing) {
            for(int i =0; i<bullets.size();i++) {
                bullets.get(i).update(gunner1.getCenteredBounds(), gunner2.getCenteredBounds(),
                cactus1.getCenteredBounds(), cactus2.getCenteredBounds(), carroca.getCenteredBounds(),
                        cactus3.getCenteredBounds(), cactus4.getCenteredBounds());
                //        cactus3.getCenteredBounds(), cactus4.getCenteredBounds());
                if (!bullets.get(i).isActive) {
                    bullets.remove(i);
                }
            }

            if (gunner1.isHit) { // && particles_gun_hit.playedOnce) {
                particles_gun_hit.setFrame(0);
                particles_gun_hit.playedOnce = false;
                gunner1.isHit = false;
            }
            if (!particles_gun_hit.playedOnce) {
                particles_gun_hit.update();
            }

            if (gunner2.isHit) { // && particles_gun_hit.playedOnce) {
                particles_gun_hit2.setFrame(0);
                particles_gun_hit2.playedOnce = false;
                gunner2.isHit = false;
            }
            if (!particles_gun_hit2.playedOnce) {
                particles_gun_hit2.update();
            }

            if (cactus1.isHit) { // && particles_gun_hit.playedOnce) {
                particles_cactus_hit.setFrame(0);
                particles_cactus_hit.playedOnce = false;
                cactus1.isHit = false;
            }
            if (!particles_cactus_hit.playedOnce) {
                particles_cactus_hit.update();
            }
            if (cactus2.isHit) { // && particles_gun_hit.playedOnce) {
                particles_cactus_hit2.setFrame(0);
                particles_cactus_hit2.playedOnce = false;
                cactus2.isHit = false;
            }
            if (!particles_cactus_hit2.playedOnce) {
                particles_cactus_hit2.update();
            }
            if (cactus3.isHit) { // && particles_gun_hit.playedOnce) {
                particles_cactus_hit3.setFrame(0);
                particles_cactus_hit3.playedOnce = false;
                cactus3.isHit = false;
            }
            if (!particles_cactus_hit3.playedOnce) {
                particles_cactus_hit3.update();
            }
            if (cactus4.isHit) { // && particles_gun_hit.playedOnce) {
                particles_cactus_hit4.setFrame(0);
                particles_cactus_hit4.playedOnce = false;
                cactus4.isHit = false;
            }
            if (!particles_cactus_hit4.playedOnce) {
                particles_cactus_hit4.update();
            }

            if (!particles_gun_shot.playedOnce) {
                particles_gun_shot.update();
            }
            if (!particles_gun_shot2.playedOnce) {
                particles_gun_shot2.update();
            }

            // reset player bullets
            if (gunner1.numBullets == 0 && gunner2.numBullets == 0) {
                gunner1.numBullets = 5;
                gunner2.numBullets = 5;
            }
            if (!Stage.gameOptions_blueHumanOn) {
                gunner1.doAI(Stage.context1);
            }
            if (Stage.gameOptions_blueHumanOn && !Stage.gameOptions_blueUseButtons && !Stage.gameOptions_blueUseSchema1) {
                gunner1.doFollowTarget();
            }
            gunner1.update();

            if (!Stage.gameOptions_redHumanOn) {
                gunner2.doAI(Stage.context1);
            }
            if (Stage.gameOptions_redHumanOn && !Stage.gameOptions_redUseButtons && !Stage.gameOptions_redUseSchema1) {
                gunner2.doFollowTarget();
            }
            gunner2.update();

            if (carrocaPosY > 120) {
                carrocaPosY = carrocaPosY - 1;
            }
        }
    }

    public void reset() {
        gunner1.x = 80;
        gunner2.x = Stage.viewport_current_size_X-80;
        gunner1.y = 300;
        gunner2.y = Stage.viewport_current_size_Y-300;
        gunner1.player_targetX = gunner1.x;
        gunner2.player_targetX = gunner2.x;
        gunner1.player_targetY = gunner1.y;
        gunner2.player_targetY = gunner2.y;
        gunner1.life = 3;
        gunner2.life = 3;
        gunner1.numBullets = 5;
        gunner2.numBullets = 5;
        cactus1.life = 3;
        cactus2.life = 3;
        cactus3.life = 3;
        cactus4.life = 3;
        gameEnded = false;
    }

    public void drawScene(Canvas canvas) {
        staticBackgroud.draw(canvas, true, 0, 0, 0, 0);


        shadow1.x = gunner1.x;
        shadow1.y = gunner1.y;
        shadow2.x = gunner2.x;
        shadow2.y = gunner2.y;
        shadow1.drawCentered(canvas);
        shadow2.drawCentered(canvas);
        sortedOjectsToDraw.clear();

        sortedOjectsToDraw.add(cactus1);
        sortedOjectsToDraw.add(cactus2);
        sortedOjectsToDraw.add(cactus3);
        sortedOjectsToDraw.add(cactus4);
        sortedOjectsToDraw.add(gunner1);
        sortedOjectsToDraw.add(gunner2);

        //cactus1.draw(canvas);
        //cactus2.draw(canvas);

        //cactus3.draw(canvas);
        //cactus4.draw(canvas);
        Collections.sort(sortedOjectsToDraw, new Comparator<GuiObject>() {
            @Override
            public int compare(GuiObject a, GuiObject b) {
                if (a.y < b.y) {
                    return -1;
                }
                if (a.y > b.y) {
                    return 1;
                }
                if (a.y == b.y) {
                    if (a.x < b.x) {
                        return -1;
                    }
                    if (a.x > b.x) {
                        return 1;
                    }
                    if (a.x == b.x) {
                        return 0;
                    }
                }
                return 0;
            }});

        // Draw selected objects
        for (int i=0; i<sortedOjectsToDraw.size(); i++) {
            sortedOjectsToDraw.get(i).draw(canvas);
        }

        for(int i =0; i<bullets.size();i++) {
            bullets.get(i).draw(canvas);
        }

        if (carrocaPosY > 120) {
            carroca.setX(640);
            carroca.setY(carrocaPosY);
            carroca.drawCentered(canvas);
        }
        //HUD
        for (int i=0; i<gunner1.life; i++) {
            gui_heart.setX(20+(50*i));
            gui_heart.setY(20);
            gui_heart.draw(canvas);
        }

        for (int i=0; i<gunner2.life; i++) {
            gui_heart.setX(Stage.viewport_current_size_X-70-(50*i));
            gui_heart.setY(20);
            gui_heart.draw(canvas);
        }

        for (int i=0; i<gunner1.numBullets; i++) {
            gui_bullet.setX(15+(gui_bullet.width*i));
            gui_bullet.setY(65);
            gui_bullet.draw(canvas);
        }

        for (int i=0; i<gunner2.numBullets; i++) {
            gui_bullet.setX(Stage.viewport_current_size_X - (gui_bullet.width * (i+1))- 15);
            gui_bullet.setY(65);
            gui_bullet.draw(canvas);
        }
        //HUD

        if (gunner1.life == 0 || gunner2.life == 0) gameEnded = true;
        if (gameEnded) {
            if (gunner2.life == 0) {
                pt2.setColor(Color.parseColor("#800000FF"));
                pt2.setStyle(Paint.Style.FILL);
                GUImask = new Rect(0, 0, Stage.DeviceWIDTH, Stage.DeviceHEIGHT);
                canvas.drawRect(GUImask, pt2);
            }
            if (gunner1.life == 0) {
                pt2.setColor(Color.parseColor("#80FF0000"));
                pt2.setStyle(Paint.Style.FILL);
                GUImask = new Rect(0, 0, Stage.DeviceWIDTH, Stage.DeviceHEIGHT);
                canvas.drawRect(GUImask, pt2);
            }
        }

        //gunner1.draw(canvas);
        //gunner2.draw(canvas);


        // todo: next section is gameScene.drawHUD(canvas);

        //bitmapText.drawString(canvas, gunner1.score+":"+ gunner2.score, (int)(Stage.viewport_current_size_X/2.6), 10, 6);
        //draw GUI: hearts and bullets
        //

        if (playing) {
            if (Stage.gameOptions_blueHumanOn && Stage.gameOptions_blueUseButtons) {
                controller.setX(playerObjBtn1UpLeft.x);
                controller.setY(playerObjBtn1UpLeft.y);
                controller.draw(canvas);

                //should be button face
                fire_button1.setX(playerObjBtn1Fire.x);
                fire_button1.setY(playerObjBtn1Fire.y);
                fire_button1.draw(canvas);

                playerObjBtn1Up.draw(canvas);
                playerObjBtn1Down.draw(canvas);
                playerObjBtn1Left.draw(canvas);
                playerObjBtn1Right.draw(canvas);
                playerObjBtn1Fire.draw(canvas);

                playerObjBtn1UpLeft.draw(canvas);
                playerObjBtn1DownLeft.draw(canvas);
                playerObjBtn1UpRight.draw(canvas);
                playerObjBtn1DownRight.draw(canvas);
            }
            if (Stage.gameOptions_redHumanOn && Stage.gameOptions_redUseButtons) {
                controller2.setX(playerObjBtn2UpLeft.x);
                controller2.setY(playerObjBtn2UpLeft.y);
                controller2.draw(canvas);

                fire_button2.setX(playerObjBtn2Fire.x);
                fire_button2.setY(playerObjBtn2Fire.y);
                fire_button2.draw(canvas);

                playerObjBtn2Up.draw(canvas);
                playerObjBtn2Down.draw(canvas);
                playerObjBtn2Left.draw(canvas);
                playerObjBtn2Right.draw(canvas);
                playerObjBtn2Fire.draw(canvas);

                playerObjBtn2UpLeft.draw(canvas);
                playerObjBtn2DownLeft.draw(canvas);
                playerObjBtn2UpRight.draw(canvas);
                playerObjBtn2DownRight.draw(canvas);
            }

            if (!particles_gun_hit.playedOnce) {
                canvas.drawBitmap(particles_gun_hit.getImage(), particles_gun_hit.src_full, particles_gun_hit.getDrawingBoundsXYZoom(gunner1.x+25, gunner1.y, 1), null);
            }
            if (!particles_gun_hit2.playedOnce) {
                canvas.drawBitmap(particles_gun_hit2.getImage(), particles_gun_hit2.src_full, particles_gun_hit2.getDrawingBoundsXYZoom(gunner2.x-25, gunner2.y, 1), null);
            }
            if (!particles_cactus_hit.playedOnce) {
                canvas.drawBitmap(particles_cactus_hit.getImage(), particles_cactus_hit.src_full, particles_cactus_hit.getDrawingBoundsXYZoom(cactus1.x, cactus1.y, 1), null);
            }
            if (!particles_cactus_hit2.playedOnce) {
                canvas.drawBitmap(particles_cactus_hit2.getImage(), particles_cactus_hit2.src_full, particles_cactus_hit2.getDrawingBoundsXYZoom(cactus2.x, cactus2.y, 1), null);
            }
            if (!particles_cactus_hit3.playedOnce) {
                canvas.drawBitmap(particles_cactus_hit3.getImage(), particles_cactus_hit3.src_full, particles_cactus_hit3.getDrawingBoundsXYZoom(cactus3.x, cactus3.y, 1), null);
            }
            if (!particles_cactus_hit4.playedOnce) {
                canvas.drawBitmap(particles_cactus_hit4.getImage(), particles_cactus_hit4.src_full, particles_cactus_hit4.getDrawingBoundsXYZoom(cactus4.x, cactus4.y, 1), null);
            }

            if (!particles_gun_shot.playedOnce) {
                canvas.drawBitmap(particles_gun_shot.getImage(), particles_gun_shot.src_full, particles_gun_shot.getDrawingBoundsXYZoom(gunner1.x+gunner1.width, gunner1.y, 1), null);
            }
            if (!particles_gun_shot2.playedOnce) {
                canvas.drawBitmap(particles_gun_shot2.getImage(), particles_gun_shot2.src_full, particles_gun_shot2.getDrawingBoundsXYZoom(gunner2.x-gunner2.width, gunner2.y, 1), null);
            }
            buttonPause.draw(canvas);

        } else if (!gameEnded){
            bitmapText.drawString(canvas,"PRESS ANYWHERE TO RESUME", 80,Stage.viewport_current_size_Y/2, 3);
            bitmapText.drawString(canvas, "MAIN MENU", BACK_MAIN_MENU.x, BACK_MAIN_MENU.y, 2);
            //BACK_MAIN_MENU.draw(canvas);
        }

        if (gameEnded) {
            if (gunner2.life == 0) {
                gunner1.draw(canvas);
                gunner2.draw(canvas);
                bitmapText.drawString(canvas, "BLUE WINS", 70, Stage.viewport_current_size_Y / 2 - 50, 8);
                playing = false;
            }
            if (gunner1.life == 0) {
                gunner1.draw(canvas);
                gunner2.draw(canvas);
                bitmapText.drawString(canvas, "RED WINS ", 130, Stage.viewport_current_size_Y / 2 - 50, 8);
                playing = false;
            }
            bitmapText.drawString(canvas, "MAIN MENU", BACK_MAIN_MENU.x, BACK_MAIN_MENU.y, 2);
            //BACK_MAIN_MENU.draw(canvas);
        }

        //bitmapText.drawString(canvas, "B1" + btn1firePointerId + " ,B2" + btn2firePointerId,100,100,1);
          //Log.d("Buttons", "B1" + btn1firePointerId + "B2" + btn2firePointerId);
    }

    public boolean handleBackButton() {
        playing = false;
        return false;
    }
}

