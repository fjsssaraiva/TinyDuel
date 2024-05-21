package com.fsaraiva.tinyduel.engine.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.fsaraiva.tinyduel.engine.utils.InputHandler;

public abstract class UIObject {
    public Rectangle box;
    public Texture face;
    public Texture faceActive;
    public int x;
    public int y;
    public int width;
    public int height;
    public int id;
    public boolean bActive;

    public Texture draw() {
        return null;
    }

    public void dispose() {
    }

    public void setValueBool(boolean val) {
    }

    public boolean getValueBool() {
        return false;
    }

    public void setValueInt(int val) {
    }
    public int getValueInt() {
        return 0;
    }

    public void update(InputHandler input) {
    }
}
