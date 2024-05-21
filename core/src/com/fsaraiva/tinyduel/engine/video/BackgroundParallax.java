package com.fsaraiva.tinyduel.engine.video;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class BackgroundParallax {

    public Array<Texture> layers;
    public Array<Float> layers_factor, layers_factorY;
    //read from file (assets)
    public int[][] map;
    public int tileSize = 40;

    // https://ailurux.github.io/ParallaxLibgdx/

    public BackgroundParallax(String asset, int actmap) {
        // Load the sprite sheet as a Texture
        layers = new Array<Texture>();
        layers_factor = new Array<Float>();
        layers_factorY = new Array<Float>();

        //layers.add(new Texture(Gdx.files.internal("background/layer-1.png")));
        layers.add(new Texture(Gdx.files.internal("background/bk-0.png")));
        layers.add(new Texture(Gdx.files.internal("background/bk-1.png")));
        layers.add(new Texture(Gdx.files.internal("background/bk-2.png")));
        layers.add(new Texture(Gdx.files.internal("background/bk-3.png")));
        layers.add(new Texture(Gdx.files.internal("background/bk-4.png")));
        //layers.add(new Texture(Gdx.files.internal("background/layer-5.png")));

        layers_factor.add(0.05f);
        layers_factor.add(0.1f);
        layers_factor.add(0.3f);
        layers_factor.add(0.4f);
        layers_factor.add(0.6f);

        layers_factorY.add(1f);
        layers_factorY.add(0.6f);
        layers_factorY.add(0.8f);
        layers_factorY.add(0.9f);
        layers_factorY.add(1f);

        layers.get(0).setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
        layers.get(1).setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
        layers.get(2).setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
        layers.get(3).setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
        layers.get(4).setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
        //layers.get(3).setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
        //1920/40 = 48, 1080/40 = 27  -> 40x40 tiles for a tilemap of 48x27
    }

    public TextureRegion getRenderImgLayer(int x, int y, int width, int height, int layer) {
        TextureRegion firstRegion = new TextureRegion(this.layers.get(layer),
                (int)(x+(x*(layers_factor.get(layer)))),
                (int)(this.layers.get(layer).getHeight() - height - (y*(1- layers_factorY.get(layer)))),  //invert y axis
                width,
                height);

        /*TextureRegion firstRegion = new TextureRegion(this.layers.get(layer),
                (int)(x+(x*(layers_factor.get(layer)))),
                (this.layers.get(layer).getHeight() - height - y),  //invert y axis
                width,
                height);
        */
/*
viewport = 0, 0, 1920, 1080, image =    0, 1080, 1920, 1080   - 1920 * 2 = 3840  x=0, y=0 -> 0, 1920
*/
        return firstRegion;
        //return this.image;
    }

    public void dispose() {
        for (Texture layer : layers) {layer.dispose();}
    }
}


/*
class Layer {
    constructor(game, width, height, speedModifier, image) {
        this.game = game;
        this.width = width;
        this.height = height;
        this.speedModifier = speedModifier;
        this.image = image;
        this.x = 0;
        this.y = 0;
    }

    update() {
        if(this.x < -this.width) this.x = 0;
        else this.x -= this.game.speed * this.speedModifier;
    }

    draw(context) {
        context.drawImage(this.image, this.x, this.y, this.width, this.height);
        // draw image twice to fill in the gap at the end
        context.drawImage(this.image, this.x + this.width, this.y, this.width, this.height);
    }
}

export class Background {
    constructor(game) {
        this.game = game;
        this.width = 1667;
        this.height = 500;

        this.layer1image = document.getElementById('layer1');
        this.layer2image = document.getElementById('layer2');
        this.layer3image = document.getElementById('layer3');
        this.layer4image = document.getElementById('layer4');
        this.layer5image = document.getElementById('layer5');

        this.layer1 = new Layer(this.game, this.width, this.height, 0, this.layer1image);
        this.layer2 = new Layer(this.game, this.width, this.height, 0.2, this.layer2image);
        this.layer3 = new Layer(this.game, this.width, this.height, 0.4, this.layer3image);
        this.layer4 = new Layer(this.game, this.width, this.height, 0.8, this.layer4image);
        this.layer5 = new Layer(this.game, this.width, this.height, 1, this.layer5image);
        this.backgroundLayers = [this.layer1, this.layer2, this.layer3, this.layer4, this.layer5];
    }

    update() {
        this.backgroundLayers.forEach(layer => {
                layer.update();
        })
    }
    draw(context) {
        this.backgroundLayers.forEach(layer => {
                layer.draw(context);
        });
    }
    restart() {
        this.x = 0;
        this.y = 0;
        this.layer1.speedModifier = 0;
        this.layer2.speedModifier = 0.2;
        this.layer3.speedModifier = 0.4;
        this.layer4.speedModifier = 0.8;
        this.layer5.speedModifier = 1;
    }
}
*/
