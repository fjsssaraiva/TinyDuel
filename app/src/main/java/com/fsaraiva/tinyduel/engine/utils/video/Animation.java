package com.fsaraiva.tinyduel.engine.utils.video;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.fsaraiva.tinyduel.engine.GuiObject;
import com.fsaraiva.tinyduel.engine.Stage;

public class Animation extends GuiObject {
    private Bitmap[] frames;
    private int currentFrame;
    private long startTime;
    private long delay;
    public boolean playedOnce;
    public Rect src_full;

    public Animation(Bitmap spritesheet, int w, int h, int numFrames)
    {
        frames = new Bitmap[numFrames];
        width=w; height=h;

        for (int i=0; i < frames.length; i++) {
            frames[i] = Bitmap.createBitmap(spritesheet, i * width, 0, width, height);
        }
        src_full = new Rect(0,0,frames[0].getWidth(),frames[0].getHeight());

        delay=50; //100;
        currentFrame = 0;
        startTime = System.nanoTime();
        //spritesheet.recycle();
    }

    public Bitmap getImage()
    {
        return frames[currentFrame];
    }

    public Bitmap getImageAt(int i) {
        return frames[i];
    }

    public void update()
    {
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if (elapsed>delay)
        {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if (currentFrame == frames.length){
            currentFrame = 0;
            playedOnce = true;
        }
    }

    public void recycleAnimation()
    {
        for (int i=0; i < frames.length; i++) {
            frames[i].recycle();
        }
        //spritesheet.recycle();
    }

    public int getFrame(){return currentFrame;}
    public boolean playedOnce() {return playedOnce;}
    public void setDelay(long d){delay = d;}
    public void setFrame(int i){currentFrame = i;}
}
