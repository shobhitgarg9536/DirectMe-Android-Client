package in.silive.directme.Fragments;

/**
 * Created by simran on 2/24/2017.
 */


import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

import in.silive.directme.Activity.ParkNowActivity;
import in.silive.directme.R;

/**
 * Created by simran on 2/23/2017.
 */

public class ParknowUsershipselectFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    @Nullable
    public static int FRAME_W=720;
    // frame height
    public static int FRAME_H;
    // number of frames
    public static int NB_FRAMES=20;
    // nb of frames in x
    public static int COUNT_X=5;
    // nb of frames in y
    public static int COUNT_Y=4;
    ImageView boat_image;
    Button select;
    private Bitmap[] bitmaps;

    public static String spritesheetimage;


    public ParknowUsershipselectFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.parknow_ship_select, container,
                false);
        spritesheetimage=getArguments().getString("name");
        FRAME_H=getArguments().getInt("Frame_width");

        boat_image = (ImageView) v.findViewById(R.id.boatimage);
        select=(Button)v.findViewById(R.id.select);
        Bitmap boat_bitmap = getBitmapFromAssets(spritesheetimage);
        select.setOnClickListener(this);
        if (boat_bitmap != null) {

            bitmaps = new Bitmap[NB_FRAMES];
            int currentFrame = 0;

            for (int i = 0; i < COUNT_Y; i++) {
                for (int j = 0; j < COUNT_X; j++) {
                    bitmaps[currentFrame] = Bitmap.createBitmap(boat_bitmap, FRAME_W
                            * j, FRAME_H * i, FRAME_W, FRAME_H);


                    if (++currentFrame >= NB_FRAMES) {
                        break;
                    }
                }


            }
            // create animation programmatically
            final AnimationDrawable animation = new AnimationDrawable();
            animation.setOneShot(false); // repeat animation

            for (int i = 0; i < NB_FRAMES; i++) {
                animation.addFrame(new BitmapDrawable(getResources(), bitmaps[i]),
                        100);
            }

            // load animation on image
            if (Build.VERSION.SDK_INT < 16) {
                boat_image.setBackgroundDrawable(animation);
            } else {
                boat_image.setBackground(animation);
            }

            // start animation on image
            boat_image.post(new Runnable() {

                @Override
                public void run() {
                    animation.start();
                }

            });
        }
        return v;

    }







    private Bitmap getBitmapFromAssets(
            String filepath) {
        AssetManager assetManager = getActivity().getAssets();
        InputStream istr = null;
        Bitmap bitmap = null;

        try {
            istr = assetManager.open(filepath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException ioe) {
            // manage exception
        } finally {
            if (istr != null) {
                try {
                    istr.close();
                } catch (IOException e) {
                }
            }
        }

        return bitmap;
    }


    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getActivity(), ParkNowActivity.class);
        startActivity(intent);
    }
}

