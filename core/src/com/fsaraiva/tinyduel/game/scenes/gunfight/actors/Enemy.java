package com.fsaraiva.tinyduel.game.scenes.gunfight.actors;

import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.audio.Sound;
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
import java.util.Random;

public class Enemy extends SceneObject {
    // Constant rows and columns of the sprite sheet
    private int frame_cols = 4, frame_rows = 5;

    public int id;

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

    //public Rectangle rect;
    public long startTimeShoot; //for shoot delay
    public long startTimeHit;
    public boolean useAI;

    // the following should be states
    public boolean isShooting;
    public boolean isHit;
    public boolean isRunning;
    public Rectangle deltaRect;
    public String direction;
    public int life;
    public int numBullets;
    public List<String> currentInput;

    private long delayShoot = 250;
    private long delayHit = 2000;

    public Enemy(String asset, int id) {
        float framespeed = 0.125f;
        this.id = id;
        direction = "E";

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

        // player state
        isShooting = false;
        isHit = false;
        isRunning = false;

        useAI = true;
        hitBox = new Rectangle();
        deltaRect = new Rectangle();

        hitBox.width = 80;
        hitBox.height = 100;
        deltaRect.x = hitBox.x;
        deltaRect.y = hitBox.y;
        deltaRect.width = 80;
        deltaRect.height = 100;

        life = 1;
        numBullets = 10;
    }

    public int update(Array<GunBullet> bullets, InputHandler input, Array<ParticleAnimation> colls, int[][] map, int targetX, int targetY) {
        int returnCode = 0;

        if (isShooting) {
            long elapsed = (System.nanoTime() - startTimeShoot) / 1000000;
            if (elapsed > delayShoot) isShooting = false;  // 500ms
        }
        if (isHit) {
            long elapsed = (System.nanoTime() - startTimeHit) / 1000000;
            if (elapsed > delayHit) isHit = false;  // 500ms
        }

        // bot behavior
        if (!isHit) {
            this.makeAImove(bullets, targetX, targetY, returnCode);
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
        return returnCode;
    }

    private void makeAImove(Array<GunBullet> bullets, int targetX, int targetY, int returnCode) {
        String olddirection = direction;
        direction = "";

        int move = new Random().nextInt(10) + 1;
        // follow target
        if (move > 4) {
            if (hitBox.y > targetY + 5) {
                direction += "S";
                deltaRect.y = hitBox.y - 200 * Gdx.graphics.getDeltaTime();
            }
            if (hitBox.y < targetY - 5) {
                direction += "N";
                deltaRect.y = hitBox.y + 200 * Gdx.graphics.getDeltaTime();
            }
            if (hitBox.x > targetX + 5) {
                direction += "W";
                deltaRect.x = hitBox.x - 200 * Gdx.graphics.getDeltaTime();
            }
            if (hitBox.x < targetX - 5) {
                direction += "E";
                deltaRect.x = hitBox.x + 200 * Gdx.graphics.getDeltaTime();
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

        isRunning = true;

        if (!isShooting && numBullets > 0) {
            int shoot = new Random().nextInt(50) + 1;
            if (shoot < 2) {
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
                isShooting = true;
                isRunning = false;
                startTimeShoot = System.nanoTime();

                returnCode = 1;
                //colls.add(new ParticleAnimation("gunfire_anim.png", (int) (id == 1 ? hitBox.x + 80 : hitBox.x), (int) hitBox.y + 30, 0.060f));

                //Stage.fightGameScene.particles_gun_shot.setFrame(0);
                //if (!Stage.gameOptions_kidModeOn) {numBullets--;}
                //if (Stage.gameOptions_soundOn) {Sample.playSound(4);}
            }
        }
    }

    public TextureRegion getRenderImg() {
        //if (id == 1) {
        if (Objects.equals(direction, "N")) {
            if (isRunning) {
                return runnerUp.getRenderImg();
            }
            if (isHit) {
                return hitUp.getRenderImg();
            }
            if (isShooting) {
                return stoppedUp.getRenderImg();
            }
            return stoppedUp.getRenderImg();

        }
        if (Objects.equals(direction, "S")) {
            if (isRunning) {
                return runnerDown.getRenderImg();
            }
            if (isHit) {
                return hitDown.getRenderImg();
            }
            if (isShooting) {
                return stoppedDown.getRenderImg();
            }
            return stoppedDown.getRenderImg();
        }
        if (Objects.equals(direction, "NW") || Objects.equals(direction, "W") || Objects.equals(direction, "SW")) {
            if (isRunning) {
                return runnerRight.getRenderImg();
            }
            if (isHit) {
                return hitRight.getRenderImg();
            }
            if (isShooting) {
                return stoppedRight.getRenderImg();
            }
            return stoppedRight.getRenderImg();
        }
        if (Objects.equals(direction, "NE") || Objects.equals(direction, "E") || Objects.equals(direction, "SE")) {
            if (isRunning) {
                return runnerLeft.getRenderImg();
            }
            if (isHit) {
                return hitLeft.getRenderImg();
            }
            if (isShooting) {
                return stoppedLeft.getRenderImg();
            }
            return stoppedLeft.getRenderImg();
        }
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
