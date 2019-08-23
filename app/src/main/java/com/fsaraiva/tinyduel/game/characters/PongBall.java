package com.fsaraiva.tinyduel.game.characters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.fsaraiva.tinyduel.R;
import com.fsaraiva.tinyduel.engine.GuiObject;
import com.fsaraiva.tinyduel.engine.Stage;
import com.fsaraiva.tinyduel.engine.utils.audio.Sample;

import java.util.Random;

public class PongBall extends GuiObject {
    //private static final int WIDTH = 30, HEIGHT = 30;
    //private Scene game;
    private int xa = 10, ya = 10;
    private int xT1, yT1, xT2, yT2, xT3, yT3;
    //private int xa = 4, ya = 4;
    public Bitmap image;
    private Rect src_full;

    public PongBall(Context context) {
        //this.game = game;
        /*
        mole1x = (int) (55*drawScaleW);
         */
        x = 400; //Stage.viewport_current_size_X / 2; //Stage.DeviceWIDTH / 2;
        y = Stage.viewport_current_size_Y / 2; //.DeviceHEIGHT / 2;
        xT1 = xT2 = xT3 = x;
        yT1 = yT2 = yT3 = y;

        width = 30;
        height = 30;
        image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.tennis_ball), Stage.DeviceWIDTH, Stage.DeviceHEIGHT, true);
        src_full = new Rect(0,0,image.getWidth(),image.getHeight());
    }

    public void update(Rect racket1, Rect racket2) {
        // update trail
        xT3 = xT2;
        xT2 = xT1;
        xT1 = x;
        yT3 = yT2;
        yT2 = yT1;
        yT1 = y;

        // update current position
        x += xa;
        y += ya;

        /*
           0-0  1-15  2-30  3-40  4-AD  5-GM
         */
        // red player scored, serves next
        if (x < 50) {
            // move to pongRacketX.updateScore();
            if (Stage.gameOptions_soundOn) {
                Sample.playSound(3);
            }
            // player at AD
            if (Stage.mainGameScene.pongRacket2.score == 4) {
                Stage.mainGameScene.pongRacket2.score = 5;
            }
            // player at 40
            if (Stage.mainGameScene.pongRacket2.score == 3) {
                if (Stage.mainGameScene.pongRacket1.score == 3) {
                    Stage.mainGameScene.pongRacket2.score = 4;
                } else {
                    if (Stage.mainGameScene.pongRacket1.score == 4) {
                        Stage.mainGameScene.pongRacket1.score = 3;
                    } else {
                        Stage.mainGameScene.pongRacket2.score = 5;
                    }
                }
            }
            // player below 40
            if (Stage.mainGameScene.pongRacket2.score < 3) {
                Stage.mainGameScene.pongRacket2.score++;
            }

            if (Stage.mainGameScene.pongRacket1.score < 5 && Stage.mainGameScene.pongRacket2.score < 5) {
                // move to pongRacketX.resetPlayerPos();
                Stage.mainGameScene.pongRacket2.x = Stage.viewport_current_size_X - 80;
                Stage.mainGameScene.pongRacket2.y = Stage.viewport_current_size_Y / 2;
                Stage.mainGameScene.pongRacket1.x = 80;
                Stage.mainGameScene.pongRacket1.y = Stage.viewport_current_size_Y / 2;
                Stage.mainGameScene.pongRacket1.player_targetX = Stage.mainGameScene.pongRacket1.x;
                Stage.mainGameScene.pongRacket2.player_targetX = Stage.mainGameScene.pongRacket2.x;
                Stage.mainGameScene.pongRacket1.player_targetY = Stage.mainGameScene.pongRacket1.y;
                Stage.mainGameScene.pongRacket2.player_targetY = Stage.mainGameScene.pongRacket2.y;

                x = Stage.viewport_current_size_X - 120 - (width / 2);
                y = Stage.viewport_current_size_Y / 2 - (height / 2);
                xa = -10; //-xa;
                ya = 10;

                /*
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
                 */
            }
        }
        // blue player scored, serves next
        else if (x > Stage.viewport_current_size_X - 50) {
            // player at AD
            if (Stage.gameOptions_soundOn) {
                Sample.playSound(3);
            }

            if (Stage.mainGameScene.pongRacket1.score == 4) {
                Stage.mainGameScene.pongRacket1.score = 5;
            }
            // player at 40
            if (Stage.mainGameScene.pongRacket1.score == 3) {
                if (Stage.mainGameScene.pongRacket2.score == 3) {
                    Stage.mainGameScene.pongRacket1.score = 4;
                } else {
                    if (Stage.mainGameScene.pongRacket2.score == 4) {
                        Stage.mainGameScene.pongRacket2.score = 3;
                    } else {
                        Stage.mainGameScene.pongRacket1.score = 5;
                    }
                }
            }
            // player below 40
            if (Stage.mainGameScene.pongRacket1.score < 3) {
                Stage.mainGameScene.pongRacket1.score++;
            }

            if (Stage.mainGameScene.pongRacket1.score < 5 && Stage.mainGameScene.pongRacket2.score < 5) {
                Stage.mainGameScene.pongRacket1.x = 80;
                Stage.mainGameScene.pongRacket1.y = Stage.viewport_current_size_Y / 2;
                Stage.mainGameScene.pongRacket2.x = Stage.viewport_current_size_X - 80;
                Stage.mainGameScene.pongRacket2.y = Stage.viewport_current_size_Y / 2;
                Stage.mainGameScene.pongRacket1.player_targetX = Stage.mainGameScene.pongRacket1.x;
                Stage.mainGameScene.pongRacket2.player_targetX = Stage.mainGameScene.pongRacket2.x;
                Stage.mainGameScene.pongRacket1.player_targetY = Stage.mainGameScene.pongRacket1.y;
                Stage.mainGameScene.pongRacket2.player_targetY = Stage.mainGameScene.pongRacket2.y;

                x = 120 + (width / 2);
                y = Stage.viewport_current_size_Y / 2 - (height / 2);
                xa = 10; //xa;
                ya = -10;

                /*

        pongRacket1.y = 300;
        pongRacket2.y = Stage.viewport_current_size_Y - 300;
                 */
            }
        } else {
            if (y < 165 || y > Stage.viewport_current_size_Y - height - 40) //Stage.DeviceHEIGHT - HEIGHT - 29)
            {
                ya = -ya;
                // todo check lower bound
                if (Stage.gameOptions_soundOn) {
                    Sample.playSound(8);
                }
            }
        }

        checkCollision(racket1, racket2);
    }

    public void checkCollision(Rect racket1, Rect racket2) {
        if (getCenteredBounds().intersects (racket1.left, racket1.top, racket1.right, racket1.bottom)) {
            x = racket1.right + (width/2);
            y = racket1.bottom;

            if (!Stage.gameOptions_kidModeOn) {
                xa = -(int) (xa * 1.1f);
            } else {
                xa = -xa;
            }

            if (xa > 50) xa = 50;
            if (xa < -50) xa = -50;

            //rndom for up or down
            if (!Stage.gameOptions_kidModeOn) {
                ya = (int) (ya * 1.1f);
            }

            if (!Stage.gameOptions_blueHumanOn) {
                int dir = new Random().nextInt(2);
                if (dir < 1) {ya = -ya;}
            } else {
                // check player dir
                if (Stage.mainGameScene.pongRacket1.ya < 0) {
                    ya = -ya;
                }
            }
            if (ya > 50) ya = 50;
            if (ya < -50) ya = -50;
            //Log.d("BALL", "speed:" + xa);
            Stage.mainGameScene.pongRacket1.isShooting = true;
            Stage.mainGameScene.pongRacket1.startTime = System.nanoTime();
            if (Stage.gameOptions_soundOn) {
                Sample.playSound(2);
            }
        }
        if (getCenteredBounds().intersects (racket2.left, racket2.top, racket2.right, racket2.bottom)) {
            x = racket2.left - (width/2);
            y = racket2.bottom;

            if (!Stage.gameOptions_kidModeOn) {
                xa = -(int) (xa * 1.1f);
            } else {
                xa = -xa;
            }

            if (xa > 50) xa = 50;
            if (xa < -50) xa = -50;


            if (!Stage.gameOptions_kidModeOn) {
                ya = (int) (ya * 1.1f);
            }

            if (!Stage.gameOptions_redHumanOn) {
                int dir = new Random().nextInt(2);
                if (dir < 1) {ya = -ya;}
            } else {
                if (Stage.mainGameScene.pongRacket2.ya < 0) {
                    ya = -ya;
                }
            }
            if (ya > 50) ya = 50;
            if (ya < -50) ya = -50;
            //Log.d("BALL", "speed:" + xa);
            //xa = -xa * 2;
            Stage.mainGameScene.pongRacket2.isShooting = true;
            Stage.mainGameScene.pongRacket2.startTime = System.nanoTime();
            if (Stage.gameOptions_soundOn) {
                Sample.playSound(2);
            }
        }
    }

    public void draw(Canvas canvas) {
        //update trail pos here?

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE); //.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(1);
        //paint.setAlpha(10);

        super.drawPosition(true, 0, 0);

        //paint.setColor(Color.LTGRAY);
        //canvas.drawRect((10+drawX-(width/2))*Stage.zoomplusX, (10+drawY-(height/2))*Stage.zoomplusY, (10+drawX+(width/2))*Stage.zoomplusX, (10+drawY+(height/2))*Stage.zoomplusY, paint);

        paint.setColor(Color.WHITE);
        paint.setAlpha(175);
        canvas.drawRect((xT3-(width/8))*Stage.zoomplusX, (yT3-(height/6))*Stage.zoomplusY, (xT3+(width/6))*Stage.zoomplusX, (yT3+(height/6))*Stage.zoomplusY, paint);
        paint.setColor(Color.LTGRAY);
        paint.setAlpha(175);
        canvas.drawRect((xT2-(width/4))*Stage.zoomplusX, (yT2-(height/4))*Stage.zoomplusY, (xT2+(width/4))*Stage.zoomplusX, (yT2+(height/4))*Stage.zoomplusY, paint);
        paint.setColor(Color.LTGRAY);
        paint.setAlpha(75);
        canvas.drawRect((xT1-(width/2))*Stage.zoomplusX, (yT1-(height/2))*Stage.zoomplusY, (xT1+(width/3))*Stage.zoomplusX, (yT1+(height/3))*Stage.zoomplusY, paint);



        //paint.setColor(Color.YELLOW);
        //canvas.drawCircle(drawX+(width/2)*Stage.zoomplusX, drawY+(height/2)*Stage.zoomplusY, width/2*Stage.zoomplusX, paint);
        //canvas.drawRect(getCenteredDrawingBounds(), paint);

        canvas.drawBitmap(image, src_full, getCenteredDrawingBounds(), null);

        //paint.setStrokeWidth(1);
        //paint.setStyle(Paint.Style.STROKE);
        //paint.setColor(Color.BLACK);
        //canvas.drawRect(getCenteredDrawingBounds(), paint);
    }
}
