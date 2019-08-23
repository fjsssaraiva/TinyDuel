package com.fsaraiva.tinyduel.engine.utils.gui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.fsaraiva.tinyduel.engine.Stage;

/**
 * Created by Fernando on 12/02/2017.
 */
public class CheckBox {
    public Rect box;
    public Rect box1;
    public Rect item;
    public Bitmap face;
    private Paint pt;
    public int x;
    public int y;
    public int width;
    public int height;
    public boolean value;

    public CheckBox (int posx, int posy, int w, int h)
    {
        x = posx;
        y = posy;
        width = w;
        height = h;

        value = false;

        box = new Rect (x, y, x+width, y+height);
        item = new Rect (0, 0, width, height);
        //box1 = new Rect (x, y, x+width*3, y+height*3);

        pt = new Paint();
        pt.setColor(Color.RED);
        pt.setStyle(Paint.Style.STROKE);
        pt.setStrokeWidth(3);
    }

    public void toggle()
    {
        value = !value;
    }

    public void draw(Canvas canvas)
    {
        //if (Stage.gameOptions_devModeOn) {
        pt.setColor(Color.LTGRAY);
            pt.setStyle(Paint.Style.STROKE);
        canvas.drawRect(new Rect((int)(box.left* Stage.zoomplusX), (int)(box.top* Stage.zoomplusY), (int)(box.right* Stage.zoomplusX), (int)(box.bottom* Stage.zoomplusY)), pt);
    }
}