package in.silive.directme.utils;

import android.widget.Toast;

import in.silive.directme.application.DirectMe;

/**
 * Created by shobhit on 28/2/17.
 */

public class ToasterUtils {

    public static void toaster(String message) {
        Toast.makeText(DirectMe.getInstance(), message, Toast.LENGTH_SHORT).show();
    }
}
