package com.fsaraiva.tinyduel.engine.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.fsaraiva.tinyduel.engine.utils.InputHandler;

public class OptionsList {
    public Array<UIObject> widgets;
    public int selectedwidget = 0;
    public boolean buttonReleased;
    public long startTime;
    private long delay = 250;

    public OptionsList() {
        widgets = new Array<>();
        this.buttonReleased = true;
    }

    public void AddOption(UIObject b) {
        widgets.add(b);
    }

    public void render(SpriteBatch batch) {
        //for (Button btn : widgets) {
        for (int i=0; i < widgets.size; i++) {
            if (widgets.get(i).getClass().getName().equals("com.fsaraiva.tinyduel.engine.ui.Slider")) {
                batch.draw(widgets.get(i).draw(), widgets.get(i).x, widgets.get(i).y, widgets.get(i).width + 10 + (int)(widgets.get(i).width / 10) * 2, widgets.get(i).height);

                int posX = widgets.get(i).x + 10 + ((widgets.get(i).width / 10) * widgets.get(i).getValueInt());
                batch.draw(((Slider)widgets.get(i)).drawPointer(), posX, widgets.get(i).y);
                //Gdx.app.log("Options", "is a slider");
            } else {
                batch.draw(widgets.get(i).draw(), widgets.get(i).x, widgets.get(i).y, widgets.get(i).width, widgets.get(i).height);
            }
            //Gdx.app.log("Options", "item " + i + " class " + widgets.get(i).getClass().getName());
        }
    }

    public void update(InputHandler input) {
        if (!this.buttonReleased) {
            long elapsed = (System.nanoTime() - this.startTime) / 1000000;
            //Gdx.app.log("Bullets", "delay " + elapsed);
            if (elapsed > delay) this.buttonReleased = true;  // 500ms
        }

        if (this.buttonReleased) {
            if (input.keys.contains("up") || input.keys_2.contains("up")) { /*input.keys.contains("left") ||*/
                selectedwidget--;
                if (selectedwidget < 1) {
                    selectedwidget = widgets.size;
                }
                this.buttonReleased = false;
                this.startTime = System.nanoTime();
            }
            if (input.keys.contains("down") || input.keys_2.contains("down")) { /*input.keys.contains("right") ||*/
                selectedwidget++;
                if (selectedwidget > widgets.size) {
                    selectedwidget = 1;
                }
                this.buttonReleased = false;
                this.startTime = System.nanoTime();
            }
        }

        // Check mouse over
        for (UIObject btn : widgets) {
            if (btn.box.overlaps(new Rectangle(input.mouseLocation.x - 5, input.mouseLocation.y - 5, 10, 10))) {
                selectedwidget = btn.id;
            }
        }

        // update selected button
        for (UIObject btn : widgets) {
            btn.bActive = btn.id == selectedwidget;
        }
    }

    public void dispose() {
        for (UIObject btn : widgets) {
            btn.dispose();
        }
    }
}
