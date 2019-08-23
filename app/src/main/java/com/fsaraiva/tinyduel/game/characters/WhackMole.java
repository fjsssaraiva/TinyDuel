package com.fsaraiva.tinyduel.game.characters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.fsaraiva.tinyduel.engine.GuiObject;
import com.fsaraiva.tinyduel.engine.Stage;

public class WhackMole extends GuiObject {
    public int ya, ID, maxPos, minPos;
    public boolean moleRising;
    public boolean moleSinking;
    public boolean moleJustHit;

    public Bitmap image;
    //public Bitmap mask;
    private Rect src_full, dest;

    public WhackMole(int ID, int x, int y, int maxP, int minP, Bitmap img) {
        this.x = x;
        this.y = y;
        maxPos = maxP;
        minPos = minP;
        this.ID = ID;
        width = 50;  //88
        height = 120; //212
        moleRising = moleSinking = moleJustHit = false;

        image = img;
        //image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.mole), Stage.DeviceWIDTH, Stage.DeviceHEIGHT, true);
        src_full = new Rect(0,0,image.getWidth(),image.getHeight());

    }

    public void doAI(int ID) {
 // next random mole?
    }

    public void update(double moleRate) {

            if (moleRising) {
                y -= moleRate;
            } else if (moleSinking) {
                y += moleRate;
            }
        //Log.d("mole","update Mole y: " + y + ", minpos: " + minPos + ",  hit?: " + moleJustHit);
            if (y >= (maxPos * 1 /*Stage.zoomplusY*/) || moleJustHit) {
                y = maxPos * 1;
                Stage.whackGameScene.pickActiveMole();
            }
            if (y <= (minPos * 1 /*Stage.zoomplusY*/)) {
                y = minPos * 1;
                moleRising = false;
                moleSinking = true;
            }
    }

    public void draw(Canvas canvas) {
        // get draw position (useful for scroll enabled games, static for one screen only games)
        super.drawPosition(true, 0, 0);
        canvas.drawBitmap(image, src_full, getDrawingBounds(), null);

    }
}
