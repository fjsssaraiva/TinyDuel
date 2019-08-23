package com.fsaraiva.tinyduel.game.characters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.fsaraiva.tinyduel.R;
import com.fsaraiva.tinyduel.engine.GuiObject;
import com.fsaraiva.tinyduel.engine.Stage;
import com.fsaraiva.tinyduel.engine.utils.video.Animation;

import java.util.Random;

public class PongRacket extends GuiObject {
    public int xa, ya, ID;
    public int score;
    public boolean isShooting, isRunning;
    public long startTime;
    private long delay = 150;

    public int player_targetX;
    public int player_targetY;

    public Bitmap image;
    public Animation running;
    //public Bitmap image_action;
    public Animation hitting;

    //private Rect src_full;

    public PongRacket(int ID, int x, Context context) {
        this.x = x;
        this.ID = ID;
        width = 80;
        height = 100;
        y = Stage.viewport_current_size_Y / 2;
        score = 0;
        // usar enum state
        isShooting = false;
        isRunning  = false;

        xa = 0;

        if (ID == 1) {
            image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.tennis_blue), Stage.DeviceWIDTH, Stage.DeviceHEIGHT, true);
            //image_action = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.tennis_blue_action), Stage.DeviceWIDTH, Stage.DeviceHEIGHT, true);

            //running = new Animation(BitmapFactory.decodeResource(context.getResources(), R.drawable.tennis_blue),80,100,1);
            hitting = new Animation(BitmapFactory.decodeResource(context.getResources(), R.drawable.tennis_blue_action),80,100,1);
            running = new Animation(BitmapFactory.decodeResource(context.getResources(), R.drawable.running_blue),80,100,4);
        } else {
            image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.tennis_red), Stage.DeviceWIDTH, Stage.DeviceHEIGHT, true);
            //image_action = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.tennis_red_action), Stage.DeviceWIDTH, Stage.DeviceHEIGHT, true);

            running = new Animation(BitmapFactory.decodeResource(context.getResources(), R.drawable.running_red),80,100,4);
            hitting = new Animation(BitmapFactory.decodeResource(context.getResources(), R.drawable.tennis_red_action),80,100,1);
        }
        //src_full = new Rect(0,0,image.getWidth(),image.getHeight());
    }

    public void doAI(int ballY) {
        // todo: add dificulty here
        if (y > ballY) {
            ya = -10;
            //y -= 10;  //ya
        } else {
            //y += 10; //ya
            ya = 10;
        }

        int direction = new Random().nextInt(10) + 1;
        if (direction < 4) {
            //x -= 10; //ya=15;
            xa = -10;
        } else {
            if (direction > 7) {
                //x += 10; //ya=15;
                xa = 10;
            } else {
                xa = 0;
            }
        }
    }

    public void doFollowTarget() {
        ya = 0;
        if ((player_targetY + 10) < y) {
            ya = -10; //ya=15;
        }
        if ((player_targetY - 10) > y) {
            ya = 10; //ya=15;
        }

        xa = 0;
        if ((player_targetX + 10) < x) {
            xa = -10;
        }
        if ((player_targetX - 10) > x) {
            xa = 10;
        }
    }

    public void update() {
        /*if (y >= 120+(height/2) && y <= Stage.viewport_current_size_Y-(height/2)-120) //Stage.DeviceHEIGHT - HEIGHT - 29)
            y += ya;
        else if (y < 120+(height/2))
            y=120+(height/2);
        else
            y=Stage.viewport_current_size_Y-(height/2)-120;
*/
        if (ID == 1)
        {
            int xb = x + xa;
            if (xb >= 80 && xb <= (Stage.viewport_current_size_X/2) - 40) {//Stage.DeviceHEIGHT - HEIGHT - 29)
                x += xa;
            } /*else {
                if (x < 80) {
                    x = 80;
                } else {
                    if (x > (Stage.viewport_current_size_X/2) - 80) {
                        x = (Stage.viewport_current_size_X/2) - 80;
                    }
                }
            }*/
        }
        if (ID == 2)
        {
            int xb = x + xa;
            if (xb > (Stage.viewport_current_size_X/2) + 40 && xb <= Stage.viewport_current_size_X - 80) {//Stage.DeviceHEIGHT - HEIGHT - 29)
                x += xa;
            } /*else {
                if (x < (Stage.viewport_current_size_X/2) + 80 + (height/2)) {
                    x = (Stage.viewport_current_size_X/2) + 80 + (height/2);
                } else {
                    if (x > Stage.viewport_current_size_X - 80) {
                        x = Stage.viewport_current_size_X - 80;
                    }
                }
            }*/
        }

        int yb = y + ya;
        // todo: error smacking ball out of court bounds
        if (yb >= 100+(height/2) && yb <= Stage.viewport_current_size_Y-(height/2)-60)
        //y > Stage.viewport_current_size_Y - height - 40
        {//Stage.DeviceHEIGHT - HEIGHT - 29)
            y += ya;
        } /*else {
            if (y < 50 + (height/2)) {
                y = 50 + (height/2);
                //y += ya;
            } else {
                y = Stage.viewport_current_size_Y - (height/2) - 50;
                //y -= ya;
            }
        }*/

        if (isShooting) {
            long elapsed = (System.nanoTime() - startTime) / 1000000;
            if (elapsed > delay) isShooting = false;
        }

        isRunning = xa != 0 || ya != 0;
        running.update();
        hitting.update();
    }

    public void draw(Canvas canvas) {
        // get draw position (useful for scroll enabled games, static for one screen only games)
        //super.drawPosition(true, 0, 0);
        if (isShooting) {
            //canvas.drawBitmap(image_action, src_full, getCenteredDrawingBounds(), null);
            canvas.drawBitmap(hitting.getImageAt(0), hitting.src_full, hitting.getDrawingBoundsXY(x,y), null);
        } else {
            //canvas.drawBitmap(image, src_full, getCenteredDrawingBounds(), null);
            if (isRunning) {
                canvas.drawBitmap(running.getImage(), running.src_full, running.getDrawingBoundsXY(x,y), null);
            } else {
                canvas.drawBitmap(running.getImageAt(0), running.src_full, running.getDrawingBoundsXY(x,y), null);
            }
        }
    }
}
