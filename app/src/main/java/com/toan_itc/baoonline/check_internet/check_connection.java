package com.toan_itc.baoonline.check_internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Toan_Kul on 11/27/2014.
 */
public class check_connection {
    private Context _context;
    private ConnectivityManager connectivityManager;
    public check_connection(Context context){
        this._context = context;
    }
    public boolean isConnectingToInternet(){
         connectivityManager = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        if (connectivityManager != null) {
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
  /*  public void hasActiveInternetConnection(final Handler handler, final int timeout) {
        // ask fo message '0' (not connected) or '1' (connected) on 'handler'
        // the answer must be send before before within the 'timeout' (in milliseconds)
        new Thread() {
                private boolean responded = false;

                @Override
                public void run() {
                    // set 'responded' to TRUE if is able to connect with google mobile (responds fast)
                    new Thread() {
                        @Override
                        public void run() {
                            HttpGet requestForTest = new HttpGet("http://m.google.com");
                            try {
                                new DefaultHttpClient().execute(requestForTest); // can last...
                                responded = true;
                            } catch (Exception e) {
                            }
                        }
                    }.start();

                    try {
                        int waited = 0;
                        while (!responded && (waited < timeout)) {
                            sleep(100);
                            if (!responded) {
                                waited += 100;
                            }
                        }
                    } catch (InterruptedException e) {
                    } // do nothing
                    finally {
                        if (!responded) {
                            handler.sendEmptyMessage(0);
                        } else {
                            handler.sendEmptyMessage(1);
                        }
                    }
                }
            }.start();
    }*/
    public boolean hasActiveInternetConnection_boolean(Context context) {
        if (isNetworkAvailable(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                Log.wtf("Toan", "Error checking internet connection", e);
                Toast localToast = Toast.makeText(context, "Lỗi Internet. Vui lòng kiểm tra lại mạng!",Toast.LENGTH_LONG);
                localToast.setGravity(Gravity.BOTTOM,0,20);
                localToast.show();
                Log.wtf("Lỗi Internet","Vui lòng kiểm tra lại mạng!");
            }
        } else {
            Log.wtf("Toan", "No network available!");
        }
        return false;
    }
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
