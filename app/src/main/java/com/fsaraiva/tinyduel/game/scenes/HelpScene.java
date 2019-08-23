package com.fsaraiva.tinyduel.game.scenes;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.fsaraiva.tinyduel.R;
import com.fsaraiva.tinyduel.engine.Scene;
import com.fsaraiva.tinyduel.engine.Stage;
import com.fsaraiva.tinyduel.engine.map.Background;
import com.fsaraiva.tinyduel.engine.utils.gui.Button;
import com.fsaraiva.tinyduel.engine.utils.video.BitmapText;

import java.util.ArrayList;

import static com.fsaraiva.tinyduel.engine.Stage.DeviceHEIGHT;
import static com.fsaraiva.tinyduel.engine.Stage.DeviceWIDTH;
import static com.fsaraiva.tinyduel.engine.Stage.viewport_current_size_X;

public class HelpScene extends Scene {

    private Background staticBackgroud;

    public enum helpCurrentPage {
        HELP_MENU_PAGE_1, HELP_MENU_PAGE_2, HELP_MENU_PAGE_3, HELP_MENU_PAGE_4, HELP_MENU_PAGE_5
    }

    public helpCurrentPage state; // = helpCurrentPage.HELP_MENU_PAGE_1;
    private Button BACK;
    private Button NEXT;
    private Button PREV;

    public HelpScene(View context) {
        super();
        isDirty = false;
    }

    public void killScene() {
        staticBackgroud.image.recycle();
        isDirty = false;
    }

    public void initScene(Context context) {
        //bitmapText = new BitmapText(BitmapFactory.decodeResource(context.getResources(), R.drawable.font));  //glyphs_green
        staticBackgroud = new Background(BitmapFactory.decodeResource(context.getResources(), R.drawable.fullmap_layer0));

        state = helpCurrentPage.HELP_MENU_PAGE_1;

        BACK = new Button(Stage.viewport_current_size_X-200, Stage.viewport_current_size_Y-50, 150, 50);
        NEXT = new Button(200, Stage.viewport_current_size_Y-50, 150, 50);
        PREV = new Button(50, Stage.viewport_current_size_Y-50, 150, 50);
        isDirty = true;
    }

    public void drawScene(Canvas canvas) {
        staticBackgroud.draw(canvas, true, 0, 0, 0, 0);

        switch(state) {
            case HELP_MENU_PAGE_1: {
                bitmapText.drawString(canvas, "QUICK HELP", (Stage.viewport_current_size_X / 2) - 200, 50, 3);
                bitmapText.drawString(canvas, "THREE MINIGAMES MENT FOR TWO PLAYER", 100, 150, 2);
                bitmapText.drawString(canvas, "BATTLES. THERES IS TENNIS, WHACKER ", 100, 200, 2);
                bitmapText.drawString(canvas, "AND ALSO GUNFIGHT.", 100, 250, 2);
                bitmapText.drawString(canvas, "YOU CAN SELECT 1 OR 2 PLAYERS.", 100, 300, 2);
                bitmapText.drawString(canvas, "DEVELOPED BY F. fsaraiva", 150, 440, 2);

                bitmapText.drawString(canvas, "BACK", BACK.x, BACK.y, 2);
                //BACK.draw(canvas);
                bitmapText.drawString(canvas, "PREV", PREV.x, PREV.y, 2);
                //PREV.draw(canvas);
                bitmapText.drawString(canvas, "NEXT", NEXT.x, NEXT.y, 2);
                //NEXT.draw(canvas);
                break;
            }
            case HELP_MENU_PAGE_2: {
                bitmapText.drawString(canvas, "TENNIS", (Stage.viewport_current_size_X / 2) - 200, 50, 3);
                bitmapText.drawString(canvas, "A QUICK GAME OF TENNIS, SCORING JUST LOKE TENNIS", 100, 200, 1);
                bitmapText.drawString(canvas, "IF GOING UP WHEN SMACKING THE BALL CHANGES DIRECTION", 100, 280, 1);
                bitmapText.drawString(canvas, "BACK", BACK.x, BACK.y, 2);
                //BACK.draw(canvas);
                bitmapText.drawString(canvas, "PREV", PREV.x, PREV.y, 2);
                //PREV.draw(canvas);
                bitmapText.drawString(canvas, "NEXT", NEXT.x, NEXT.y, 2);
                //NEXT.draw(canvas);
                break;
            }
            case HELP_MENU_PAGE_3: {
                bitmapText.drawString(canvas, "WHACK STYLE", (Stage.viewport_current_size_X / 2) - 200, 50, 3);
                bitmapText.drawString(canvas, "YOU HAVE TO HIT THE MOLES OF YOUR OPPONENT COLOR", 100, 200, 2);
                bitmapText.drawString(canvas, "FIRST TO HIT 10 MOLES WINS", 100, 280, 2);
                bitmapText.drawString(canvas, "BACK", BACK.x, BACK.y, 2);
                //BACK.draw(canvas);
                bitmapText.drawString(canvas, "PREV", PREV.x, PREV.y, 2);
                //PREV.draw(canvas);
                bitmapText.drawString(canvas, "NEXT", NEXT.x, NEXT.y, 2);
                //NEXT.draw(canvas);
                break;
            }
            case HELP_MENU_PAGE_4: {
                bitmapText.drawString(canvas, "GUNFIGHT", (Stage.viewport_current_size_X / 2) - 200, 50, 3);
                bitmapText.drawString(canvas, "THE PLAYERS SHOOT AT EACH OTHER.", 100, 200, 2);
                bitmapText.drawString(canvas, "ONLY ONE BULLET PER PLAYER CAN BE ACTIVE, AND A PLAYER GET 5 BULLETS.", 100, 280, 2);
                bitmapText.drawString(canvas, "IF ALL PLAYERS USE ALL BULLETS THAN THEY RELOAD.", 100, 360, 2);
                bitmapText.drawString(canvas, "BACK", BACK.x, BACK.y, 2);
                //BACK.draw(canvas);
                bitmapText.drawString(canvas, "PREV", PREV.x, PREV.y, 2);
                //PREV.draw(canvas);
                bitmapText.drawString(canvas, "NEXT", NEXT.x, NEXT.y, 2);
                //NEXT.draw(canvas);
                break;
            }
            case HELP_MENU_PAGE_5: {
                bitmapText.drawString(canvas, "CONTROLS", (Stage.viewport_current_size_X / 2) - 200, 50, 3);
                bitmapText.drawString(canvas, "TOUCH THE SCREEN TO MOVE PLAYER", 100, 200, 2);
                bitmapText.drawString(canvas, "PRESS THE SCREEN ON THE PLAYER SIDE TO MOVE.", 100, 280, 2);
                bitmapText.drawString(canvas, "PRESS THE PLAYER FOR ACTION, IF APPLICABLE.", 100, 360, 2);
                bitmapText.drawString(canvas, "OR USE BUTTONS.", 100, 440, 2);
                bitmapText.drawString(canvas, "BACK", BACK.x, BACK.y, 2);
                //BACK.draw(canvas);
                bitmapText.drawString(canvas, "PREV", PREV.x, PREV.y, 2);
                //PREV.draw(canvas);
                bitmapText.drawString(canvas, "NEXT", NEXT.x, NEXT.y, 2);
                //NEXT.draw(canvas);
                break;
            }
        }
    }

    public void checkInput(MotionEvent event) {
        // get touch screen coordinates
        touchX = event.getX();
        touchY = event.getY();
        Rect touchArea = new Rect((int) ((touchX-10)/Stage.zoomplusX), (int) ((touchY-10)/Stage.zoomplusY), (int) ((touchX+10)/Stage.zoomplusX), (int) ((touchY+10)/Stage.zoomplusY));
        // Check for Action_DOWN only, to prevent option cycling
        int maskedAction = event.getActionMasked();

        switch (maskedAction) {
            case MotionEvent.ACTION_DOWN:
                if (Rect.intersects(touchArea, BACK.box)) {
                    Stage.titleScene.initScene(Stage.context1);
                    Stage.currentScene = Stage.GameState.TITLE_MENU;
                    Stage.currScene = Stage.titleScene;
                    state = helpCurrentPage.HELP_MENU_PAGE_1;
                } else {
                    switch (state) {
                        case HELP_MENU_PAGE_1: {
                            if (Rect.intersects(touchArea, PREV.box)) {
                                state = helpCurrentPage.HELP_MENU_PAGE_5;
                            }
                            if (Rect.intersects(touchArea, NEXT.box)) {
                                state = helpCurrentPage.HELP_MENU_PAGE_2;
                            }
                            break;
                        }
                        case HELP_MENU_PAGE_2: {

                            if (Rect.intersects(touchArea, PREV.box)) {
                                state = helpCurrentPage.HELP_MENU_PAGE_1;
                            }
                            if (Rect.intersects(touchArea, NEXT.box)) {
                                state = helpCurrentPage.HELP_MENU_PAGE_3;
                            }
                            break;
                        }
                        case HELP_MENU_PAGE_3: {

                            if (Rect.intersects(touchArea, PREV.box)) {
                                state = helpCurrentPage.HELP_MENU_PAGE_2;
                            }
                            if (Rect.intersects(touchArea, NEXT.box)) {
                                state = helpCurrentPage.HELP_MENU_PAGE_4;
                            }
                            break;
                        }
                        case HELP_MENU_PAGE_4: {

                            if (Rect.intersects(touchArea, PREV.box)) {
                                state = helpCurrentPage.HELP_MENU_PAGE_3;
                            }
                            if (Rect.intersects(touchArea, NEXT.box)) {
                                state = helpCurrentPage.HELP_MENU_PAGE_5;
                            }
                            break;
                        }
                        case HELP_MENU_PAGE_5: {

                            if (Rect.intersects(touchArea, PREV.box)) {
                                state = helpCurrentPage.HELP_MENU_PAGE_4;
                            }
                            if (Rect.intersects(touchArea, NEXT.box)) {
                                state = helpCurrentPage.HELP_MENU_PAGE_1;
                            }
                            break;
                        }
                    }
                }
                break;
        }
    }
}
