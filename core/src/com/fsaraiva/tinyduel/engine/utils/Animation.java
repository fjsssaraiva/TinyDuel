package com.fsaraiva.tinyduel.engine.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {
    public com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> animation; // Must declare frame type (TextureRegion)
    Texture animationSheet;
    // A variable for tracking elapsed time for the animation
    public float stateTime;

    public Animation(String asset, int frame_cols, int frame_rows, int ini_row, int end_row, int ini_col, int end_col, float frame_duration) {
        // Load the sprite sheet as a Texture
        animationSheet = new Texture(Gdx.files.internal(asset));
        // Use the split utility method to create a 2D array of TextureRegions. This is possible because
        // this sprite sheet contains frames of equal size and they are all aligned.
        TextureRegion[][] tmp = TextureRegion.split(animationSheet,
                animationSheet.getWidth() / frame_cols,
                animationSheet.getHeight() / frame_rows);
        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFrames = new TextureRegion[(end_col-ini_col)*(end_row-ini_row)];
        int index = 0;
        for (int i = ini_row; i < end_row; i++) {
            for (int j = ini_col; j < end_col; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        // Initialize the Animation with the frame interval (ex: 0.025f) and array of frames
        animation = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(frame_duration, walkFrames);
        // Reset the elapsed animation time to 0
        this.stateTime = 0f;
    }

    public TextureRegion getRenderImg() {
        this.stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        // Get current frame of animation for the current stateTime
        //Gdx.app.log("ANIM", "statetime: " + this.stateTime);
        return animation.getKeyFrame(this.stateTime, true);
    }

    public void dispose() {
        animationSheet.dispose();
    }
}
