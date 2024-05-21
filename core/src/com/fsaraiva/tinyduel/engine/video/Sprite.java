package com.fsaraiva.tinyduel.engine.video;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Sprite extends SceneObject {
    Texture image1;
    TextureRegion[][] image;

    public Sprite(String asset) {
        // Load the sprite sheet as a Texture
        image1 = new Texture(Gdx.files.internal(asset));
        image = TextureRegion.split(image1,
                image1.getWidth(),
                image1.getHeight());
    }

    public TextureRegion getRenderImg() {
        return image[0][0];
    }

    public void dispose() {
        image1.dispose();
    }
}
