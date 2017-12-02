package com.toan_itc.baoonline;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.toan_itc.baoonline.Lib.Class_khaibao;
import com.toan_itc.baoonline.page_read_rss.page_load_webview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.id.list;

public class BannerActivity extends AppCompatActivity {
    // Remove the below line after defining your own ad unit ID.
    private String link="",tittle="",pudate="",description="";
    int actionbar;
    int statusbar;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent i = getIntent();
        Bundle bundle = i.getBundleExtra(Class_khaibao.SEND_INTENT);
        if (bundle != null) {
            link = bundle.getString(Class_khaibao.URL);
            actionbar = bundle.getInt(Class_khaibao.COLOR_ACTIONBAR, R.color.myPrimaryColor);
            statusbar = bundle.getInt(Class_khaibao.COLOR_STATUSBAR, R.color.myPrimaryDarkColor);
            tittle = bundle.getString(Class_khaibao.TITLE);
            pudate = bundle.getString(Class_khaibao.PUDATE);
            description = bundle.getString(Class_khaibao.DESCRIPTION);
        }
        // Create the InterstitialAd and set the adUnitId (defined in values/strings.xml).
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                showInterstitial();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.d("onAdFailedToLoad","errorCode"+errorCode);
            }

            @Override
            public void onAdClosed() {
                Bundle bundle = new Bundle();
                Intent i = new Intent(BannerActivity.this, page_load_webview.class);
                bundle.putString(Class_khaibao.URL, link);
                bundle.putInt(Class_khaibao.COLOR_ACTIONBAR, R.color.myPrimaryColor5);
                bundle.putInt(Class_khaibao.COLOR_STATUSBAR, R.color.myPrimaryDarkColor5);
                bundle.putString(Class_khaibao.TITLE, tittle);
                bundle.putString(Class_khaibao.PUDATE, pudate);
                i.putExtra(Class_khaibao.SEND_INTENT, bundle);
                startActivity(i);
            }
        });
        return interstitialAd;
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    private void loadInterstitial() {
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }
}
