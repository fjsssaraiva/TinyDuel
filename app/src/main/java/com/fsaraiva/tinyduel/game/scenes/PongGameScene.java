package com.fsaraiva.tinyduel.game.scenes;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
// import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.fsaraiva.tinyduel.R;
import com.fsaraiva.tinyduel.engine.Scene;
import com.fsaraiva.tinyduel.engine.Stage;
import com.fsaraiva.tinyduel.engine.map.Background;
import com.fsaraiva.tinyduel.engine.utils.gui.Button;
import com.fsaraiva.tinyduel.engine.utils.video.Sprite;
import com.fsaraiva.tinyduel.game.MainAppActivity;
import com.fsaraiva.tinyduel.game.characters.PongBall;
import com.fsaraiva.tinyduel.game.characters.PongRacket;

public class PongGameScene extends Scene {
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

    private Button playerObjBtn1Area;
    private Button playerObjBtn2Area;

    private Button buttonPause;
    private Sprite controller;
    private Sprite controller2;

    private Sprite crowdEyeCenter;
    private Sprite crowdEyeLeft;
    private Sprite crowdEyeRight;
    private Sprite shadow1;
    private Sprite shadow2;

    private Button BACK_MAIN_MENU;
    public PongBall pongBall;
    public PongRacket pongRacket1;
    public PongRacket pongRacket2;

    public boolean playing = false;
    public boolean gameEnded = false;

    public PongGameScene(View context) {
        super();
        isDirty = false;
    }

    public void initScene(Context context) {
        staticBackgroud = new Background(BitmapFactory.decodeResource(context.getResources(), R.drawable.fullmap_tennis));

        controller = new Sprite(10, 150, 150 , context);
        //controller2 = new Sprite(10, (Stage.viewport_current_size_X / 2)+150, 150 , context);

        if (Stage.gameOptions_blueUseSchema1) {
            playerObjBtn1Up = new Button(50 + 50, Stage.viewport_current_size_Y - 150 - 50, 50, 50);
            playerObjBtn1Down = new Button(50 + 50, Stage.viewport_current_size_Y - 50 - 50, 50, 50);
            playerObjBtn1Left = new Button(0 + 50, Stage.viewport_current_size_Y - 100 - 50, 50, 50);
            playerObjBtn1Right = new Button(100 + 50, Stage.viewport_current_size_Y - 100 - 50, 50, 50);
            //playerObjBtn1Fire = new Button(200 + 50, Stage.viewport_current_size_Y - 100 - 50, 100, 100);

            playerObjBtn1UpLeft = new Button(0 + 50, Stage.viewport_current_size_Y - 150 - 50, 50, 50);
            playerObjBtn1DownLeft = new Button(0 + 50, Stage.viewport_current_size_Y - 50 - 50, 50, 50);
            playerObjBtn1UpRight = new Button(100 + 50, Stage.viewport_current_size_Y - 150 - 50, 50, 50);
            playerObjBtn1DownRight = new Button(100 + 50, Stage.viewport_current_size_Y - 50 - 50, 50, 50);
        } else {
            playerObjBtn1Up = new Button(50 + 50, (Stage.viewport_current_size_Y/2) - 50, 50, 50);
            playerObjBtn1Down = new Button(50 + 50, (Stage.viewport_current_size_Y/2) + 50, 50, 50);
            playerObjBtn1Left = new Button(0 + 50, (Stage.viewport_current_size_Y/2) - 0, 50, 50);
            playerObjBtn1Right = new Button(100 + 50, (Stage.viewport_current_size_Y/2) - 0, 50, 50);

            //playerObjBtn1Fire = new Button(0 + 50, (Stage.viewport_current_size_Y/2) - 0, 100, 100);

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
        controller2 = new Sprite(10, 150, 150 , context);
        if (Stage.gameOptions_redUseSchema1) {
            playerObjBtn2Up = new Button((Stage.viewport_current_size_X / 2) + 50 + 250, Stage.viewport_current_size_Y - 150 - 50, 50, 50);
            playerObjBtn2Down = new Button((Stage.viewport_current_size_X / 2) + 50 + 250, Stage.viewport_current_size_Y - 50 - 50, 50, 50);
            playerObjBtn2Left = new Button((Stage.viewport_current_size_X / 2) + 0 + 250, Stage.viewport_current_size_Y - 100 - 50, 50, 50);
            playerObjBtn2Right = new Button((Stage.viewport_current_size_X / 2) + 100 + 250, Stage.viewport_current_size_Y - 100 - 50, 50, 50);
            //playerObjBtn2Fire = new Button((Stage.viewport_current_size_X / 2)+ 200+250, Stage.viewport_current_size_Y - 100-50, 100, 100);

            playerObjBtn2UpLeft = new Button((Stage.viewport_current_size_X / 2) + 0 + 250, Stage.viewport_current_size_Y - 150 - 50, 50, 50);
            playerObjBtn2DownLeft = new Button((Stage.viewport_current_size_X / 2) + 0 + 250, Stage.viewport_current_size_Y - 50 - 50, 50, 50);
            playerObjBtn2UpRight = new Button((Stage.viewport_current_size_X / 2) + 100 + 250, Stage.viewport_current_size_Y - 150 - 50, 50, 50);
            playerObjBtn2DownRight = new Button((Stage.viewport_current_size_X / 2) + 100 + 250, Stage.viewport_current_size_Y - 50 - 50, 50, 50);
        } else {
            playerObjBtn2Up = new Button((Stage.viewport_current_size_X - 200) + 50, (Stage.viewport_current_size_Y/2) - 50, 50, 50);
            playerObjBtn2Down = new Button((Stage.viewport_current_size_X - 200) + 50, (Stage.viewport_current_size_Y/2) + 50, 50, 50);
            playerObjBtn2Left = new Button((Stage.viewport_current_size_X - 200) + 0, (Stage.viewport_current_size_Y/2) - 0, 50, 50);
            playerObjBtn2Right = new Button((Stage.viewport_current_size_X - 200) + 100, (Stage.viewport_current_size_Y/2) - 0, 50, 50);
            //playerObjBtn2Fire = new Button((Stage.viewport_current_size_X / 2)+ 200+250, Stage.viewport_current_size_Y - 100-50, 100, 100);

            playerObjBtn2UpLeft = new Button((Stage.viewport_current_size_X - 200) + 0, (Stage.viewport_current_size_Y/2) - 50, 50, 50);
            playerObjBtn2DownLeft = new Button((Stage.viewport_current_size_X - 200) + 0, (Stage.viewport_current_size_Y/2) + 50, 50, 50);
            playerObjBtn2UpRight = new Button((Stage.viewport_current_size_X - 200) + 100, (Stage.viewport_current_size_Y/2) - 50, 50, 50);
            playerObjBtn2DownRight = new Button((Stage.viewport_current_size_X - 200) + 100, (Stage.viewport_current_size_Y/2) + 50, 50, 50);
        }

        playerObjBtn1Area = new Button(0, 0, Stage.viewport_current_size_X / 2, Stage.viewport_current_size_Y);
        playerObjBtn2Area = new Button(Stage.viewport_current_size_X / 2, 0, Stage.viewport_current_size_X / 2, Stage.viewport_current_size_Y);

        buttonPause = new Button((Stage.viewport_current_size_X / 2) - 25, Stage.viewport_current_size_Y - 75, 50, 50);
        buttonPause.face = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_player_obj);
        buttonPause.update();

        crowdEyeCenter = new Sprite(60,19,6,context);
        crowdEyeLeft = new Sprite(61,19,6,context);
        crowdEyeRight = new Sprite(62,19,6,context);
        shadow1 = new Sprite(70, 80, 110, context);
        shadow2 = new Sprite(71, 80, 110, context);

        BACK_MAIN_MENU = new Button(80, Stage.viewport_current_size_Y - 50, 300, 50);

        pongBall = new PongBall(context);
        pongRacket1 = new PongRacket(1, 80, context);
        pongRacket2 = new PongRacket(2, Stage.viewport_current_size_X - 80, context);
        isDirty = true;
    }

    public void killScene() {
        staticBackgroud.image.recycle();
        buttonPause.face.recycle();
        pongBall.image.recycle();
        pongRacket1.image.recycle();
        //pongRacket1.image_action.recycle();
        pongRacket1.hitting.recycleAnimation();
        pongRacket1.running.recycleAnimation();
        pongRacket2.image.recycle();
        //pongRacket2.image_action.recycle();
        pongRacket2.hitting.recycleAnimation();
        pongRacket2.running.recycleAnimation();
        controller.image.recycle();
        controller2.image.recycle();

        crowdEyeCenter.image.recycle();
        crowdEyeLeft.image.recycle();
        crowdEyeRight.image.recycle();
        shadow1.image.recycle();
        shadow2.image.recycle();

        isDirty = false;
    }

    public void checkInput(MotionEvent event) {
        //touchX = event.getX();
        //touchY = event.getY();
        //Rect touchArea; // = new Rect((int) touchX - 10, (int) touchY - 10, (int) touchX + 10, (int) touchY + 10);

        // reset button states
        playerObjBtn1Up.bActive = false;
        playerObjBtn1Down.bActive = false;
        playerObjBtn1Left.bActive = false;
        playerObjBtn1Right.bActive = false;

        playerObjBtn1UpLeft.bActive = false;
        playerObjBtn1DownLeft.bActive = false;
        playerObjBtn1UpRight.bActive = false;
        playerObjBtn1DownRight.bActive = false;

        playerObjBtn2Up.bActive = false;
        playerObjBtn2Down.bActive = false;
        playerObjBtn2Left.bActive = false;
        playerObjBtn2Right.bActive = false;

        playerObjBtn2UpLeft.bActive = false;
        playerObjBtn2DownLeft.bActive = false;
        playerObjBtn2UpRight.bActive = false;
        playerObjBtn2DownRight.bActive = false;
        // if not buttons
        playerObjBtn1Area.bActive = false;
        playerObjBtn2Area.bActive = false;

        if (playing) {
            int maskedAction; // = 0; //event.getActionMasked(i);
            for (int i = 0; i < event.getPointerCount(); i++) {
                touchX = event.getX(i);
                touchY = event.getY(i);
                //touchArea = new Rect((int) touchX - 10, (int) touchY - 10, (int) touchX + 10, (int) touchY + 10);
                //todo invert touch: new Rect((int)(touchX / Stage.zoomplusX)- 10), ...
                Rect touchArea = new Rect((int) ((touchX - 10) / Stage.zoomplusX), (int) ((touchY - 10) / Stage.zoomplusY), (int) ((touchX + 10) / Stage.zoomplusX), (int) ((touchY + 10) / Stage.zoomplusY));
                maskedAction = event.getActionMasked();

                switch (maskedAction) {
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP: {
                        pongRacket1.ya = 0;
                        pongRacket2.ya = 0;
                        pongRacket1.xa = 0;
                        pongRacket2.xa = 0;
                        break;
                    }

                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                    case MotionEvent.ACTION_MOVE: {
                        if (Stage.gameOptions_blueHumanOn) {
                            if (Stage.gameOptions_blueUseButtons) {
                                if (Rect.intersects(touchArea, playerObjBtn1UpLeft.box)) {
                                    playerObjBtn1UpLeft.bActive = true;
                                    pongRacket1.ya = -15;
                                    pongRacket1.xa = -15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn1UpRight.box)) {
                                    playerObjBtn1UpRight.bActive = true;
                                    pongRacket1.ya = -15;
                                    pongRacket1.xa = 15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn1DownLeft.box)) {
                                    playerObjBtn1DownLeft.bActive = true;
                                    pongRacket1.ya = 15;
                                    pongRacket1.xa = -15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn1DownRight.box)) {
                                    playerObjBtn1DownRight.bActive = true;
                                    pongRacket1.ya = 15;
                                    pongRacket1.xa = 15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn1Up.box)) {
                                    playerObjBtn1Up.bActive = true;
                                    pongRacket1.ya = -15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn1Down.box)) {
                                    playerObjBtn1Down.bActive = true;
                                    pongRacket1.ya = 15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn1Left.box)) {
                                    playerObjBtn1Left.bActive = true;
                                    pongRacket1.xa = -15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn1Right.box)) {
                                    playerObjBtn1Right.bActive = true;
                                    pongRacket1.xa = 15;
                                }
                                // reset direction vector values
                                if (!playerObjBtn1Up.bActive && !playerObjBtn1Down.bActive
                                        && !playerObjBtn1UpLeft.bActive && !playerObjBtn1DownLeft.bActive
                                        && !playerObjBtn1UpRight.bActive && !playerObjBtn1DownRight.bActive){
                                    pongRacket1.ya = 0;
                                }
                                if (!playerObjBtn1Left.bActive && !playerObjBtn1Right.bActive
                                        && !playerObjBtn1UpLeft.bActive && !playerObjBtn1DownLeft.bActive
                                        && !playerObjBtn1UpRight.bActive && !playerObjBtn1DownRight.bActive) {
                                    pongRacket1.xa = 0;
                                }
                            } else {
                                // using touch mode
                                if (Rect.intersects(touchArea, playerObjBtn1Area.box)) {
                                    // for target mode
                                    pongRacket1.player_targetX = (int)(event.getX(i) / Stage.zoomplusX);
                                    pongRacket1.player_targetY = (int)(event.getY(i) / Stage.zoomplusY);

                                    playerObjBtn1Area.bActive = true;
                                    if (pongRacket1.y > (touchY + Stage.touchThreshold) / Stage.zoomplusY) {
                                        pongRacket1.ya = -15;
                                    } else {
                                        if (pongRacket1.y < (touchY - Stage.touchThreshold) / Stage.zoomplusY) {
                                            pongRacket1.ya = 15;
                                        } else {
                                            pongRacket1.ya = 0;
                                        }
                                    }
                                    if (pongRacket1.x > (touchX + Stage.touchThreshold) / Stage.zoomplusX) {
                                        pongRacket1.xa = -15;
                                    } else {
                                        if (pongRacket1.x < (touchX - Stage.touchThreshold) / Stage.zoomplusX) {
                                            pongRacket1.xa = 15;
                                        } else {
                                            pongRacket1.xa = 0;
                                        }
                                    }
                                }
                            }
                        }
                        if (Stage.gameOptions_redHumanOn) {
                            if (Stage.gameOptions_redUseButtons) {
                                if (Rect.intersects(touchArea, playerObjBtn2UpLeft.box)) {
                                    playerObjBtn2UpLeft.bActive = true;
                                    pongRacket2.ya = -15;
                                    pongRacket2.xa = -15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn2UpRight.box)) {
                                    playerObjBtn2UpRight.bActive = true;
                                    pongRacket2.ya = -15;
                                    pongRacket2.xa = 15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn2DownLeft.box)) {
                                    playerObjBtn2DownLeft.bActive = true;
                                    pongRacket2.ya = 15;
                                    pongRacket2.xa = -15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn2DownRight.box)) {
                                    playerObjBtn2DownRight.bActive = true;
                                    pongRacket2.ya = 15;
                                    pongRacket2.xa = 15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn2Up.box)) {
                                    playerObjBtn2Up.bActive = true;
                                    pongRacket2.ya = -15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn2Down.box)) {
                                    playerObjBtn2Down.bActive = true;
                                    pongRacket2.ya = 15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn2Left.box)) {
                                    playerObjBtn2Left.bActive = true;
                                    pongRacket2.xa = -15;
                                }
                                if (Rect.intersects(touchArea, playerObjBtn2Right.box)) {
                                    playerObjBtn2Right.bActive = true;
                                    pongRacket2.xa = 15;
                                }
                                ///////// 2
                                // reset direction vector values
                                if (!playerObjBtn2Up.bActive && !playerObjBtn2Down.bActive
                                        && !playerObjBtn2UpLeft.bActive && !playerObjBtn2DownLeft.bActive
                                        && !playerObjBtn2UpRight.bActive && !playerObjBtn2DownRight.bActive){
                                    pongRacket2.ya = 0;
                                }
                                if (!playerObjBtn2Left.bActive && !playerObjBtn2Right.bActive
                                        && !playerObjBtn2UpLeft.bActive && !playerObjBtn2DownLeft.bActive
                                        && !playerObjBtn2UpRight.bActive && !playerObjBtn2DownRight.bActive) {
                                    pongRacket2.xa = 0;
                                }
                                /*if (!playerObjBtn2Up.bActive && !playerObjBtn2Down.bActive) {
                                    pongRacket2.ya = 0;
                                }
                                if (!playerObjBtn2Left.bActive && !playerObjBtn2Right.bActive) {
                                    pongRacket2.xa = 0;
                                }*/
                            } else {
                                // using touch mode
                                if (Rect.intersects(touchArea, playerObjBtn2Area.box)) {
                                    // for target mode
                                    pongRacket2.player_targetX = (int)(event.getX(i) / Stage.zoomplusX);
                                    pongRacket2.player_targetY = (int)(event.getY(i) / Stage.zoomplusY);

                                    playerObjBtn2Area.bActive = true;
                                    if (pongRacket2.y > (touchY + Stage.touchThreshold) / Stage.zoomplusY) {
                                        pongRacket2.ya = -15;
                                    } else {
                                        if (pongRacket2.y < (touchY - Stage.touchThreshold) / Stage.zoomplusY) {
                                            pongRacket2.ya = 15;
                                        } else {
                                            pongRacket2.ya = 0;
                                        }
                                    }
                                    if (pongRacket2.x > (touchX + Stage.touchThreshold) / Stage.zoomplusX) {
                                        pongRacket2.xa = -15;
                                    } else {
                                        if (pongRacket2.x < (touchX - Stage.touchThreshold) / Stage.zoomplusX) {
                                            pongRacket2.xa = 15;
                                        } else {
                                            pongRacket2.xa = 0;
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
            Rect touchArea = new Rect((int) ((touchX - 10) / Stage.zoomplusX), (int) ((touchY - 10) / Stage.zoomplusY), (int) ((touchX + 10) / Stage.zoomplusX), (int) ((touchY + 10) / Stage.zoomplusY));
            switch (maskedAction) {
                case MotionEvent.ACTION_DOWN: {
                    if (/*gameEnded ||*/ Rect.intersects(touchArea, BACK_MAIN_MENU.box)) {
                        //Sample.resumeMusic();
                        if (Stage.gameOptions_adCounter > 2) {
                            if (MainAppActivity.mInterstitialAd.isLoaded()) {
                                MainAppActivity.mInterstitialAd.show();
                            } // else {
                                // Log.d("TAG", "The interstitial wasn't loaded yet.");
                            // }
                            Stage.gameOptions_adCounter = 0;
                            Stage.snapshotHandler.writeOptions(Stage.context1);
                        }

                        Stage.titleScene.initScene(Stage.context1);
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
            //pongBall.update(pongRacket1.getCenteredBounds(), pongRacket2.getCenteredBounds());

            if (!Stage.gameOptions_blueHumanOn) {
                pongRacket1.doAI(pongBall.y);
            }
            if (Stage.gameOptions_blueHumanOn && !Stage.gameOptions_blueUseButtons && !Stage.gameOptions_blueUseSchema1) {
                pongRacket1.doFollowTarget();
            }
            pongRacket1.update();

            if (!Stage.gameOptions_redHumanOn) {
                pongRacket2.doAI(pongBall.y);
            }
            if (Stage.gameOptions_redHumanOn && !Stage.gameOptions_redUseButtons && !Stage.gameOptions_redUseSchema1) {
                pongRacket2.doFollowTarget();
            }
            pongRacket2.update();

            pongBall.update(pongRacket1.getCenteredBounds(), pongRacket2.getCenteredBounds());
        }
    }

    public void reset() {

        pongBall.x = 120 + (pongBall.width / 2);
        pongBall.y = Stage.viewport_current_size_Y / 2 - (pongBall.height / 2);
        pongRacket1.x = 80;
        pongRacket2.x = Stage.viewport_current_size_X - 80;
        pongRacket1.y = 300;
        pongRacket2.y = Stage.viewport_current_size_Y - 300;
        pongRacket1.player_targetX = pongRacket1.x;
        pongRacket2.player_targetX = pongRacket2.x;
        pongRacket1.player_targetY = pongRacket1.y;
        pongRacket2.player_targetY = pongRacket2.y;
        pongRacket1.score = 0;
        pongRacket2.score = 0;
        gameEnded = false;
    }

    public void drawScene(Canvas canvas) {
        staticBackgroud.draw(canvas, true, 0, 0, 0, 0);

        drawCrowdMember(5, 9, canvas);
        drawCrowdMember(53, 9, canvas);
        drawCrowdMember(111, 6, canvas);
        drawCrowdMember(165, 6, canvas);
        drawCrowdMember(220, 9, canvas);
        drawCrowdMember(268, 9, canvas);
        drawCrowdMember(394, 6, canvas);
        drawCrowdMember(447, 9, canvas);
        drawCrowdMember(559, 9, canvas);
        drawCrowdMember(613, 6, canvas);
        drawCrowdMember(667, 6, canvas);
        drawCrowdMember(776, 6, canvas);
        drawCrowdMember(831, 9, canvas);
        drawCrowdMember(885, 6, canvas);
        drawCrowdMember(984, 9, canvas);
        drawCrowdMember(1036, 6, canvas);
        drawCrowdMember(1089, 9, canvas);
        drawCrowdMember(1144, 9, canvas);
        drawCrowdMember(1194, 6, canvas);
        drawCrowdMember(1242, 6, canvas);

        drawCrowdMember(633, 69, canvas);

        if (pongRacket1.score == 5 || pongRacket2.score == 5) gameEnded = true;

        if (gameEnded) {
            if (pongRacket1.score == 5) {
                pt2.setColor(Color.parseColor("#800000FF"));
                pt2.setStyle(Paint.Style.FILL);
                GUImask = new Rect(0, 0, Stage.DeviceWIDTH, Stage.DeviceHEIGHT);
                canvas.drawRect(GUImask, pt2);
            }
            if (pongRacket2.score == 5) {
                pt2.setColor(Color.parseColor("#80FF0000"));
                pt2.setStyle(Paint.Style.FILL);
                GUImask = new Rect(0, 0, Stage.DeviceWIDTH, Stage.DeviceHEIGHT);
                canvas.drawRect(GUImask, pt2);
            }
        }

        pongRacket1.draw(canvas);
        pongRacket2.draw(canvas);

        shadow1.x = pongRacket1.x;
        shadow1.y = pongRacket1.y;
        shadow2.x = pongRacket2.x;
        shadow2.y = pongRacket2.y;
        shadow1.drawCentered(canvas);
        shadow2.drawCentered(canvas);

        pongBall.draw(canvas);

        //bitmapText.drawString(canvas, (pongRacket1.score * 15) + "", 495, 55, 3);
        switch(pongRacket1.score) {
            case 0: {
                bitmapText.drawString(canvas, "00", 485, 54, 3);
                break;
            }
            case 1: {
                bitmapText.drawString(canvas, "15", 485, 54, 3);
                break;
            }
            case 2: {
                bitmapText.drawString(canvas, "30", 485, 54, 3);
                break;
            }
            case 3: {
                bitmapText.drawString(canvas, "40", 485, 54, 3);
                break;
            }
            case 4: {
                bitmapText.drawString(canvas, "AD", 485, 54, 3);
                break;
            }
            case 5: {
                bitmapText.drawString(canvas, "GM", 485, 54, 3);
                break;
            }
        }
        switch(pongRacket2.score) {
            case 0: {
                bitmapText.drawString(canvas, "00", 706, 54, 3);
                break;
            }
            case 1: {
                bitmapText.drawString(canvas, "15", 706, 54, 3);
                break;
            }
            case 2: {
                bitmapText.drawString(canvas, "30", 706, 54, 3);
                break;
            }
            case 3: {
                bitmapText.drawString(canvas, "40", 706, 54, 3);
                break;
            }
            case 4: {
                bitmapText.drawString(canvas, "AD", 706, 54, 3);
                break;
            }
            case 5: {
                bitmapText.drawString(canvas, "GM", 706, 54, 3);
                break;
            }
        }
        //bitmapText.drawString(canvas, "00", 485, 54, 3);
        //bitmapText.drawString(canvas, (pongRacket2.score * 15) + "", 706, 54, 3);

        if (playing) {
            if (Stage.gameOptions_blueHumanOn && Stage.gameOptions_blueUseButtons) {
                //controller.setX(50);
                //controller.setY(Stage.viewport_current_size_Y - 150-50);
                controller.setX(playerObjBtn1UpLeft.x);
                controller.setY(playerObjBtn1UpLeft.y);
                controller.draw(canvas);
                playerObjBtn1Up.draw(canvas);
                playerObjBtn1Down.draw(canvas);
                playerObjBtn1Left.draw(canvas);
                playerObjBtn1Right.draw(canvas);
                //playerObjBtn1Fire.draw(canvas);

                playerObjBtn1UpLeft.draw(canvas);
                playerObjBtn1DownLeft.draw(canvas);
                playerObjBtn1UpRight.draw(canvas);
                playerObjBtn1DownRight.draw(canvas);
            }
            if (Stage.gameOptions_redHumanOn && Stage.gameOptions_redUseButtons) {
                controller2.setX(playerObjBtn2UpLeft.x);
                controller2.setY(playerObjBtn2UpLeft.y);
                controller2.draw(canvas);
                playerObjBtn2Up.draw(canvas);
                playerObjBtn2Down.draw(canvas);
                playerObjBtn2Left.draw(canvas);
                playerObjBtn2Right.draw(canvas);
                //playerObjBtn2Fire.draw(canvas);

                playerObjBtn2UpLeft.draw(canvas);
                playerObjBtn2DownLeft.draw(canvas);
                playerObjBtn2UpRight.draw(canvas);
                playerObjBtn2DownRight.draw(canvas);
            }
            /*if (Stage.gameOptions_blueHumanOn && !Stage.gameOptions_blueUseButtons) {
                playerObjBtn1Area.draw(canvas);
            }
            if (Stage.gameOptions_redHumanOn && !Stage.gameOptions_redUseButtons) {
                playerObjBtn2Area.draw(canvas);
            }*/

            buttonPause.draw(canvas);

        } else if (!gameEnded) {
            bitmapText.drawString(canvas, "PRESS ANYWHERE TO START", 100, Stage.viewport_current_size_Y / 2, 3);
            bitmapText.drawString(canvas, "MAIN MENU", BACK_MAIN_MENU.x, BACK_MAIN_MENU.y, 2);
            //BACK_MAIN_MENU.draw(canvas);
        }

        if (gameEnded) {
            if (pongRacket1.score == 5) {
                bitmapText.drawString(canvas, "BLUE WINS", 70, Stage.viewport_current_size_Y / 2 - 50, 8);
                playing = false;
            }
            if (pongRacket2.score == 5) {
                bitmapText.drawString(canvas, "RED WINS ", 130, Stage.viewport_current_size_Y / 2 - 50, 8);
                playing = false;
            }
            bitmapText.drawString(canvas, "MAIN MENU", BACK_MAIN_MENU.x, BACK_MAIN_MENU.y, 2);
            //BACK_MAIN_MENU.draw(canvas);
        }
    }

    public void drawCrowdMember(int xc, int yc, Canvas canvas) {
        if (pongBall.x - xc < -20) {
            crowdEyeLeft.setX(xc);
            crowdEyeLeft.setY(yc);
            crowdEyeLeft.draw(canvas);
        } else {
            if (pongBall.x - xc > 20) {
                crowdEyeRight.setX(xc);
                crowdEyeRight.setY(yc);
                crowdEyeRight.draw(canvas);
            } else {
                crowdEyeCenter.setX(xc);
                crowdEyeCenter.setY(yc);
                crowdEyeCenter.draw(canvas);
            }
        }
    }

    public boolean handleBackButton() {
        playing = false;
        return false;
    }

    public void unloadScene(Stage context) {
    }
}

