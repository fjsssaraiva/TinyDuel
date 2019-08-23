package com.fsaraiva.tinyduel.engine.utils.video;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.fsaraiva.tinyduel.R;
import com.fsaraiva.tinyduel.engine.GuiObject;

public class Sprite extends GuiObject {
    public Bitmap image;
    private Rect src_full;
    private int id;
private Paint pt;

    public Sprite(int ID, int xi, int yi, Context context) {
        x = 0;
        y = 0;
        id = ID;

        width = xi;
        height = yi;

        pt = new Paint();

        // this should be on spritesheet
        switch (ID) {
            case 1:
                //image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.heart), Stage.DeviceWIDTH, Stage.DeviceHEIGHT, true);
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart);
                break;
            case 2:
                //image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet_sprite), Stage.DeviceWIDTH, Stage.DeviceHEIGHT, true);
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet_sprite);
                break;
            case 10:
                //image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.controller), Stage.DeviceWIDTH, Stage.DeviceHEIGHT, true);
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.controller);
                break;
            case 11:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.blue_wack_sprite);
                break;
            case 12:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.red_wack_sprite);
                break;
            case 15:
                //image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.button_fire), Stage.DeviceWIDTH, Stage.DeviceHEIGHT, true);
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_fire);
                break;
            case 16:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_on);
                break;
            case 17:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_off);
                break;
            case 20:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.whack1);
                break;
            case 21:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.whacker_red);
                break;
            case 22:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.whacker_blue);
                break;
            case 23:
                //image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.carroca), Stage.DeviceWIDTH, Stage.DeviceHEIGHT, true);
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.carroca);
                break;
            case 30:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.opt_sound_on);
                break;
            case 31:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.opt_sound_off);
                break;
            case 32:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.opt_human);
                break;
            case 33:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.opt_ai);
                break;
            case 34:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.opt_human_buttons);
                break;
            case 35:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.opt_human_buttons_bottom);
                break;
            case 36:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.opt_human_buttons_side);
                break;
            case 37:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.opt_human_touch);
                break;
            case 38:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.opt_human_touch_press);
                break;
            case 39:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.opt_human_touch_target);
                break;
            case 50:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.opt_lang_eng_eu);
                break;
            case 51:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.opt_lang_pt_eu);
                break;
            case 52:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.opt_lang_fr);
                break;
            case 53:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.opt_lang_esp);
                break;
            case 54:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.opt_lang_ger);
                break;
            case 55:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.opt_lang_eng_us);
                break;
            case 56:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.opt_lang_pt_br);
                break;
            case 60:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.tennis_eyes_center);
                break;
            case 61:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.tennis_eyes_left);
                break;
            case 62:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.tennis_eyes_right);
                break;
            case 70:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.blue_shadow);
                break;
            case 71:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.red_shadow);
                break;
                /*
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
                 */
        }
        src_full = new Rect(0,0,image.getWidth(),image.getHeight());
    }

    public void update() {
    }

    public void draw(Canvas canvas) {

        super.drawPosition(true, 0, 0);
        if (id == 10 || id == 15) {
            pt.setAlpha(80);
            canvas.drawBitmap(image, src_full, getDrawingBounds(), pt);
        } else {
            canvas.drawBitmap(image, src_full, getDrawingBounds(), null);
        }
    }
    public void drawCentered(Canvas canvas) {

        super.drawPosition(true, 0, 0);
        if (id == 10 || id == 15 || id==70 || id==71) {
            pt.setAlpha(80);
            canvas.drawBitmap(image, src_full, getCenteredDrawingBounds(), pt);
        } else {
            canvas.drawBitmap(image, src_full, getCenteredDrawingBounds(), null);
        }
    }
}
