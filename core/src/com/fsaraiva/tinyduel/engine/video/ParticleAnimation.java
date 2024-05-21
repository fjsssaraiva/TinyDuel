package com.fsaraiva.tinyduel.engine.video;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.fsaraiva.tinyduel.engine.utils.Animation;


// Basically an animation as screen object for update and position,
// more a gif than an actual particle
public class ParticleAnimation extends SceneObject {
    private int frame_cols = 5, frame_rows = 1; // set variable
    Animation animation;

    //String asset, int frame_cols, int frame_rows, int ini_row, int end_row, int ini_col, int end_col, float frame_duration
    public ParticleAnimation(String asset, int x, int y, float framespeed) {
        markedForDeletion = false;
        animation = new Animation(asset, frame_cols, frame_rows, 0, 1, 0,5, framespeed);
        hitBox = new Rectangle(x, y,10, 10);
        //rect = new Rectangle(x, y, animation.getRenderImg().getRegionWidth(), animation.getRenderImg().getRegionHeight());
    }

    public TextureRegion getRenderImg() {return animation.getRenderImg();}

    public void update() {
        // run animation only once
        if (animation.animation.getKeyFrameIndex(animation.stateTime) >= (frame_cols - 1)) {
            markedForDeletion = true;
        }
    }

    public void dispose() {
        animation.dispose(); }
}

