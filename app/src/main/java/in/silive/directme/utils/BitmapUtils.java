package in.silive.directme.utils;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

import in.silive.directme.application.DirectMe;

/**
 * Created by shobhit on 28/2/17.
 */

public class BitmapUtils {
    public static Bitmap getBitmapFromAssets(String filepath) {
        AssetManager assetManager = DirectMe.getInstance().mContext.getAssets();
        InputStream istr = null;
        Bitmap bitmap = null;

        try {
            istr = assetManager.open(filepath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException ioe) {
            // manage exception
            ioe.printStackTrace();
        } finally {
            if (istr != null) {
                try {
                    istr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    public static Bitmap[] getBitmapsFromSprite(Bitmap bitmap, int NB_FRAMES, int COUNT_X, int COUNT_Y, int FRAME_H, int FRAME_W) {
        int currentFrame = 0;
        Bitmap[] bitmaps = new Bitmap[NB_FRAMES];

        for (int i = 0; i < COUNT_Y; i++) {
            for (int j = 0; j < COUNT_X; j++) {
                bitmaps[currentFrame] = Bitmap.createBitmap(bitmap, FRAME_W
                        * j, FRAME_H * i, FRAME_W, FRAME_H);


                if (++currentFrame >= NB_FRAMES) {
                    break;
                }
            }
        }
        return bitmaps;
    }
}
