package com.fsaraiva.tinyduel.engine.video;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.fsaraiva.tinyduel.engine.utils.Animation;

public class Particle {
    private int frame_cols = 5, frame_rows = 1;
    public int x, y;
    Animation runner;
    public Rectangle rect;
    public boolean markedForDeletion = false;

    public Particle(int x, int y) {

        float framespeed = 0.070f;
        this.x = x+80;
        this.y = y+30;
        this.markedForDeletion = false;
        this.runner = new Animation("gunfire_anim.png", frame_cols, frame_rows, 0, 1,0,1, framespeed);
        this.rect = new Rectangle();
    }

    public TextureRegion getRenderImg() {return this.runner.getRenderImg();}

    public void update() {

        // play sound on init
        //if (this.frameX === 0) this.sound.play();
        //move sprite with the game
        //this.x -= this.game.speed;
//        if (this.frameTimer > this.frameInterval) {
//            this.frameX++;
//            this.frameTimer = 0;
//        } else {
//            this.frameTimer += deltaTime;
//        }
//        Gdx.app.log("ANIM", "statetime: " + this.runner.animation.getKeyFrameIndex(this.runner.stateTime) + " limit:" + frame_rows);
        if (this.runner.animation.getKeyFrameIndex(this.runner.stateTime) >= (frame_cols - 1)) {
            this.markedForDeletion = true;
        }
    }

    public void dispose() { this.runner.dispose(); }
}

/*
class Particle {
    constructor(game) {
        this.game = game;
        this.markedForDeletion = false;
    }

    update() {
        this.x -= this.speedX + this.game.speed;
        this.y -= this.speedY;
        // shrink particle
        this.size *= 0.95;
        if (this.size < 0.5) this.markedForDeletion = true;
    }
}

export class Dust extends Particle {
    constructor(game, x, y) {
        super(game);
        this.size = Math.random() * 10 + 10;
        this.x = x;
        this.y = y;
        this.speedX = Math.random();
        this.speedY = Math.random();
        this.color = 'rgba(0,0,0,0.2)';
    };

    draw(context) {
        context.beginPath();
        context.arc(this.x, this.y, this.size, 0, Math.PI * 2);
        context.fillStyle = this.color;
        context.fill();
    }
}

export class Splash extends Particle {
    constructor(game, x, y) {
        super(game);
        this.size = Math.random() * 100 + 100;
        this.x = x - this.size * 0.4;
        this.y = y - this.size * 0.5;
        this.speedX = Math.random() * 6 - 4;
        this.speedY = Math.random() * 2 + 1;
        this.gravity = 0;
        this.image = document.getElementById('fire');
    }
    update() {
        super.update();
        this.gravity += 0.1;
        this.y += this.gravity;
    }
    draw(context) {
        context.drawImage(this.image, this.x, this.y, this.size, this.size);
    }
}

export class Fire extends Particle {
    constructor(game, x, y) {
        super(game);
        this.image = document.getElementById('fire');
        this.size = Math.random() * 100 + 50;
        this.x = x;
        this.y = y;
        this.speedX = 1;
        this.speedY = 1;
        this.angle = 0;
        // particle rotation
        this.va = Math.random() * 0.2 - 0.1;
    }
    update() {
        super.update();
        this.angle += this.va;
        this.x += Math.sin(this.angle * 10);
    }

    draw(context) {
        context.save();
        context.translate(this.x, this.y);
        context.rotate(this.angle);
        context.drawImage(this.image, -this.size * 0.5, -this.size * 0.5, this.size, this.size);
        context.restore();
    }
}*/

/*
* package com.fsaraiva.tinyduel.engine.video;

public class Fire extends Particle {
    double size, va, angle;
    int speedX, speedY;

    public Fire(int x, int y) {
        super(x, y);
        this.runner = new Animation("fire.png", 1, 1, 0,1, 0,1,0.02f);
        //double size = 50;
        this.x = x;
        this.y = y;
        this.speedX = 1;
        this.speedY = 1;
        this.angle = 0;
        // particle rotation
        this.va = Math.random() * 0.2 - 0.1;
    }

    public void update() {
        //super.update();
        //this.x -= this.speedX + 1;
        //this.y -= this.speedY;
        // shrink particle
        this.size *= 0.5;
        if (this.size < 1) this.markedForDeletion = true;

        this.angle += this.va;
        this.x += Math.sin(this.angle * 10);

    }

    public void dispose() { this.runner.dispose(); }

}

* */