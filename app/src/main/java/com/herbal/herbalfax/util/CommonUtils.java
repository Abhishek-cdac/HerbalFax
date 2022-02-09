package com.herbal.herbalfax.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class CommonUtils {


    /**
     * Is internet on boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean isInternetOn(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isAvailable()
                    && networkInfo.isConnected();
        }
        return false;
    }


}

