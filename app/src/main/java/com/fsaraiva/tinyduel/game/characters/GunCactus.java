package com.fsaraiva.tinyduel.game.characters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.fsaraiva.tinyduel.R;
import com.fsaraiva.tinyduel.engine.GuiObject;

public class GunCactus extends GuiObject {
    public Bitmap image_full;
    public Bitmap image_half;
    public Bitmap image_empty;
    private Rect src_full;
    public int life;
    public boolean isHit;

    public GunCactus (int xi, int yi, Context context) {
        x = xi;
        y = yi;

        width = 100;
        height = 139;

        isHit = false;
        life = 3;
        // this should be on spritesheet
        //image_full = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.cactus_full), Stage.DeviceWIDTH, Stage.DeviceHEIGHT, true);
        image_full = BitmapFactory.decodeResource(context.getResources(), R.drawable.cactus_full);
        //image_half = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.cactus_half), Stage.DeviceWIDTH, Stage.DeviceHEIGHT, true);
        image_half = BitmapFactory.decodeResource(context.getResources(), R.drawable.cactus_half);
        //image_empty = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.cactus_empty), Stage.DeviceWIDTH, Stage.DeviceHEIGHT, true);
        image_empty = BitmapFactory.decodeResource(context.getResources(), R.drawable.cactus_empty);
        src_full = new Rect(0,0,image_full.getWidth(),image_full.getHeight());
    }

    public void update() {

    }

    public void draw(Canvas canvas) {

        super.drawPosition(true, 0, 0);

        switch (life) {
            case 1:
                canvas.drawBitmap(image_empty, src_full, getCenteredDrawingBounds(), null);
                break;
            case 2:
                canvas.drawBitmap(image_half, src_full, getCenteredDrawingBounds(), null);
                break;
            case 3:
                canvas.drawBitmap(image_full, src_full, getCenteredDrawingBounds(), null);
                break;
        }


        //paint.setStrokeWidth(1);
        //paint.setStyle(Paint.Style.STROKE);
        //paint.setColor(Color.BLACK);
        //canvas.drawRect(getCenteredDrawingBounds(), paint);
    }
}
