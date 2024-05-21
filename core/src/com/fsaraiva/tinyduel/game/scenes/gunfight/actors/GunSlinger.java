package com.fsaraiva.tinyduel.game.scenes.gunfight.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.fsaraiva.tinyduel.engine.utils.InputHandler;
import com.fsaraiva.tinyduel.engine.video.SceneObject;
import com.fsaraiva.tinyduel.engine.utils.Animation;
import com.fsaraiva.tinyduel.engine.video.ParticleAnimation;
import com.fsaraiva.tinyduel.game.scenes.gunfight.props.GunBullet;

import java.util.List;
import java.util.Objects;

public class GunSlinger extends SceneObject {
    public enum states {
        HIT,
        RUNNING,
        SHOOTING,
        STOPPED
    }
    // Constant rows and columns of the sprite sheet
    private int frame_cols = 4, frame_rows = 5;

    public int id, score;

    Animation runnerUp;
    Animation runnerDown;
    Animation runnerLeft;
    Animation runnerRight;
    Animation stoppedUp;
    Animation stoppedDown;
    Animation stoppedLeft;
    Animation stoppedRight;
    Animation hitUp;
    Animation hitDown;
    Animation hitLeft;
    Animation hitRight;

    Animation gunnerEmptyMask;

    public long startTimeShoot; //for shoot delay
    public long startTimeHit;
    public boolean useAI;

    public states currentState;

    public Rectangle deltaRect;
    public String direction;
    public int life;
    public int numBullets;
    public List<String> currentInput;

    private long delayShoot = 250;
    private long delayHit = 2000;

    public GunSlinger(String asset, int id, int posX, int posY) {
        float framespeed = 0.125f;
        hitBox = new Rectangle();
        deltaRect = new Rectangle();

        score = 0;
        this.id = id;
        direction = "E";
        hitBox.x = posX;
        hitBox.y = posY;
        hitBox.width = 80;
        hitBox.height = 100;
        deltaRect.x = hitBox.x;
        deltaRect.y = hitBox.y;
        deltaRect.width = hitBox.width;
        deltaRect.height = hitBox.height;

        // animations with 4 frames in 1 row
        runnerUp =      new Animation(asset, frame_cols, frame_rows, 3, 4, 0,4,framespeed);
        runnerDown =    new Animation(asset, frame_cols, frame_rows, 1, 2, 0,4,framespeed);
        runnerLeft =    new Animation(asset, frame_cols, frame_rows, 0, 1, 0,4,framespeed);
        runnerRight =   new Animation(asset, frame_cols, frame_rows, 2, 3, 0,4,framespeed);

        // image with 1 frame in 1 row (first image of each row)
        stoppedUp =     new Animation(asset, frame_cols, frame_rows, 3, 4, 0,1,framespeed);
        stoppedDown =   new Animation(asset, frame_cols, frame_rows, 1, 2, 0,1,framespeed);
        stoppedLeft =   new Animation(asset, frame_cols, frame_rows, 0, 1, 0,1,framespeed);
        stoppedRight =  new Animation(asset, frame_cols, frame_rows, 2, 3, 0,1,framespeed);

        // image, with 4 different images, each with 1 frame, in 1 row
        hitUp =         new Animation(asset, frame_cols, frame_rows, 4, 5,3,4,framespeed);
        hitDown =       new Animation(asset, frame_cols, frame_rows, 4, 5,1,2,framespeed);
        hitRight =      new Animation(asset, frame_cols, frame_rows, 4, 5,2,3,framespeed);
        hitLeft =       new Animation(asset, frame_cols, frame_rows, 4, 5,0,1,framespeed);
        gunnerEmptyMask = new Animation("gunner-empty-mask.png", 1, 1, 0, 1,0,1,framespeed);

        currentState = states.STOPPED;

        useAI = false;
        life = 5;
        numBullets = 10;
    }

    private void makeMovement(InputHandler input, int[][] map) {
        String olddirection = direction;
        direction = "";

        // Set movement by touch (should be on InputHandler, send object pos as arg to check
        if (input.touchEnabled) {
            if (id == 1 && !input.useOnScreenCtrl1) {
                input.checkTouchCoords(hitBox, id);
            }
            if (id == 2 && !input.useOnScreenCtrl2) {
                input.checkTouchCoords(hitBox, id);
            }
        }

        // Set movement by keyboard
        if (currentInput.contains("up")) {
            deltaRect.y = hitBox.y + 400 * Gdx.graphics.getDeltaTime();
            //hitBox.y += 200 * Gdx.graphics.getDeltaTime();
            if (currentState == states.STOPPED) {currentState = states.RUNNING;}
            direction += "N";
        }
        if (currentInput.contains("down")) {
            deltaRect.y = hitBox.y - 400 * Gdx.graphics.getDeltaTime();
            //hitBox.y -= 200 * Gdx.graphics.getDeltaTime();
            if (currentState == states.STOPPED) {currentState = states.RUNNING;}
            direction += "S";
        }
        if (currentInput.contains("left")) {
            deltaRect.x = hitBox.x - 400 * Gdx.graphics.getDeltaTime();
            if (currentState == states.STOPPED) {currentState = states.RUNNING;}
            direction += "W";
        }
        if (currentInput.contains("right")) {
            deltaRect.x = hitBox.x + 400 * Gdx.graphics.getDeltaTime();
            //hitBox.x += 200 * Gdx.graphics.getDeltaTime();
            if (currentState == states.STOPPED) {currentState = states.RUNNING;}
            direction += "E";
        }

        if (direction.equals("")) {
            direction = olddirection;
        }
        if (direction.length() > 2) {
            direction = direction.substring(0, 2);
        }
        // check boundaries
        if (deltaRect.x < 0)
            deltaRect.x = 0;
        if (deltaRect.x >  (map[0].length * 40) - 64)  //1920
            deltaRect.x = (map[0].length * 40) - 64;
        if (deltaRect.y < 0)
            deltaRect.y = 0;
        if (deltaRect.y > (map.length * 40) - 64)  //1080
            deltaRect.y = (map.length * 40) - 64;

        if (!getMapCollision((int)deltaRect.x, (int)hitBox.y, map, 40)) {
            hitBox.x = deltaRect.x;
        } else {
            isActive = false;
        }
        if (!getMapCollision((int)hitBox.x, (int)deltaRect.y, map, 40)) {
            hitBox.y = deltaRect.y;
        } else {
            isActive = false;
        }
    }

    public int update(Array<GunBullet> bullets, InputHandler input, Array<ParticleAnimation> colls, int[][] map) {
        int returnCode = 0;
        if (id == 1) {currentInput = input.keys;}
        if (id == 2) {currentInput = input.keys_2;}

        switch (currentState) {
            case HIT: {
                long elapsed = (System.nanoTime() - startTimeHit) / 1000000;
                if (elapsed > delayHit) currentState = states.STOPPED;  // 500ms
                makeMovement(input, map);
                break;
            }
            case SHOOTING: {
                long elapsed = (System.nanoTime() - startTimeShoot) / 1000000;
                if (elapsed > delayShoot) currentState = states.STOPPED;  // 500ms
                makeMovement(input, map);
                break;
            }
            case STOPPED:
            case RUNNING: {
                //isRunning = false;
                currentState = states.STOPPED;
                makeMovement(input, map);

                if (currentInput.contains("space")) {
                    if (!(currentState == states.SHOOTING) && numBullets > 0) {
                        switch (direction) {
                            case "N": {
                                bullets.add(new GunBullet((int) (hitBox.x + (int) hitBox.width / 2), (int) (hitBox.y + hitBox.height), 0, 50, id));
                                break;
                            }
                            case "S": {
                                bullets.add(new GunBullet((int) (hitBox.x + (int) hitBox.width / 2), (int) hitBox.y - 30, 0, -50, id));
                                break;
                            }
                            case "E": {
                                bullets.add(new GunBullet((int) (hitBox.x + hitBox.width), (int) (hitBox.y + 30), 50, 0, id));
                                break;
                            }
                            case "W": {
                                bullets.add(new GunBullet((int) (hitBox.x - 30), (int) hitBox.y + 30, -50, 0, id));
                                break;
                            }
                            case "NE": {
                                bullets.add(new GunBullet((int) (hitBox.x + hitBox.width), (int) (hitBox.y + hitBox.height), 50, 50, id));
                                break;
                            }
                            case "SW": {
                                bullets.add(new GunBullet((int) (hitBox.x - 30), (int) hitBox.y, -50, -50, id));
                                break;
                            }
                            case "NW": {
                                bullets.add(new GunBullet((int) (hitBox.x - 30), (int) (hitBox.y + hitBox.height), -50, 50, id));
                                break;
                            }
                            case "SE": {
                                bullets.add(new GunBullet((int) (hitBox.x + hitBox.width), (int) (hitBox.y - 30), 50, -50, id));
                                break;
                            }
                        }

                        numBullets--;
                        currentState = states.SHOOTING;
                        startTimeShoot = System.nanoTime();

                        // add entry in eventHandler
                        returnCode = 1;
                        //dropSound.play((float)game.soundLvl / 10.0f);
                        //colls.add(new ParticleAnimation("gunfire_anim.png", (int) (id == 1 ? hitBox.x + 80 : hitBox.x), (int) hitBox.y + 30, 0.060f));
                    }
                }
                break;
            }
        }
        return returnCode;
    }

    public TextureRegion getRenderImg() {
        switch (currentState) {
            case SHOOTING: {
                if (Objects.equals(direction, "N")) { return hitUp.getRenderImg(); }
                if (Objects.equals(direction, "S")) { return hitDown.getRenderImg(); }
                if (Objects.equals(direction, "NW") || Objects.equals(direction, "W") || Objects.equals(direction, "SW")) { return hitRight.getRenderImg();}
                if (Objects.equals(direction, "NE") || Objects.equals(direction, "E") || Objects.equals(direction, "SE")) { return hitLeft.getRenderImg();}
                break;
            }
            case RUNNING: {
                if (Objects.equals(direction, "N")) { return runnerUp.getRenderImg(); }
                if (Objects.equals(direction, "S")) { return runnerDown.getRenderImg(); }
                if (Objects.equals(direction, "NW") || Objects.equals(direction, "W") || Objects.equals(direction, "SW")) { return runnerRight.getRenderImg(); }
                if (Objects.equals(direction, "NE") || Objects.equals(direction, "E") || Objects.equals(direction, "SE")) { return runnerLeft.getRenderImg(); }
                break;
            }
            case STOPPED: {
                if (Objects.equals(direction, "N")) { return stoppedUp.getRenderImg(); }
                if (Objects.equals(direction, "S")) { return stoppedDown.getRenderImg(); }
                if (Objects.equals(direction, "NW") || Objects.equals(direction, "W") || Objects.equals(direction, "SW")) { return stoppedRight.getRenderImg(); }
                if (Objects.equals(direction, "NE") || Objects.equals(direction, "E") || Objects.equals(direction, "SE")) { return stoppedLeft.getRenderImg(); }
                break;
            }
            case HIT: {
                long elapsed2 = (System.nanoTime() - startTimeHit) / 1000000;
                if (Objects.equals(direction, "N")) {
                    if (elapsed2 % 2 == 0) { return runnerUp.getRenderImg(); } else { return gunnerEmptyMask.getRenderImg(); }
                }
                if (Objects.equals(direction, "S")) {
                    if (elapsed2 % 2 == 0) { return runnerDown.getRenderImg(); } else { return gunnerEmptyMask.getRenderImg(); }
                }
                if (Objects.equals(direction, "NW") || Objects.equals(direction, "W") || Objects.equals(direction, "SW")) {
                    if (elapsed2 % 2 == 0) { return runnerRight.getRenderImg(); } else { return gunnerEmptyMask.getRenderImg(); }
                }
                if (Objects.equals(direction, "NE") || Objects.equals(direction, "E") || Objects.equals(direction, "SE")) {
                    if (elapsed2 % 2 == 0) { return runnerLeft.getRenderImg(); } else { return gunnerEmptyMask.getRenderImg(); }
                }
                break;
            }
        }
        // default value
        return stoppedRight.getRenderImg();
    }

    public void dispose() {
        runnerUp.dispose();
        runnerDown.dispose();
        runnerLeft.dispose();
        runnerRight.dispose();

        hitUp.dispose();
        hitDown.dispose();
        hitLeft.dispose();
        hitRight.dispose();

        stoppedUp.dispose();
        stoppedDown.dispose();
        stoppedRight.dispose();
        stoppedLeft.dispose();
    }
}