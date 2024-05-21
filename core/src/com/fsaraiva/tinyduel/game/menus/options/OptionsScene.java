package com.fsaraiva.tinyduel.game.menus.options;

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
import com.fsaraiva.tinyduel.engine.ui.Checkbox;
import com.fsaraiva.tinyduel.engine.ui.OptionsList;
import com.fsaraiva.tinyduel.engine.ui.Slider;
import com.fsaraiva.tinyduel.engine.ui.UIObject;
import com.fsaraiva.tinyduel.engine.video.BackgroundLayer;
import com.fsaraiva.tinyduel.game.menus.mainmenu.MainMenuScene;

public class OptionsScene implements Screen {

    final TinyDuel game;
    BackgroundLayer background;
    OrthographicCamera camera;
    Music rainMusic;
    public BitmapFont font24;
    OptionsList options;

    public OptionsScene(final TinyDuel gam) {
        //Gdx.app.log("SCENE", "Created new OptionsScene");
        game = gam;
        background = new BackgroundLayer("settings2.png", 0);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080); //1280, 720);

        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/main.mp3")); //mp3
        //rainMusic.setVolume(0.1f);
        rainMusic.setVolume((float)game.musicLvl / 10);
        rainMusic.setLooping(true);

        Gdx.input.setCatchKey(Input.Keys.BACK, false);
        //Gdx.input.setCatchKey(Input.Keys.MENU, true);
        options = new OptionsList();
        options.AddOption(new Checkbox(600, 900, 94, 50,1)); //useOnScreenCtrl1
        options.AddOption(new Checkbox(600, 800, 94, 50,2)); //useOnScreenCtrl2
        options.AddOption(new Checkbox(600, 700, 94, 50,3)); //debugmode1
        options.AddOption(new Checkbox(600, 600, 94, 50,4)); //debugmode2
        options.AddOption(new Button("controller/exit.png", "controller/exit_active.png", 1800, 100, 100, 100,5));
        options.AddOption(new Checkbox(600, 500, 94, 50,6)); //music on-off
        options.AddOption(new Checkbox(600, 400, 94, 50,7)); //sound on-off

        options.AddOption(new Slider(600, 300, 282, 50,8)); //sound slider
        options.AddOption(new Slider(600, 200, 282, 50,9)); //sound slider

        options.widgets.get(0).setValueBool(game.input.useOnScreenCtrl1);
        options.widgets.get(1).setValueBool(game.input.useOnScreenCtrl2);
        options.widgets.get(2).setValueBool(game.DebugMode);
        options.widgets.get(3).setValueBool(game.DebugMode2);
        options.widgets.get(5).setValueBool(game.musicOn);
        options.widgets.get(6).setValueBool(game.soundOn);
        options.widgets.get(7).setValueInt(game.soundLvl);
        options.widgets.get(8).setValueInt(game.musicLvl);

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
    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(background.getRenderImg(0, 0, 1920, 1080), 0, 0, 1920, 1080);
        font24.getData().setScale(1, 1);
        font24.setColor(Color.WHITE);
        font24.draw(game.batch, "FPS: " + Float.toString(Gdx.graphics.getFramesPerSecond()), 5, 45); //1075);
        //font24.draw(game.batch, "FPS: " + Float.toString(Gdx.graphics.), 5, 65); //1075);

        options.render(game.batch);

        font24.setColor(Color.CYAN);
        font24.draw(game.batch, "Music", options.widgets.get(5).x + options.widgets.get(5).width +20, options.widgets.get(5).y+50);
        font24.draw(game.batch, "Sound", options.widgets.get(6).x + options.widgets.get(6).width +20, options.widgets.get(6).y+45);
        font24.draw(game.batch, "On Screen Ctrl player 1", options.widgets.get(0).x + options.widgets.get(0).width +20, options.widgets.get(0).y+45);
        font24.draw(game.batch, "On Screen Ctrl player 2", options.widgets.get(1).x + options.widgets.get(0).width +20, options.widgets.get(1).y+45);
        font24.draw(game.batch, "Enable debug mode", options.widgets.get(2).x + options.widgets.get(0).width +20, options.widgets.get(2).y+45);
        font24.draw(game.batch, "Draw collision boxes", options.widgets.get(3).x + options.widgets.get(0).width +20, options.widgets.get(3).y+45);
        font24.draw(game.batch, "Sound lvl " + options.widgets.get(7).getValueInt(), options.widgets.get(7).x + options.widgets.get(7).width + 80, options.widgets.get(7).y+45);
        font24.draw(game.batch, "Music lvl " + options.widgets.get(8).getValueInt(), options.widgets.get(8).x + options.widgets.get(8).width + 80, options.widgets.get(8).y+45);

        game.batch.end();

        update();
        checkInput();
    }

    public void checkInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            //Gdx.app.log("INPUT", "esc");
            dispose();
            game.setScreen(new MainMenuScene(game));
        }

        //for (Button btn : options.widgets) {
        for (int i=0; i < options.widgets.size; i++) {
            UIObject btn = options.widgets.get(i);
            if (btn.box.overlaps(new Rectangle(game.input.touchX - 5, game.input.touchY - 5, 10, 10))) {
                //game.input.clearInput();
                checkAction(btn.id);
            }
        }

        if ((game.input.keys.contains("space") || game.input.keys.contains("left") || game.input.keys.contains("right")) && options.selectedwidget != 0) {
            //rainMusic.stop();
            //dispose();
            //game.input.clearInput();
            checkAction(options.selectedwidget);
        }
    }

    public void checkAction(int action) {
        switch (action) {
            case 1: {
                game.input.clearInput();
                options.widgets.get(0).setValueBool(!options.widgets.get(0).getValueBool());
                game.input.useOnScreenCtrl1 = options.widgets.get(0).getValueBool();
                game.settings.prefs.putBoolean("useOnScreenCtrl1", game.input.useOnScreenCtrl1);
                game.settings.prefs.flush();
                break;
            }
            case 2: {
                game.input.clearInput();
                options.widgets.get(1).setValueBool(!options.widgets.get(1).getValueBool());
                game.input.useOnScreenCtrl2 = options.widgets.get(1).getValueBool();
                game.settings.prefs.putBoolean("useOnScreenCtrl2", game.input.useOnScreenCtrl2);
                game.settings.prefs.flush();
                break;
            }
            case 3: {
                game.input.clearInput();
                options.widgets.get(2).setValueBool(!options.widgets.get(2).getValueBool());
                game.DebugMode = options.widgets.get(2).getValueBool();
                game.settings.prefs.putBoolean("DebugMode", game.DebugMode);
                game.settings.prefs.flush();
                break;
            }
            case 4: {
                game.input.clearInput();
                options.widgets.get(3).setValueBool(!options.widgets.get(3).getValueBool());
                game.DebugMode2 = options.widgets.get(3).getValueBool();
                game.settings.prefs.putBoolean("DebugMode2", game.DebugMode2);
                game.settings.prefs.flush();
                break;
            }
            case 5: {
                //Gdx.app.log("INPUT", "touch BACK key");
                if (game.musicLvl > 0) {
                    rainMusic.stop();
                }
                dispose();
                game.input.clearInput();
                game.setScreen(new MainMenuScene(game));
                break;
            }
            case 6: {
                game.input.clearInput();
                options.widgets.get(5).setValueBool(!options.widgets.get(5).getValueBool());
                game.musicOn = options.widgets.get(5).getValueBool();
                game.settings.prefs.putBoolean("musicOn", game.musicOn);
                game.settings.prefs.flush();
                break;
            }
            case 7: {
                game.input.clearInput();
                options.widgets.get(6).setValueBool(!options.widgets.get(6).getValueBool());
                game.soundOn = options.widgets.get(6).getValueBool();
                game.settings.prefs.putBoolean("soundOn", game.soundOn);
                game.settings.prefs.flush();
                break;
            }
            case 8: {
                // check keys
                options.widgets.get(7).update(game.input);

                // move this inside Slider class
                if (game.input.touchX > (options.widgets.get(7).x + (float)(options.widgets.get(7).width / 2))) {
                    options.widgets.get(7).setValueInt(options.widgets.get(7).getValueInt() + 1);
                    if (options.widgets.get(7).getValueInt() > 10) {
                        options.widgets.get(7).setValueInt(10);
                    }
                }
                if (game.input.touchX > 0 && game.input.touchX < (options.widgets.get(7).x + (float)(options.widgets.get(7).width / 2))) {
                    options.widgets.get(7).setValueInt(options.widgets.get(7).getValueInt() - 1);
                    if (options.widgets.get(7).getValueInt() < 0) {
                        options.widgets.get(7).setValueInt(0);
                    }
                }

                game.input.clearInput();
                game.soundLvl = options.widgets.get(7).getValueInt();
                game.settings.prefs.putInteger("soundLvl", game.soundLvl);
                game.settings.prefs.flush();

                //Gdx.app.log("vol", "lvl: " + (float)(game.soundLvl / 10.0f));
                break;
            }
            case 9: {
                // check keys
                options.widgets.get(8).update(game.input);

                // move this inside Slider class
                if (game.input.touchX > (options.widgets.get(8).x + (float)(options.widgets.get(8).width / 2))) {
                    options.widgets.get(8).setValueInt(options.widgets.get(8).getValueInt() + 1);
                    if (options.widgets.get(8).getValueInt() > 10) {
                        options.widgets.get(8).setValueInt(10);
                    }
                }
                if (game.input.touchX > 0 && game.input.touchX < (options.widgets.get(8).x + (float)(options.widgets.get(8).width / 2))) {
                    options.widgets.get(8).setValueInt(options.widgets.get(8).getValueInt() - 1);
                    if (options.widgets.get(8).getValueInt() < 0) {
                        options.widgets.get(8).setValueInt(0);
                    }
                }

                game.input.clearInput();
                game.musicLvl = options.widgets.get(8).getValueInt();
                game.settings.prefs.putInteger("musicLvl", game.musicLvl);
                game.settings.prefs.flush();

                //Gdx.app.log("music", "lvl: " + ((float)game.musicLvl / 10.0f));
                rainMusic.stop();
                rainMusic.setVolume(((float)game.musicLvl / 10.0f));
                rainMusic.play();
                //Gdx.app.log("upd", "upd music lvl");
                break;
            }
        }
    }

    public void update() {
        options.update(game.input);
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
        font24.dispose();
        background.dispose();
        options.dispose();
        rainMusic.dispose();
    }
}

/*
public class OptionsScene extends Scene {

    private Background staticBackgroud;
    //Rect GUImask;
    //Paint pt2;
    //private BitmapText bitmapText;
    private CheckBox KIDMODE_ON;
    private CheckBox SOUND_ON;
    private CheckBox DEVMODE_ON;
    private CheckBox BLUE_HUMAN_ON;
    private CheckBox RED_HUMAN_ON;
    private CheckBox BLUE_HUMAN_SCHEMA1;
    private CheckBox RED_HUMAN_SCHEMA1;
    private CheckBox LANGUAGE;
    private CheckBox BLUE_HUMAN_BUTTONS;
    private CheckBox RED_HUMAN_BUTTONS;

    private Sprite buttonOn;
    private Sprite buttonOff;
    private Sprite imgSoundOn;
    private Sprite imgSoundOff;
    private Sprite imgHuman;
    private Sprite imgAI;
    private Sprite imgHumanButtons;
    private Sprite imgHumanButtonsSchema1;
    private Sprite imgHumanButtonsSchema2;
    private Sprite imgHumanTouch;
    private Sprite imgHumanTouchSchema1;
    private Sprite imgHumanTouchSchema2;

    private Sprite imgLangENG;
    private Sprite imgLangPT;
    private Sprite imgLangFR;
    private Sprite imgLangES;
    private Sprite imgLangGER;
    private Sprite imgLangUS;
    private Sprite imgLangBR;

    private Button BACK;
    private Button NEXT;

    private int setLanguage;

    public void initScene(Context context)
    {
        staticBackgroud = new Background(BitmapFactory.decodeResource(context.getResources(), R.drawable.new_options));

        buttonOn = new Sprite(16, 94, 50, context);
        buttonOff = new Sprite(17, 94, 50, context);

        imgSoundOn = new Sprite(30, 120, 120, context);
        imgSoundOff = new Sprite(31, 120, 120, context);
        imgHuman = new Sprite(32, 120, 120, context);
        imgAI = new Sprite(33, 120, 120, context);
        imgHumanButtons = new Sprite(34, 120, 120, context);
        imgHumanButtonsSchema1 = new Sprite(35, 120, 120, context);
        imgHumanButtonsSchema2 = new Sprite(36, 120, 120, context);
        imgHumanTouch = new Sprite(37, 120, 120, context);
        imgHumanTouchSchema1 = new Sprite(38, 120, 120, context);
        imgHumanTouchSchema2 = new Sprite(39, 120, 120, context);

        imgLangENG = new Sprite(50, 120, 120, context);
        imgLangPT = new Sprite(51, 120, 120, context);
        imgLangFR = new Sprite(52, 120, 120, context);
        imgLangES = new Sprite(53, 120, 120, context);
        imgLangGER = new Sprite(54, 120, 120, context);
        imgLangUS = new Sprite(55, 120, 120, context);
        imgLangBR = new Sprite(56, 120, 120, context);

        BLUE_HUMAN_ON = new CheckBox(75, 220, 120, 120);
        BLUE_HUMAN_ON.value = Stage.gameOptions_blueHumanOn;
        RED_HUMAN_ON = new CheckBox(740, 220, 120, 120);
        RED_HUMAN_ON.value = Stage.gameOptions_redHumanOn;
        BLUE_HUMAN_BUTTONS = new CheckBox(240, 220, 120, 120);
        BLUE_HUMAN_BUTTONS.value = Stage.gameOptions_blueUseButtons;
        RED_HUMAN_BUTTONS = new CheckBox(900, 220, 120, 120);
        RED_HUMAN_BUTTONS.value = Stage.gameOptions_redUseButtons;
        BLUE_HUMAN_SCHEMA1 = new CheckBox(400, 220, 120, 120);
        BLUE_HUMAN_SCHEMA1.value = Stage.gameOptions_blueUseSchema1;
        RED_HUMAN_SCHEMA1 = new CheckBox(1060, 220, 120, 120);
        RED_HUMAN_SCHEMA1.value = Stage.gameOptions_redUseSchema1;

        SOUND_ON = new CheckBox(75, 430, 120, 120);  //affects gameOptions_kidModeOn  850, 550
        SOUND_ON.value = Stage.gameOptions_soundOn;
        LANGUAGE = new CheckBox(310, 430, 120, 120);
        //LANGUAGE..value = Stage.gameOptions_language;
        DEVMODE_ON = new CheckBox(1050, 450, 94, 50);  //affects gameOptions_kidModeOn  850, 550
        DEVMODE_ON.value = Stage.gameOptions_devModeOn;
        KIDMODE_ON = new CheckBox(1050, 530, 94, 50);  //affects gameOptions_kidModeOn  850, 550
        KIDMODE_ON.value = Stage.gameOptions_kidModeOn;

        BACK = new Button(Stage.viewport_current_size_X-200, Stage.viewport_current_size_Y-50, 150, 50);
        NEXT = new Button(200, Stage.viewport_current_size_Y-50, 150, 50);

        imgLangENG.setX(LANGUAGE.x); imgLangENG.setY(LANGUAGE.y);
        imgLangPT.setX(LANGUAGE.x); imgLangPT.setY(LANGUAGE.y);
        imgLangFR.setX(LANGUAGE.x); imgLangFR.setY(LANGUAGE.y);
        imgLangES.setX(LANGUAGE.x); imgLangES.setY(LANGUAGE.y);
        imgLangGER.setX(LANGUAGE.x); imgLangGER.setY(LANGUAGE.y);
        imgLangUS.setX(LANGUAGE.x); imgLangUS.setY(LANGUAGE.y);
        imgLangBR.setX(LANGUAGE.x); imgLangBR.setY(LANGUAGE.y);

        setLanguage = Stage.gameOptions_language;

        isDirty = true;
        //Sample.playSoundLoop(6);
    }

    public void drawScene(Canvas canvas)
    {
        staticBackgroud.draw(canvas, true, 0, 0, 0, 0);

        //bitmapText.drawString(canvas, "OPTIONS", (Stage.viewport_current_size_X/2)-150, 50, 3);
        bitmapText.drawString(canvas, getTranslation("OPTIONS"), (Stage.viewport_current_size_X/2)-150, 50, 3);
        //bitmapText.drawString(canvas, "RESET GAME STATISTICS                NO", 250, 150, 1);

        //bitmapText.drawString(canvas, "ENABLE SOUND", 250, 200, 2);
        //bitmapText.drawString(canvas, "BLUE HUMAN", 250, 275, 2);
        //bitmapText.drawString(canvas, "RED HUMAN", 250, 350, 2);
        //bitmapText.drawString(canvas, "ENABLE IN GAME HELP                  NO", 250, 350, 1);
        //bitmapText.drawString(canvas, "ENABLE IN GAME ZOOM BUTTONS          YES", 250, 400, 1);


        if (Stage.gameOptions_soundOn) { // SOUND_ON.value
            bitmapText.drawString(canvas, getTranslation("SOUND ON"), SOUND_ON.x, SOUND_ON.y + 130, 1);
            imgSoundOn.setX(SOUND_ON.x); imgSoundOn.setY(SOUND_ON.y); imgSoundOn.draw(canvas);
        } else {
            bitmapText.drawString(canvas, getTranslation("SOUND OFF"), SOUND_ON.x, SOUND_ON.y + 130, 1);
            imgSoundOff.setX(SOUND_ON.x); imgSoundOff.setY(SOUND_ON.y); imgSoundOff.draw(canvas);
        }

        if (Stage.gameOptions_blueHumanOn) { // SOUND_ON.value
            bitmapText.drawString(canvas, getTranslation("HUMAN"), BLUE_HUMAN_ON.x, BLUE_HUMAN_ON.y + 130, 1);
            imgHuman.setX(BLUE_HUMAN_ON.x); imgHuman.setY(BLUE_HUMAN_ON.y); imgHuman.draw(canvas);
        } else {
            bitmapText.drawString(canvas, getTranslation("AI"), BLUE_HUMAN_ON.x, BLUE_HUMAN_ON.y + 130, 1);
            imgAI.setX(BLUE_HUMAN_ON.x); imgAI.setY(BLUE_HUMAN_ON.y); imgAI.draw(canvas);
        }
        if (Stage.gameOptions_blueHumanOn) {
            if (Stage.gameOptions_blueUseButtons) { // SOUND_ON.value
                bitmapText.drawString(canvas, getTranslation("BUTTONS"), BLUE_HUMAN_BUTTONS.x, BLUE_HUMAN_BUTTONS.y + 130, 1);
                imgHumanButtons.setX(BLUE_HUMAN_BUTTONS.x); imgHumanButtons.setY(BLUE_HUMAN_BUTTONS.y); imgHumanButtons.draw(canvas);
                if (Stage.gameOptions_blueUseSchema1) {
                    bitmapText.drawString(canvas, getTranslation("BOTTOM"), BLUE_HUMAN_SCHEMA1.x, BLUE_HUMAN_SCHEMA1.y + 130, 1);
                    imgHumanButtonsSchema1.setX(BLUE_HUMAN_SCHEMA1.x); imgHumanButtonsSchema1.setY(BLUE_HUMAN_SCHEMA1.y); imgHumanButtonsSchema1.draw(canvas);
                } else {
                    bitmapText.drawString(canvas, getTranslation("SIDE"), BLUE_HUMAN_SCHEMA1.x, BLUE_HUMAN_SCHEMA1.y + 130, 1);
                    imgHumanButtonsSchema2.setX(BLUE_HUMAN_SCHEMA1.x); imgHumanButtonsSchema2.setY(BLUE_HUMAN_SCHEMA1.y); imgHumanButtonsSchema2.draw(canvas);
                }
            } else {
                bitmapText.drawString(canvas, getTranslation("TOUCH"), BLUE_HUMAN_BUTTONS.x, BLUE_HUMAN_BUTTONS.y + 130, 1);
                imgHumanTouch.setX(BLUE_HUMAN_BUTTONS.x); imgHumanTouch.setY(BLUE_HUMAN_BUTTONS.y); imgHumanTouch.draw(canvas);
                if (Stage.gameOptions_blueUseSchema1) {
                    bitmapText.drawString(canvas, getTranslation("PRESS"), BLUE_HUMAN_SCHEMA1.x, BLUE_HUMAN_SCHEMA1.y + 130, 1);
                    imgHumanTouchSchema1.setX(BLUE_HUMAN_SCHEMA1.x); imgHumanTouchSchema1.setY(BLUE_HUMAN_SCHEMA1.y); imgHumanTouchSchema1.draw(canvas);
                } else {
                    bitmapText.drawString(canvas, getTranslation("TARGET"), BLUE_HUMAN_SCHEMA1.x, BLUE_HUMAN_SCHEMA1.y + 130, 1);
                    imgHumanTouchSchema2.setX(BLUE_HUMAN_SCHEMA1.x); imgHumanTouchSchema2.setY(BLUE_HUMAN_SCHEMA1.y); imgHumanTouchSchema2.draw(canvas);
                }
            }
        }

        if (Stage.gameOptions_redHumanOn) { // SOUND_ON.value
            bitmapText.drawString(canvas, getTranslation("HUMAN"), RED_HUMAN_ON.x, RED_HUMAN_ON.y + 130, 1);
            //buttonOn.setX(850); buttonOn.setY(350); buttonOn.draw(canvas);
            imgHuman.setX(RED_HUMAN_ON.x); imgHuman.setY(RED_HUMAN_ON.y); imgHuman.draw(canvas);
        } else {
            bitmapText.drawString(canvas, getTranslation("AI"), RED_HUMAN_ON.x, RED_HUMAN_ON.y + 130, 1);
            //buttonOff.setX(850); buttonOff.setY(350); buttonOff.draw(canvas);
            imgAI.setX(RED_HUMAN_ON.x); imgAI.setY(RED_HUMAN_ON.y); imgAI.draw(canvas);
        }
        if (Stage.gameOptions_redHumanOn) {
            if (Stage.gameOptions_redUseButtons) { // SOUND_ON.value
                bitmapText.drawString(canvas, getTranslation("BUTTONS"), RED_HUMAN_BUTTONS.x, RED_HUMAN_BUTTONS.y + 130, 1);
                imgHumanButtons.setX(RED_HUMAN_BUTTONS.x); imgHumanButtons.setY(RED_HUMAN_BUTTONS.y); imgHumanButtons.draw(canvas);
                if (Stage.gameOptions_redUseSchema1) {
                    bitmapText.drawString(canvas, getTranslation("BOTTOM"), RED_HUMAN_SCHEMA1.x, RED_HUMAN_SCHEMA1.y + 130, 1);
                    imgHumanButtonsSchema1.setX(RED_HUMAN_SCHEMA1.x); imgHumanButtonsSchema1.setY(RED_HUMAN_SCHEMA1.y); imgHumanButtonsSchema1.draw(canvas);
                } else {
                    bitmapText.drawString(canvas, getTranslation("SIDE"), RED_HUMAN_SCHEMA1.x, RED_HUMAN_SCHEMA1.y + 130, 1);
                    imgHumanButtonsSchema2.setX(RED_HUMAN_SCHEMA1.x); imgHumanButtonsSchema2.setY(RED_HUMAN_SCHEMA1.y); imgHumanButtonsSchema2.draw(canvas);
                }
            } else {
                bitmapText.drawString(canvas, getTranslation("TOUCH"), RED_HUMAN_BUTTONS.x, RED_HUMAN_BUTTONS.y + 130, 1);
                imgHumanTouch.setX(RED_HUMAN_BUTTONS.x); imgHumanTouch.setY(RED_HUMAN_BUTTONS.y); imgHumanTouch.draw(canvas);
                if (Stage.gameOptions_redUseSchema1) {
                    bitmapText.drawString(canvas, getTranslation("PRESS"), RED_HUMAN_SCHEMA1.x, RED_HUMAN_SCHEMA1.y + 130, 1);
                    imgHumanTouchSchema1.setX(RED_HUMAN_SCHEMA1.x); imgHumanTouchSchema1.setY(RED_HUMAN_SCHEMA1.y); imgHumanTouchSchema1.draw(canvas);
                } else {
                    bitmapText.drawString(canvas, getTranslation("TARGET"), RED_HUMAN_SCHEMA1.x, RED_HUMAN_SCHEMA1.y + 130, 1);
                    imgHumanTouchSchema2.setX(RED_HUMAN_SCHEMA1.x); imgHumanTouchSchema2.setY(RED_HUMAN_SCHEMA1.y); imgHumanTouchSchema2.draw(canvas);
                }
            }
        }

        bitmapText.drawString(canvas, "DEV VALS", 750, 450, 2);
        bitmapText.drawString(canvas, "KID MODE", 750, 530, 2);

        if (Stage.gameOptions_devModeOn) { // DEVMODE_ON.value
            //bitmapText.drawString(canvas, "YES", 850, 450, 2);
            buttonOn.setX(1050); buttonOn.setY(450); buttonOn.draw(canvas);
        } else {
            //bitmapText.drawString(canvas, "NO", 850, 450, 2);
            buttonOff.setX(1050); buttonOff.setY(450); buttonOff.draw(canvas);
        }

        if (Stage.gameOptions_kidModeOn) { // KIDMODE_ON.value
            //bitmapText.drawString(canvas, "YES", 850, 550, 2);
            buttonOn.setX(1050); buttonOn.setY(530); buttonOn.draw(canvas);
        } else {
            //bitmapText.drawString(canvas, "NO", 850, 550, 2);
            buttonOff.setX(1050); buttonOff.setY(530); buttonOff.draw(canvas);
        }
        //

        //LANGUAGE
        switch (setLanguage) {
            case 1: {
                imgLangENG.draw(canvas);
                //bitmapText.drawString(canvas, "ENGLISH", LANGUAGE.x, LANGUAGE.y + 130, 1);
                break;
            }
            case 2: {
                imgLangPT.draw(canvas);
                //bitmapText.drawString(canvas, "PORTUGUES", LANGUAGE.x, LANGUAGE.y + 130, 1);
                break;
            }
            case 3: {
                imgLangFR.draw(canvas);
                //bitmapText.drawString(canvas, "FRANCAIS", LANGUAGE.x, LANGUAGE.y + 130, 1);
                break;
            }
            case 4: {
                imgLangES.draw(canvas);
                //bitmapText.drawString(canvas, "ESPANOL", LANGUAGE.x, LANGUAGE.y + 130, 1);
                break;
            }
            case 5: {
                imgLangGER.draw(canvas);
                //bitmapText.drawString(canvas, "DEUTCH", LANGUAGE.x, LANGUAGE.y + 130, 1);
                break;
            }
            case 6: {
                imgLangUS.draw(canvas);
                //bitmapText.drawString(canvas, "ENGLISH", LANGUAGE.x, LANGUAGE.y + 130, 1);
                break;
            }
            case 7: {
                imgLangBR.draw(canvas);
                //bitmapText.drawString(canvas, "PORTUGUES", LANGUAGE.x, LANGUAGE.y + 130, 1);
                break;
            }
        }
        bitmapText.drawString(canvas, getTranslation("ENGLISH"), LANGUAGE.x, LANGUAGE.y + 130, 1);

        bitmapText.drawString(canvas, "BACK", BACK.x, BACK.y, 2);
        //BACK.draw(canvas);
        //KIDMODE_ON.draw(canvas);
        //SOUND_ON.draw(canvas);
        //BLUE_HUMAN_ON.draw(canvas);
        //RED_HUMAN_ON.draw(canvas);
        //BLUE_HUMAN_BUTTONS.draw(canvas);
        //RED_HUMAN_BUTTONS.draw(canvas);
        //DEVMODE_ON.draw(canvas);
    }

    public void checkInput(MotionEvent event)
    {
        // get touch screen coordinates
        touchX = event.getX();
        touchY = event.getY();
        Rect touchArea = new Rect((int) ((touchX-10)/Stage.zoomplusX), (int) ((touchY-10)/Stage.zoomplusY), (int) ((touchX+10)/Stage.zoomplusX), (int) ((touchY+10)/Stage.zoomplusY));
        // Check for Action_DOWN only, to prevent option cycling
        int maskedAction = event.getActionMasked();

        // FirebaseCrash.log("Activity created");
        switch (maskedAction) {
            case MotionEvent.ACTION_DOWN:
                if (Rect.intersects(touchArea, KIDMODE_ON.box)) {
                    //Crashlytics.getInstance().crash(); // Force a crash
                    KIDMODE_ON.toggle();
                    Stage.gameOptions_kidModeOn = KIDMODE_ON.value;
                    if (Stage.gameOptions_soundOn) {
                        Sample.playSound(5);
                    }
                    Stage.snapshotHandler.writeOptions(Stage.context1);
                }
                if (Rect.intersects(touchArea, SOUND_ON.box)) {
                    SOUND_ON.toggle();
                    Stage.gameOptions_soundOn = SOUND_ON.value;
                    if (Stage.gameOptions_soundOn) {
                        Sample.playSound(5);
                        Sample.resumeAll();
                    } else {
                        Sample.pauseAll();
                    }
                    Stage.snapshotHandler.writeOptions(Stage.context1);
                }

                if (Rect.intersects(touchArea, BLUE_HUMAN_ON.box)) {
                    BLUE_HUMAN_ON.toggle();
                    Stage.gameOptions_blueHumanOn = BLUE_HUMAN_ON.value;
                    if (Stage.gameOptions_soundOn) {
                        Sample.playSound(5);
                    }
                    Stage.snapshotHandler.writeOptions(Stage.context1);
                }
                if (Rect.intersects(touchArea, BLUE_HUMAN_BUTTONS.box)) {
                    BLUE_HUMAN_BUTTONS.toggle();
                    Stage.gameOptions_blueUseButtons = BLUE_HUMAN_BUTTONS.value;
                    if (Stage.gameOptions_soundOn) {
                        Sample.playSound(5);
                    }
                    Stage.snapshotHandler.writeOptions(Stage.context1);
                }
                if (Rect.intersects(touchArea, BLUE_HUMAN_SCHEMA1.box)) {
                    BLUE_HUMAN_SCHEMA1.toggle();
                    Stage.gameOptions_blueUseSchema1 = BLUE_HUMAN_SCHEMA1.value;
                    if (Stage.gameOptions_soundOn) {
                        Sample.playSound(5);
                    }
                    Stage.snapshotHandler.writeOptions(Stage.context1);
                }
                if (Rect.intersects(touchArea, RED_HUMAN_ON.box)) {
                    RED_HUMAN_ON.toggle();
                    Stage.gameOptions_redHumanOn = RED_HUMAN_ON.value;
                    if (Stage.gameOptions_soundOn) {
                        Sample.playSound(5);
                    }
                    Stage.snapshotHandler.writeOptions(Stage.context1);
                }
                if (Rect.intersects(touchArea, RED_HUMAN_BUTTONS.box)) {
                    RED_HUMAN_BUTTONS.toggle();
                    Stage.gameOptions_redUseButtons = RED_HUMAN_BUTTONS.value;
                    if (Stage.gameOptions_soundOn) {
                        Sample.playSound(5);
                    }
                    Stage.snapshotHandler.writeOptions(Stage.context1);
                }
                if (Rect.intersects(touchArea, RED_HUMAN_SCHEMA1.box)) {
                    RED_HUMAN_SCHEMA1.toggle();
                    Stage.gameOptions_redUseSchema1 = RED_HUMAN_SCHEMA1.value;
                    if (Stage.gameOptions_soundOn) {
                        Sample.playSound(5);
                    }
                    Stage.snapshotHandler.writeOptions(Stage.context1);
                }

                if (Rect.intersects(touchArea, LANGUAGE.box)) {
                    //DEVMODE_ON.toggle();
                    setLanguage++;
                    if (setLanguage > 7) {
                        setLanguage = 1;
                    }
                    Stage.gameOptions_language = setLanguage;
                    if (Stage.gameOptions_soundOn) {
                        Sample.playSound(5);
                    }
                    Stage.snapshotHandler.writeOptions(Stage.context1);
                }

                if (Rect.intersects(touchArea, DEVMODE_ON.box)) {
                    DEVMODE_ON.toggle();
                    Stage.gameOptions_devModeOn = DEVMODE_ON.value;
                    if (Stage.gameOptions_soundOn) {
                        Sample.playSound(5);
                    }
                    Stage.snapshotHandler.writeOptions(Stage.context1);
                }

                if (Rect.intersects(touchArea, BACK.box)) {
                    //Sample.resumeMusic();
                    if (Stage.gameOptions_soundOn) {
                        Sample.playSound(5);
                    }
                    Stage.titleScene.initScene(Stage.context1);
                    Stage.currentScene = Stage.GameState.TITLE_MENU;
                    Stage.currScene = Stage.titleScene;
                }
                break;
        }
    }
}
 */
