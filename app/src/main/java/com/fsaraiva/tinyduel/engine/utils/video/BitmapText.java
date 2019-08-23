package com.fsaraiva.tinyduel.engine.utils.video;

import java.util.HashMap;
import java.util.Map;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.fsaraiva.tinyduel.engine.Stage;

/**
 * Created by Fernando on 17/10/2016.
 */

public class BitmapText {
    private static final String TAG = BitmapText.class.getSimpleName();
    private Bitmap bitmap;  // bitmap containing the character map/sheet

    // Map to associate a bitmap to each character
    private Map<Character, Bitmap> glyphs = new HashMap<Character, Bitmap>(39);  //(62);
    private int width;  // width in pixels of one character
    private int height; // height in pixels of one character

    private char[] charactersU = new char[] {'0', '1', '2', '3', '4', '5', '6', '7',  // 35 chars
                '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
                'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T',
                'U', 'V', 'W', 'X', 'Y', 'Z', '.', ',', 'Ç', 'Õ'};

    public BitmapText(Bitmap bitmap) {
        super();
        this.bitmap = bitmap;
        this.width = 16; //8
        this.height = 16; //12

        for (int i = 0; i < 39; i++) { // 26 (original elems in row for original image
            glyphs.put(charactersU[i], Bitmap.createBitmap(bitmap, i * width, 0, width, height));
        }

        // fix character 'O'
        glyphs.put('O', Bitmap.createBitmap(bitmap, 0, 0, width, height));
        // clean up
        bitmap.recycle();
    }

    public void drawString(Canvas canvas, String text, int x, int y, int scale) {
        float scalexf = Stage.zoomplusX; //(Stage.DeviceWIDTH / Stage.viewport_current_size_X);  //reference size, where original was tested
        float scaleyf = Stage.zoomplusY; //(Stage.DeviceHEIGHT / Stage.viewport_current_size_Y);

        for (int i = 0; i < text.length(); i++) {
            Character ch = text.charAt(i);
            Rect src =  new Rect(0, 0, width, height);
            Rect dest = new Rect(
                    (int)((x + (i * width * scale))*scalexf),
                    (int)(y*scaleyf),
                    (int)((x + (i * width * scale) + (width*scale))*scalexf),
                    (int)((y + (height*scale))*scaleyf));
            if (glyphs.get(ch) != null) {
                //canvas.drawBitmap(glyphs.get(ch), x + (i * width), y, null);
                canvas.drawBitmap(glyphs.get(ch), src, dest, null); //working
                //canvas.drawBitmap(glyphs.get(ch), m1, p);
            }
            /*
             Rect dest = new Rect(
                    x +(int) ((i * width)*scalexf),
                    y,
                    x +(int) (((i * width)*scalexf) + width*scalexf),
                    y +(int) (height*scaleyf));
             */
        }
    }
}
