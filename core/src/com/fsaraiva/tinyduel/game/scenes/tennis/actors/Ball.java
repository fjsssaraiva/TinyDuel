package com.fsaraiva.tinyduel.game.scenes.tennis.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.fsaraiva.tinyduel.engine.video.SceneObject;
import com.fsaraiva.tinyduel.engine.utils.Animation;

public class Ball extends SceneObject {
    Animation ball;
    Animation shadow;
    public int x_step, y_step;
    public boolean hitLeft, hitRight, ballUp = false;
    public int ballHeigth = 5;

    public Ball(int x, int y, int xai, int yai) {

        this.x = x;  //400; //Stage.viewport_current_size_X / 2; //Stage.DeviceWIDTH / 2;
        this.y = y; //Stage.viewport_current_size_Y / 2; //.DeviceHEIGHT / 2;
        x_step = xai * 30;
        y_step = yai * 20;

        width = 30;
        height = 30;

        float framespeed = 0.125f;
        ball = new Animation("tennis_ball.png", 1, 1, 0, 1, 0,1,framespeed);
        shadow = new Animation("ball_shadow.png", 1, 1, 0, 1, 0,1,framespeed);

        hitBox = new Rectangle(x, y, width, height);
        isActive = true;

        //Gdx.app.log("ball", "x:" + hitBox.x + " y: " + hitBox.y);
    }

    public int update(int[][] map, int tilesize) {
        Rectangle deltaRect = new Rectangle(hitBox);
        deltaRect.x = hitBox.x + (x_step * Gdx.graphics.getDeltaTime()); //deltaRect.y = hitBox.y + 200 * Gdx.graphics.getDeltaTime();
        deltaRect.y = hitBox.y + (y_step * Gdx.graphics.getDeltaTime());
        //Gdx.app.log("ball UPDATED", "x:" + hitBox.x + " y: " + hitBox.y + " xa:" + deltaRect.x + " ya: " + deltaRect.y + " d:" + Gdx.graphics.getDeltaTime());
        if (hitBox.x < 0 || hitBox.x > (map[0].length * tilesize)) { //1920
            isActive = false;
            //Gdx.app.log("ball REMOVED", "x:" + hitBox.x + " y: " + hitBox.y + " xa:" + xa + " ya: " + ya);
        }
        if (hitBox.y < 0 || hitBox.y > (map.length * tilesize)) { //1080
            isActive = false;
            //Gdx.app.log("ball REMOVED", "x:" + hitBox.x + " y: " + hitBox.y + " xa:" + xa + " ya: " + ya);
        }

        if (!getMapCollision((int)deltaRect.x, (int)hitBox.y, map, tilesize)) {
            hitBox.x = deltaRect.x;
        } else {
            if ((int) deltaRect.x < 90) {
                hitLeft = true;
            }
            if ((int) deltaRect.x > 1800) {
                hitRight = true;
            }
            x_step = -x_step;
        }
        if (!getMapCollision((int)hitBox.x, (int)deltaRect.y, map, tilesize)) {
            hitBox.y = deltaRect.y;
        } else {
            y_step = -y_step;
        }
        if (ballHeigth > 0 && !ballUp) {
            ballHeigth = ballHeigth - 2;
            if (ballHeigth <= 0) {
                ballHeigth = 1;
                ballUp = true;
            }
        }
        if (ballHeigth > 0 && ballUp) {
            ballHeigth = ballHeigth + 2;
            if (ballHeigth > 100) {
                ballHeigth = 99;
                ballUp = false;
            }
        }

        return  0;
    }

    public TextureRegion getRenderImg() {
        return ball.getRenderImg();
    }

    public TextureRegion getRenderShadowImg() {
        return shadow.getRenderImg();
    }

    public void dispose() {
        ball.dispose();
        //walkSheet.dispose();
    }
}

/*
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
        // 0-0  1-15  2-30  3-40  4-AD  5-GM

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

        paint.setColor(Color.WHITE);
        paint.setAlpha(175);
        canvas.drawRect((xT3-(width/8))*Stage.zoomplusX, (yT3-(height/6))*Stage.zoomplusY, (xT3+(width/6))*Stage.zoomplusX, (yT3+(height/6))*Stage.zoomplusY, paint);
        paint.setColor(Color.LTGRAY);
        paint.setAlpha(175);
        canvas.drawRect((xT2-(width/4))*Stage.zoomplusX, (yT2-(height/4))*Stage.zoomplusY, (xT2+(width/4))*Stage.zoomplusX, (yT2+(height/4))*Stage.zoomplusY, paint);
        paint.setColor(Color.LTGRAY);
        paint.setAlpha(75);
        canvas.drawRect((xT1-(width/2))*Stage.zoomplusX, (yT1-(height/2))*Stage.zoomplusY, (xT1+(width/3))*Stage.zoomplusX, (yT1+(height/3))*Stage.zoomplusY, paint);

        canvas.drawBitmap(image, src_full, getCenteredDrawingBounds(), null);
 */
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////