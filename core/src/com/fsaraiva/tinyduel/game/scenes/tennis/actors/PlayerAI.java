package com.fsaraiva.tinyduel.game.scenes.tennis.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.fsaraiva.tinyduel.engine.utils.InputHandler;
import com.fsaraiva.tinyduel.engine.video.SceneObject;
import com.fsaraiva.tinyduel.engine.utils.Animation;
import com.fsaraiva.tinyduel.engine.video.ParticleAnimation;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class PlayerAI extends SceneObject {
    // Constant rows and columns of the sprite sheet
    public enum states {
        SERVING,
        RUNNING,
        SHOOTING,
        STOPPED,
    }

    private int frame_cols = 4, frame_rows = 8;

    public int id, score;

    Animation runnerUp;
    Animation runnerDown;
    Animation runnerLeft;
    Animation runnerRight;
    Animation stoppedUp;
    Animation stoppedDown;
    Animation stoppedLeft;
    Animation stoppedRight;
    Animation shootingUp;
    Animation shootingDown;
    Animation shootingLeft;
    Animation shootingRight;

    public long startTimeShoot; //for shoot delay
    public long startTimeHit;
    public boolean useAI;

    public Player.states currentState;

    public Rectangle deltaRect;
    public String direction;
    public int life;
    public int numBullets;
    public List<String> currentInput;

    private long delayShoot = 500;

    public PlayerAI(String asset, int id, int posX, int posY) {
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
        stoppedRight =  new Animation(asset, frame_cols, frame_rows, 2, 3, 1,2,framespeed);

        // image, with 4 different images, each with 1 frame, in 1 row
        shootingUp =    new Animation(asset, frame_cols, frame_rows, 7, 8,0,4,framespeed);
        shootingDown =  new Animation(asset, frame_cols, frame_rows, 5, 6,0,4,framespeed);
        shootingLeft = new Animation(asset, frame_cols, frame_rows, 4, 5,0,4,framespeed);
        shootingRight =  new Animation(asset, frame_cols, frame_rows, 6, 7,0,4,framespeed);

        currentState = Player.states.STOPPED;

        useAI = false;
        life = 15;
        numBullets = 10;
    }

    private void makeMovement(InputHandler input, int[][] map) {
        //currentState = states.STOPPED;
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

        // Set movement by game logic
        if (currentInput.contains("up")) {
            deltaRect.y = hitBox.y + 400 * Gdx.graphics.getDeltaTime();
            //hitBox.y += 200 * Gdx.graphics.getDeltaTime();
            currentState = Player.states.RUNNING;
            direction += "N";
        }
        if (currentInput.contains("down")) {
            deltaRect.y = hitBox.y - 400 * Gdx.graphics.getDeltaTime();
            //hitBox.y -= 200 * Gdx.graphics.getDeltaTime();
            currentState = Player.states.RUNNING;
            direction += "S";
        }
        if (currentInput.contains("left")) {
            deltaRect.x = hitBox.x - 400 * Gdx.graphics.getDeltaTime();
            currentState = Player.states.RUNNING;
            direction += "W";
        }
        if (currentInput.contains("right")) {
            deltaRect.x = hitBox.x + 400 * Gdx.graphics.getDeltaTime();
            //rect.x += 200 * Gdx.graphics.getDeltaTime();
            currentState = Player.states.RUNNING;
            direction += "E";
        }

        if (direction.equals("")) {
            direction = olddirection;
        }

        // check boundaries
        if (deltaRect.x < 0)
            deltaRect.x = 0;
        if (deltaRect.x > (map[0].length * 40) - 64)  //1920
            deltaRect.x = (map[0].length * 40) - 64;
        if (deltaRect.y < 0)
            deltaRect.y = 0;
        if (deltaRect.y > (map.length * 40) - 64)  //1080
            deltaRect.y = (map.length * 40) - 64;

//        if (!getMapCollision((int)deltaRect.x, (int)hitBox.y, map, 40)) {
        hitBox.x = deltaRect.x;
//        } else {isActive = false;}
//        if (!getMapCollision((int)hitBox.x, (int)deltaRect.y, map, 40)) {
        hitBox.y = deltaRect.y;
//        } else {isActive = false;}

    }

    public int update(Array<ParticleAnimation> colls, int[][] map, int ballX, int ballY) {
        switch (currentState) {
            case SERVING: {
                makeAImove(map, ballX, ballY);

                if (deltaRect.x < (map[0].length * 40) - 75)
                    deltaRect.x = (map[0].length * 40) - 75;
                if (deltaRect.x > (map[0].length * 40) - 85)  //1920
                    deltaRect.x = (map[0].length * 40) - 85;
                if (deltaRect.y < 100)
                    deltaRect.y = 100;
                if (deltaRect.y > (map.length * 40) - 264)  //1080
                    deltaRect.y = (map.length * 40) - 264;

                hitBox.x = deltaRect.x;
                hitBox.y = deltaRect.y;

                int dir = new Random().nextInt(10) + 1;
                if (dir < 5) {
                    if (!(currentState == Player.states.SHOOTING)) {
                        currentState = Player.states.SHOOTING;
                        startTimeShoot = System.nanoTime();
                    }
                }

                if (!(currentState == Player.states.SHOOTING)) {
                    currentState = Player.states.SERVING;
                }

                break;
            }
            case SHOOTING: {
                long elapsed = (System.nanoTime() - startTimeShoot) / 1000000;
                if (elapsed > delayShoot) {
                    currentState = Player.states.STOPPED;
                }  // 500ms

                //makeAImove(map, ballX, ballY);

                break;
            }
            case STOPPED:
            case RUNNING: {
                currentState = Player.states.STOPPED;
                makeAImove(map, ballX, ballY);

                if (hitBox.overlaps(new Rectangle(ballX, ballY, 40 ,40))) {
                    if (!(currentState == Player.states.SHOOTING)) {
                        currentState = Player.states.SHOOTING;
                        startTimeShoot = System.nanoTime();
                    }
                }
                break;
            }
        }
        return  0;
    }

    public TextureRegion getRenderImg() {
        //if (id == 1) {
        switch (currentState) {
            case SHOOTING: {
                if (Objects.equals(direction, "N")) { return shootingUp.getRenderImg(); }
                if (Objects.equals(direction, "S")) { return shootingDown.getRenderImg(); }
                if (Objects.equals(direction, "NW") || Objects.equals(direction, "W") || Objects.equals(direction, "SW")) { return shootingRight.getRenderImg();}
                if (Objects.equals(direction, "NE") || Objects.equals(direction, "E") || Objects.equals(direction, "SE")) { return shootingLeft.getRenderImg();}
                break;
            }
            case RUNNING: {
                if (Objects.equals(direction, "N")) { return runnerUp.getRenderImg(); }
                if (Objects.equals(direction, "S")) { return runnerDown.getRenderImg(); }
                if (Objects.equals(direction, "NW") || Objects.equals(direction, "W") || Objects.equals(direction, "SW")) { return runnerRight.getRenderImg(); }
                if (Objects.equals(direction, "NE") || Objects.equals(direction, "E") || Objects.equals(direction, "SE")) { return runnerLeft.getRenderImg(); }
                break;
            }
            case SERVING:
            case STOPPED: {
                if (Objects.equals(direction, "N")) { return stoppedUp.getRenderImg(); }
                if (Objects.equals(direction, "S")) { return stoppedDown.getRenderImg(); }
                if (Objects.equals(direction, "NW") || Objects.equals(direction, "W") || Objects.equals(direction, "SW")) { return stoppedRight.getRenderImg(); }
                if (Objects.equals(direction, "NE") || Objects.equals(direction, "E") || Objects.equals(direction, "SE")) { return stoppedLeft.getRenderImg(); }
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

        shootingUp.dispose();
        shootingDown.dispose();
        shootingLeft.dispose();
        shootingRight.dispose();

        stoppedUp.dispose();
        stoppedDown.dispose();
        stoppedRight.dispose();
        stoppedLeft.dispose();
    }

    private void makeAImove(int[][] map, int targetX, int targetY) {
        String olddirection = direction;
        direction = "";

        int move = new Random().nextInt(10) + 1;
        // follow target
        if (move > 4) {
            if (hitBox.y > targetY + 5) {
                direction += "S";
                deltaRect.y = hitBox.y - 500 * Gdx.graphics.getDeltaTime();
            }
            if (hitBox.y < targetY - 5) {
                direction += "N";
                deltaRect.y = hitBox.y + 500 * Gdx.graphics.getDeltaTime();
            }
            if (hitBox.x > targetX + 5) {
                direction += "W";
                deltaRect.x = hitBox.x - 500 * Gdx.graphics.getDeltaTime();
            }
            if (hitBox.x < targetX - 5) {
                direction += "E";
                deltaRect.x = hitBox.x + 500 * Gdx.graphics.getDeltaTime();
            }
        }
        // random move
        if (move < 2) {
            int dir = new Random().nextInt(10) + 1;
            if (dir < 3) {
                direction += "S";
                deltaRect.y = hitBox.y - 200 * Gdx.graphics.getDeltaTime();
                //hitBox.y -= 200 * Gdx.graphics.getDeltaTime(); //ya=15;
            } else if (dir > 7) {
                direction += "N";
                deltaRect.y = hitBox.y + 200 * Gdx.graphics.getDeltaTime();
                //hitBox.y += 200 * Gdx.graphics.getDeltaTime();
            }

            dir = new Random().nextInt(10) + 1;
            if (dir < 5) {
                //x -= 10; //ya=15;
                direction += "E";
                deltaRect.x = hitBox.x - 200 * Gdx.graphics.getDeltaTime();
                //hitBox.x -= 200 * Gdx.graphics.getDeltaTime();;
            } else if (dir > 8) {
                direction += "W";
                deltaRect.x = hitBox.x + 200 * Gdx.graphics.getDeltaTime();
                //hitBox.x += 200 * Gdx.graphics.getDeltaTime();;
            }
        }

        if (direction.equals("")) {
            direction = olddirection;
        }

        // check boundaries
        if (deltaRect.x < 1000)
            deltaRect.x = 1000;
        if (deltaRect.x > (map[0].length * 40) - 64)  //1920
            deltaRect.x = (map[0].length * 40) - 64;
        if (deltaRect.y < 0)
            deltaRect.y = 0;
        if (deltaRect.y > (map.length * 40) - 64)  //1080
            deltaRect.y = (map.length * 40) - 64;

//        if (!getMapCollision((int)deltaRect.x, (int)hitBox.y, map, 40)) {
        hitBox.x = deltaRect.x;
//        } else {isActive = false;}
//        if (!getMapCollision((int)hitBox.x, (int)deltaRect.y, map, 40)) {
        hitBox.y = deltaRect.y;
//        } else {isActive = false;}
    }

}
