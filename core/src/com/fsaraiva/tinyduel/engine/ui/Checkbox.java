package com.fsaraiva.tinyduel.engine.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Checkbox extends UIObject{
    public Texture faceOn;
    public Texture faceOnActive;
    public boolean value;


    public Checkbox(int posx, int posy, int w, int h, int id) {
        x = posx;
        y = posy;
        width = w;
        height = h;

        this.id = id;
        bActive = false;
        box = new Rectangle(x, y, width, height);

        faceOn = new Texture(Gdx.files.internal("controller/checkboxOn.png"));
        faceOnActive = new Texture(Gdx.files.internal("controller/checkboxOn_active.png"));

        face = new Texture(Gdx.files.internal("controller/checkboxOff.png"));
        faceActive = new Texture(Gdx.files.internal("controller/checkboxOff_active.png"));

        value = false;
    }

    @Override
    public boolean getValueBool() {
        return this.value;
    }


    public void setValueBool(boolean val) {
        this.value = val;
    }

    public Texture draw() {
        if (this.value && this.bActive) {
            return faceOnActive;
        }
        if (this.value && !this.bActive) {
            return faceOn;
        }
        if (!this.value && this.bActive) {
            return faceActive;
        }
        if (!this.value && !this.bActive) {
            return face;
        }
        return null;
    }

    public void dispose() {
        face.dispose();
        faceActive.dispose();
    }
}