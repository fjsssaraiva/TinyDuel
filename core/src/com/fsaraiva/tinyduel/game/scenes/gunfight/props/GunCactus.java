package com.fsaraiva.tinyduel.game.scenes.gunfight.props;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.fsaraiva.tinyduel.engine.video.SceneObject;
import com.fsaraiva.tinyduel.engine.utils.Animation;

public class GunCactus extends SceneObject {
    Animation animation;
    Animation animation_half;
    Animation animation_empty;
    //public Rectangle rect;
    //public int currentState = 0;
    //public String image_full;
    //public String image_half;
    //public String image_empty;
    //private Rect src_full;
    public int life;
    public boolean isHit;

    public GunCactus(String asset, int posx, int posy, int w, int h) {
        float framespeed = 0.125f;

        // Constant rows and columns of the sprite sheet
        int frame_cols = 1;
        int frame_rows = 1;
        this.animation = new Animation(asset, frame_cols, frame_rows, 0, 1,  0,1,framespeed);  //cactus_full.png
        this.animation_half = new Animation("cactus_half.png", frame_cols, frame_rows, 0, 1, 0,1,framespeed);
        this.animation_empty = new Animation("cactus_empty2.png", frame_cols, frame_rows, 0, 1, 0,1,framespeed);
        this.hitBox = new Rectangle(posx, posy, w, h);
        life = 3;
        isHit = false;
        isActive = true;
    }

    public TextureRegion getRenderImg() {
        switch (life) {
            case 3: {
                return this.animation.getRenderImg();
            }
            case 2: {
                return this.animation_half.getRenderImg();
            }
            default: {
                return this.animation_empty.getRenderImg();
            }
        }
    }

    public void dispose() {
        this.animation.dispose();
        this.animation_half.dispose();
        this.animation_empty.dispose();
    }
}
