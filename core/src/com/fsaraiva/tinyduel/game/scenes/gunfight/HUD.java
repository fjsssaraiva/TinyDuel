package com.fsaraiva.tinyduel.game.scenes.gunfight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HUD {

    private final Texture gui_bullet;
    private final Texture gui_heart;
    private final Texture paused;

    //    public int x, y, targetX, targetY;
//    public boolean markedForDeletion;
//    float timer;
//    public String value;
//
   public HUD() {
       gui_bullet = new Texture(Gdx.files.internal("bullet_sprite.png"));
       gui_heart = new Texture(Gdx.files.internal("heart.png"));
       paused = new Texture(Gdx.files.internal("paused.png"));
   }

   public void render(SpriteBatch batch, BitmapFont font, BitmapFont font2, int gbl, int gbb, int grl, int grb, int gbsc, int grsc, String inputType) {
//        this.value = value;
//        this.x = x;
//        this.y = y;
//        this.targetX = targetX;
//        this.targetY = targetY;
//        this.markedForDeletion = false;
//        this.timer = 0;
//
       font.getData().setScale(2, 2);
       font.setColor(Color.GOLD);
       font.draw(batch, "FPS: " + Float.toString(Gdx.graphics.getFramesPerSecond()) + " -input:" + inputType, 5, 40);

       font2.getData().setScale(2, 2);
       font2.setColor(Color.BLACK);
       font2.draw(batch, "" + gbsc, 18, 1072);
       font2.draw(batch, "" + grsc, 1848, 1072);
       font2.setColor(Color.WHITE);
       font2.draw(batch, "" + gbsc, 20, 1070);
       font2.draw(batch, "" + grsc, 1850, 1070);

        for (int i=0; i<gbl; i++) {
            batch.draw(gui_heart, 20+(50*i), 980);
        }
        for (int i=0; i<gbb; i++) {
            batch.draw(gui_bullet, 20+(30*i), 935);
        }
        // red
        for (int i=0; i<grl; i++) {
            batch.draw(gui_heart, 1850-(50*i), 980);
        }
        for (int i=0; i<grb; i++) {
            batch.draw(gui_bullet, 1850-(30*i), 935);
        }
   }

    public void paused(SpriteBatch batch, BitmapFont font, int gbl, int gbb, int grl, int grb, int gbsc, int grsc, String inputType) {

        batch.draw(paused, 450, 800);
        font.getData().setScale(2, 2);
        font.setColor(Color.GOLD);
        font.draw(batch, "PAUSED: " + Float.toString(Gdx.graphics.getFramesPerSecond()) + " -input:" + inputType, 5, 40);

        font.getData().setScale(5, 4);
        font.setColor(Color.BLACK);
        font.draw(batch, "" + gbsc, 18, 1072);
        font.draw(batch, "" + grsc, 1898, 1072);
        font.setColor(Color.WHITE);
        font.draw(batch, "" + gbsc, 20, 1070);
        font.draw(batch, "" + grsc, 1900, 1070);

    }

    public void dispose() {
        gui_bullet.dispose();
        gui_heart.dispose();
        paused.dispose();
    }

//
//    public void update() {
//        // make text travel 0.3% of distance to target in each animation frame
//        // will start moving fast and slow down near the target
//        this.x += (this.targetX - this.x) * 0.03;
//        this.y += (this.targetY - this.y) * 0.03;
//        this.timer++;
//        if (this.timer > 100) this.markedForDeletion = true;
//    }
}


/*
export class UI {
    constructor(game) {
        this.game = game;
        this.fontSize = 30;
        //this.fontFamily = 'Helvetica';
        this.fontFamily = 'Creepster';
        this.livesImage = document.getElementById('lives');
    }

    draw(context) {
        context.save();
        // shadows are bad for performance
        context.shadowOffsetX = 2;
        context.shadowOffsetY = 2;
        context.shadowColor = 'white';
        context.shadowBlur = 0;
        context.font = this.fontSize + 'px ' + this.fontFamily;
        context.textAlign = 'left';
        context.fillStyle = this.game.fontColor;

        // score
        context.fillText('Score: ' + this.game.score, 20, 50);

        // timer
        context.font = this.fontSize * 0.8 + 'px ' + this.fontFamily;
        context.fillText('Time: ' + (this.game.time * 0.001).toFixed(1), 20, 80);

        // lives
        for (let i = 0; i < this.game.lives; i++) {
            context.drawImage(this.livesImage, 25 * i + 20, 95, 25, 25);
        }

        // game over message
        if (this.game.gameOver) {
            context.textAlign = 'center';
            context.font = this.fontSize * 2 + 'px ' + this.fontFamily;
            if (this.game.score > this.game.winningScore) {
                context.fillText('Boo-yah', this.game.width * 0.5, this.game.height * 0.5 - 20);
                context.font = this.fontSize * 0.7 + 'px ' + this.fontFamily;
                context.fillText('What are night creatures afraid of? YOU!!!',
                        this.game.width * 0.5, this.game.height * 0.5 + 20);
                //context.font = this.fontSize * 0.7 + 'px ' + this.fontFamily;
                context.fillText('Press R to restart',
                        this.game.width * 0.5, this.game.height * 0.5 + 45);
            } else {
                context.fillText('Love at first bite?', this.game.width * 0.5, this.game.height * 0.5 - 20);
                context.font = this.fontSize * 0.7 + 'px ' + this.fontFamily;
                context.fillText('Nope, better luck next time!',
                        this.game.width * 0.5, this.game.height * 0.5 + 20);
                //context.font = this.fontSize * 0.7 + 'px ' + this.fontFamily;
                context.fillText('Press R to restart',
                        this.game.width * 0.5, this.game.height * 0.5 + 45);
            }
        }
        context.restore();
    }
}*/
