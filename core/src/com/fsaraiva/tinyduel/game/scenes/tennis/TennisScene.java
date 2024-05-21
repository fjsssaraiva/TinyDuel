package com.fsaraiva.tinyduel.game.scenes.tennis;

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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.fsaraiva.tinyduel.TinyDuel;
import com.fsaraiva.tinyduel.engine.ui.Button;
import com.fsaraiva.tinyduel.engine.ui.OptionsList;
import com.fsaraiva.tinyduel.engine.ui.UIObject;
import com.fsaraiva.tinyduel.engine.video.Map;
import com.fsaraiva.tinyduel.engine.video.SceneObject;
import com.fsaraiva.tinyduel.engine.video.SceneObjectSorter;
import com.fsaraiva.tinyduel.engine.utils.Animation;
import com.fsaraiva.tinyduel.engine.video.BackgroundLayer;
import com.fsaraiva.tinyduel.engine.video.FloatingMessage;
import com.fsaraiva.tinyduel.engine.video.Particle;
import com.fsaraiva.tinyduel.engine.video.ParticleAnimation;
import com.fsaraiva.tinyduel.game.menus.mainmenu.MainMenuScene;
import com.fsaraiva.tinyduel.game.scenes.tennis.actors.Ball;
import com.fsaraiva.tinyduel.game.scenes.tennis.actors.Player;
import com.fsaraiva.tinyduel.game.scenes.tennis.actors.PlayerAI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TennisScene implements Screen {
    final TinyDuel game;

    public enum states {
        PLAYING,
        PAUSED,
        GAMEOVER,
        SERVE
    };

    Map tennisCourt;
    BackgroundLayer blueWin;
    BackgroundLayer redWin;
    BackgroundLayer pausedbg;

    Animation crowdEyeLeft;
    Animation crowdEyeRight;
    Animation crowdEyeCenter;

    Sound dropSound;
    Music rainMusic;
    OrthographicCamera camera;
    Player player_blue;
    Player player_red;
    PlayerAI player_ai;

    Ball ball;

    Button pauseButton;

    int numPlayers;
    //boolean paused; //game status
    boolean buttonPauseReleased;
    public long gameTimer;
    public int gameElapsedTime;
    public long startPauseTime;
    private long delayPause = 250;

    //boolean gameOver; //game status
    public states currentState;
    Rectangle viewport;
    //Texture gui_bullet;
    //Texture gui_heart;

    //Array<GunCactus> cactusList;
    //Array<GunBullet> bullets;
    Array<ParticleAnimation> collisions;
    Array<Particle> particles;
    Array<FloatingMessage> messages;
    Array<Vector2> crowd;
    ArrayList<SceneObject> objectsToDraw;

    public BitmapFont font24;
    public BitmapFont font24stripped;
    Score hud;

    OptionsList options;
    OptionsList optionsEnd;
    /*
    private int carrocaPosY;
     */

    public TennisScene(final TinyDuel gam, int numpl) {

        //Gdx.app.log("SCENE", "Created new GunFightScene");
        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080); //1280, 720);  //800x480
        game = gam;
        numPlayers = numpl;
        viewport = new Rectangle(0,0,1920,1080);

        // scene specific fonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("bitmapfont/Sportsball.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.borderWidth = 1;
        parameter.color = Color.WHITE;
        //parameter.shadowOffsetX = 3;
        //parameter.shadowOffsetY = 3;
        //parameter.shadowColor = new Color(0, 0.5f, 0, 0.75f);
        font24 = generator.generateFont(parameter); // font size 24 pixels
        generator.dispose();

        FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("bitmapfont/Sportsball.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2.size = 30;
        parameter2.borderWidth = 1;
        parameter2.color = Color.WHITE;
        parameter.shadowOffsetX = 2;
        parameter.shadowOffsetY = 2;
        parameter.shadowColor = new Color(Color.LIGHT_GRAY); //0, 0.5f, 0, 0.75f);
        font24stripped = generator2.generateFont(parameter2); // font size 24 pixels
        generator2.dispose();

        //  create new class crowd.java
        crowdEyeCenter = new Animation("tennis_eyes_center.png", 1, 1, 0, 1, 0, 1, 0.125f);
        crowdEyeLeft = new Animation("tennis_eyes_left.png", 1, 1, 0, 1, 0, 1, 0.125f);
        crowdEyeRight = new Animation("tennis_eyes_right.png", 1, 1, 0, 1, 0, 1, 0.125f);
        crowd = new Array<Vector2>();
        crowd.add(new Vector2(0, 1058));
        crowd.add(new Vector2(76, 1054));
        crowd.add(new Vector2(162, 1056));
        crowd.add(new Vector2(242, 1060));
        crowd.add(new Vector2(325, 1058));
        crowd.add(new Vector2(400, 1060));

        crowd.add(new Vector2(591, 1060));
        crowd.add(new Vector2(667, 1058));

        crowd.add(new Vector2(835, 1058));
        crowd.add(new Vector2(919, 1056));
        crowd.add(new Vector2(993, 1058));

        crowd.add(new Vector2(1159, 1060));
        crowd.add(new Vector2(1239, 1060));
        crowd.add(new Vector2(1321, 1060));

        crowd.add(new Vector2(1468, 1058));
        crowd.add(new Vector2(1549, 1060));
        crowd.add(new Vector2(1627, 1056));
        crowd.add(new Vector2(1710, 1054));
        crowd.add(new Vector2(1784, 1059));
        crowd.add(new Vector2(1858, 1056));

        player_blue = new Player("running_blue2.png",1, 800, 200);

        if (numPlayers == 2) {
            player_red = new Player("running_red2.png", 2, new Random().nextInt(100) + 1500, 400);
            player_red.useAI = false;
        }

        player_ai = new PlayerAI("running_red2.png", 2, new Random().nextInt(100) + 1000, 100);

        ball = new Ball(960, 500, 20, 20);
        collisions = new Array<ParticleAnimation>();
        particles = new Array<Particle>();
        messages = new Array<FloatingMessage>();
        objectsToDraw = new ArrayList<SceneObject>();

        //this.enemies = [];

        //backButton = new Button("left_button.png", "left_button_active.png", 1800, 50, 32, 32);
        pauseButton = new Button("controller/pause.png", "pause_button_active.png", 1800, 960, 100, 100,0);
        //backButtonBig = new Button("left_button.png", "left_button_active.png", 600, 400, 128, 128,0);
        //pauseButtonBig = new Button("pause_button.png", "pause_button_active.png", 600, 600, 128, 128,0);
        tennisCourt = new Map("fullmap_tennis2.png",2);
        blueWin = new BackgroundLayer("blue_win.png",2);
        redWin = new BackgroundLayer("red_win.png",2);
        pausedbg = new BackgroundLayer("pausedbg.png",2);

        Gdx.input.setCatchKey(Input.Keys.BACK, true);

        hud = new Score();

        // load the drop sound effect and the rain background "music"
        dropSound = Gdx.audio.newSound(Gdx.files.internal("sound/xland.mp3"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/mLush.mp3")); //mp3
        rainMusic.setVolume(((float)game.musicLvl / 10));
        //rainMusic.setVolume(0.1f);
        rainMusic.setLooping(true);

        // game states
        gameTimer = System.nanoTime();
        gameElapsedTime = 0;
        //paused = false;
        //gameOver = false;
        //currentState = states.PLAYING;
        currentState = states.SERVE;
        player_blue.currentState = Player.states.SERVING;
        if (numPlayers == 2) {
            player_red.currentState = Player.states.RUNNING;
        }

        options = new OptionsList();
        options.AddOption(new Button("controller/exit.png", "controller/exit_active.png", 600, 400, 128, 128,1));
        options.AddOption(new Button("controller/pauseSmall.png", "controller/pauseSmall_active.png", 600, 600, 128, 128,2));

        optionsEnd = new OptionsList();
        optionsEnd.AddOption(new Button("controller/exit.png", "controller/exit_active.png", 600, 400, 128, 128,1));
    }

    public void update() {
        if (currentState.equals(states.PLAYING)) {
            ball.update(tennisCourt.activeMap, 40);

            if (ball.hitBox.overlaps(player_blue.hitBox) && player_blue.currentState == Player.states.SHOOTING) {
                if (ball.x_step < 0) {
                    ball.x_step = -ball.x_step;
                }
                //dropSound.play(1.0f);
                dropSound.play((float) game.soundLvl / 10.0f);
                ball.y_step = -ball.y_step;
                ball.hitBox.x = player_blue.hitBox.x + player_blue.hitBox.width;
            }
            if (numPlayers == 2) {
                if (ball.hitBox.overlaps(player_red.hitBox) && player_red.currentState == Player.states.SHOOTING) {
                    if (ball.x_step > 0) {
                        ball.x_step = -ball.x_step;
                    }
                    dropSound.play((float) game.soundLvl / 10.0f);
                    ball.y_step = -ball.y_step;
                    ball.hitBox.x = player_red.hitBox.x - ball.hitBox.width;
                }
            }
            if (ball.hitBox.overlaps(player_ai.hitBox) && player_ai.currentState == Player.states.SHOOTING) {
                if (ball.x_step > 0) {
                    ball.x_step = -ball.x_step;
                }
                dropSound.play((float) game.soundLvl / 10.0f);
                ball.y_step = -ball.y_step;
                ball.hitBox.x = player_ai.hitBox.x - ball.hitBox.width;
            }

            if (ball.hitLeft && numPlayers == 2) {
                player_red.score++;
                currentState = states.SERVE;
                player_red.currentState = Player.states.SERVING;
                player_blue.currentState = Player.states.RUNNING;
                ball.hitLeft = false;
            }
            if (ball.hitRight) {
                player_blue.score++;
                currentState = states.SERVE;
                player_blue.currentState = Player.states.SERVING;
                ball.hitRight = false;
            }
        }

        if (currentState == states.SERVE) {
            if (player_blue.currentState == Player.states.SERVING) {
                ball.hitBox.x = player_blue.hitBox.x + player_blue.hitBox.width;
                ball.hitBox.y = player_blue.hitBox.y;
                if (ball.x_step < 0) {
                    ball.x_step = -ball.x_step;
                }
            }
            if (numPlayers == 2) {
                if (player_red.currentState == Player.states.SERVING) {
                    ball.hitBox.x = player_red.hitBox.x - ball.hitBox.width;
                    ball.hitBox.y = player_red.hitBox.y;
                    if (ball.x_step > 0) {
                        ball.x_step = -ball.x_step;
                    }
                }
            }
            ball.update(tennisCourt.activeMap, 40);
        }

        player_blue.update(game.input, this.collisions, tennisCourt.activeMap);
        if (numPlayers == 2) {
            player_red.update(game.input, this.collisions, tennisCourt.activeMap);
        }
        if (numPlayers == 1) {
            if (currentState == states.SERVE && player_blue.currentState != Player.states.SERVING) {
                currentState = states.PLAYING;
            }
        } else {
            if (currentState == states.SERVE && player_blue.currentState != Player.states.SERVING && player_red.currentState != Player.states.SERVING) {
                currentState = states.PLAYING;
            }
        }

        player_ai.update(this.collisions, tennisCourt.activeMap, (int)ball.hitBox.x, (int)ball.hitBox.y);

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

        long elapsedTmp = (System.nanoTime() - this.gameTimer) / 1000000;
        if (elapsedTmp > 1000) {
            this.gameTimer = System.nanoTime();
            gameElapsedTime++;
        }

        // update viewport (track blue player)
        this.viewport.x = player_blue.hitBox.x - (viewport.width / 2);
        this.viewport.y = player_blue.hitBox.y - (viewport.height / 2);
        if (this.viewport.x < 0)
            this.viewport.x = 0;
        if (this.viewport.x + this.viewport.width > (tennisCourt.activeMap[0].length * tennisCourt.tileSize))
            this.viewport.x = (tennisCourt.activeMap[0].length * tennisCourt.tileSize) - viewport.width;
        if (this.viewport.y < 0)
            this.viewport.y = 0;
        if (this.viewport.y + this.viewport.height > (tennisCourt.activeMap.length * tennisCourt.tileSize))
            this.viewport.y = (tennisCourt.activeMap.length * tennisCourt.tileSize) - viewport.height;

        // END GAME CONDITIONS
        if ((numPlayers == 2 && player_red.score == 4) || player_blue.score == 4) {
            currentState = states.GAMEOVER;
            game.input.clearInput();
        }
    }

    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The arguments to clear are the red, green
        // blue and alpha component in the range [0,1] of the color to be used to clear the screen.
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update(); // tell the camera to update its matrices.

        game.batch.setProjectionMatrix(camera.combined); // tell the SpriteBatch to render in the coordinate system specified by the camera.
        game.batch.begin(); // begin a new batch and draw the gunslinger_blue and all drops

        game.batch.draw(tennisCourt.getRenderImg((int)viewport.x, (int)viewport.y, (int)viewport.width, (int)viewport.height), 0, 0, 1920, 1080); // draw background
        //(int)viewport.x, (int)viewport.y, (int)viewport.width, (int)viewport.height), 0, 0, 1920, 1080
        for (Vector2 crwd : crowd) {
            int ey = drawCrowdMember((int)crwd.x, (int)crwd.y);
            switch (ey) {
                case 0: {
                    game.batch.draw(crowdEyeLeft.getRenderImg(), (int)crwd.x, (int)crwd.y);
                    break;
                }
                case 1: {
                    game.batch.draw(crowdEyeCenter.getRenderImg(), (int)crwd.x, (int)crwd.y);
                    break;
                }
                case 2: {
                    game.batch.draw(crowdEyeRight.getRenderImg(), (int)crwd.x, (int)crwd.y);
                    break;
                }
            }
        }

        // if virtual controllers
        game.input.drawControllers(game.batch, numPlayers);


        game.batch.draw(ball.getRenderShadowImg(), player_blue.hitBox.x, player_blue.hitBox.y - 10, player_blue.hitBox.width, ball.hitBox.height);
        if (numPlayers == 2) {
            game.batch.draw(ball.getRenderShadowImg(), player_red.hitBox.x, player_red.hitBox.y - 10, player_blue.hitBox.width, ball.hitBox.height);
        }
        game.batch.draw(ball.getRenderShadowImg(), player_ai.hitBox.x, player_ai.hitBox.y - 10, player_blue.hitBox.width, ball.hitBox.height);

        // draw stage objects
        objectsToDraw.clear();
        objectsToDraw.add(player_blue);
        if (numPlayers == 2) {
            objectsToDraw.add(player_red);
        }
        objectsToDraw.add(player_ai);
        Collections.sort(objectsToDraw, new SceneObjectSorter());
        for (SceneObject objs : objectsToDraw) {
            //Gdx.app.log("draw", objs.getClass().toString());
            game.batch.draw(objs.getRenderImg(), objs.getScreenCoords(this.viewport).x, objs.getScreenCoords(this.viewport).y);
        }


        // draw shadows
        game.batch.draw(ball.getRenderShadowImg(), ball.getScreenCoords(this.viewport).x, ball.getScreenCoords(this.viewport).y);
        game.batch.draw(ball.getRenderImg(), ball.getScreenCoords(this.viewport).x, ball.getScreenCoords(this.viewport).y + ball.ballHeigth);

        // draw particles
        for (ParticleAnimation coll : collisions) {
            objectsToDraw.add(coll);
        }



        font24.getData().setScale(3, 3);
        font24.setColor(Color.WHITE);
        font24.draw(game.batch, "" + gameElapsedTime, 900, 1050);// 500ms

        // game state
        switch (currentState) {
            case SERVE:
            case PLAYING: {
                if (numPlayers == 2) {
                    hud.render(game.batch, game.font, font24, 0, 0, 0, 0,
                            player_blue.score, player_red.score, game.input.selectedInputType);
                    if (player_blue.life > 0 && player_red.life > 0) {
                        update();
                    }
                } else {
                    hud.render(game.batch, game.font, font24, 0, 0, 0, 0, player_blue.score, 0, game.input.selectedInputType);
                    if (player_blue.life > 0) {
                        update();
                    }
                }
                //If android
                game.batch.draw(pauseButton.draw(), pauseButton.x, pauseButton.y);

                if (player_blue.currentState == Player.states.SERVING) {
                    font24.getData().setScale(0.5f, 0.5f);
                    font24.setColor(Color.WHITE);
                    font24.draw(game.batch, "serve", player_blue.hitBox.x, player_blue.hitBox.y + player_blue.hitBox.height);
                }
                if (numPlayers == 2 && player_red.currentState == Player.states.SERVING) {
                    font24.getData().setScale(0.5f, 0.5f);
                    font24.setColor(Color.WHITE);
                    font24.draw(game.batch, "serve", player_red.hitBox.x, player_red.hitBox.y + player_red.hitBox.height);
                }
                break;
            }
            case PAUSED: {
                game.batch.draw(pausedbg.getRenderImg((int)viewport.x, (int)viewport.y, (int)viewport.width, (int)viewport.height), 0, 0, 1920, 1080);
                //game.batch.draw(pauseButtonBig.draw(), pauseButtonBig.x, pauseButtonBig.y, pauseButtonBig.width, pauseButtonBig.height);
                //game.batch.draw(backButtonBig.draw(), backButtonBig.x, backButtonBig.y, backButtonBig.width, backButtonBig.height);
                options.render(game.batch);

                //game.batch.draw(backButton.draw(), backButton.x, backButton.y);
                if (numPlayers == 2) {
                    hud.paused(game.batch, font24, player_blue.life, player_blue.numBullets, player_red.life, player_red.numBullets, player_blue.score, player_red.score, game.input.selectedInputType);
                } else {
                    hud.paused(game.batch, font24, player_blue.life, player_blue.numBullets, 0, 0, player_blue.score, 0, game.input.selectedInputType);
                }

                //game.batch.draw(greyPause.getRenderImg(), 0, 0, 1920, 1080);
                font24.getData().setScale(2, 2);
                font24.setColor(Color.WHITE);
                //font24.draw(game.batch, "RESUME", pauseButtonBig.x + pauseButtonBig.width, pauseButtonBig.y + pauseButtonBig.height);
                //font24.draw(game.batch, "BACK TO MENU", backButtonBig.x + backButtonBig.width, backButtonBig.y + backButtonBig.height);
                font24.draw(game.batch, "RESUME", options.widgets.get(1).x + options.widgets.get(1).width, options.widgets.get(1).y + options.widgets.get(1).height);
                font24.draw(game.batch, "BACK TO MENU", options.widgets.get(0).x + options.widgets.get(0).width, options.widgets.get(0).y + options.widgets.get(0).height);

                break;
            }
            case GAMEOVER: {
                if (numPlayers == 2) {
                    // END GAME CONDITIONS
                    if (player_blue.score == 4) {
                        game.batch.draw(blueWin.getRenderImg((int)viewport.x, (int)viewport.y, (int)viewport.width, (int)viewport.height), 0, 0, 1920, 1080);
                        font24stripped.getData().setScale(4, 4);
                        font24stripped.setColor(Color.WHITE);
                        font24stripped.draw(game.batch, "BLUE WIN", 600, 800);
                    }
                    if (player_red.score == 4) {
                        game.batch.draw(redWin.getRenderImg((int)viewport.x, (int)viewport.y, (int)viewport.width, (int)viewport.height), 0, 0, 1920, 1080);
                        font24stripped.getData().setScale(4, 4);
                        font24stripped.setColor(Color.WHITE);
                        font24stripped.draw(game.batch, "RED WIN", 600, 800);
                    }
                } else {
                    // END GAME CONDITIONS
                    if (numPlayers == 1 && player_blue.score == 4) {
                        game.batch.draw(blueWin.getRenderImg((int)viewport.x, (int)viewport.y, (int)viewport.width, (int)viewport.height), 0, 0, 1920, 1080);
                        font24stripped.getData().setScale(4, 4);
                        font24stripped.setColor(Color.WHITE);
                        font24stripped.draw(game.batch, "BLUE WIN", 600, 800);
                    }
                }

                optionsEnd.render(game.batch);
                //game.batch.draw(backButtonBig.draw(), backButtonBig.x, backButtonBig.y, backButtonBig.width, backButtonBig.height);
                font24.getData().setScale(2, 2);
                font24.setColor(Color.WHITE);
                font24.draw(game.batch, "BACK TO MENU", optionsEnd.widgets.get(0).x + optionsEnd.widgets.get(0).width, optionsEnd.widgets.get(0).y + optionsEnd.widgets.get(0).height);

                break;
            }
        }

        if (game.DebugMode) {
            game.font.getData().setScale(2, 2);
            game.font.setColor(Color.BLACK);
            game.font.draw(game.batch, "p0:" + game.input.pointers.get(0).x + "/" + game.input.pointers.get(0).y, 10, 850);
            game.font.draw(game.batch, "p1:" + game.input.pointers.get(1).x + "/" + game.input.pointers.get(1).y, 10, 800);
            game.font.draw(game.batch, "p2:" + game.input.pointers.get(2).x + "/" + game.input.pointers.get(2).y, 10, 750);
            game.font.draw(game.batch, "p3:" + game.input.pointers.get(3).x + "/" + game.input.pointers.get(3).y, 10, 700);

            game.font.draw(game.batch, "pl1:" + game.input.onScreenCtrl1Pointer + " / ", 10, 650);
            game.font.draw(game.batch, "pl2:" + game.input.onScreenCtrl2Pointer + " / ", 10, 600);
            game.font.draw(game.batch, "k2:" + game.input.keys, 10, 500);

            game.font.draw(game.batch, "ctrl:" + game.input.debug, 10, 550);

            game.font.getData().setScale(2, 2);
            game.font.setColor(Color.YELLOW);
            game.font.draw(game.batch, "p0:" + game.input.pointers.get(0).x + "/" + game.input.pointers.get(0).y, 10, 850);
            game.font.draw(game.batch, "p1:" + game.input.pointers.get(1).x + "/" + game.input.pointers.get(1).y, 10, 800);
            game.font.draw(game.batch, "p2:" + game.input.pointers.get(2).x + "/" + game.input.pointers.get(2).y, 10, 750);
            game.font.draw(game.batch, "p3:" + game.input.pointers.get(3).x + "/" + game.input.pointers.get(3).y, 10, 700);

            game.font.draw(game.batch, "pl1:" + game.input.onScreenCtrl1Pointer + " / ", 10, 650);
            game.font.draw(game.batch, "pl2:" + game.input.onScreenCtrl2Pointer + " / ", 10, 600);
            //game.font.draw(game.batch, "ball: " + ball.hitBox.x + "/" + ball.hitBox.y + " step: " + ball.x_step + "/" + ball.y_step, 10, 600);
            //game.font.draw(game.batch, "ballUP:" + ball.ballUp + " H:" + ball.ballHeigth + " hl:" + ball.hitLeft + " hr:" + ball.hitRight, 10, 550);

            //Gdx.app.log("BALL:", "ball: " + ball.hitBox.x + "/" + ball.hitBox.y + " step: " + ball.x_step + "/" + ball.y_step);
            //Gdx.app.log("BALL:","ballUP:" + ball.ballUp + " H:" + ball.ballHeigth + " hl:" + ball.hitLeft + " hr:" + ball.hitRight);

            //game.font.draw(game.batch, "ball: " + ball.hitBox.x + "/" + ball.hitBox.y + " step: " + ball.x_step + "/" + ball.y_step, 10, 600);
            //game.font.draw(game.batch, "ballUP:" + ball.ballUp + " H:" + ball.ballHeigth + " hl:" + ball.hitLeft + " hr:" + ball.hitRight, 10, 550);
            // Game DEBUG

            game.font.getData().setScale(2, 2);
            game.font.setColor(Color.BLACK);
            game.font.draw(game.batch, "P1:" + (int) player_blue.deltaRect.x + "/" + (int) player_blue.deltaRect.y + " dir:" + player_blue.direction, 10, 1050);
            if (numPlayers == 2) {
                game.font.draw(game.batch, "P2:" + (int) player_red.deltaRect.x + "/" + (int) player_red.deltaRect.y, 10, 1000);
            }
            game.font.draw(game.batch, "C1:" + game.input.controller1ID, 10, 850);
            game.font.draw(game.batch, "C2:" + game.input.controller2ID, 10, 800);

            // tile map positions
            game.font.getData().setScale(1, 1);
            game.font.setColor(Color.LIGHT_GRAY);
            for (int i = 0; i < tennisCourt.activeMap.length; i++) {
                for (int j = 0; j < tennisCourt.activeMap[0].length; j++) {
                    String coord = i + "/" + j;
                    //game.font.draw(game.batch, coord, j * 40, ((tennisCourt.map.length - i) * 40) - 40 + 15);
                    game.font.draw(game.batch, coord, tennisCourt.getScreenCoords(j, i, this.viewport).x, tennisCourt.getScreenCoords(j, i, this.viewport).y - 40 + 15);
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
            for (int i = 0; i < tennisCourt.activeMap.length; i++) {
                for (int j = 0; j < tennisCourt.activeMap[0].length; j++) {
                    if (tennisCourt.activeMap[i][j] == 1) {
                        game.shape.setColor(Color.RED.r, Color.RED.g, Color.RED.b, transparency);
                        game.shape.rect(tennisCourt.getScreenCoords(j, i, this.viewport).x, tennisCourt.getScreenCoords(j, i, this.viewport).y, 40, 40);
                        //game.shape.rect(j * 40, ((tennisCourt.map_clean.length - i) * 40) - 40, 40, 40);
                    } else {
                        game.shape.setColor(Color.YELLOW.r, Color.YELLOW.g, Color.YELLOW.b, transparency);
                        game.shape.rect(tennisCourt.getScreenCoords(j, i, this.viewport).x, tennisCourt.getScreenCoords(j, i, this.viewport).y, 40, 40);
                        //game.shape.rect(j * 40, ((tennisCourt.map_clean.length - i) * 40) - 40, 40, 40);
                    }
                }
            }
            game.shape.setColor(Color.DARK_GRAY.r, Color.DARK_GRAY.g, Color.DARK_GRAY.b, transparency);

            game.shape.setColor(Color.BLUE.r, Color.BLUE.g, Color.BLUE.b, transparency);
            game.shape.rect(player_blue.getScreenCoords(this.viewport).x, player_blue.getScreenCoords(this.viewport).y, player_blue.hitBox.width, player_blue.hitBox.height);
            if (numPlayers == 2) {
                game.shape.setColor(Color.RED.r, Color.RED.g, Color.RED.b, transparency);
                game.shape.rect(player_red.getScreenCoords(this.viewport).x, player_red.getScreenCoords(this.viewport).y, player_red.hitBox.width, player_red.hitBox.height);
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

    public int drawCrowdMember(int xc, int yc) {
        if (ball.hitBox.x - xc < -100) {
            return 0;
        } else {
            if (ball.hitBox.x - xc > 100) {
                return 2;
            } else {
                return 1;
            }
        }
    }

    // process user input in scene UI elements, like back or pause button
    public void checkInput() {
        switch (currentState) {
            case SERVE:
            case PLAYING: {
                if (pauseButton.box.overlaps(new Rectangle(game.input.touchX - 5, game.input.touchY - 5, 10, 10))) {
                    //Gdx.app.log("PAUSE", "touch pause on screen button");
                    game.input.clearInput();
                    //paused = true;
                    currentState = states.PAUSED;
                }
                if (game.input.keys.contains("escape")) {
                    //paused = true;
                    currentState = states.PAUSED;
                    game.input.clearInput();
                    //Gdx.app.log("PAUSE", "pause");
                }
                // back button android
                if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
                    //Gdx.app.log("INPUT", "touch BACK key");
                    //paused = true;
                    currentState = states.PAUSED;
                    game.input.clearInput();
                    //Gdx.app.log("PAUSE", "pause");
                    //rainMusic.stop();
                    //dispose();
                    //game.input.clearInput();
                    //game.setScreen(new MainMenuScene(game));
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
                                //paused = false;
                                currentState = states.PLAYING;
                                game.input.clearInput();
                                //Gdx.app.log("PAUSE", "resume");
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
                            //paused = false;
                            currentState = states.PLAYING;
                            game.input.clearInput();
                            //Gdx.app.log("PAUSE", "resume");
                            break;
                        }
                    }
                }
                // special cases
                if (game.input.keys.contains("escape")) {
                    //paused = false;
                    currentState = states.PLAYING;
                    game.input.clearInput();
                    //Gdx.app.log("PAUSE", "resume");
                }
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
        tennisCourt.dispose();
        blueWin.dispose();
        redWin.dispose();
        pausedbg.dispose();

        crowdEyeLeft.dispose();
        crowdEyeRight.dispose();
        crowdEyeCenter.dispose();

        player_blue.dispose();
        if (numPlayers == 2) {
            player_red.dispose();
        }
        player_ai.dispose();
        ball.dispose();
        //backButtonBig.dispose();
        pauseButton.dispose();
        //pauseButtonBig.dispose();

        dropSound.dispose();
        rainMusic.dispose();

        for (ParticleAnimation coll : collisions) {coll.dispose();}
        for (Particle part : particles) {part.dispose();}

        font24.dispose();
        font24stripped.dispose();
        hud.dispose();

        options.dispose();
        optionsEnd.dispose();
    }
}

/*
    case MotionEvent.ACTION_DOWN: {
        if ( Rect.intersects(touchArea, BACK_MAIN_MENU.box)) {
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

    if (!Stage.gameOptions_redHumanOn) { pongRacket2.doAI(pongBall.y); }

    if (gameEnded) {
        if (pongRacket1.score == 5) {
            pt2.setColor(Color.parseColor("#800000FF"));
            pt2.setStyle(Paint.Style.FILL);
            GUImask = new Rect(0, 0, Stage.DeviceWIDTH, Stage.DeviceHEIGHT);
            canvas.drawRect(GUImask, pt2);

    shadow1.x = pongRacket1.x;
    shadow1.y = pongRacket1.y;
    shadow2.x = pongRacket2.x;
    shadow2.y = pongRacket2.y;
    shadow1.drawCentered(canvas);
    shadow2.drawCentered(canvas);
 */
