package com.fsaraiva.tinyduel.game.scenes.tennis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Score {

    //    public int x, y, targetX, targetY;
//    public boolean markedForDeletion;
//    float timer;
//    public String value;
//


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
        font.setColor(Color.DARK_GRAY);
        font.draw(batch, "FPS: " + Float.toString(Gdx.graphics.getFramesPerSecond()) + " -input:" + inputType, 5, 25); //1075);

        font2.getData().setScale(2, 2);
        font2.setColor(Color.WHITE);
        drawScore(batch, font2, gbsc, grsc);
    }

    public void paused(SpriteBatch batch, BitmapFont font, int gbl, int gbb, int grl, int grb, int gbsc, int grsc, String inputType) {

        //batch.draw(paused, 450, 800);
        font.getData().setScale(1, 1);
        font.setColor(Color.DARK_GRAY);
        font.draw(batch, "PAUSED: " + Float.toString(Gdx.graphics.getFramesPerSecond()) + " -input:" + inputType, 5, 40);

        font.getData().setScale(2, 2);
        font.setColor(Color.WHITE);
        drawScore(batch, font, gbsc, grsc);
    }

    private void drawScore(SpriteBatch batch, BitmapFont font, int gbsc, int grsc) {
        switch (gbsc) {
            case 0: {
                font.draw(batch, "00", 745, 990);
                break;
            }
            case 1: {
                font.draw(batch, "15", 745, 990);
                break;
            }
            case 2: {
                font.draw(batch, "30", 745, 990);
                break;
            }
            case 3: {
                font.draw(batch, "40", 745, 990);
                break;
            }
            case 4: {
                font.draw(batch, "GM", 745, 990);
                break;
            }
            default: {
                font.draw(batch, "p"+gbsc, 745, 990);
                break;
            }
        }

        switch (grsc) {
            case 0: {
                font.draw(batch, "00", 1075, 990);
                break;
            }
            case 1: {
                font.draw(batch, "15", 1075, 990);
                break;
            }
            case 2: {
                font.draw(batch, "30", 1075, 990);
                break;
            }
            case 3: {
                font.draw(batch, "40", 1075, 990);
                break;
            }
            case 4: {
                font.draw(batch, "GM", 1075, 990);
                break;
            }
            default: {
                font.draw(batch, "p"+grsc, 1075, 990);
                break;
            }
        }
    }

    public void dispose() {
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
