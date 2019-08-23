package com.fsaraiva.tinyduel.engine.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
//import android.util.Log;

import com.fsaraiva.tinyduel.engine.Stage;

public class Background {
    public Bitmap image;

    private Paint p;
    //ColorFilter filter;
    Rect src, src_full, dest;
    String debugString;
    public int x, y;

    public Background(Bitmap res)
    {
        image = res; //Bitmap.createScaledBitmap(res, Stage.DeviceWIDTH, Stage.DeviceHEIGHT, true);
        x = y = 100;

        //set the paint settings
        p = new Paint();
        p.setAntiAlias(false);
        p.setFilterBitmap(false);
        debugString = "1";
        src_full = new Rect(0,0,image.getWidth(),image.getHeight());

        dest = new Rect(0, 0, Stage.DeviceWIDTH, Stage.DeviceHEIGHT);
        //Log.d("background:", "bkW: " +image.getWidth() + " , bkH: " + image.getHeight());
    }

    public void update(int cameraX, int cameraY)
    {
        // use to update map viewed position in maps bigger than viewport or scrolling, parallax is done in other class
        x = cameraX;
        y = cameraY;
    }

    // The offset variables determine where the viewport is located in themap, could be called camera view
    public void draw(Canvas canvas, boolean fullcreen, int offset_fmap_x_ini, int offset_fmap_y_ini, int offset_fmap_x_end, int offset_fmap_y_end)
    {
        if (fullcreen)
        {
            canvas.drawBitmap(image, src_full,dest, p);
        }
        else
        {
            //canvas.drawBitmap(image, src_full, dest, p);
            src = new Rect(offset_fmap_x_ini, offset_fmap_y_ini, offset_fmap_x_end, offset_fmap_y_end);
            canvas.drawBitmap(image, src, dest, p);
        }
    }
}
