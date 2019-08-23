package com.fsaraiva.tinyduel.game.characters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.fsaraiva.tinyduel.engine.GuiObject;
import com.fsaraiva.tinyduel.engine.Stage;
import com.fsaraiva.tinyduel.engine.utils.audio.Sample;

public class GunBullet extends GuiObject {
    //private static final int WIDTH = 30, HEIGHT = 30;
    //private Scene game;
    private int xa, ya = 0;
    private int xT1, yT1, xT2, yT2, xT3, yT3;
    //private int xa = 4, ya = 4;
    public boolean isActive;

    public Bitmap image;
    private Rect src_full;

    public GunBullet(int xi, int yi, int xai, Context context) {
        //this.game = game;
        /*
        mole1x = (int) (55*drawScaleW);
         */
        x = xi;  //400; //Stage.viewport_current_size_X / 2; //Stage.DeviceWIDTH / 2;
        y = yi; //Stage.viewport_current_size_Y / 2; //.DeviceHEIGHT / 2;
        xa = xai;
        xT1 = xT2 = xT3 = x;
        yT1 = yT2 = yT3 = y;

        width = 30;
        height = 30;
        //image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet), Stage.DeviceWIDTH, Stage.DeviceHEIGHT, true);

        image = Stage.fightGameScene.bullet_image;
        src_full = new Rect(0,0,image.getWidth(),image.getHeight());
        isActive = true;
    }

    public void update(Rect gun1, Rect gun2, Rect cact1, Rect cact2, Rect wagon, Rect cact3, Rect cact4) {
        // update trail
        xT3 = xT2;
        xT2 = xT1;
        xT1 = x;
        yT3 = yT2;
        yT2 = yT1;
        yT1 = y;

        // update current position
        x += xa;
        if (x < 50 || x > (Stage.viewport_current_size_X - 50)) {
            isActive = false;
        }

        checkCollision(gun1, gun2, cact1, cact2, wagon, cact3, cact4);
    }

    public void checkCollision(Rect gun1, Rect gun2, Rect cact1, Rect cact2, Rect wagon, Rect cact3, Rect cact4) {
        if (getCenteredBounds().intersects (gun1.left, gun1.top, gun1.right, gun1.bottom)) {
            isActive = false;
            Stage.fightGameScene.gunner1.isHit = true;
            Stage.fightGameScene.gunner1.life--;
            if (Stage.gameOptions_soundOn) {
                Sample.playSound(1);
            }
        }
        if (getCenteredBounds().intersects (gun2.left, gun2.top, gun2.right, gun2.bottom)) {
            isActive = false;
            Stage.fightGameScene.gunner2.isHit = true;
            Stage.fightGameScene.gunner2.life--;
            if (Stage.gameOptions_soundOn) {
                Sample.playSound(1);
            }
        }
        if (getCenteredBounds().intersects (cact1.left, cact1.top, cact1.right, cact1.bottom)) {
            if (Stage.fightGameScene.cactus1.life > 1) {
                Stage.fightGameScene.cactus1.life--;
                isActive = false;
                Stage.fightGameScene.cactus1.isHit = true;
                if (Stage.gameOptions_soundOn) {
                    Sample.playSound(7);
                }
                //Stage.fightGameScene.drawParticles(cactus_boom);
            }
        }
        if (getCenteredBounds().intersects (cact2.left, cact2.top, cact2.right, cact2.bottom)) {
            if (Stage.fightGameScene.cactus2.life > 1) {
                Stage.fightGameScene.cactus2.life--;
                isActive = false;
                Stage.fightGameScene.cactus2.isHit = true;
                if (Stage.gameOptions_soundOn) {
                    Sample.playSound(7);
                }
                //Stage.fightGameScene.drawParticles(cactus_boom);
            }
        }
        if (getCenteredBounds().intersects (cact3.left, cact3.top, cact3.right, cact3.bottom)) {
            if (Stage.fightGameScene.cactus3.life > 1) {
                Stage.fightGameScene.cactus3.life--;
                isActive = false;
                Stage.fightGameScene.cactus3.isHit = true;
                if (Stage.gameOptions_soundOn) {
                    Sample.playSound(7);
                }
                //Stage.fightGameScene.drawParticles(cactus_boom);
            }
        }
        if (getCenteredBounds().intersects (cact4.left, cact4.top, cact4.right, cact4.bottom)) {
            if (Stage.fightGameScene.cactus4.life > 1) {
                Stage.fightGameScene.cactus4.life--;
                isActive = false;
                Stage.fightGameScene.cactus4.isHit = true;
                if (Stage.gameOptions_soundOn) {
                    Sample.playSound(7);
                }
                //Stage.fightGameScene.drawParticles(cactus_boom);
            }
        }
        if (getCenteredBounds().intersects (wagon.left, wagon.top, wagon.right, wagon.bottom)) {
            if (wagon.top > 120) {
                isActive = false;
                if (Stage.gameOptions_soundOn) {
                    Sample.playSound(7);
                }
                //Stage.fightGameScene.drawParticles(cactus_boom);
            }
        }

    }

    public void draw(Canvas canvas) {
        //update trail pos here?

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL); //.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(1);

        super.drawPosition(true, 0, 0);

        //paint.setColor(Color.LTGRAY);
        //canvas.drawRect((10+drawX-(width/2))*Stage.zoomplusX, (10+drawY-(height/2))*Stage.zoomplusY, (10+drawX+(width/2))*Stage.zoomplusX, (10+drawY+(height/2))*Stage.zoomplusY, paint);

        paint.setColor(Color.WHITE);
        paint.setAlpha(50);
        canvas.drawRect((xT3-(width/2))*Stage.zoomplusX, (yT3-(height/2))*Stage.zoomplusY, (xT3+(width/2))*Stage.zoomplusX, (yT3+(height/2))*Stage.zoomplusY, paint);
        paint.setColor(Color.WHITE);
        paint.setAlpha(80);
        canvas.drawRect((xT2-(width/2))*Stage.zoomplusX, (yT2-(height/2))*Stage.zoomplusY, (xT2+(width/2))*Stage.zoomplusX, (yT2+(height/2))*Stage.zoomplusY, paint);
        paint.setColor(Color.WHITE);
        paint.setAlpha(120);
        canvas.drawRect((xT1-(width/2))*Stage.zoomplusX, (yT1-(height/2))*Stage.zoomplusY, (xT1+(width/2))*Stage.zoomplusX, (yT1+(height/2))*Stage.zoomplusY, paint);


        //paint.setColor(Color.BLACK);
        //canvas.drawCircle(drawX+(width/2)*Stage.zoomplusX, drawY+(height/2)*Stage.zoomplusY, width/2*Stage.zoomplusX, paint);
        //canvas.drawRect(getCenteredDrawingBounds(), paint);
        canvas.drawBitmap(image, src_full, getCenteredDrawingBounds(), null);
        //paint.setStyle(Paint.Style.STROKE);
        //paint.setColor(Color.BLACK);
        //canvas.drawRect(getCenteredDrawingBounds(), paint);
    }
}
