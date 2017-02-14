package com.silab.direct_me;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.util.List;

/**
 * Created by Lenovo on 14-Jan-17.
 */
//Background service for sound
public class BackgroundSoundService extends Service {

    MediaPlayer player;
    public IBinder onBind(Intent arg0) {

        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.idl);
        player.setLooping(true); // Set looping
        player.setVolume(100,100);

    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();

        return flags;
    }

    public void onStart(Intent intent, int startId) {
        // TO DO
    }
    public IBinder onUnBind(Intent arg0) {
        // TO DO Auto-generated method
        return null;
    }



    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void onPause() {

        player.stop();



    }

    protected void onStop() {

        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> services = activityManager
                .getRunningTasks(Integer.MAX_VALUE);
        boolean isActivityFound = false;

        if (services.get(0).topActivity.getPackageName().toString()
                .equalsIgnoreCase(getPackageName().toString())) {
            isActivityFound = true; // Activity belongs to your app is in foreground.
        }

        if (!isActivityFound) {
            if (player != null && player.isPlaying()) {
                player.release();
            }
        }
    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();

    }

    @Override
    public void onLowMemory() {

    }


}
