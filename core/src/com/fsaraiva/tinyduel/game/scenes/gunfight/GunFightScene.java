package com.fsaraiva.tinyduel.game.scenes.gunfight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.fsaraiva.tinyduel.TinyDuel;
import com.fsaraiva.tinyduel.engine.ui.Button;
import com.fsaraiva.tinyduel.engine.ui.OptionsList;
import com.fsaraiva.tinyduel.engine.ui.UIObject;
import com.fsaraiva.tinyduel.engine.video.Map;
import com.fsaraiva.tinyduel.engine.video.SceneObject;
import com.fsaraiva.tinyduel.engine.video.SceneObjectSorter;
import com.fsaraiva.tinyduel.engine.video.BackgroundLayer;
import com.fsaraiva.tinyduel.engine.video.FloatingMessage;
import com.fsaraiva.tinyduel.engine.video.BackgroundParallax;
import com.fsaraiva.tinyduel.engine.video.Particle;
import com.fsaraiva.tinyduel.engine.video.ParticleAnimation;
import com.fsaraiva.tinyduel.engine.video.Sprite;
import com.fsaraiva.tinyduel.game.menus.mainmenu.MainMenuScene;
import com.fsaraiva.tinyduel.game.scenes.gunfight.actors.Enemy;
import com.fsaraiva.tinyduel.game.scenes.gunfight.actors.GunSlinger;
import com.fsaraiva.tinyduel.game.scenes.gunfight.props.GunBullet;
import com.fsaraiva.tinyduel.game.scenes.gunfight.props.GunCactus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GunFightScene implements Screen {
    final TinyDuel game;

    public enum states {
        PLAYING,
        PAUSED,
        GAMEOVER
    }

    Map gunFightTown;
    BackgroundLayer gunFightTown2;
    BackgroundParallax parallax;
    BackgroundLayer blueWin;
    BackgroundLayer redWin;
    BackgroundLayer pausedbg;

    Sound dropSound, hitSound, shootSound;
    Music rainMusic;

    OrthographicCamera camera;
    GunSlinger gunslinger_blue;
    GunSlinger gunslinger_red;
    Sprite shadow;

    Button pauseButton;

    OptionsList options;
    OptionsList optionsEnd;

    int numPlayers;

    public long gameTimer;
    public int gameElapsedTime;

    public states currentState;

    Rectangle viewport;

    Array<GunCactus> cactusList;
    Array<GunBullet> bullets;
    Array<ParticleAnimation> collisions;
    Array<Particle> particles;
    Array<FloatingMessage> messages;
    Array<Enemy> gunslingers;
    ArrayList<SceneObject> objectsToDraw;

    public BitmapFont font24;
    public BitmapFont font24stripped;
    HUD hud;

    public GunFightScene(final TinyDuel gam, int numpl) { // initScene
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080); //1280, 720);  //800x480
        game = gam;
        numPlayers = numpl;
        viewport = new Rectangle(0,0,1920,1080);

        // scene specific fonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("bitmapfont/RioGrande.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.borderWidth = 1;
        parameter.color = Color.WHITE;
        //parameter.shadowOffsetX = 3;
        //parameter.shadowOffsetY = 3;
        //parameter.shadowColor = new Color(0, 0.5f, 0, 0.75f);
        font24 = generator.generateFont(parameter); // font size 24 pixels
        generator.dispose();

        FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("bitmapfont/RioGrandeStripped.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2.size = 30;
        parameter2.borderWidth = 1;
        parameter2.color = Color.WHITE;
        parameter.shadowOffsetX = 2;
        parameter.shadowOffsetY = 2;
        parameter.shadowColor = new Color(Color.LIGHT_GRAY); //0, 0.5f, 0, 0.75f);
        font24stripped = generator2.generateFont(parameter2); // font size 24 pixels
        generator2.dispose();

        shadow = new Sprite("ball_shadow.png");

        gunslinger_blue = new GunSlinger("gunner_blue_move-af.png",1, 800, 200);

        if (numPlayers == 2) {
            gunslinger_red = new GunSlinger("gunner_red_move.png", 2, new Random().nextInt(100) + 1500, 400);
            gunslinger_red.useAI = false;
        }

        gunslingers = new Array<Enemy>();
        gunslingers.add(
//                new Enemy("gunner_green_move.png",4),
//                new Enemy("gunner_green_move.png",5)
//                new Enemy("gunner_green_move.png",6),
                new Enemy("gunner_green_move.png",7)
        );
        /*gunslingers.add(
                new Enemy("gunner_green_move.png",8),
                new Enemy("gunner_green_move.png",9),
                new Enemy("gunner_green_move.png",10),
                new Enemy("gunner_green_move.png",11)
        );*/
        for (Enemy gunslinger : gunslingers) {
            gunslinger.useAI = true;
            gunslinger.isHit = false;
            gunslinger.numBullets = 5;

            // gunslinger.hitBox.x = new Random().nextInt(1000) + 100;
            // gunslinger.hitBox.y = new Random().nextInt(800) + 100;
            switch(gunslinger.id) {
                case 4: {
                    gunslinger.hitBox.x = 310;
                    gunslinger.hitBox.y = 780;
                    break;
                }
                case 5: {
                    gunslinger.hitBox.x = 260;
                    gunslinger.hitBox.y = 160;
                    break;
                }
                case 6: {
                    gunslinger.hitBox.x = 1660;
                    gunslinger.hitBox.y = 780;
                    break;
                }
                case 7: {
                    gunslinger.hitBox.x = 1740;
                    gunslinger.hitBox.y = 138;
                    break;
                }
            }
        }

        collisions = new Array<ParticleAnimation>();
        particles = new Array<Particle>();
        messages = new Array<FloatingMessage>();
        bullets = new Array<GunBullet>();
        objectsToDraw = new ArrayList<SceneObject>();
        //this.enemies = [];

        cactusList = new Array<GunCactus>();
        cactusList.add(
                new GunCactus("cactus_full.png",370,430,100,139),
                new GunCactus("cactus_full.png",1015,90,100,139),
                new GunCactus("cactus_full.png",270,150,100,139),
                new GunCactus("cactus_full.png",835,290,100,139)
        );
        cactusList.add(
                new GunCactus("cactus_full.png",1370,430,100,139),
                new GunCactus("cactus_full.png",915,690,100,139),
                new GunCactus("cactus_full.png",1270,850,100,139),
                new GunCactus("cactus_full.png",1635,190,100,139)
        );
        cactusList.add(
                new GunCactus("cactus_full.png",2370,900,100,139),
                new GunCactus("cactus_full.png",1915,850,100,139),
                new GunCactus("cactus_full.png",2270,700,100,139),
                new GunCactus("cactus_full.png",635,970,100,139)
        );

        options = new OptionsList();
        options.AddOption(new Button("controller/exit.png", "controller/exit_active.png", 600, 400, 128, 128,1));
        options.AddOption(new Button("controller/pauseSmall.png", "controller/pauseSmall_active.png", 600, 600, 128, 128,2));

        optionsEnd = new OptionsList();
        optionsEnd.AddOption(new Button("controller/exit.png", "controller/exit_active.png", 600, 400, 128, 128,1));

        //backButton = new Button("left_button.png", "left_button_active.png", 1800, 50, 32, 32);
        //pauseButton = new Button("pause_button.png", "pause_button_active.png", 960, 50, 32, 32,0);
        pauseButton = new Button("controller/pause.png", "pause_button_active.png", 910, 20, 100, 100,0);

        /*if (numpl == 2) {
            gunFightTown = new Background("fullmap_gunfight.png", 0);
        }
        else { */
            gunFightTown = new Map("fullmap_gunfight2.png", 1);
            parallax = new BackgroundParallax("", 0);
            gunFightTown2 = new BackgroundLayer("fullmap_gunfight3.png", 1);
        //}
        blueWin = new BackgroundLayer("blue_win.png",0);
        redWin = new BackgroundLayer("red_win.png",0);
        pausedbg = new BackgroundLayer("pausedbg.png",0);

        Gdx.input.setCatchKey(Input.Keys.BACK, true);
        //Gdx.input.setCatchKey(Input.Keys.MENU, true);

        hud = new HUD();

        // load the drop sound effect and the rain background "music"
        hitSound = Gdx.audio.newSound(Gdx.files.internal("sound/drop.wav"));
        dropSound =  Gdx.audio.newSound(Gdx.files.internal("sound/xland.mp3"));
        shootSound = Gdx.audio.newSound(Gdx.files.internal("sound/boom.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/mBoss.mp3")); //mp3
        //rainMusic.setVolume(0.1f);
        rainMusic.setVolume(((float)game.musicLvl / 10));
        rainMusic.setLooping(true);

        gameTimer = System.nanoTime();
        gameElapsedTime = 0;

        currentState = states.PLAYING;
    }

    public void update() {
        for (GunBullet bull : bullets) {
            bull.update(gunFightTown.activeMap, gunFightTown.tileSize);
            if (!bull.isActive) {

                // just get proper hitbox values from collision
                int bullX = (int)bull.hitBox.x;
                int bullY = (int)bull.hitBox.y;
                if (bull.x_step < 0) {
                    bullX -= 100;
                }
                if (bull.y_step < 0) {
                    bullY -= 90;
                }
                this.collisions.add(new ParticleAnimation("boom.png", bullX, (int) bullY, 0.125f));
                dropSound.play((float)game.soundLvl / 10.0f);
            }

            if (bull.isActive) {
                if (bull.hitBox.overlaps(gunslinger_blue.hitBox)) {
                    if (!(gunslinger_blue.currentState == GunSlinger.states.HIT)) {
                        gunslinger_blue.currentState = GunSlinger.states.HIT;
                        gunslinger_blue.life--;
                        if (bull.origin == 2 && numPlayers == 2) {
                            gunslinger_red.score++;
                        }
                        gunslinger_blue.startTimeHit = System.nanoTime();
                        this.messages.add(new FloatingMessage("-1", (int) (gunslinger_blue.hitBox.x + (gunslinger_blue.hitBox.width / 2)), (int) (gunslinger_blue.hitBox.y + (gunslinger_blue.hitBox.height)),
                                (int) (gunslinger_blue.hitBox.x + (gunslinger_blue.hitBox.width / 2)), (int) (gunslinger_blue.hitBox.y + (gunslinger_blue.hitBox.height)) + 100));
                        hitSound.play((float)game.soundLvl / 10.0f);
                    }
                    bull.isActive = false;
                    this.collisions.add(new ParticleAnimation("particles_gun_hit.png", (int) gunslinger_blue.hitBox.x, (int) gunslinger_blue.hitBox.y, 0.125f));
                }
                if (numPlayers == 2) {
                    if (bull.hitBox.overlaps(gunslinger_red.hitBox)) {
                        if (!(gunslinger_red.currentState == GunSlinger.states.HIT)) {
                            gunslinger_red.currentState = GunSlinger.states.HIT;
                            gunslinger_red.life--;
                            if (bull.origin == 1) {
                                gunslinger_blue.score++;
                            }
                            gunslinger_red.startTimeHit = System.nanoTime();
                            this.messages.add(new FloatingMessage("-1", (int) (gunslinger_red.hitBox.x + (gunslinger_red.hitBox.width / 2)), (int) (gunslinger_red.hitBox.y + (gunslinger_red.hitBox.height)),
                                    (int) (gunslinger_red.hitBox.x + (gunslinger_red.hitBox.width / 2)), (int) (gunslinger_red.hitBox.y + (gunslinger_red.hitBox.height)) + 100));
                        }
                        hitSound.play((float)game.soundLvl / 10.0f);
                        bull.isActive = false;
                        this.collisions.add(new ParticleAnimation("particles_gun_hit.png", (int) gunslinger_red.hitBox.x, (int) gunslinger_red.hitBox.y, 0.125f));
                    }
                }
                for (Enemy gunner : gunslingers) {
                    if (bull.hitBox.overlaps(gunner.hitBox)) {
                        hitSound.play((float)game.soundLvl / 10.0f);
                        gunner.isHit = true;
                        gunner.isRunning = false;
                        gunner.isShooting = false;
                        gunner.life--;
                        if (bull.origin == 2 && numPlayers == 2) {
                            gunslinger_red.score++;
                        }
                        if (bull.origin == 1) {
                            gunslinger_blue.score++;
                        }
                        gunner.startTimeHit = System.nanoTime();
                        bull.isActive = false;
                        this.messages.add(new FloatingMessage("-1",
                                (int) (gunner.hitBox.x + (gunner.hitBox.width / 2)),
                                (int) (gunner.hitBox.y + gunner.hitBox.height),
                                (int) (gunner.hitBox.x + (gunner.hitBox.width / 2)),
                                (int) (gunner.hitBox.y + gunner.hitBox.height + 100)));
                        this.collisions.add(new ParticleAnimation("particles_gun_hit.png", (int) gunner.hitBox.x, (int) gunner.hitBox.y, 0.125f));
                        //this.collisions.add(new ParticleAnimation("boom.png", (int) gunner.hitBox.x, (int) gunner.hitBox.y, 0.125f));

                        if (gunner.life == 0) {
                            switch(gunner.id) {
                                case 4: {
                                    gunner.hitBox.x = 310;
                                    gunner.hitBox.y = 780;
                                    break;
                                }
                                case 5: {
                                    gunner.hitBox.x = 260;
                                    gunner.hitBox.y = 160;
                                    break;
                                }
                                case 6: {
                                    gunner.hitBox.x = 1660;
                                    gunner.hitBox.y = 780;
                                    break;
                                }
                                case 7: {
                                    gunner.hitBox.x = 1740;
                                    gunner.hitBox.y = 138;
                                    break;
                                }
                            }
                            gunner.deltaRect.x = gunner.hitBox.x;
                            gunner.deltaRect.y = gunner.hitBox.y;
                            gunner.life = 1;
                            gunner.numBullets = 5;
                            gunner.isHit = false;
                        }
                    }
                }

                for (GunCactus cactus : cactusList) {
                    if (bull.hitBox.overlaps(cactus.hitBox) && cactus.life > 1) {
                        cactus.life--;
                        //if (cactus.life < 0) cactus.life = 0;
                        bull.isActive = false;
                        this.collisions.add(new ParticleAnimation("cactus_anim.png", (int) cactus.hitBox.x + 10, (int) cactus.hitBox.y + 10, 0.125f));
                    }
                }
            }

            if (!bull.isActive) {
                bullets.removeValue(bull, false);
            }
        }

        // shouldn't pass sounds or collisions as args, should implement some sort of eventListener
        // currently we use the return of update method as id of action to perform
        if (gunslinger_blue.update(this.bullets, game.input, this.collisions, gunFightTown.activeMap) == 1) {
            shootSound.play((float)game.soundLvl / 10.0f);
        }

        if (numPlayers == 2) {
            if (gunslinger_red.update(this.bullets, game.input, this.collisions, gunFightTown.activeMap) == 1) {
                shootSound.play((float) game.soundLvl / 10.0f);
            }
        }

        for (Enemy gunner : gunslingers) {
            if (gunner.update(this.bullets, game.input, this.collisions, gunFightTown.activeMap, (int)gunslinger_blue.hitBox.x, (int)gunslinger_blue.hitBox.y) == 1) {
                shootSound.play((float) game.soundLvl / 10.0f);
            }
        }

        for (ParticleAnimation coll : collisions) {
            coll.update();
            if (coll.markedForDeletion) {
                collisions.removeValue(coll, false);
            }
        }
        for (Particle part : particles) {
            part.update();
            if (part.markedForDeletion) {
                particles.removeValue(part, false);
            }
        }
        for (FloatingMessage msg : messages) {
            msg.update();
            if (msg.markedForDeletion) {
                messages.removeValue(msg, false);
            }
        }

        //if (gunslinger_blue.life <= 0) {
        //    game.setScreen(new OptionsScene(game));
        //    dispose();
        //}
        if (numPlayers == 2) {
            if (gunslinger_blue.numBullets <= 0 && gunslinger_red.numBullets <= 0) {
                gunslinger_blue.numBullets = 10;
                gunslinger_red.numBullets = 10;
            }
        } else {
            if (gunslinger_blue.numBullets <= 0) {
                gunslinger_blue.numBullets = 10;
            }
        }

        long elapsedTmp = (System.nanoTime() - this.gameTimer) / 1000000;
        if (elapsedTmp > 1000) {
            this.gameTimer = System.nanoTime();
            gameElapsedTime++;
        }
        // update viewport (track blue player)
        this.viewport.x = gunslinger_blue.hitBox.x - (viewport.width / 2);
        this.viewport.y = gunslinger_blue.hitBox.y - (viewport.height / 2);
        if (this.viewport.x < 0)
            this.viewport.x = 0;
        if (this.viewport.x + this.viewport.width > (gunFightTown.activeMap[0].length * gunFightTown.tileSize))
            this.viewport.x = (gunFightTown.activeMap[0].length * gunFightTown.tileSize) - viewport.width;
        if (this.viewport.y < 0)
            this.viewport.y = 0;
        if (this.viewport.y + this.viewport.height > (gunFightTown.activeMap.length * gunFightTown.tileSize))
            this.viewport.y = (gunFightTown.activeMap.length * gunFightTown.tileSize) - viewport.height;

        // END GAME CONDITIONS
        if ((numPlayers == 2 && gunslinger_red.life == 0) || gunslinger_blue.life == 0) {
            currentState = states.GAMEOVER;
            game.input.clearInput();
        }
    }

    @Override
    public void render(float delta) {
        // Clear the screen. The arguments to clear are the red, green
        // blue and alpha component in the range [0,1] of the color to be used to clear the screen.
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update(); // tell the camera to update its matrices.

        game.batch.setProjectionMatrix(camera.combined); // tell the SpriteBatch to render in the coordinate system specified by the camera.
        game.batch.begin(); // begin a new batch and draw the gunslinger_blue and all drops

        // draw background
        game.batch.draw(gunFightTown.getRenderImg((int)viewport.x, (int)viewport.y, (int)viewport.width, (int)viewport.height), 0, 0, 1920, 1080);
        //game.batch.draw(gunFightTown.getRenderImg(), 0, 0, (int)viewport.getX(), (int)viewport.getY(), (int)viewport.getWidth(), (int)viewport.getHeight());

        // draw tile map positions
        for (int i = 0; i < gunFightTown.activeMap.length; i++) {
            for (int j = 0; j < gunFightTown.activeMap[0].length; j++) {
                if (gunFightTown.getSpriteImg(j, i) != null) {
                    //game.batch.draw(gunFightTown.getSpriteImg(j, i), j * 40, ((gunFightTown.map.length - i) * 40) - 40);
                    game.batch.draw(gunFightTown.getSpriteImg(j, i), gunFightTown.getScreenCoords(j, i, this.viewport).x, gunFightTown.getScreenCoords(j, i, this.viewport).y);

                }
            }
        }

        // draw parallax
        for (int i = 0; i < parallax.layers.size; i++) {
            //game.batch.draw(parallax.getRenderImgLayer((int)viewport.x, (int)viewport.y, (int)viewport.width, (int)viewport.height, i), 0, 0, 1920, 1080);
            game.batch.draw(parallax.getRenderImgLayer((int)viewport.x, (int)viewport.y, (int)viewport.width, 450, i), 0, 1000 - (int)viewport.y, 1920, 450);
        }

        game.batch.draw(gunFightTown2.getRenderImg((int)viewport.x, (int)viewport.y, (int)viewport.width, (int)viewport.height), 0, 0, 1920, 1080);


        shadow.hitBox.x = gunslinger_blue.hitBox.x;
        shadow.hitBox.y = gunslinger_blue.hitBox.y;
        game.batch.draw(shadow.getRenderImg(), shadow.getScreenCoords(this.viewport).x, shadow.getScreenCoords(this.viewport).y - 10, gunslinger_blue.hitBox.width, 30);

        if (numPlayers == 2) {
            shadow.hitBox.x = gunslinger_red.hitBox.x;
            shadow.hitBox.y = gunslinger_red.hitBox.y;
            game.batch.draw(shadow.getRenderImg(), shadow.getScreenCoords(this.viewport).x, shadow.getScreenCoords(this.viewport).y - 10, gunslinger_red.hitBox.width, 30);
        }
        for (Enemy gunner : gunslingers) {
            shadow.hitBox.x = gunner.hitBox.x;
            shadow.hitBox.y = gunner.hitBox.y;
            game.batch.draw(shadow.getRenderImg(), shadow.getScreenCoords(this.viewport).x, shadow.getScreenCoords(this.viewport).y - 10, gunner.hitBox.width, 30);
        }

        // draw stage objects
        objectsToDraw.clear();
        objectsToDraw.add(gunslinger_blue);
        if (numPlayers == 2) {
            objectsToDraw.add(gunslinger_red);
        }

        //bFilter arrays for visible on screen (x,y inside viewportX,Y)
        for (Enemy gunner : gunslingers) {
            objectsToDraw.add(gunner);
        }

        for (GunCactus cactus : cactusList) {
            objectsToDraw.add(cactus);
        }
        for (GunBullet bullet : bullets) {
            objectsToDraw.add(bullet);
        }
        for (ParticleAnimation coll : collisions) {
            objectsToDraw.add(coll);
        }
        Collections.sort(objectsToDraw, new SceneObjectSorter());
        for (SceneObject objs : objectsToDraw) {
            //Gdx.app.log("draw", objs.getClass().toString());
            game.batch.draw(objs.getRenderImg(), objs.getScreenCoords(this.viewport).x, objs.getScreenCoords(this.viewport).y);
        }

        // draw messages
        for (FloatingMessage msg : messages) {
            game.font.getData().setScale(1, 1);
            game.font.setColor(Color.YELLOW);
            game.font.draw(game.batch, msg.value, (int)(msg.x+3 - this.viewport.x), (int)(msg.y+3 - this.viewport.y));
            game.font.setColor(Color.BLACK);
            game.font.draw(game.batch, msg.value, (int)(msg.x - this.viewport.x), (int)(msg.y - this.viewport.y));
        }
        //game.font.draw(game.batch, "input:" + game.input.selectedInputType, 5, 940);

        font24.getData().setScale(4, 4);
        font24.setColor(Color.WHITE);
        font24.draw(game.batch, "" + gameElapsedTime, 900, 1050);// 500ms

        // game state
        switch (currentState) {
            case PLAYING: {
                if (numPlayers == 2) {
                    hud.render(game.batch, game.font, font24, gunslinger_blue.life, gunslinger_blue.numBullets, gunslinger_red.life, gunslinger_red.numBullets, gunslinger_blue.score, gunslinger_red.score, game.input.selectedInputType);
                    if (gunslinger_blue.life > 0 && gunslinger_red.life > 0) {
                        update();
                    }
                } else {
                    hud.render(game.batch, game.font, font24, gunslinger_blue.life, gunslinger_blue.numBullets, 0, 0, gunslinger_blue.score, 0, game.input.selectedInputType);
                    if (gunslinger_blue.life > 0) {
                        update();
                    }
                }

          /*      //if (player_blue.currentState == Player.states.SERVING) {
                    font24.getData().setScale(1, 1);
                    font24.setColor(Color.WHITE);
                    font24.draw(game.batch, gunslinger_blue.currentState.toString(), gunslinger_blue.hitBox.x, gunslinger_blue.hitBox.y + gunslinger_blue.hitBox.height);
                //}
                if (numPlayers == 2) {
                    font24.getData().setScale(1, 1);
                    font24.setColor(Color.WHITE);
                    font24.draw(game.batch, gunslinger_red.currentState.toString(), gunslinger_red.hitBox.x, gunslinger_red.hitBox.y + gunslinger_red.hitBox.height);
                }
*/
                //If android
                game.batch.draw(pauseButton.draw(), pauseButton.x, pauseButton.y);
                // if virtual controllers
                game.input.drawControllers(game.batch, numPlayers);

                break;
            }
            case PAUSED: {
                game.batch.draw(pausedbg.getRenderImg((int)viewport.x, (int)viewport.y, (int)viewport.width, (int)viewport.height), 0, 0, 1920, 1080);

                options.render(game.batch);

                if (numPlayers == 2) {
                    hud.paused(game.batch, game.font, gunslinger_blue.life, gunslinger_blue.numBullets, gunslinger_red.life, gunslinger_red.numBullets, gunslinger_blue.score, gunslinger_red.score, game.input.selectedInputType);
                } else {
                    hud.paused(game.batch, game.font, gunslinger_blue.life, gunslinger_blue.numBullets, 0, 0, gunslinger_blue.score, 0, game.input.selectedInputType);
                }

                font24.getData().setScale(2, 2);
                font24.setColor(Color.WHITE);
                font24.draw(game.batch, "RESUME", options.widgets.get(1).x + options.widgets.get(1).width, options.widgets.get(1).y + options.widgets.get(1).height);
                font24.draw(game.batch, "BACK TO MENU", options.widgets.get(0).x + options.widgets.get(0).width, options.widgets.get(0).y + options.widgets.get(0).height);
                break;
            }
            case GAMEOVER: {
                if (numPlayers == 2) {
                    // END GAME CONDITIONS
                    if (gunslinger_red.life == 0) {
                        game.batch.draw(blueWin.getRenderImg((int)viewport.x, (int)viewport.y, (int)viewport.width, (int)viewport.height), 0, 0, 1920, 1080);
                        font24stripped.getData().setScale(4, 4);
                        font24stripped.setColor(Color.WHITE);
                        font24stripped.draw(game.batch, "BLUE WIN", 800, 900);
                    }
                    if (gunslinger_blue.life == 0) {
                        game.batch.draw(redWin.getRenderImg((int)viewport.x, (int)viewport.y, (int)viewport.width, (int)viewport.height), 0, 0, 1920, 1080);
                        font24stripped.getData().setScale(4, 4);
                        font24stripped.setColor(Color.WHITE);
                        font24stripped.draw(game.batch, "RED WIN", 800, 900);
                    }
                } else {
                    // END GAME CONDITIONS
                    if (numPlayers == 1 && gunslinger_blue.life == 0) {
                        game.batch.draw(redWin.getRenderImg((int)viewport.x, (int)viewport.y, (int)viewport.width, (int)viewport.height), 0, 0, 1920, 1080);
                        font24stripped.getData().setScale(4, 4);
                        font24stripped.setColor(Color.BROWN);
                        font24stripped.draw(game.batch, "BLUE DIED", 800, 900);
                    }
                }

                optionsEnd.render(game.batch);
                font24.getData().setScale(2, 2);
                font24.setColor(Color.WHITE);
                font24.draw(game.batch, "BACK TO MENU", optionsEnd.widgets.get(0).x + optionsEnd.widgets.get(0).width, optionsEnd.widgets.get(0).y + optionsEnd.widgets.get(0).height);
                break;
            }
        }

        // Game DEBUG
        if (game.DebugMode) {
            game.font.getData().setScale(2, 2);
            game.font.setColor(Color.WHITE);
            game.font.draw(game.batch, "P1:" + (int) gunslinger_blue.deltaRect.x + "/" + (int) gunslinger_blue.deltaRect.y + " dir:" + gunslinger_blue.direction, 10, 1050);
            if (numPlayers == 2) {
                game.font.draw(game.batch, "P2:" + (int) gunslinger_red.deltaRect.x + "/" + (int) gunslinger_red.deltaRect.y, 10, 1000);
            }
            //game.font.draw(game.batch, "Bul:" + bullets.size, 10, 950);
            //game.font.draw(game.batch, "Cac:" + cactusList.size, 10, 900);
            game.font.draw(game.batch, "ctrlBtn:" + game.input.debug, 10, 900);
            game.font.draw(game.batch, "C1:" + game.input.controller1ID, 10, 850);
            game.font.draw(game.batch, "C2:" + game.input.controller2ID, 10, 800);

        // tile map positions
            game.font.getData().setScale(1, 1);
            game.font.setColor(Color.LIGHT_GRAY);
            for (int i = 0; i < gunFightTown.activeMap.length; i++) {
                for (int j = 0; j < gunFightTown.activeMap[0].length; j++) {
                    String coord = i + "/" + j;
                    //game.font.draw(game.batch, coord, j * 40, ((gunFightTown.map.length - i) * 40) - 40 + 15);
                    game.font.draw(game.batch, coord, gunFightTown.getScreenCoords(j, i, this.viewport).x, gunFightTown.getScreenCoords(j, i, this.viewport).y- 40 + 15);
                }
            }
        }
        if (game.DebugMode2) {
            //usage of game.shape prevents game.font from drawing?
            // draw collision boxes
            //Gdx.gl.glEnable(GL20.GL_BLEND);
            //Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            game.shape.setProjectionMatrix(camera.combined);
            game.shape.begin(ShapeRenderer.ShapeType.Filled); // (ShapeRenderer.ShapeType.Line);

            // Draw tilemap
            game.shape.setColor(Color.RED.r, Color.RED.g, Color.RED.b, 0.5f);

            float transparency = 0.15f;
            for (int i = 0; i < gunFightTown.activeMap.length; i++) {
                for (int j = 0; j < gunFightTown.activeMap[0].length; j++) {
                    if (gunFightTown.activeMap[i][j] == 1) {
                        game.shape.setColor(Color.RED.r, Color.RED.g, Color.RED.b, transparency);
                        game.shape.rect(gunFightTown.getScreenCoords(j, i, this.viewport).x, gunFightTown.getScreenCoords(j, i, this.viewport).y, 40, 40);
                        //game.shape.rect(j * 40, ((gunFightTown.map.length - i) * 40) - 40, 40, 40);
                    } else {
                        game.shape.setColor(Color.YELLOW.r, Color.YELLOW.g, Color.YELLOW.b, transparency);
                        game.shape.rect(gunFightTown.getScreenCoords(j, i, this.viewport).x, gunFightTown.getScreenCoords(j, i, this.viewport).y, 40, 40);
                        //game.shape.rect(j * 40, ((gunFightTown.map.length - i) * 40) - 40, 40, 40);
                    }
                }
            }
            game.shape.setColor(Color.DARK_GRAY.r, Color.DARK_GRAY.g, Color.DARK_GRAY.b, transparency);
            for (GunBullet bullet : bullets) {
                game.shape.rect(bullet.getScreenCoords(this.viewport).x, bullet.getScreenCoords(this.viewport).y, bullet.hitBox.width, bullet.hitBox.height);
                //    game.batch.draw(bullet.getRenderImg(), bullet.hitBox.x, bullet.hitBox.y);
            }
            game.shape.setColor(Color.GREEN.r, Color.GREEN.g, Color.GREEN.b, transparency);
            for (GunCactus cactus : cactusList) {
                game.shape.rect(cactus.getScreenCoords(this.viewport).x, cactus.getScreenCoords(this.viewport).y, cactus.hitBox.width, cactus.hitBox.height);
                //    objs.getScreenCoords(this.viewport).x, objs.getScreenCoords(this.viewport).y
            }

            game.shape.setColor(Color.BLUE.r, Color.BLUE.g, Color.BLUE.b, transparency);
            game.shape.rect(gunslinger_blue.getScreenCoords(this.viewport).x, gunslinger_blue.getScreenCoords(this.viewport).y, gunslinger_blue.hitBox.width, gunslinger_blue.hitBox.height);
            if (numPlayers == 2) {
                game.shape.setColor(Color.RED.r, Color.RED.g, Color.RED.b, transparency);
                game.shape.rect(gunslinger_red.getScreenCoords(this.viewport).x, gunslinger_red.getScreenCoords(this.viewport).y, gunslinger_red.hitBox.width, gunslinger_red.hitBox.height);
            }
            game.shape.setColor(Color.GREEN.r, Color.GREEN.g, Color.GREEN.b, transparency);
            for (Enemy gunner : gunslingers) {
                game.shape.rect(gunner.getScreenCoords(this.viewport).x, gunner.getScreenCoords(this.viewport).y, gunner.hitBox.width, gunner.hitBox.height);
            }

            game.shape.end();
            //Gdx.gl.glDisable(GL20.GL_BLEND);
        }
        game.batch.end();

        options.update(game.input);
        if (currentState == states.GAMEOVER) {
            optionsEnd.update(game.input);
        }
        checkInput();
    }

    // process user input in scene UI elements, like back or pause button
    public void checkInput() {
        switch (currentState) {
            case PLAYING: {
                if (pauseButton.box.overlaps(new Rectangle(game.input.touchX - 5, game.input.touchY - 5, 10, 10))) {
                    //Gdx.app.log("PAUSE", "touch pause on screen button");
                    game.input.clearInput();
                    currentState = states.PAUSED;
                }
                if (game.input.keys.contains("escape")) {
                    game.input.clearInput();
                    currentState = states.PAUSED;
                }
                //Gdx.app.log("INPUT GFS", "touch BACK key:" + game.input.keys);
                //if (game.input.keys.contains("escape")) {
                //    dispose();
                //    game.setScreen(new MainMenuScene(game));
                //}
                //Gdx.app.log("NOT PAUSED", "key:" + game.input.keys);

                // back button android
                if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
                    //Gdx.app.log("INPUT", "touch BACK key");
                    if (game.musicLvl > 0) {
                        rainMusic.stop();
                    }
                    dispose();
                    game.input.clearInput();
                    game.setScreen(new MainMenuScene(game));
                }
                break;
            }
            case PAUSED: {
                for (int i = 0; i < options.widgets.size; i++) {
                    UIObject btn = options.widgets.get(i);
                    if (btn.box.overlaps(new Rectangle(game.input.touchX - 5, game.input.touchY - 5, 10, 10))) {
                        game.input.clearInput();
                        switch (btn.id) {
                            case 1: {
                                if (game.musicLvl > 0) {
                                    rainMusic.stop();
                                }
                                dispose();
                                game.input.clearInput();
                                game.setScreen(new MainMenuScene(game));
                                break;
                            }
                            case 2: {
                                currentState = states.PLAYING;
                                game.input.clearInput();
                                break;
                            }
                        }
                    }
                }
                if (game.input.keys.contains("space") || game.input.keys_2.contains("space")) {
                    switch (options.selectedwidget) {
                        case 1: {
                            if (game.musicLvl > 0) {
                                rainMusic.stop();
                            }
                            dispose();
                            game.input.clearInput();
                            game.setScreen(new MainMenuScene(game));
                            break;
                        }
                        case 2: {
                            currentState = states.PLAYING;
                            game.input.clearInput();
                            break;
                        }
                    }
                }

                if (Gdx.input.isKeyPressed(Input.Keys.BACK) || game.input.keys.contains("escape")) {
                    currentState = states.PLAYING;
                    game.input.clearInput();
                }
                break;
            }
            case GAMEOVER: {
                for (int i = 0; i < options.widgets.size; i++) {
                    UIObject btn = options.widgets.get(i);
                    if (btn.box.overlaps(new Rectangle(game.input.touchX - 5, game.input.touchY - 5, 10, 10))) {
                        game.input.clearInput();
                        if (btn.id == 1) {
                            if (game.musicLvl > 0) {
                                rainMusic.stop();
                            }
                            dispose();
                            game.input.clearInput();
                            game.setScreen(new MainMenuScene(game));
                        }
                    }
                }

                if (game.input.keys.contains("escape") || Gdx.input.isKeyPressed(Input.Keys.BACK)) {
                    //Gdx.app.log("INPUT", "touch BACK key");
                    if (game.musicLvl > 0) {
                        rainMusic.stop();
                    }
                    dispose();
                    game.input.clearInput();
                    game.setScreen(new MainMenuScene(game));
                }

                if (game.input.keys.contains("space") || game.input.keys_2.contains("space")) {
                    if (optionsEnd.selectedwidget == 1) {
                        if (game.musicLvl > 0) {
                            rainMusic.stop();
                        }
                        dispose();
                        game.input.clearInput();
                        game.setScreen(new MainMenuScene(game));
                    }
                }
                break;
            }
        }

        // GENERIC DEBUG KEYS
        if (Gdx.input.isKeyPressed(Input.Keys.K)) {
            game.DebugMode = ! game.DebugMode;
            game.input.clearInput();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.L)) {
            game.DebugMode2 = ! game.DebugMode2;
            game.input.clearInput();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            //Gdx.app.log("VIEWPORT", "(" + this.viewport.x + "/" + this.viewport.y + ")");
            this.viewport.x -= 200 * Gdx.graphics.getDeltaTime();
            if (this.viewport.x < 0) this.viewport.x = 0;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.J)) {
            //Gdx.app.log("VIEWPORT", "(" + viewport.x + "/" + viewport.y + ")");
            this.viewport.x += 200 * Gdx.graphics.getDeltaTime();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        if (game.musicLvl > 0) {
            rainMusic.play();
        }
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        gunFightTown.dispose();
        gunFightTown2.dispose();
        blueWin.dispose();
        redWin.dispose();
        pausedbg.dispose();
        gunslinger_blue.dispose();
        if (numPlayers == 2) {
            gunslinger_red.dispose();
        }
        pauseButton.dispose();

        for (GunCactus cactus : cactusList) {cactus.dispose();}
        for (GunBullet bullet : bullets) {bullet.dispose();}
        for (ParticleAnimation coll : collisions) {coll.dispose();}
        for (Particle part : particles) {part.dispose();}
        for (Enemy gunner : gunslingers) {gunner.dispose();}

        hud.dispose();

        font24.dispose();
        font24stripped.dispose();

        dropSound.dispose();
        hitSound.dispose();
        shootSound.dispose();
        rainMusic.dispose();

        options.dispose();
        optionsEnd.dispose();
        shadow.dispose();
    }
}

/*
    if ( Rect.intersects(touchArea, BACK_MAIN_MENU.box)) {  // gameEnded
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
 */
