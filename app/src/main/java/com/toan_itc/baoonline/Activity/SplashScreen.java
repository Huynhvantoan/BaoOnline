package com.toan_itc.baoonline.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import com.toan_itc.baoonline.Cache.File_Cache;
import com.toan_itc.baoonline.R;
import com.toan_itc.baoonline.check_internet.check_connection;
import com.toan_itc.baoonline.progressbar_dialog.Progressbar.ProgressBarCircular;
/**
 * Created by Toan_Kul on 1/10/2015.
 */
public class SplashScreen extends Activity {
    static int SPLASH_TIME_OUT = 1500;
    ProgressBarCircular progressBar;
    check_connection check_wifi;
    Boolean isInternetPresent;
    File_Cache file_cache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        //Tao Folder luu cache
        file_cache = new File_Cache(SplashScreen.this);
        file_cache.CreateFolder(SplashScreen.this);
        //Check_internet
        check_wifi = new check_connection(SplashScreen.this);
        isInternetPresent = check_wifi.isConnectingToInternet();
        if (!isInternetPresent) {
           /* new MaterialDialog.Builder(this)
                    .title(R.string.dialog_title_internet)
                    .content(R.string.dialog_internet)
                    .positiveText("Thử Lại")
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            super.onPositive(dialog);
                            finish();
                            startActivity(new Intent(getIntent()));
                        }
                    })
                    .show();*/
        } else {
            //Check_Internet();
            new Load_Asynctask().execute();
        }
    }
   /* private void Check_Internet() {
        new Thread() {
            private boolean responded = false;
            @Override
            public void run() {
                new Thread() {
                    @Override
                    public void run() {
                        HttpGet url = new HttpGet("https://www.google.com/");
                        try {
                            new DefaultHttpClient().execute(url);
                            responded = true;
                        } catch (Exception e) {
                        }
                    }
                }.start();
                try {
                    int waited = 0;
                    while (!responded && (waited < 3000)) {
                        sleep(100);
                        if (!responded) {
                            waited += 100;
                        }
                    }
                } catch (InterruptedException e) {
                } // do nothing
                finally {
                    if (!responded) {
                        Log.wtf("DisInternet", "NoComplete!");
                        Disconnection.sendEmptyMessage(0);
                    } else {
                        Log.wtf("Internet","Complete!");
                        Connectioncheck.sendEmptyMessage(1);
                    }
                }
            }
        }.start();
    }
    Handler Disconnection = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            new MaterialDialog.Builder(SplashScreen.this)
                    .title(R.string.dialog_title_rotmang)
                    .content(R.string.dialog_content_rotmang)
                    .positiveText("Thử Lại")
                    .negativeText("Thoát")
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            super.onPositive(dialog);
                            finish();
                            startActivity(new Intent(getIntent()));
                        }
                        @Override
                        public void onNegative(MaterialDialog dialog) {
                            super.onNegative(dialog);
                            finish();
                        }
                    })
                    .show();
        }
    };
    Handler Connectioncheck = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            new Load_Asynctask().execute();
        }
    };*/
    private class Load_Asynctask extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar=(ProgressBarCircular)findViewById(R.id.progressBar);
            progressBar.setBackgroundColor(getResources().getColor(R.color.myPrimaryColor));
        }
        @Override
        protected Void doInBackground(Void... params) {
            SystemClock.sleep(SPLASH_TIME_OUT);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);
            SplashScreen.this.finish();
        }
    }
}
