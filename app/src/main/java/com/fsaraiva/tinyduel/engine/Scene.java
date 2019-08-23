package com.fsaraiva.tinyduel.engine;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.fsaraiva.tinyduel.R;
import com.fsaraiva.tinyduel.engine.utils.video.BitmapText;

import java.util.ArrayList;

/**
 * Created by Fernando on 24/03/2018.
 */

public abstract class Scene {
    protected BitmapText bitmapText;
    protected float touchX;           // screen touch coordinates
    protected float touchY;
    protected Paint pt2;
    protected Rect GUImask;
    public boolean isDirty;

    public Scene() {
        pt2 = new Paint();
        bitmapText = new BitmapText(BitmapFactory.decodeResource(Stage.context1.getResources(), R.drawable.font_emboss));  //glyphs_green
    }

    public void update() {} // update logic for this scene

    public void checkInput(MotionEvent event) {}

    public void drawScene(Canvas canvas) {} // redraw everything on screen

    public void reset() {} // load all of the data and graphics that this scene needs to function.

    public void unload() {} // unload everything that the garbage collector won’t unload, itself, including graphics

    public boolean handleBackButton() {return true;}
}