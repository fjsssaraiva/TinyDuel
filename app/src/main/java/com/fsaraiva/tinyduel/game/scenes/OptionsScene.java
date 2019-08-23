package com.fsaraiva.tinyduel.game.scenes;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.fsaraiva.tinyduel.R;
import com.fsaraiva.tinyduel.engine.Scene;
import com.fsaraiva.tinyduel.engine.Stage;
import com.fsaraiva.tinyduel.engine.map.Background;
import com.fsaraiva.tinyduel.engine.utils.audio.Sample;
import com.fsaraiva.tinyduel.engine.utils.gui.Button;
import com.fsaraiva.tinyduel.engine.utils.gui.CheckBox;
import com.fsaraiva.tinyduel.engine.utils.video.Sprite;

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

    //private int musicID;

    private int setLanguage;

    public OptionsScene(View context)
    {
        super();
        isDirty = false;
    }

    public void killScene()
    {
        staticBackgroud.image.recycle();
        buttonOn.image.recycle();
        buttonOff.image.recycle();

        imgSoundOn.image.recycle();
        imgSoundOff.image.recycle();

        imgHuman.image.recycle();
        imgAI.image.recycle();
        imgHumanButtons.image.recycle();
        imgHumanButtonsSchema1.image.recycle();
        imgHumanButtonsSchema2.image.recycle();
        imgHumanTouch.image.recycle();
        imgHumanTouchSchema1.image.recycle();
        imgHumanTouchSchema2.image.recycle();
        imgLangENG.image.recycle();
        imgLangPT.image.recycle();
        imgLangFR.image.recycle();
        imgLangES.image.recycle();
        imgLangGER.image.recycle();
        imgLangUS.image.recycle();
        imgLangBR.image.recycle();

        isDirty = false;
    }

    public void initScene(Context context)
    {
        //pt2 = new Paint();
        //bitmapText = new BitmapText(BitmapFactory.decodeResource(context.getResources(), R.drawable.font_emboss));  //glyphs_green

        //staticBackgroud = new Background(BitmapFactory.decodeResource(context.getResources(), R.drawable.fullmap_layer0));
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

    private String getTranslation(String text)
    {
        switch (Stage.gameOptions_language) {
            case 1:
            case 6: {
                //bitmapText.drawString(canvas, "ENGLISH", LANGUAGE.x, LANGUAGE.y + 130, 1);
                switch (text) {
                    case "ENGLISH": { return "ENGLISH"; }
                    case "OPTIONS": { return "OPTIONS"; }
                    case "SOUND ON": { return "SOUND ON"; }
                    case "SOUND OFF": { return "SOUND OFF"; }

                    case "HUMAN": { return "HUMAN"; }
                    case "AI": { return "AI"; }
                    case "BUTTONS": { return "BUTTONS"; }
                    case "SIDE": { return "SIDE"; }
                    case "BOTTOM": { return "DOWN"; }
                    case "TOUCH": { return "TOUCH"; }
                    case "PRESS": { return "PRESS"; }
                    case "TARGET": { return "TARGET"; }
                }
            }
            case 2:
            case 7: {
                //bitmapText.drawString(canvas, "PORTUGUES", LANGUAGE.x, LANGUAGE.y + 130, 1); break;
                switch (text) {
                    case "ENGLISH": { return "PORTUGUES"; }
                    case "OPTIONS": { return "OPÇÕES"; }
                    case "SOUND ON": { return "SOM LIGADO"; }
                    case "SOUND OFF": { return "SOM DESLIGADO"; }

                    case "HUMAN": { return "HUMANO"; }
                    case "AI": { return "IA"; }
                    case "BUTTONS": { return "BOTOES"; }
                    case "SIDE": { return "LADO"; }
                    case "BOTTOM": { return "BAIXO"; }
                    case "TOUCH": { return "TOQUE"; }
                    case "PRESS": { return "PRESSIONAR"; }
                    case "TARGET": { return "ALVO"; }
                }
            }
            case 3: {
                //bitmapText.drawString(canvas, "FRANCAIS", LANGUAGE.x, LANGUAGE.y + 130, 1); break;
                switch (text) {
                    case "ENGLISH": { return "FRANCAIS"; }
                    case "OPTIONS": { return "OPTIONS"; }
                    case "SOUND ON": { return "SON ON"; }
                    case "SOUND OFF": { return "SON OFF"; }

                    case "HUMAN": { return "HUMAIN"; }
                    case "AI": { return "IA"; }
                    case "BUTTONS": { return "BOUTONS"; }
                    case "SIDE": { return "COTE"; }
                    case "BOTTOM": { return "VERS LE BAS"; }
                    case "TOUCH": { return "TOUCHER"; }
                    case "PRESS": { return "PRESSE"; }
                    case "TARGET": { return "CIBLE"; }
                }
            }
            case 4: {
                //bitmapText.drawString(canvas, "ESPANOL", LANGUAGE.x, LANGUAGE.y + 130, 1); break;
                switch (text) {
                    case "ENGLISH": { return "ESPANOL"; }
                    case "OPTIONS": { return "OPCIONES"; }
                    case "SOUND ON": { return "SONIDO ENCENDIDO"; }
                    case "SOUND OFF": { return "SONIDO APAGADO"; }

                    case "HUMAN": { return "HUMANO"; }
                    case "AI": { return "IA"; }
                    case "BUTTONS": { return "BOTONES"; }
                    case "SIDE": { return "LADO"; }
                    case "BOTTOM": { return "ABAJO"; }
                    case "TOUCH": { return "TOQUE"; }
                    case "PRESS": { return "PRESIONAR"; }
                    case "TARGET": { return "OBJETIVO"; }
                }
            }
            case 5: {
                //bitmapText.drawString(canvas, "DEUTCH", LANGUAGE.x, LANGUAGE.y + 130, 1); break;
                switch (text) {
                    case "ENGLISH": { return "DEUTSCH"; }
                    case "OPTIONS": { return "OPTIONEN"; }
                    case "SOUND ON": { return "TON AN"; }
                    case "SOUND OFF": { return "TON AUS"; }

                    case "HUMAN": { return "MENSCH"; }
                    case "AI": { return "AI"; }
                    case "BUTTONS": { return "TASTEN"; }
                    case "SIDE": { return "SEITE"; }
                    case "BOTTOM": { return "NIEDER"; }
                    case "TOUCH": { return "BERÜHREN"; }
                    case "PRESS": { return "DRUCKEN"; }
                    case "TARGET": { return "ZIEL"; }
                }
            }
            default: {
                return "ERROR";
            }
        }
    }

    public void drawScene(Canvas canvas)
    {
        staticBackgroud.draw(canvas, true, 0, 0, 0, 0);

        //bitmapText.drawString(canvas, "OPTIONS", (Stage.viewport_current_size_X/2)-150, 50, 3);
        bitmapText.drawString(canvas, getTranslation("OPTIONS"), (Stage.viewport_current_size_X/2)-150, 50, 3);
        //bitmapText.drawString(canvas, "RESET GAME STATISTICS                NO", 250, 150, 1);
        /*
        bitmapText.drawString(canvas, "ENABLE SOUND", 250, 200, 2);
        bitmapText.drawString(canvas, "BLUE HUMAN", 250, 275, 2);
        bitmapText.drawString(canvas, "RED HUMAN", 250, 350, 2);
        //bitmapText.drawString(canvas, "ENABLE IN GAME HELP                  NO", 250, 350, 1);
        //bitmapText.drawString(canvas, "ENABLE IN GAME ZOOM BUTTONS          YES", 250, 400, 1);
        */

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


/*
enum diccionary language
hash table dic

1-ENGLISH
2-PORTUGUES
3-FRANCAIS
4-ESPANOL
5-DEUTCH
6-ENGLISH
7-PORTUGUES

1,6-OPTIONS
SOUND ON
SOUND OFF
HUMAN
AI
BUTTONS
BOTTOM
SIDE
TOUCH
PRESS
TARGET
BACK
WHACKER
GUNFIGHT
TENNIS
OPTIONS
HELP

PRESS ANYWHERE TO START
MAIN MENU
BLUE WINS
RED WINS

 */