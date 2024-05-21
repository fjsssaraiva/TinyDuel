package com.fsaraiva.tinyduel.engine.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Button extends UIObject {

    public Button (String asset, String asset_active, int posx, int posy, int w, int h, int id) {
        x = posx;
        y = posy;
        width = w;
        height = h;

        this.id = id;
        bActive = false;
        box = new Rectangle(x, y, width, height);
        face = new Texture(Gdx.files.internal(asset));
        faceActive= new Texture(Gdx.files.internal(asset_active));
        //value = false;
    }

    public Texture draw() {
        if (this.bActive) {
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
