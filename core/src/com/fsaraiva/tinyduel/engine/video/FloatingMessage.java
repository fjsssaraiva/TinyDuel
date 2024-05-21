package com.fsaraiva.tinyduel.engine.video;

import com.badlogic.gdx.math.Rectangle;

public class FloatingMessage {
    public int x, y, targetX, targetY;
    public Rectangle rect;
    public boolean markedForDeletion;
    float timer;
    public String value;
    public String style;

    public FloatingMessage(String value, int x, int y, int targetX, int targetY) {
        this.value = value;
        this.x = x;
        this.y = y;

        this.rect = new Rectangle(x, y, 24, 24);

        this.targetX = targetX;
        this.targetY = targetY;
        this.markedForDeletion = false;
        this.timer = 0;
        this.style = "";
    }

    public void update() {
        // make text travel 0.3% of distance to target in each animation frame
        // will start moving fast and slow down near the target
        this.x += (this.targetX - this.x) * 0.03;
        this.y += (this.targetY - this.y) * 0.03;
        this.rect.x = this.x;
        this.rect.y = this.y;
        this.timer++;
        if (this.timer > 50) this.markedForDeletion = true;
    }
}

