package com.fsaraiva.tinyduel.engine.video;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public abstract class SceneObject {
    public Rectangle hitBox = new Rectangle(0, 0, 0, 0);
    public boolean isActive = false;  //change to isActive
    public boolean markedForDeletion = false;
    public int x, y, width, height;

    public TextureRegion getRenderImg() {
        return null;
    }

    public boolean getMapCollision(int x, int y, int[][] map, int tilesize) {
        int left_tile = (int) (x / tilesize);   //map.tileSize = 40
        int bottom_tile = (map.length - 1) - (int) (y / tilesize);
        int right_tile = (int) ((x + hitBox.width) / tilesize);
        int top_tile = (map.length - 1) - (int) ((y + hitBox.height) / tilesize);
        boolean any_collision = false;

        if (left_tile < 0) left_tile = 0;
        if (right_tile > (map[0].length - 1)) right_tile = (map[0].length - 1);
        if (top_tile < 0) top_tile = 0;
        if (bottom_tile > (map.length - 1)) bottom_tile = (map.length - 1);

        //Gdx.app.log("COLLISION OBJ", "check bounds : l:" + left_tile + " r:" + right_tile + " b:" + bottom_tile+ " t:" + top_tile);
        for (int i = left_tile; i <= right_tile; i++) {
            for (int j = top_tile; j <= bottom_tile; j++) {
                if (map[j][i] == 1) {
                    any_collision = true;
                    break;
                }
            }
        }
        return any_collision;
    }

    public Rectangle getScreenCoords(Rectangle viewport) {
        //Gdx.app.log("COLLISION OBJ", "check bounds : rx:" + rect.x + " ry:" + rect.y + " vx:" + viewport.x+ " vy:" + viewport.y);
        return new Rectangle((int)(hitBox.x - viewport.x), (int)(hitBox.y - viewport.y), hitBox.width, hitBox.height);
        //Gdx.app.log("COLLISION OBJ", "check bounds : rx:" + ret.x + " ry:" + ret.y);
        //Gdx.app.log("COLLISION OBJ", "---");
        //return ret;
    }
}


/*
public abstract class GuiObject {
    public int x;
    public int y;
    //protected int dy;
    public int width;
    public int height;
    protected Paint p = new Paint();

    public int offset_fmap_x_ini;
    public int offset_fmap_y_ini;
    public int offset_fmap_x_end;
    public int offset_fmap_y_end;
    public int drawX, drawY;


    public Rect getCenteredBounds()
    {
        // Get collision box from center point
        return new Rect(x-(width/2), y-(height/2), x+(width/2), y+(height/2));
    }

    public Rect getBounds()
    {
        // Get collision box from coords + size
        return new Rect(x, y, x+width, y+height);
    }

    // Get the position where to draw the object, only useful where board bigger than screen
    protected void drawPosition(boolean fullScreen, int cameraX, int cameraY) {
        //public void drawPosition(boolean fmap, int playerX, int playerY, float currentZoom) {
        if (fullScreen) {
            drawX= x-(width/2);
            drawY= y-(height/2);
        } else {
            offset_fmap_x_ini = cameraX - (Stage.viewport_current_size_X/2);
            offset_fmap_y_ini = cameraY - (Stage.viewport_current_size_Y/2);
            offset_fmap_x_end = cameraX + (Stage.viewport_current_size_X/2);
            offset_fmap_y_end = cameraY + (Stage.viewport_current_size_Y/2);

            // correct offset on side limits of map
            if (offset_fmap_x_ini < 0) {
                offset_fmap_x_end += Math.abs(offset_fmap_x_ini);
                offset_fmap_x_ini = 0;
            }
            if (offset_fmap_x_end > Stage.viewport_current_size_X){//Stage.activeCollisionMap.image.getWidth()) { // 1728) {
                offset_fmap_x_ini -= Math.abs(offset_fmap_x_end - Stage.viewport_current_size_X); //Stage.activeCollisionMap.image.getWidth());
            }
            if (offset_fmap_y_ini < 0) {
                offset_fmap_y_end += Math.abs(offset_fmap_y_ini);
                offset_fmap_y_ini = 0;
            }
            if (offset_fmap_y_end > Stage.viewport_current_size_X) { //Stage.activeCollisionMap.image.getHeight()) {
                offset_fmap_y_ini -= Math.abs(offset_fmap_y_end - Stage.viewport_current_size_X); //Stage.activeCollisionMap.image.getHeight());
            }

            drawX= (x-offset_fmap_x_ini) * Stage.DeviceWIDTH / Stage.viewport_current_size_X;
            drawY= (y-offset_fmap_y_ini) * Stage.DeviceHEIGHT / Stage.viewport_current_size_Y;
        }
    }

    public Rect getDrawingBounds()
    {
        // Get drawing box from coords
        return new Rect(
                (int)(x*Stage.zoomplusX),
                (int)(y*Stage.zoomplusY),
                (int)((x+width)*Stage.zoomplusX),
                (int)((y+height)*Stage.zoomplusY));
    }

    public Rect getCenteredDrawingBounds()
    {
        // Get drawing box from center point
        drawX= x-(width/2);
        drawY= y-(height/2);
        return new Rect(
                (int)(drawX*Stage.zoomplusX),
                (int)(drawY*Stage.zoomplusY),
                (int)((drawX+width)*Stage.zoomplusX),
                (int)((drawY+height)*Stage.zoomplusY));
    }

    public Rect getDrawingBoundsXY(int refx, int refy) {
        return new Rect(
                (int)((refx-(width/2))*Stage.zoomplusX),
                (int)((refy-(height/2))*Stage.zoomplusY),
                (int)((refx-(width/2)+width)*Stage.zoomplusX),
                (int)((refy-(height/2)+height)*Stage.zoomplusY));
    }

    public Rect getDrawingBoundsXYZoom(int refx, int refy, int zoom) {
        return new Rect(
                (int)(((refx-(width/2))*zoom)*Stage.zoomplusX),
                (int)(((refy-(height/2))*zoom)*Stage.zoomplusY),
                (int)(((refx-(width/2)+width)*zoom)*Stage.zoomplusX),
                (int)(((refy-(height/2)+height)*zoom)*Stage.zoomplusY));
    }
 */
