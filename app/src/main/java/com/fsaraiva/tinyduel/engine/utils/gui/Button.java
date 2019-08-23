package com.fsaraiva.tinyduel.engine.utils.gui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.fsaraiva.tinyduel.engine.Stage;

/**
 * Created by Fernando on 30/10/2016.
 */
public class Button {

    public Rect box, dest;
    public Rect box1, box2;
    public Rect item;
    public Bitmap face;
    private Paint pt;
    public int x;
    public int y;
    public int width;
    public int height;
    public boolean bActive;

    public Button (int posx, int posy, int w, int h) {
        x = posx;
        y = posy;
        width = w;
        height = h;

        face = null;
        box = new Rect (x, y, x+width, y+height);
        item = new Rect (0, 0, width, height);

        //float scalexf = Stage.DeviceWIDTH / Stage.viewport_current_size_X;  //reference size, where original was tested
        //float scaleyf = Stage.DeviceHEIGHT / Stage.viewport_current_size_Y;

        //box1 = new Rect (x, y, (x+width)*Stage.zoomplusX, (y+height)*Stage.zoomplusY); //zoom?
        //box1 = new Rect (x, y, (int)(x+width*scalexf), (int)(y+height*scaleyf)); //zoom?
        //box2 = new Rect (x, y, x+100, y+100);
        //box1 = new Rect (x, y, (int)(x+width*Stage.zoomplusX), (int)(y+height*Stage.zoomplusY));
        pt = new Paint();
        pt.setAntiAlias(true);
        pt.setDither(true);
        pt.setColor(Color.LTGRAY);
        pt.setStyle(Paint.Style.STROKE); // .FILL_AND_STROKE); //.STROKE);
        pt.setStrokeJoin(Paint.Join.ROUND);
        pt.setStrokeWidth(3);
    }

    public void update() {
        item = new Rect (0, 0, face.getWidth(), face.getHeight());
    }

    public void draw(Canvas canvas) {
        if (bActive) {
            pt.setColor(Color.RED);
            pt.setAlpha(120);
        } else {
            pt.setColor(Color.GRAY);
            pt.setAlpha(40);
        }
        //if (Stage.gameOptions_devModeOn) {
        if (bActive) {
            canvas.drawRect(new Rect((int)(box.left* Stage.zoomplusX), (int)(box.top* Stage.zoomplusY), (int)(box.right* Stage.zoomplusX), (int)(box.bottom* Stage.zoomplusY)), pt);  //debug
        }
        //buttonFullMapGUI = new Rect (DeviceWIDTH-30, 0, DeviceWIDTH, 30);
        if (face != null) {
            dest = new Rect((int)(x* Stage.zoomplusX), (int)(y* Stage.zoomplusY), (int)((x + width) * Stage.zoomplusX), (int)((y+ height) * Stage.zoomplusY));
            canvas.drawBitmap(face, item, dest, null);
        }
    }
}
