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
}
