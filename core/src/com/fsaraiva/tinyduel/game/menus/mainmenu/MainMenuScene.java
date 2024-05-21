package com.fsaraiva.tinyduel.game.menus.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.fsaraiva.tinyduel.TinyDuel;
import com.fsaraiva.tinyduel.engine.ui.Button;
import com.fsaraiva.tinyduel.engine.ui.OptionsList;
import com.fsaraiva.tinyduel.engine.ui.UIObject;
import com.fsaraiva.tinyduel.engine.video.BackgroundLayer;
import com.fsaraiva.tinyduel.engine.video.Sprite;
import com.fsaraiva.tinyduel.game.menus.options.OptionsScene;
import com.fsaraiva.tinyduel.game.scenes.gunfight.GunFightScene;
import com.fsaraiva.tinyduel.game.scenes.tennis.TennisScene;
//import com.fsaraiva.tinyduel.characters.gunfight.Cactus;

public class MainMenuScene implements Screen {
    final TinyDuel game;

    public enum states {
        MAIN,
        TENNIS,
        GUNFIGHT
    }
    public states currentState;

    BackgroundLayer background;
    //Background background2;
    OrthographicCamera camera;
    Music rainMusic;
    public BitmapFont font24;
    Sprite titleTiny, titleDuel;
    int titleTinyx = 0, titleDuelx = 5, titleTinyy = 5, titleDuely = 0, titleTinydirx = 200, titleDueldirx = -200, titleTinydiry = -200, titleDueldiry = 200;

    OptionsList options;
    OptionsList optionsTennis;
    OptionsList optionsGunfight;

    public String adText;

    public MainMenuScene(final TinyDuel gam) {
        //Gdx.app.log("SCENE", "Created new TitleScene");
        game = gam;
        background = new BackgroundLayer("settings2.png", 0);

        titleTiny = new Sprite("titleTiny.png");
        titleDuel = new Sprite("titleDuel.png");
        //background2 = new Background("blue_win.png", 0);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080); //1280, 720);

        Gdx.input.setCatchKey(Input.Keys.BACK, false);
        //Gdx.input.setCatchKey(Input.Keys.MENU, true);

        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/main.mp3")); //mp3
        //rainMusic.setVolume(0.1f);
        rainMusic.setVolume((float)game.musicLvl / 10);
        rainMusic.setLooping(true);

        options = new OptionsList();
        options.AddOption(new Button("menu_tennis2.png", "menu_tennis_active1.png",1440, 800, 440, 195, 1));
        //options.AddOption(new Button("menu_tennis1.png", "menu_tennis_active1.png",1390, 670, 440, 95,2));
        options.AddOption(new Button("menu_gunfight2.png", "menu_gunfight_active1.png",1340, 550, 440, 195,2));
        //options.AddOption(new Button("menu_gunfight1.png", "menu_gunfight_active1.png",1290, 370, 440, 95,4));
        options.AddOption(new Button("menu_options1.png", "menu_options_active1.png",1240, 300, 440, 195,3));
        //options.AddOption(new Button("menu_help.png", "menu_help_active.png",1260, 20, 220, 220,6));
        options.AddOption(new Button("menu_options1.png", "menu_options_active1.png",1140, 50, 440, 195,4));
        //Gdx.app.exit();

        optionsTennis = new OptionsList();
        optionsTennis.AddOption(new Button("menu_tennis2.png", "menu_tennis_active1.png",1440, 820, 440, 95, 1));
        optionsTennis.AddOption(new Button("menu_tennis1.png", "menu_tennis_active1.png",1390, 670, 440, 95,2));
        optionsTennis.AddOption(new Button("controller/exit.png", "controller/exit_active.png", 1540, 500, 128, 128,3));

        optionsGunfight = new OptionsList();
        optionsGunfight.AddOption(new Button("menu_gunfight1.png", "menu_gunfight_active1.png",1440, 820, 440, 95,1));
        optionsGunfight.AddOption(new Button("menu_gunfight1.png", "menu_gunfight_active1.png",1390, 670, 440, 95,2));
        optionsGunfight.AddOption(new Button("controller/exit.png", "controller/exit_active.png", 1540, 500, 128, 128,3));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("bitmapfont/Sportsball.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        parameter.borderWidth = 1;
        parameter.borderColor = Color.BLACK;
        parameter.borderStraight = false;
        //parameter.color = Color.CYAN;
        parameter.color =  new Color(40f/255f, 236f/255f, 241f/255f, 255f/255f);
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 4;
        //parameter.shadowColor = Color.BLUE;
        parameter.shadowColor = new Color(20f/255f, 134f/255f, 241f/255f, 255f/255f);
        font24 = generator.generateFont(parameter); // font size 24 pixels
        generator.dispose();

        currentState = states.MAIN;

        game.advertisement.submitScore("bla", 10);

    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(background.getRenderImg(0, 0, 1920, 1080), 0, 0, 1920, 1080);

        game.batch.draw(titleTiny.getRenderImg(), 50 + titleTinyx, 530 + titleTinyy);
        game.batch.draw(titleDuel.getRenderImg(), 150 + titleDuelx, 90 + titleDuely);

        //game.batch.draw(background2.getRenderImg(), 0, 0, 1920, 1080);
        game.font.getData().setScale(2, 2);
        game.font.setColor(Color.GOLD);
        game.font.draw(game.batch, "FPS: " + Float.toString(Gdx.graphics.getFramesPerSecond()), 5, 1040);
        game.font.draw(game.batch, "input:" + game.input.selectedInputType, 5, 940);

        font24.setColor(Color.CYAN);
        font24.getData().setScale(0.7f, 2.5f);
        switch (currentState) {
            case MAIN: {
                options.render(game.batch);
                font24.draw(game.batch, "TENNIS", options.widgets.get(0).x - 10, options.widgets.get(0).y + 120);
                font24.draw(game.batch, "GUNFIGHT", options.widgets.get(1).x - 10, options.widgets.get(1).y + 120);
                font24.draw(game.batch, "OPTIONS", options.widgets.get(2).x - 10, options.widgets.get(2).y + 120);
                font24.draw(game.batch, "EXIT", options.widgets.get(3).x -10, options.widgets.get(3).y+120);

                break;
            }
            case TENNIS: {
                optionsTennis.render(game.batch);
                font24.draw(game.batch, "TENNIS 1P", optionsTennis.widgets.get(0).x - 10, optionsTennis.widgets.get(0).y + 100);
                font24.draw(game.batch, "TENNIS 2P", optionsTennis.widgets.get(1).x - 10, optionsTennis.widgets.get(1).y + 100);
                break;
            }
            case GUNFIGHT: {
                optionsGunfight.render(game.batch);
                font24.draw(game.batch, "GUNFIGHT 1P", optionsGunfight.widgets.get(0).x - 10, optionsGunfight.widgets.get(0).y + 100);
                font24.draw(game.batch, "GUNFIGHT 2P", optionsGunfight.widgets.get(1).x - 10, optionsGunfight.widgets.get(1).y + 100);
                break;
            }
        }
        //Gdx.app.exit();

        if (game.DebugMode) {
            game.font.getData().setScale(1, 1);
            game.font.setColor(Color.DARK_GRAY);

            game.font.draw(game.batch, "selected option:" + options.selectedwidget + " " + options.buttonReleased, 5, 1000);
            game.font.draw(game.batch, "settings:" + game.settings.getStringValue("name"), 5, 900);
            game.font.draw(game.batch, "input:" + game.input.selectedInputType, 5, 1100);

            //game.font.draw(game.batch, "controller:" + game.input., 5, 900);
            game.font.draw(game.batch, game.OStype, 5, 950);
        }

        game.batch.end();

        update();
        checkInput();
    }

    public void checkInput() {
        // TOUCH Controls
        //for (Button btn : options.widgets) {
        switch (currentState) {
            case MAIN: {
                for (int i = 0; i < options.widgets.size; i++) {
                    UIObject btn = options.widgets.get(i);
                    if (btn.box.overlaps(new Rectangle(game.input.touchX - 5, game.input.touchY - 5, 10, 10))) {
                        if (game.musicLvl > 0) {
                            rainMusic.stop();
                        }
                        switch (btn.id) {
                            case 1: {
                                //game.setScreen(new TennisScene(game, 1));
                                currentState = states.TENNIS;
                                game.input.clearInput();
                                break;
                            }
                            case 2: {
                                currentState = states.GUNFIGHT;
                                game.input.clearInput();
                                break;
                            }
                            case 3: {
                                game.setScreen(new OptionsScene(game));
                                break;
                            }
                            case 4: {
                                game.input.clearInput();
                                dispose();
                                Gdx.app.exit();
                                break;
                            }
                            default: {
                                break;
                            }
                        }
                    }
                }

                if (game.input.keys.contains("space") && options.selectedwidget != 0) {
                    if (game.musicLvl > 0) {
                        rainMusic.stop();
                    }
                    //dispose();
                    //game.input.clearInput();
                    switch (options.selectedwidget) {
                        case 1: {
                            //game.setScreen(new TennisScene(game, 1));
                            currentState = states.TENNIS;
                            game.input.clearInput();
                            break;
                        }
                        case 2: {
                            //game.setScreen(new GunFightScene(game, 1));
                            currentState = states.GUNFIGHT;
                            game.input.clearInput();
                            break;
                        }
                        case 3: {
                            game.setScreen(new OptionsScene(game));
                            break;
                        }
                        case 4: {
                            game.input.clearInput();
                            dispose();
                            Gdx.app.exit();
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                }
                break;
            }
            case TENNIS: {
                for (int i = 0; i < optionsTennis.widgets.size; i++) {
                    UIObject btn = optionsTennis.widgets.get(i);
                    if (btn.box.overlaps(new Rectangle(game.input.touchX - 5, game.input.touchY - 5, 10, 10))) {
                        if (game.musicLvl > 0) {
                            rainMusic.stop();
                        }
                        //dispose();
                        //game.input.clearInput();
                        switch (btn.id) {
                            case 1: {
                                dispose();
                                game.input.clearInput();
                                game.setScreen(new TennisScene(game, 1));
                                break;
                            }
                            case 2: {
                                dispose();
                                game.input.clearInput();
                                game.setScreen(new TennisScene(game, 2));
                                break;
                            }
                            case 3: {
                                currentState = states.MAIN;
                                game.input.clearInput();
                                break;
                            }
                            default: {
                                break;
                            }
                        }
                    }
                }

                if (game.input.keys.contains("space") && options.selectedwidget != 0) {
                    if (game.musicLvl > 0) {
                        rainMusic.stop();
                    }

                    switch (options.selectedwidget) {
                        case 1: {
                            dispose();
                            game.input.clearInput();
                            game.setScreen(new TennisScene(game, 1));
                            break;
                        }
                        case 2: {
                            dispose();
                            game.input.clearInput();
                            game.setScreen(new TennisScene(game, 2));
                            break;
                        }
                        case 3: {
                            currentState = states.MAIN;
                            game.input.clearInput();
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                }
                break;
            }
            case GUNFIGHT: {
                for (int i = 0; i < optionsGunfight.widgets.size; i++) {
                    UIObject btn = optionsGunfight.widgets.get(i);
                    if (btn.box.overlaps(new Rectangle(game.input.touchX - 5, game.input.touchY - 5, 10, 10))) {
                        if (game.musicLvl > 0) {
                            rainMusic.stop();
                        }
                        switch (btn.id) {
                            case 1: {
                                dispose();
                                game.input.clearInput();
                                game.setScreen(new GunFightScene(game, 1));
                                break;
                            }
                            case 2: {
                                dispose();
                                game.input.clearInput();
                                game.setScreen(new GunFightScene(game, 2));
                                break;
                            }
                            case 3: {
                                currentState = states.MAIN;
                                game.input.clearInput();
                                break;
                            }
                            default: {
                                break;
                            }
                        }
                    }
                }

                if (game.input.keys.contains("space") && options.selectedwidget != 0) {
                    if (game.musicLvl > 0) {
                        rainMusic.stop();
                    }
                    //dispose();
                    //game.input.clearInput();
                    switch (options.selectedwidget) {
                        case 1: {
                            dispose();
                            game.input.clearInput();
                            game.setScreen(new GunFightScene(game, 1));
                            break;
                        }
                        case 2: {
                            dispose();
                            game.input.clearInput();
                            game.setScreen(new GunFightScene(game, 2));
                            break;
                        }
                        case 3: {
                            currentState = states.MAIN;
                            game.input.clearInput();
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                }
                break;
            }
        }
    }

    public void update() {
        titleDuelx = (int)(titleDuelx + (titleDueldirx * Gdx.graphics.getDeltaTime() / 2.0f));
        titleDuely = (int)(titleDuely + (titleDueldiry * Gdx.graphics.getDeltaTime() / 1.5f));
        titleTinyx = (int)(titleTinyx + (titleTinydirx * Gdx.graphics.getDeltaTime() / 1.5f));
        titleTinyy = (int)(titleTinyy + (titleTinydiry * Gdx.graphics.getDeltaTime() / 2.0f));

        if (titleDuelx < 0) {
            titleDuelx = 0;
            titleDueldirx = -titleDueldirx;
        }
        if (titleDuelx > 30) {
            titleDuelx = 30;
            titleDueldirx = -titleDueldirx;
        }
        if (titleDuely < 0) { //1920
            titleDuely = 0;
            titleDueldiry = -titleDueldiry;
        }
        if (titleDuely > 30) { //1920
            titleDuely = 30;
            titleDueldiry = -titleDueldiry;
        }
        if (titleTinyx < 0) {
            titleTinyx = 0;
            titleTinydirx = -titleTinydirx;
        }
        if (titleTinyx > 30) {
            titleTinyx = 30;
            titleTinydirx = -titleTinydirx;
        }
        if (titleTinyy < 0) {  //1080
            titleTinyy = 0;
            titleTinydiry = -titleTinydiry;
        }
        if (titleTinyy > 30) {  //1080
            titleTinyy = 30;
            titleTinydiry = -titleTinydiry;
        }

        // if state =
        switch (currentState) {
            case MAIN: {
                options.update(game.input);
                break;
            }
            case TENNIS: {
                optionsTennis.update(game.input);
                break;
            }
            case GUNFIGHT: {
                optionsGunfight.update(game.input);
                break;
            }
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
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
        //Gdx.app.log("SCENE", "Dispose TitleScene");
        background.dispose();
        rainMusic.dispose();
        options.dispose();
        optionsTennis.dispose();
        optionsGunfight.dispose();
        font24.dispose();
        titleTiny.dispose();
        titleDuel.dispose();
    }
}
