package in.silive.directme.Service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import in.silive.directme.AsyncTask.FirebaseTokenBackgroundWorker;
import in.silive.directme.Interface.AsyncResponse;
import in.silive.directme.Utils.FCMConfig;

/**
 * Created by Shobhit-pc on 2/16/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    public static final String MyPREFERENCES = "UserContact";
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
    SharedPreferences pref;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        System.out.println("refreshedToken" + refreshedToken);
        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        // sending reg id to your server
        //// TODO: 2/20/2017 implement correct fcm url and uncomment
//        sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(FCMConfig.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {

        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String mobileNo = sharedpreferences.getString("UserContact", "");
        if (!mobileNo.equals("")) {
            // sending fcm token to server
            FirebaseTokenBackgroundWorker firebaseTokenBackgroundWorker = new FirebaseTokenBackgroundWorker(new AsyncResponse() {
                @Override
                public void processFinish(String output) {

                    System.out.println(output);
                    if (output.equals("Firebase updated")) {
                        pref = getApplicationContext().getSharedPreferences(FCMConfig.SHARED_PREF, 0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("FirebaseIdSendToServer", "1");//1 means firebase id is registered
                        editor.commit();
                    }
                }
            });
            firebaseTokenBackgroundWorker.execute(mobileNo, token);
        }
        Log.e(TAG, "sendRegistrationToServer: " + token);
    }

    private void storeRegIdInPref(String token) {
        pref = getApplicationContext().getSharedPreferences(FCMConfig.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.putString("FirebaseIdSendToServer", "0");//0 means firebase id is not updated to server
        editor.commit();
    }
}