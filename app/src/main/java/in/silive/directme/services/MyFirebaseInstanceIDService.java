package in.silive.directme.services;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import in.silive.directme.application.DirectMe;
import in.silive.directme.listeners.AsyncResponse;
import in.silive.directme.network.FetchData;
import in.silive.directme.utils.API_URL_LIST;
import in.silive.directme.utils.Constants;

/**
 * Created by Shobhit-pc on 2/16/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    //    public static final String Authorization_Token = "Authorization_Token";
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
        sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Constants.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {

        SharedPreferences sharedpreferences1 = DirectMe.getInstance().sharedPrefs;
        String authorization_token = sharedpreferences1.getString("Authorization_Token", "");

        // sending fcm token to server
        FetchData fetchData = new FetchData(new AsyncResponse() {
            @Override
            public void processStart() {

            }

            @Override
            public void processFinish(String output) {

            }
        });
        String post_data = "";
        try {
            post_data = URLEncoder.encode("fcm_token", "UTF-8") + "=" + URLEncoder.encode(token, "UTF-8");
            Log.d("fcm", post_data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        fetchData.setArgs(API_URL_LIST.FIREBASE_TOKEN_UPDATE, authorization_token, post_data);
        fetchData.execute();

        Log.e(TAG, "sendRegistrationToServer: " + token);
    }

    private void storeRegIdInPref(String token) {
        pref = DirectMe.getInstance().sharedPrefs;
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.putString("FirebaseIdSendToServer", "0");//0 means firebase id is not updated to server
        editor.commit();
    }
}