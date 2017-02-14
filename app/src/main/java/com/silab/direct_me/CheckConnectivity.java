package com.silab.direct_me;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
//class for checking connectivity
public class CheckConnectivity {
    public static boolean isNetConnected(Context mContext) {
        NetworkInfo netInfo = ((ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
