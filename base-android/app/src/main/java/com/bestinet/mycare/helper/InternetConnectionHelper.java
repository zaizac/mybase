package com.bestinet.mycare.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class InternetConnectionHelper {

    private InternetConnectionHelper(){
    }

    public static boolean checkInternetConnection(Context context) {

        // get Connectivity Manager object to check connection
        ConnectivityManager connectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connectionManager.getNetworkInfo(0).getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                connectionManager.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connectionManager.getNetworkInfo(1).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connectionManager.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            //Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        } else if (
                connectionManager.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connectionManager.getNetworkInfo(1).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED) {
            Toast.makeText(context, " Please check your Internet Connection ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }

}
