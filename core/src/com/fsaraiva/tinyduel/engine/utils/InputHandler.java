package com.fsaraiva.tinyduel.engine.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.fsaraiva.tinyduel.engine.ui.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//https://libgdx.com/wiki/input/event-handling
public class InputHandler implements InputProcessor, ControllerListener {
    //TinyDuel game;
    public List<String> keys;
    public List<String> keys_2;
    public List<Vector3> pointers;

    public String debug = "";
    public final int BUTTON_A = 0;
    public final int BUTTON_START = 6;
    public final int BUTTON_B = 1;
    public final int BUTTON_X = 2;
    public final int BUTTON_Y = 3;
    public final int BUTTON_SELECT = 4;
    public final int BUTTON_SYSTEM = 5;
    //public final int BUTTON_START = 6;
    public final int BUTTON_LS = 7; //Left Stick pressed down
    public final int BUTTON_RS = 8; //Right Stick pressed down
    public final int BUTTON_LB = 9;
    public final int BUTTON_RB = 10;

    //axis
    public final int AXIS_LY = 1; // -0.99 | -3.05e | 1.0
    public final int AXIS_LX = 0; // -1 | 0 | 1
    public final int AXIS_RY = 3; // -0.99 | -3.05e | 1.0
    public final int AXIS_RX = 2; // -1 | 0 | 0.99
    public final int AXIS_LT = 4; // 0 | 1 (left trigger)
    public final int AXIS_RT = 5; // 0 | 1 (right trigger)
    public final int UP = 11;
    public final int DOWN = 12;
    public final int LEFT = 13;
    public final int RIGHT = 14;

    OrthographicCamera camera;
    public float touchX, touchY, mouseOverX, mouseOverY;
    public boolean touchEnabled = false;
    public Vector3 mouseLocation;
    public int touchThreshold;
    public boolean isDragged = false;

    public boolean isVirtualController1Enabled = false;
    public boolean isVirtualController2Enabled = false;
    public boolean useOnScreenCtrl1 = true;
    public boolean useOnScreenCtrl2 = true;
    public boolean onScreenCtrl1Active = false;
    public boolean onScreenCtrl2Active = false;
    public int onScreenCtrl1Pointer = -1;
    public int onScreenCtrl2Pointer = -1;

    public boolean controllEnabled = false;
    public String controller1ID = "";
    public String controller2ID = "";

    public Button controller1;
    Button controller1In;
    Button controller1U;
    Button controller1D;
    Button controller1L;
    Button controller1R;
    Button controller1UL;
    Button controller1UR;
    Button controller1DL;
    Button controller1DR;

    public Button controller2;
    Button controller2In;
    Button controller2U;
    Button controller2D;
    Button controller2L;
    Button controller2R;
    Button controller2UL;
    Button controller2UR;
    Button controller2DL;
    Button controller2DR;

    public Button controllerAction1;
    public Button controllerAction2;

    public int numPlayers = 1;

    public String selectedInputType = "none";

    public InputHandler() {

        camera = new OrthographicCamera(); // for touch projection on viewport
        camera.setToOrtho(false, 1920, 1080); //1280, 720);
        keys = new ArrayList<String>();
        keys_2 = new ArrayList<String>();
        pointers = new ArrayList<Vector3>();

        //ArrayList<String> keys = new ArrayList<String>();
        keys.add("ini");
        keys_2.add("ini");

        pointers.add(new Vector3(0,0,0));
        pointers.add(new Vector3(0,0,0));
        pointers.add(new Vector3(0,0,0));
        pointers.add(new Vector3(0,0,0));

        //for (String str : keys) {Gdx.app.log("INPUT", "keys: " + str);}
        //touch controls (mobile)
        touchX = 0;
        touchY = 0;
        touchThreshold = 5;
        mouseLocation = new Vector3();

        if(Controllers.getControllers().size > 0) {
            //Gdx.app.log("BOOT controllers", "num:" + Controllers.getControllers().size);
            //Gdx.app.log("BOOT controllers", "ID1:" + controller1ID);
            controller1ID = Controllers.getControllers().get(0).getUniqueId();
            if(Controllers.getControllers().size > 1) {
                controller2ID = Controllers.getControllers().get(1).getUniqueId();
            }
        }

        // if virtual controllers enabled (android)
        controller1 = new Button("controller/ctrlOut.png", "controller/ctrlOut.png",20, 120, 300, 300,0);
        controller1In = new Button("controller/ctrlIn.png", "controller/ctrlOut.png",20 + 61, 120 + 58, 178, 185,0);
        controller2 = new Button("controller/ctrlOut.png", "controller/ctrlOut.png",1920 - 400 - 150, 20, 300, 300,0);
        controller2In = new Button("controller/ctrlIn.png", "controller/ctrlOut.png",1920 - 400 - 150 + 61, 20 + 58, 300, 300,0);

        /*controller1L = new Button("butgamepad.png", "butgamepad_enabled.png",20, 120 + 50, 50, 50,0);
        controller1UL = new Button("butgamepad.png", "butgamepad_enabled.png",20, 120 + 100, 50, 50,0);
        controller1DL = new Button("butgamepad.png", "butgamepad_enabled.png",20, 120, 50, 50,0);
        controller1U = new Button("butgamepad.png", "butgamepad_enabled.png",20 + 50, 120 + 100, 150, 50,0);
        controller1D = new Button("butgamepad.png", "butgamepad_enabled.png",20 + 50, 120, 50, 50,0);
        controller1R = new Button("butgamepad.png", "butgamepad_enabled.png",20 + 100, 120 + 50, 50, 50,0);
        controller1UR = new Button("butgamepad.png", "butgamepad_enabled.png",20 + 100, 120 + 100, 50, 50,0);
        controller1DR = new Button("butgamepad.png", "butgamepad_enabled.png",20 + 100, 120, 50, 50,0);*/

        controller1L =  new Button("butgamepadBig.png", "butgamepadBig_enabled.png",20,       120 + 100,       100, 100,0);
        controller1UL = new Button("butgamepadBig.png", "butgamepadBig_enabled.png",20,       120 + 100 + 100,  100, 100,0);
        controller1DL = new Button("butgamepadBig.png", "butgamepadBig_enabled.png",20,       120,            100, 100,0);
        controller1U =  new Button("butgamepadBig.png", "butgamepadBig_enabled.png",20 + 100,  120 + 100 + 100,  100, 100,0);
        controller1D =  new Button("butgamepadBig.png", "butgamepadBig_enabled.png",20 + 100,  120,            100, 100,0);
        controller1R =  new Button("butgamepadBig.png", "butgamepadBig_enabled.png",20 + 200, 120 + 100,       100, 100,0);
        controller1UR = new Button("butgamepadBig.png", "butgamepadBig_enabled.png",20 + 200, 120 + 100 + 100,  100, 100,0);
        controller1DR = new Button("butgamepadBig.png", "butgamepadBig_enabled.png",20 + 200, 120,            100, 100,0);

        /*         UL  U   UR
                   L   x   R
                   DL  D   DR
         */

        controller2L =  new Button("butgamepadBig.png", "butgamepadBig_enabled.png",1920 - 400 - 150,         20 + 100,    100, 100,0);
        controller2UL = new Button("butgamepadBig.png", "butgamepadBig_enabled.png",1920 - 400 - 150,         20 + 200,   100, 100,0);
        controller2DL = new Button("butgamepadBig.png", "butgamepadBig_enabled.png",1920 - 400 - 150,         20,         100, 100,0);
        controller2U =  new Button("butgamepadBig.png", "butgamepadBig_enabled.png",1920 - 400 - 150 + 100,    20 + 200,   100, 100,0);
        controller2D =  new Button("butgamepadBig.png", "butgamepadBig_enabled.png",1920 - 400 - 150 + 100,    20,         100, 100,0);
        controller2R =  new Button("butgamepadBig.png", "butgamepadBig_enabled.png",1920 - 400 - 150 + 200,   20 + 100,    100, 100,0);
        controller2UR = new Button("butgamepadBig.png", "butgamepadBig_enabled.png",1920 - 400 - 150 + 200,   20 + 200,   100, 100,0);
        controller2DR = new Button("butgamepadBig.png", "butgamepadBig_enabled.png",1920 - 400 - 150 + 200,   20,         100, 100,0);

        controllerAction1 = new Button("controller/buttonA.png", "controller/buttonA_active.png", 330, 20, 200, 200,0);
        controllerAction2 = new Button("controller/buttonA.png", "controller/buttonA_active.png", 1920 - 20 - 200, 150, 200, 200,0);
        //controllerAction2 = new Button("button_fire.png", "button_fire_active.png", 1920 - 20 - 100, 150, 100, 100,0);
        onScreenCtrl1Active = false;
        onScreenCtrl1Pointer = -1;
        onScreenCtrl2Active = false;
        onScreenCtrl2Pointer = -1;
    }

    public void clearInput() {
        //Gdx.app.log("INPUT", "Clear");

        keys.remove("left");
        keys.remove("right");
        keys.remove("up");
        keys.remove("down");
        keys.remove("space");

        keys_2.remove("left");
        keys_2.remove("right");
        keys_2.remove("up");
        keys_2.remove("down");
        keys_2.remove("space");

        keys.remove("escape");

        touchX = 0;
        touchY = 0;

        onScreenCtrl1Active = false;
        onScreenCtrl1Pointer = -1;
        onScreenCtrl2Active = false;
        onScreenCtrl2Pointer = -1;

        debug = "";
    }

    ///////////////////////////////
    // KEYBOARD/MOUSE SPECIFIC CODE
    ///////////////////////////////
    public boolean keyDown(int keycode) {
        //Gdx.app.log("KEY DOWN", " - " + keycode);
        touchEnabled = false;
        selectedInputType = "keyboard";
        switch (keycode)
        {
            // player 1
            case Input.Keys.LEFT:
                if (!this.keys.contains("left")) { this.keys.add("left"); }
                break;
            case Input.Keys.RIGHT:
                if (!this.keys.contains("right")) { this.keys.add("right"); }
                break;
            case Input.Keys.UP:
                if (!this.keys.contains("up")) { this.keys.add("up"); }
                break;
            case Input.Keys.DOWN:
                if (!this.keys.contains("down")) { this.keys.add("down"); }
                break;
            case Input.Keys.NUMPAD_0: //.SPACE does not work due to key roll over issue with physical keyboard
                if (!this.keys.contains("space")) { this.keys.add("space"); }
                break;

            // player 2
            case Input.Keys.A:
                if (!this.keys_2.contains("left")) { this.keys_2.add("left"); }
                break;
            case Input.Keys.D:
                if (!this.keys_2.contains("right")) { this.keys_2.add("right"); }
                break;
            case Input.Keys.W:
                if (!this.keys_2.contains("up")) { this.keys_2.add("up"); }
                break;
            case Input.Keys.S:
                if (!this.keys_2.contains("down")) { this.keys_2.add("down"); }
                break;
            case Input.Keys.F:
                if (!this.keys_2.contains("space")) { this.keys_2.add("space"); }
                break;

            case Input.Keys.ESCAPE:
                if (!this.keys.contains("escape")) { this.keys.add("escape"); }
                break;
        }
        //Gdx.app.log("KEY1", "" + this.keys);
        //Gdx.app.log("KEY2", "" + this.keys_2);
        return true;
    }

    public boolean keyUp(int keycode) {
        //Gdx.app.log("KEY UP", " - " + keycode);
        touchEnabled = false;
        selectedInputType = "keyboard";

        switch (keycode)
        {
            // player 1
            case Input.Keys.LEFT:
                //if (this.keys.contains("left")) {
                this.keys.remove("left");
                break;
            case Input.Keys.RIGHT:
                //if (this.keys.contains("right")) {
                this.keys.remove("right");
                break;
            case Input.Keys.UP:
                //if (this.keys.contains("up")) {
                this.keys.remove("up");
                break;
            case Input.Keys.DOWN:
                //if (this.keys.contains("down")) {
                this.keys.remove("down");
                break;
            case Input.Keys.NUMPAD_0: //.SPACE:
                //if (this.keys.contains("space")) {
                this.keys.remove("space");
                break;

            // player 2
            case Input.Keys.A:
                //if (this.keys_2.contains("left")) {
                this.keys_2.remove("left");
                break;
            case Input.Keys.D:
                //if (this.keys_2.contains("right")) {
                this.keys_2.remove("right");
                break;
            case Input.Keys.W:
                //if (this.keys_2.contains("up")) {
                this.keys_2.remove("up");
                break;
            case Input.Keys.S:
                //if (this.keys_2.contains("down")) {
                this.keys_2.remove("down");
                break;
            case Input.Keys.F:
                //if (this.keys_2.contains("space")) {
                this.keys_2.remove("space");
                break;

            case Input.Keys.ESCAPE:
                //if (this.keys.contains("escape")) {
                this.keys.remove("escape");
                break;
        }
        //Gdx.app.log("KEY1", "" + this.keys);
        //Gdx.app.log("KEY2", "" + this.keys_2);
        return true;
    }

    public boolean keyTyped(char character) {
        return false;
    }

    public boolean mouseMoved(int x, int y) {
        selectedInputType = "keyboard";
        mouseLocation = new Vector3(x, y, 0);
        camera.unproject(mouseLocation);
        //Gdx.app.log("MOUSE_MV", " " + x + "/" + y + " loc:" + mouseLocation.x + "/" + mouseLocation.y);
        return true;
    }

    public boolean scrolled(float amountX, float amountY) {
        //Gdx.app.log("MOUSE_WL", " " + (int)amountX + "/" + (int)amountY);
        return true;
    }

    ///////////////////////////////
    // TOUCH/MOUSE SPECIFIC CODE
    ///////////////////////////////
    public boolean touchDown(int x, int y, int pointer, int button) {
        // Set movement by touch (finger/mouse)
        //Gdx.app.log("TOUCH_DOWN", " (" + this.touchX + "/" + this.touchY + ") Ptr:" + pointer + " Btn:" + button);
        selectedInputType = "touch";
        //if (pointer == 0) {
        // MOUSE
        if (button == Input.Buttons.LEFT) {
            this.touchEnabled = true;
            selectedInputType = "keyboard";
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(pointer), Gdx.input.getY(pointer), 0);
            //Gdx.app.log("INPUT", "touch down: " + touchPos.x + ", " + touchPos.y);
            camera.unproject(touchPos);
            this.touchX = touchPos.x;
            this.touchY = touchPos.y;

            pointers.get(pointer).x = touchPos.x;
            pointers.get(pointer).y = touchPos.y;
        }

        if (button == Input.Buttons.RIGHT) {
            selectedInputType = "keyboard";
            if (this.touchX < 960) {
                if (!keys.contains("space")) {
                    //Gdx.app.log("MOUSE", " RIGHT down");
                    keys.add("space");
                    //Gdx.app.log("KEYS", " " + this.keys);
                }
            } else {
                if (!keys_2.contains("space")) {
                    //Gdx.app.log("MOUSE", " RIGHT down");
                    keys_2.add("space");
                    //Gdx.app.log("KEYS", " " + this.keys);
                }
            }
        }

        this.checkVirtualControllers(pointer, 0);
            //Gdx.app.log("TOUCH_DOWN", " (" + this.touchX + "/" + this.touchY + ") Ptr:" + pointer + " Btn:" + button + " x,y:" + x + "," + this.touchX);
        //}

        return true;
    }

    public boolean touchUp(int x, int y, int pointer, int button) {
        //if (pointer == 0) {
        selectedInputType = "touch";
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(pointer), Gdx.input.getY(pointer), 0);
            camera.unproject(touchPos);
            this.touchX = 0;
            this.touchY = 0;
            pointers.get(pointer).x = 0;
            pointers.get(pointer).y = 0;

            //touchEnabled = false;
            if (button == Input.Buttons.RIGHT) {
                selectedInputType = "keyboard";
                //if (touchPos.x < 960) {
                    this.keys.remove("space");
                //} else {
                    this.keys_2.remove("space");
                //}
            }
            //Gdx.app.log("TOUCH_UP", " (" + this.touchX + "/" + this.touchY + ") Ptr:" + pointer + " Btn:" + button + " x,y:" + x + "," + touchPos.x);
        //}

        this.checkVirtualControllers(pointer, 1);

        return true;
    }

    public boolean touchDragged(int x, int y, int pointer) {
        //Gdx.app.log("TOUCH_DRAG", " " + this.touchX + "/" + this.touchY + ") Ptr:" + pointer);
        touchEnabled = true;
        selectedInputType = "touch";
        Vector3 touchPos = new Vector3();
        touchPos.set(Gdx.input.getX(pointer), Gdx.input.getY(pointer), 0);
        camera.unproject(touchPos);
        touchX = touchPos.x;
        touchY = touchPos.y;

        pointers.get(pointer).x = touchPos.x;
        pointers.get(pointer).y = touchPos.y;

        if (pointer == 0 && (Gdx.input.isButtonPressed(Input.Buttons.LEFT))) {
            selectedInputType = "keyboard";
            isDragged = true;
        }
        //Gdx.app.log("TOUCH_DRAG", " (" + this.touchX + "/" + this.touchY + ") Ptr:" + pointer + " Btn: n x,y:" + x + "," + y);
        this.checkVirtualControllers(pointer, 2);

        return true;
    }

    public void checkTouchCoords(Rectangle hitBox, int playerId) {
        List<String> currentInput = new ArrayList<String>();
        if (playerId == 1) { currentInput = keys; }
        if (playerId == 2) { currentInput = keys_2; }

        currentInput.remove("up");
        currentInput.remove("down");
        currentInput.remove("left");
        currentInput.remove("right");

        for (Vector3 point : pointers) {
            if ((playerId == 1 && point.x < 960 && point.x > 0 && !controllerAction1.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10))) ||
                    (playerId == 2 && point.x > 960 && !controllerAction2.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10)))) {
                if ((int) point.y + touchThreshold > (int) hitBox.y + hitBox.height) {
                    currentInput.add("up");
                }
                if ((int) point.y - touchThreshold < (int) hitBox.y) {
                    currentInput.add("down");
                }
                if ((int) point.x - touchThreshold < (int) hitBox.x) {
                    currentInput.add("left");
                }
                if ((int) point.x + touchThreshold > (int) hitBox.x + hitBox.width) {
                    currentInput.add("right");
                }
            }
        }
    }

    ///////////////////////////////
    // CONTROLLER SPECIFIC CODE
    ///////////////////////////////
    @Override
    public void connected(Controller controller) {
        //Gdx.app.log("CONTROLLER", " connected " + controller.getUniqueId());
        //Gdx.app.log("CONTROLLER", " can vibrate " + controller.canVibrate());
        //Gdx.app.log("CONTROLLER", " power level " + controller.getPowerLevel());
        //Gdx.app.log("CONTROLLER", " mapping " + controller.getMapping());
        if (Objects.equals(controller1ID, "")) {
            controller1ID = controller.getUniqueId();
        } else {
            controller2ID = controller.getUniqueId();
        }
    }

    @Override
    public void disconnected(Controller controller) {
        //Gdx.app.log("CONTROLLER", " disconnected " + controller.getUniqueId());
        if (Objects.equals(controller.getUniqueId(), controller1ID)) {
            controller1ID = "";
        }
        if (Objects.equals(controller.getUniqueId(), controller2ID)) {
            controller2ID = "";
        }
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        debug = debug + " btn:" + buttonCode;
        //Gdx.app.log("CONTROLLER", controller.getUniqueId() + " - buttonDown " + buttonCode);
        touchEnabled = false;
        selectedInputType = "controller";

        if (Objects.equals(controller.getUniqueId(), controller1ID)) {
            if (buttonCode == BUTTON_A && !this.keys.contains("space")) {
                this.keys.add("space");
            }
            if (buttonCode == UP && !this.keys.contains("up")) {
                this.keys.add("up");
            }
            if (buttonCode == DOWN && !this.keys.contains("down")) {
                this.keys.add("down");
            }
            if (buttonCode == LEFT && !this.keys.contains("left")) {
                this.keys.add("left");
            }
            if (buttonCode == RIGHT && !this.keys.contains("right")) {
                this.keys.add("right");
            }
            if (buttonCode == BUTTON_START && !this.keys.contains("escape")) {
                this.keys.add("escape");
            }
        }
        if (Objects.equals(controller.getUniqueId(), controller2ID)) {
            if (buttonCode == BUTTON_A && !this.keys_2.contains("space")) {
                this.keys_2.add("space");
            }
            if (buttonCode == UP && !this.keys.contains("up")) {
                this.keys_2.add("up");
            }
            if (buttonCode == DOWN && !this.keys.contains("down")) {
                this.keys_2.add("down");
            }
            if (buttonCode == LEFT && !this.keys.contains("left")) {
                this.keys_2.add("left");
            }
            if (buttonCode == RIGHT && !this.keys.contains("right")) {
                this.keys_2.add("right");
            }
            if (buttonCode == BUTTON_START && !this.keys_2.contains("escape")) {
                this.keys.add("escape");
            }
        }
        return true;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        //debug = " btn:" + buttonCode;
        //debug.replace(" btn:" + buttonCode, "");
        //Gdx.app.log("CONTROLLER", controller.getUniqueId() + " - buttonUp " + buttonCode);
        touchEnabled = false;
        selectedInputType = "controller";

        if (Objects.equals(controller.getUniqueId(), controller1ID)) {
            if (buttonCode == BUTTON_A) {
                this.keys.remove("space");
            }
            if (buttonCode == UP) {
                this.keys.remove("up");
            }
            if (buttonCode == DOWN) {
                this.keys.remove("down");
            }
            if (buttonCode == LEFT) {
                this.keys.remove("left");
            }
            if (buttonCode == RIGHT) {
                this.keys.remove("right");
            }
            if (buttonCode == BUTTON_START) {
                this.keys.remove("escape");
            }
        }
        if (Objects.equals(controller.getUniqueId(), controller2ID)) {
            if (buttonCode == BUTTON_A) {
                this.keys_2.remove("space");
            }
            if (buttonCode == UP) {
                this.keys_2.remove("up");
            }
            if (buttonCode == DOWN) {
                this.keys_2.remove("down");
            }
            if (buttonCode == LEFT) {
                this.keys_2.remove("left");
            }
            if (buttonCode == RIGHT) {
                this.keys_2.remove("right");
            }
            if (buttonCode == BUTTON_START) {
                this.keys.remove("escape");
            }
        }
        return true;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        //Gdx.app.log("CONTROLLER1:", controller1ID);
        //debug = debug + " axis:" + axisCode + "=" + value;
        touchEnabled = false;
        selectedInputType = "controller";

        float deadZone = 0.02f;
        float axys_ly_val = 0.0f;
        if (axisCode == 1) {
            this.keys.remove("up");
            this.keys.remove("down");
            axys_ly_val = value; //controller.getAxis(AXIS_LY);
            if (axys_ly_val > -deadZone && axys_ly_val < deadZone) {
                axys_ly_val = 0.0f;
            }
        }

        float axys_lx_val = 0.0f;
        if (axisCode == 0) {
            this.keys.remove("left");
            this.keys.remove("right");
            axys_lx_val = value; //controller.getAxis(AXIS_LX);
            if (axys_lx_val > -deadZone && axys_lx_val < deadZone) {
                axys_lx_val = 0.0f;
            }
        }

        Gdx.app.log("CONTROLLER1", controller.getUniqueId());
        if (axisCode == 0) {
            //axys_lx_val = value;
            Gdx.app.log("CONTROLLER1", "axis " + axisCode + ":" + value + " - normal: " + axys_lx_val);
            Gdx.app.log("CONTROLLER1", "axis 1:" + "-" + " - normal: " + axys_ly_val);
        } else {
            //axys_ly_val = value;
            Gdx.app.log("CONTROLLER1", "axis 0:" + "-" + " - normal: " + axys_lx_val);
            Gdx.app.log("CONTROLLER1", "axis " + axisCode + ":" + value + " - normal: " + axys_ly_val);
        }

        /*if (controller.getUniqueId().equals(controller1ID)) {
            if (axys_lx_val < 0.0f && !this.keys.contains("left")) {
                this.keys.add("left");
            }
            if (axys_lx_val > 0.0f && !this.keys.contains("right")) {
                this.keys.add("right");
            }
            if (axys_lx_val == 0.0f) {
                this.keys.remove("left");
                this.keys.remove("right");
            }

            if (axys_ly_val < 0.0f && !this.keys.contains("up")) {
                this.keys.add("up");
            }
            if (axys_ly_val > 0.0f && !this.keys.contains("down")) {
                this.keys.add("down");
            }
            if (axys_ly_val == 0.0f) {
                this.keys.remove("up");
                this.keys.remove("down");
            }
        }
        if (controller.getUniqueId().equals(controller2ID)) {
            if (controller.getAxis(AXIS_LX) < 0.0f && !this.keys_2.contains("left")) {
                this.keys_2.add("left");
            }
            if (controller.getAxis(AXIS_LX) == 0.0f) {
                this.keys_2.remove("left");
            }

            if (controller.getAxis(AXIS_LX) > 0.0f && !this.keys_2.contains("right")) {
                this.keys_2.add("right");
            }
            if (controller.getAxis(AXIS_LX) == 0.0f) {
                this.keys_2.remove("right");
            }

            if (controller.getAxis(AXIS_LY) > -1.0f && controller.getAxis(AXIS_LY) < -0.991f && !this.keys_2.contains("up")) {
                this.keys_2.add("up");
            } else {
                this.keys_2.remove("up");
            }

            if (controller.getAxis(AXIS_LY) > 0.0f && !this.keys_2.contains("down")) {
                this.keys_2.add("down");
            }
            if (controller.getAxis(AXIS_LY) <= 0.0f) {
                this.keys_2.remove("down");
            }
        }*/
        //Gdx.app.log("CONTROLLER", " - keys " + this.keys);
        return true;
    }


    ////////////////////////////////
    // VIRTUAL CONTROLLER
    ////////////////////////////////
    public void checkVirtualControllers(int pointer, int action) {
        // Check action buttons (static)
        this.keys.remove("space");
        controllerAction1.bActive = false;
        controllerAction2.bActive = false;

        for (Vector3 point : pointers) {
            if (numPlayers == 2) {
                if (controllerAction1.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10))) {
                    controllerAction1.bActive = true;
                    if (!this.keys.contains("space")) {
                        this.keys.add("space");
                    }
                }
            }
            if (numPlayers == 1) {
                if (controllerAction2.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10))) {
                    controllerAction2.bActive = true;
                    if (!this.keys.contains("space")) {
                        this.keys.add("space");
                    }
                }
            }
        }

        // virtual 2
        if (numPlayers == 2) {
            this.keys_2.remove("space");
            for (Vector3 point : pointers) {
                if (controllerAction2.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10))) {
                    controllerAction2.bActive = true;
                    if (!this.keys_2.contains("space")) {
                        this.keys_2.add("space");
                    }
                }
            }
        }

        // Check virtual D-pads
        if (useOnScreenCtrl1) {
            // if touch up check which pointer was stopped
            if (action == 1) {
                if (pointer == onScreenCtrl1Pointer) {
                    onScreenCtrl1Active = false;
                    onScreenCtrl1Pointer = -1;
                    resetVirtualControllersPos(1);
                    //Gdx.app.log("DPAD", "set c1 pointer to " + onScreenCtrl1Pointer);
                }
                /*if (pointer == onScreenCtrl2Pointer) {
                    onScreenCtrl2Active = false;
                    onScreenCtrl2Pointer = -1;
                    resetVirtualControllersPos(2);
                }*/
            }


            this.keys.remove("up");
            this.keys.remove("down");
            this.keys.remove("left");
            this.keys.remove("right");
            //this.keys.remove("space");
            controller1L.bActive = false;
            controller1UL.bActive = false;
            controller1DL.bActive = false;
            controller1R.bActive = false;
            controller1UR.bActive = false;
            controller1DR.bActive = false;
            controller1U.bActive = false;
            controller1D.bActive = false;

            //for (Vector3 point : pointers) {
            for (int i = 0; i < pointers.size(); i++) {
                Vector3 point = pointers.get(i);
                // if touch down check which D-pad was activated
                if (action == 0) {
                    if (onScreenCtrl1Pointer < 0 && point.x > 0 && point.x < 900 &&
                        !(controllerAction1.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10))) && !(controllerAction2.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10)))) {
                        onScreenCtrl1Active = true;
                        //Gdx.app.log("DPAD", "set c1 pointer to " + i + " coords:" + pointers.get(i).x + "/" + pointers.get(i).y);
                        onScreenCtrl1Pointer = i;
                        controller1.box.x = (int)point.x - 150;
                        controller1.box.y = (int)point.y - 150;
                        // this buton x,y = pointerx, y
                        updateVirtualControllersPos(1);
                    }
                }
                // check drag to update DPAD position
                if (action == 2) {
                    if (onScreenCtrl1Pointer == i && point.x > 0 && point.x < 900 &&
                            !(controllerAction1.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10))) && !(controllerAction2.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10)))) {
                        if (pointers.get(i).x < controller1.box.x - 50) {
                            controller1.box.x = pointers.get(i).x;
                            updateVirtualControllersPos(1);
                        } else if (pointers.get(i).x < controller1.box.x) {
                            controller1.box.x = controller1.box.x - 10; //pointers.get(i).x;
                            updateVirtualControllersPos(1);
                        }
                        if (pointers.get(i).y < controller1.box.y - 50) {
                            controller1.box.y = pointers.get(i).y;
                            updateVirtualControllersPos(1);
                        } else if (pointers.get(i).y < controller1.box.y) {
                            controller1.box.y = controller1.box.y - 10; //pointers.get(i).y;
                            updateVirtualControllersPos(1);
                        }

                        if (pointers.get(i).x > controller1.box.x + 350) {
                            controller1.box.x = pointers.get(i).x;
                            updateVirtualControllersPos(1);
                        } else if (pointers.get(i).x > controller1.box.x + 300) {
                            controller1.box.x = controller1.box.x + 10;
                            updateVirtualControllersPos(1);
                        }
                        if (pointers.get(i).y > controller1.box.y + 350) {
                            controller1.box.y = pointers.get(i).y;
                            updateVirtualControllersPos(1);
                        } else if (pointers.get(i).y > controller1.box.y + 300) {
                            controller1.box.y = controller1.box.y + 10; //pointers.get(i).y;
                            updateVirtualControllersPos(1);
                        }
                    }
                }

                controller1.bActive = controller1.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10));

                if (controller1L.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10))) {
                    controller1L.bActive = true;
                    if (!this.keys.contains("left")) {
                        this.keys.add("left");
                    }
                }
                if (controller1UL.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10))) {
                    controller1UL.bActive = true;
                    if (!this.keys.contains("left")) {
                        this.keys.add("left");
                    }
                    if (!this.keys.contains("up")) {
                        this.keys.add("up");
                    }
                }
                if (controller1DL.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10))) {
                    controller1DL.bActive = true;
                    if (!this.keys.contains("left")) {
                        this.keys.add("left");
                    }
                    if (!this.keys.contains("down")) {
                        this.keys.add("down");
                    }
                }
                if (controller1U.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10))) {
                    controller1U.bActive = true;
                    if (!this.keys.contains("up")) {
                        this.keys.add("up");
                    }
                }
                if (controller1D.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10))) {
                    controller1D.bActive = true;
                    if (!this.keys.contains("down")) {
                        this.keys.add("down");
                    }
                }
                if (controller1R.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10))) {
                    controller1R.bActive = true;
                    if (!this.keys.contains("right")) {
                        this.keys.add("right");
                    }
                }
                if (controller1UR.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10))) {
                    controller1UR.bActive = true;
                    if (!this.keys.contains("right")) {
                        this.keys.add("right");
                    }
                    if (!this.keys.contains("up")) {
                        this.keys.add("up");
                    }
                }
                if (controller1DR.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10))) {
                    controller1DR.bActive = true;
                    if (!this.keys.contains("right")) {
                        this.keys.add("right");
                    }
                    if (!this.keys.contains("down")) {
                        this.keys.add("down");
                    }
                }
            }
        }

        // virtual 2
        if (numPlayers == 2) {

            if (useOnScreenCtrl2) {
                this.keys_2.remove("up");
                this.keys_2.remove("down");
                this.keys_2.remove("left");
                this.keys_2.remove("right");
                //this.keys_2.remove("space");
                controller2L.bActive = false;
                controller2UL.bActive = false;
                controller2DL.bActive = false;
                controller2R.bActive = false;
                controller2UR.bActive = false;
                controller2DR.bActive = false;
                controller2U.bActive = false;
                controller2D.bActive = false;

               for (int i = 0; i < pointers.size(); i++) {
                        Vector3 point = pointers.get(i);
                        // if touch down check which D-pad was activated
                        if (action == 0) {
                            if (onScreenCtrl2Pointer < 0 && point.x > 1100 &&
                                    !(controllerAction1.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10))) && !(controllerAction2.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10)))) {
                                onScreenCtrl2Active = true;
                                //Gdx.app.log("DPAD", "set c2 pointer to " + i + " coords:" + pointers.get(i).x + "/" + pointers.get(i).y);
                                onScreenCtrl2Pointer = i;
                                controller2.box.x = (int)point.x - 150;
                                controller2.box.y = (int)point.y - 150;
                                // this buton x,y = pointerx, y
                                updateVirtualControllersPos(2);
                            }
                        }
                        // check drag to update DPAD position
                        if (action == 2) {
                            if (onScreenCtrl2Pointer == i && point.x > 1100 &&
                                    !(controllerAction1.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10))) && !(controllerAction2.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10)))) {
                                if (pointers.get(i).x < controller2.box.x - 50) {
                                    controller2.box.x = pointers.get(i).x;
                                    updateVirtualControllersPos(2);
                                } else if (pointers.get(i).x < controller2.box.x) {
                                    controller2.box.x = controller2.box.x - 10; //pointers.get(i).x;
                                    updateVirtualControllersPos(2);
                                }
                                if (pointers.get(i).y < controller2.box.y - 50) {
                                    controller2.box.y = pointers.get(i).y;
                                    updateVirtualControllersPos(2);
                                } else if (pointers.get(i).y < controller2.box.y) {
                                    controller2.box.y = controller2.box.y - 10; //pointers.get(i).y;
                                    updateVirtualControllersPos(2);
                                }

                                if (pointers.get(i).x > controller2.box.x + 350) {
                                    controller2.box.x = pointers.get(i).x;
                                    updateVirtualControllersPos(2);
                                } else if (pointers.get(i).x > controller2.box.x + 300) {
                                    controller2.box.x = controller2.box.x + 10;
                                    updateVirtualControllersPos(2);
                                }
                                if (pointers.get(i).y > controller2.box.y + 350) {
                                    controller2.box.y = pointers.get(i).y;
                                    updateVirtualControllersPos(2);
                                } else if (pointers.get(i).y > controller2.box.y + 300) {
                                    controller2.box.y = controller2.box.y + 10; //pointers.get(i).y;
                                    updateVirtualControllersPos(2);
                                }
                            }
                        }

                    controller2.bActive = controller2.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10));

                    if (controller2L.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10))) {
                        controller2L.bActive = true;
                        if (!this.keys_2.contains("left")) {
                            this.keys_2.add("left");
                        }
                    }
                    if (controller2UL.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10))) {
                        controller2UL.bActive = true;
                        if (!this.keys_2.contains("left")) {
                            this.keys_2.add("left");
                        }
                        if (!this.keys_2.contains("up")) {
                            this.keys_2.add("up");
                        }
                    }
                    if (controller2DL.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10))) {
                        controller2DL.bActive = true;
                        if (!this.keys_2.contains("left")) {
                            this.keys_2.add("left");
                        }
                        if (!this.keys_2.contains("down")) {
                            this.keys_2.add("down");
                        }
                    }
                    if (controller2U.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10))) {
                        controller2U.bActive = true;
                        if (!this.keys_2.contains("up")) {
                            this.keys_2.add("up");
                        }
                    }
                    if (controller2D.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10))) {
                        controller2D.bActive = true;
                        if (!this.keys_2.contains("down")) {
                            this.keys_2.add("down");
                        }
                    }
                    if (controller2R.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10))) {
                        controller2R.bActive = true;
                        if (!this.keys_2.contains("right")) {
                            this.keys_2.add("right");
                        }
                    }
                    if (controller2UR.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10))) {
                        controller2UR.bActive = true;
                        if (!this.keys_2.contains("right")) {
                            this.keys_2.add("right");
                        }
                        if (!this.keys_2.contains("up")) {
                            this.keys_2.add("up");
                        }
                    }
                    if (controller2DR.box.overlaps(new Rectangle(point.x - 5, point.y - 5, 10, 10))) {
                        controller2DR.bActive = true;
                        if (!this.keys_2.contains("right")) {
                            this.keys_2.add("right");
                        }
                        if (!this.keys_2.contains("down")) {
                            this.keys_2.add("down");
                        }
                    }
                }
            }
        }
    }

    public void updateVirtualControllersPos(int player) {
        if (player == 1) {
            controller1In.x = (int)(controller1.box.x + 61);
            controller1In.y = (int)(controller1.box.y + 58);

            controller1L.box.x = controller1.box.x;
            controller1L.box.y = controller1.box.y + 100;
            controller1UL.box.x = controller1.box.x;
            controller1UL.box.y = controller1.box.y + 100 + 100;
            controller1DL.box.x = controller1.box.x;
            controller1DL.box.y = controller1.box.y;
            controller1U.box.x = controller1.box.x + 100;
            controller1U.box.y = controller1.box.y + 100 + 100;
            controller1D.box.x = controller1.box.x + 100;
            controller1D.box.y = controller1.box.y;
            controller1R.box.x = controller1.box.x + 200;
            controller1R.box.y = controller1.box.y + 100;
            controller1UR.box.x = controller1.box.x + 200;
            controller1UR.box.y = controller1.box.y + 100 + 100;
            controller1DR.box.x = controller1.box.x + 200;
            controller1DR.box.y = controller1.box.y;
        }
        if (player == 2) {
            controller2In.x = (int)(controller2.box.x + 61);
            controller2In.y = (int)(controller2.box.y + 58);

            controller2L.box.x = controller2.box.x;
            controller2L.box.y = controller2.box.y + 100;
            controller2UL.box.x = controller2.box.x;
            controller2UL.box.y = controller2.box.y + 100 + 100;
            controller2DL.box.x = controller2.box.x;
            controller2DL.box.y = controller2.box.y;
            controller2U.box.x = controller2.box.x + 100;
            controller2U.box.y = controller2.box.y + 100 + 100;
            controller2D.box.x = controller2.box.x + 100;
            controller2D.box.y = controller2.box.y;
            controller2R.box.x = controller2.box.x + 200;
            controller2R.box.y = controller2.box.y + 100;
            controller2UR.box.x = controller2.box.x + 200;
            controller2UR.box.y = controller2.box.y + 100 + 100;
            controller2DR.box.x = controller2.box.x + 200;
            controller2DR.box.y = controller2.box.y;
        }
    }

    public void resetVirtualControllersPos(int player) {
        if (player == 1) {
            controller1.box.x = 20;
            controller1.box.y = 120;

            controller1In.x = (int)(controller1.box.x + 61);
            controller1In.y = (int)(controller1.box.y + 58);

            controller1L.box.x = controller1.box.x;
            controller1L.box.y = controller1.box.y + 100;
            controller1UL.box.x = controller1.box.x;
            controller1UL.box.y = controller1.box.y + 100 + 100;
            controller1DL.box.x = controller1.box.x;
            controller1DL.box.y = controller1.box.y;
            controller1U.box.x = controller1.box.x + 100;
            controller1U.box.y = controller1.box.y + 100 + 100;
            controller1D.box.x = controller1.box.x + 100;
            controller1D.box.y = controller1.box.y;
            controller1R.box.x = controller1.box.x + 200;
            controller1R.box.y = controller1.box.y + 100;
            controller1UR.box.x = controller1.box.x + 200;
            controller1UR.box.y = controller1.box.y + 100 + 100;
            controller1DR.box.x = controller1.box.x + 200;
            controller1DR.box.y = controller1.box.y;
        }
        if (player == 2) {
            controller2.box.x = 1920 - 400 - 150;
            controller2.box.y = 20;

            controller2In.x = (int)(controller2.box.x + 61);
            controller2In.y = (int)(controller2.box.y + 58);

            controller2L.box.x = controller2.box.x;
            controller2L.box.y = controller2.box.y + 100;
            controller2UL.box.x = controller2.box.x;
            controller2UL.box.y = controller2.box.y + 100 + 100;
            controller2DL.box.x = controller2.box.x;
            controller2DL.box.y = controller2.box.y;
            controller2U.box.x = controller2.box.x + 100;
            controller2U.box.y = controller2.box.y + 100 + 100;
            controller2D.box.x = controller2.box.x + 100;
            controller2D.box.y = controller2.box.y;
            controller2R.box.x = controller2.box.x + 200;
            controller2R.box.y = controller2.box.y + 100;
            controller2UR.box.x = controller2.box.x + 200;
            controller2UR.box.y = controller2.box.y + 100 + 100;
            controller2DR.box.x = controller2.box.x + 200;
            controller2DR.box.y = controller2.box.y;
        }
    }
    // DRAW
    public void drawControllers(SpriteBatch batch, int numPl) {
        numPlayers = numPl;
        if (touchEnabled) {
            if (numPlayers == 1) {
                batch.draw(controllerAction2.draw(), controllerAction2.x, controllerAction2.y);
            }
            if (numPlayers == 2) {
                batch.draw(controllerAction1.draw(), controllerAction1.x, controllerAction1.y);
                batch.draw(controllerAction2.draw(), controllerAction2.x, controllerAction2.y);
            }

            if (useOnScreenCtrl1 && onScreenCtrl1Active) {
                batch.draw(controller1.draw(), controller1.box.x, controller1.box.y);

                int stick_x = controller1In.x;
                int stick_y = controller1In.y;

                if (controller1U.bActive || controller1UL.bActive || controller1UR.bActive) {
                    stick_y = stick_y + 50;
                }
                if (controller1D.bActive || controller1DL.bActive || controller1DR.bActive) {
                    stick_y = stick_y - 50;
                }
                if (controller1L.bActive || controller1UL.bActive || controller1DL.bActive) {
                    stick_x = stick_x - 50;
                }
                if (controller1R.bActive || controller1UR.bActive || controller1DR.bActive) {
                    stick_x = stick_x + 50;
                }

                batch.draw(controller1In.draw(), stick_x, stick_y);
            }

            if (numPlayers == 2 && useOnScreenCtrl2 && onScreenCtrl2Active) {
                batch.draw(controller2.draw(), controller2.box.x, controller2.box.y);

                int stick_x2 = controller2In.x;
                int stick_y2 = controller2In.y;

                if (controller2U.bActive || controller2UL.bActive || controller2UR.bActive) {
                    stick_y2 = stick_y2 + 50;
                }
                if (controller2D.bActive || controller2DL.bActive || controller2DR.bActive) {
                    stick_y2 = stick_y2 - 50;
                }
                if (controller2L.bActive || controller2UL.bActive || controller2DL.bActive) {
                    stick_x2 = stick_x2 - 50;
                }
                if (controller2R.bActive || controller2UR.bActive || controller2DR.bActive) {
                    stick_x2 = stick_x2 + 50;
                }

                batch.draw(controller2In.draw(), stick_x2, stick_y2);
            }
        }
    }
}

