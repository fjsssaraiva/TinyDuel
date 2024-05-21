package com.fsaraiva.tinyduel.engine.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.fsaraiva.tinyduel.engine.utils.InputHandler;

public class Slider extends UIObject{
    //public Texture faceOn;
    //public Texture faceOnActive;
    public Texture pointer;
    public boolean buttonReleased;
    public long startTime;
    private long delay = 250;

    public int value;

    public Slider(int posx, int posy, int w, int h, int id) {
        x = posx;
        y = posy;
        width = w;
        height = h;

        this.id = id;
        bActive = false;
        box = new Rectangle(x, y, width, height);

        //faceOn = new Texture(Gdx.files.internal("controller/checkboxOn.png"));
        //faceOnActive = new Texture(Gdx.files.internal("controller/checkboxOn_active.png"));

        face = new Texture(Gdx.files.internal("controller/checkboxOff.png"));
        faceActive = new Texture(Gdx.files.internal("controller/checkboxOff_active.png"));

        value = 0;
        this.buttonReleased = true;
    }

    public void update(InputHandler input) {
        //Gdx.app.log("In2", "touchX:" + input.touchX);
        //Gdx.app.log("In2", "slideX:" + (this.x + (float)(this.width / 2)));
        //Gdx.app.log("In2", "left: 1:" + input.keys.contains("left") + " 2:" + input.keys_2.contains("left"));
        //Gdx.app.log("In2", "right: 1:" + input.keys.contains("right") + " 2:" + input.keys_2.contains("right"));

        if (!this.buttonReleased) {
            long elapsed = (System.nanoTime() - this.startTime) / 1000000;
            //Gdx.app.log("Bullets", "delay " + elapsed);
            if (elapsed > delay) this.buttonReleased = true;  // 500ms
        }

        if (this.buttonReleased) {
            if (input.keys.contains("right") || input.keys_2.contains("right")) {

                this.setValueInt(this.getValueInt() + 1);
                if (this.getValueInt() > 10) {
                    this.setValueInt(10);
                }
                this.buttonReleased = false;
                this.startTime = System.nanoTime();
            }
            if (input.keys.contains("left") || input.keys_2.contains("left")) {
                this.setValueInt(this.getValueInt() - 1);
                if (this.getValueInt() < 0) {
                    this.setValueInt(0);
                }
                this.buttonReleased = false;
                this.startTime = System.nanoTime();
            }
        }
    }

    public void setValueInt(int val) {
        value = val;
    }

    public int getValueInt() {
        return value;
    }

    public Texture draw() {
        if (this.bActive) {
            return faceActive;
        } else {
            return face;
        }
    }

    public Texture drawPointer() {
        if (!this.bActive) {
            return faceActive;
        } else {
            return face;
        }
    }

    public void dispose() {
        face.dispose();
        faceActive.dispose();
    }
}