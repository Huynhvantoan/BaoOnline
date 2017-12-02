package com.toan_itc.baoonline.page_read_rss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.SeekBar;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.toan_itc.baoonline.About_Library.About_Activity;
import com.toan_itc.baoonline.Activity.BaseActivity;
import com.toan_itc.baoonline.Lib.Class_khaibao;
import com.toan_itc.baoonline.Menucontext.ContextMenuDialogFragment;
import com.toan_itc.baoonline.Menucontext.MenuObject;
import com.toan_itc.baoonline.Menucontext.interfaces.OnMenuItemClickListener;
import com.toan_itc.baoonline.Menucontext.interfaces.OnMenuItemLongClickListener;
import com.toan_itc.baoonline.ObservableScrollView.ObservableScrollViewCallbacks;
import com.toan_itc.baoonline.ObservableScrollView.ObservableWebView;
import com.toan_itc.baoonline.ObservableScrollView.ScrollState;
import com.toan_itc.baoonline.R;
import com.toan_itc.baoonline.progressbar_dialog.Progressbar.Progressbar_dialog;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Toan_Kul on 11/19/2014.
 *
 */
public class page_load_webview extends BaseActivity implements ObservableScrollViewCallbacks,SeekBar.OnSeekBarChangeListener,OnMenuItemClickListener,OnMenuItemLongClickListener {
    private String link="",tittle="",pudate="",description="";
    private ObservableWebView webView;
    private String html="",html_backgroup="";
    private String configweb="";
    private Progressbar_dialog pd;
    private WebSettings webSettings;
    private String text_title = "",text_header="",text_details="",text_time="";
    private Document document;
    private String fit_image="<style> img { width:100%; } iframe { width:100%; } </style>";
    private String color_image="<style>p {font-size:160%; }</style>";
    private SeekBar seek_Bar;
    private int ProgressChanged=0;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    private String getpreferences_color;
    private DialogFragment mMenuDialogFragment;
    private FragmentManager fragmentManager;
    private String Preferencesname="Font_Preferences";
    private String Font_Setting="Font_Setting";
    private String Progress_Setting="Progress_Setting";
    private String BackGroup_Setting="BackGroup_Setting";
    private String Color_Setting="Color_Setting";
    int actionbar;
    int statusbar;
    Toolbar mToolbar;
    SystemBarTintManager tintManager;
    Elements detail,header;
    int getpreferences_Text,getpreferences_Progress,getpreferences_backgroup;
    int Image_ngoisao=600;
    int Text_Default=25;
    int Text_Default_Min=15;
    int Progress =0;
    String colorend="Color_End";
    private int backgroup_black,backgroup_white;
    String color_white="<style>table {color:White ;} body  {color:White ;}</style>",color_black="<style>table {color:Black ;}  body  {color:Black ;}</style>",color2="</font>";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
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
            setContentView(R.layout.activity_rss_read);
            webView = (ObservableWebView) findViewById(R.id.web_view);
            seek_Bar = (SeekBar) findViewById(R.id.seek_bar);
            seek_Bar.setOnSeekBarChangeListener(this);
            mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
            setSupportActionBar(mToolbar);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            mToolbar.setBackgroundColor(getResources().getColor(actionbar));
            tintManager.setStatusBarTintColor(getResources().getColor(statusbar));
            webView.setScrollViewCallbacks(this);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            backgroup_black = getResources().getColor(R.color.Background_read_rss);
            backgroup_white = getResources().getColor(R.color.backgroup);
            //Menu custom
            fragmentManager = getSupportFragmentManager();
            mMenuDialogFragment = ContextMenuDialogFragment.newInstance((int) getResources().getDimension(R.dimen.menu_context_Height), getMenuObjects());
            //// url khong tim thay
            if (link.isEmpty()) {
                Toast localToast2 = Toast.makeText(page_load_webview.this, "Không tìm thấy đường dẫn tin tức :(", Toast.LENGTH_SHORT);
                localToast2.setGravity(Gravity.BOTTOM, 0, 20);
                localToast2.show();
                this.finish();
            }
            SetLink();
            getDocument();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
   /* private void Check_Internet(){
        new Thread() {
            private boolean responded = false;

            @Override
            public void run() {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            getDocument();
                            responded = true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

                try {
                    int waited = 0;
                    while (!responded && (waited < 15000)) {
                        sleep(100);
                        if (!responded) {
                            waited += 100;
                        }
                    }
                } catch (InterruptedException e) {
                    Log.wtf("Log",e);
                } // do nothing
                finally {
                    if (!responded) {
                        pd.dismiss();
                        Disconnection.sendEmptyMessage(0);
                    } else {
                        Log.wtf("Internet", "Complete!");
                        Connection_OK.sendEmptyMessage(1);
                        pd.dismiss();
                    }
                }
            }
        }.start();
    }
    Handler Disconnection = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                new MaterialDialog.Builder(page_load_webview.this)
                        .title(R.string.dialog_title_internet)
                        .content(R.string.dialog_internet)
                        .positiveText("Thử Lại")
                        .negativeText("Thoát")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                super.onPositive(dialog);
                                page_load_webview.this.finish();
                                startActivity(new Intent(page_load_webview.this.getIntent()));
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                super.onNegative(dialog);
                                page_load_webview.this.finish();
                            }
                        })
                        .show();
            }
            catch (NullPointerException e){
                e.printStackTrace();
                Log.wtf("Error","Dialog Error");
            }catch (MaterialDialog.DialogException e){
                new MaterialDialog.Builder(page_load_webview.this)
                        .title(R.string.dialog_title_error)
                        .content(R.string.dialog_content_error)
                        .positiveText(R.string.dialog_button)
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                super.onPositive(dialog);
                                finish();
                                startActivity(new Intent(page_load_webview.this.getIntent()));
                            }
                        })
                        .show();
            }
        }
    };
    Handler Connection_OK = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            SetLink();
        }
    };*/
     private void SetLink(){
         webSettings = this.webView.getSettings();
         this.configweb = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.12 Safari/537.36";
         webSettings.setBuiltInZoomControls(false);
         webSettings.setJavaScriptEnabled(true);
         webSettings.setSupportZoom(false);
         webSettings.setAppCacheEnabled(true);
         webSettings.setDisplayZoomControls(false);
         webSettings.setAllowFileAccess(false);
         this.webView.setVerticalScrollBarEnabled(false);
         this.webView.setHorizontalScrollBarEnabled(false);
         this.webView.setWebChromeClient(new WebChromeClient());
         this.webView.setWebViewClient(new WebViewClient() {
             @Override
             public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                 super.onReceivedError(view, request, error);
                 page_load_webview.this.webView.loadUrl(link);
             }

             @Override
             public void onPageStarted(WebView view, String url, Bitmap favicon) {
                 super.onPageStarted(view, url, favicon);
                 pd = new Progressbar_dialog(page_load_webview.this, statusbar);
                 pd.show();
             }

             @Override
             public void onPageFinished(WebView view, String url) {
                 super.onPageFinished(view, url);
                 if(pd!=null&&pd.isShowing()){
                     pd.dismiss();
                 }
             }
         });
         LoadPreferences();
         //check link bao
         if (link.matches("(.*)vnexpress.net(.*)")) {
             if (link.startsWith("http://sohoa.vnexpress.net/")) {
                 new Noidung_Sohoa().execute();
             }
             else {
                 new Noidung_Vnexpress().execute();
             }
         } else if (link.startsWith("http://dantri.com.vn/")) {
             new Noidung_Dantri().execute();
         } else if (link.startsWith("http://www.doisongphapluat.com/")) {
             new Noidung_Doisongphapluat().execute();
         } else if (link.startsWith("http://ngoisao.net/")) {
             webView.setInitialScale(getScale());
             new Noidung_NgoiSao().execute();
         } else if (link.startsWith("http://bongdaplus.vn/")) {
             new Noidung_BongDaPlus().execute();
         } else if (link.startsWith("http://kenh14.vn/")) {
             new Noidung_Kenh14().execute();
         } else if (link.startsWith("http://gamek.vn/")) {
             new Noidung_Gamek().execute();
         /////////////////////////////////////////////
         } else if (link.startsWith("http://www.pcworld.com.vn")) {
             new Noidung_PCWord().execute();
         } else if(link.startsWith("http://vietnamnet.vn/vn")){
             Log.wtf("vietnamnet","Complete");
             new Noidung_Vietnamnet().execute();
         } else if(link.startsWith("http://www.techz.vn/")) {
            new Noidung_TechZ().execute();
         } else if (link.startsWith("http://vnreview.vn/")) {
            webView.setInitialScale(getScale());
            new Noidung_Vnreview().execute();
         } else if(link.startsWith("https://www.tinhte.vn/")) {
             new Noidung_Tinhte().execute();
         } else if (link.startsWith("http://news.zing.vn/")) {
            new Noidung_Zingnews().execute();
         } else if (link.startsWith("http://www.24h.com.vn/")) {
             new Noidung_24h().execute();
         }else if (link.startsWith("http://www.nguoiduatin.vn/")) {
             new Noidung_Nguoiduatin().execute();
         }else if (link.startsWith("http://cand.com.vn/")) {
             new Noidung_CAND().execute();
         }
         else{
             webView.loadUrl(link);
         }
     }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        seek_Bar=seekBar;
        if(progress%10==0){
            Progress = progress/10;
        }
        ProgressChanged=Text_Default_Min+Progress;
        webSettings.setDefaultFontSize(ProgressChanged);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        seek_Bar.getHandler().postDelayed(new Runnable() {
            public void run() {
                seek_Bar.setVisibility(View.GONE);
            }
        }, 3000);
        SavePreferences();
    }
    private void SavePreferences(){
        sharedpreferences =getApplicationContext().getSharedPreferences(Preferencesname, MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.putInt(Font_Setting, ProgressChanged);
        editor.putInt(Progress_Setting, Progress * 10);
        editor.putString(colorend, color2);
        editor.apply();
    }
    private void LoadPreferences(){
        sharedpreferences = getApplicationContext().getSharedPreferences(Preferencesname, MODE_PRIVATE);
        seek_Bar.setMax(200);
        getpreferences_Progress=sharedpreferences.getInt(Progress_Setting, ProgressChanged);
        getpreferences_backgroup=sharedpreferences.getInt(BackGroup_Setting, backgroup_white);
        getpreferences_Text=sharedpreferences.getInt(Font_Setting,Text_Default);
        this.webView.setBackgroundColor(getpreferences_backgroup);
        this.webSettings.setDefaultFontSize(getpreferences_Text);
        this.seek_Bar.setProgress(getpreferences_Progress);
    }
   
    private void increaseNumberOfOpenning() {
        SharedPreferences sp = this.getSharedPreferences("OPENING_APP_COUNT",page_load_webview.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("numOfOpenning", getNumOfOpenningApp() + 1);
        editor.commit();
    }
    private int getNumOfOpenningApp() {
        SharedPreferences sp = this.getSharedPreferences("OPENING_APP_COUNT",
                page_load_webview.MODE_PRIVATE);
        int numOfOpenningSetting = sp.getInt("numOfOpenning", 0);
        return numOfOpenningSetting;
    }
    public void onBackPressed()
    {
        super.onBackPressed();
        page_load_webview.this.finish();
        transition();
    }
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                transition();
                return true;
            case R.id.context_menu:
                mMenuDialogFragment.show(fragmentManager, "Menu_BáoOnline");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //Menu
    private List<MenuObject> getMenuObjects() {
        List<MenuObject> menuObjects = new ArrayList<>();
        menuObjects.add(new MenuObject(R.mipmap.ic_back));
        menuObjects.add(new MenuObject(R.mipmap.ic_black, "Nền Tối"));
        menuObjects.add(new MenuObject(R.mipmap.ic_white, "Nền Sáng"));
        menuObjects.add(new MenuObject(R.mipmap.ic_font, "Font Chữ"));
        menuObjects.add(new MenuObject(R.mipmap.ic_about, "Thông tin ứng dụng"));
        menuObjects.add(new MenuObject(R.mipmap.icn_close, "Thoát"));
        return menuObjects;
    }
    @Override
    public void onMenuItemClick(View view, int position) {
        switch (position) {
            case 0:

                break;
            case 1:
                webView.setBackgroundColor(backgroup_black);
                sharedpreferences =getApplicationContext().getSharedPreferences(Preferencesname, MODE_PRIVATE);
                editor = sharedpreferences.edit();
                editor.putInt(BackGroup_Setting,backgroup_black);
                editor.putString(Color_Setting,color_white);
                editor.apply();
                html_backgroup=color_white+html+color2;
                LoadURL_backgroup();
                break;
            case 2:
                webView.setBackgroundColor(backgroup_white);
                sharedpreferences =getApplicationContext().getSharedPreferences(Preferencesname, MODE_PRIVATE);
                editor = sharedpreferences.edit();
                editor.putInt(BackGroup_Setting,backgroup_white);
                editor.putString(Color_Setting,color_black);
                editor.apply();
                html_backgroup=color_black+html+color2;
                LoadURL_backgroup();
                break;
            case 3:
                seek_Bar.setVisibility(View.VISIBLE);
                break;
            case 4:
                Intent i=new Intent(page_load_webview.this, About_Activity.class);
                startActivity(i);
                break;
            case 5:
                AppExit();
                break;
        }
    }
    private void AppExit()
    {
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        transitionExit();
    }
    private void transition(){
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
    private void transitionExit(){
        overridePendingTransition(R.anim.scale_exit, R.anim.scale_exit);
    }
    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        ActionBar ab = getSupportActionBar();
        if (scrollState == ScrollState.UP) {
            if (ab.isShowing()) {
                ab.hide();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (!ab.isShowing()) {
                ab.show();
            }
        }
    }
    protected void onDestroy()
    {
        super.onDestroy();
        this.webView.clearCache(true);
        this.webView.clearHistory();
        this.webView.destroy();
    }
    private void getDocument() throws IOException {
        try {
            Connection.Response response = Jsoup.connect(link)
                    .userAgent(page_load_webview.this.configweb)
                    .ignoreHttpErrors(true)
                    .timeout(10 * 1000)
                    .method(Connection.Method.POST)
                    .execute();
            Map<String, String> cookies = response.cookies();
            document = Jsoup.connect(link)
                    .cookies(cookies)
                    .userAgent(page_load_webview.this.configweb)
                    .timeout(10 * 1000)
                    .ignoreHttpErrors(true)
                    .method(Connection.Method.POST)
                    .get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void LoadURL(){
        sharedpreferences = getApplicationContext().getSharedPreferences(Preferencesname, MODE_PRIVATE);
        getpreferences_color=sharedpreferences.getString(Color_Setting,"");
        if(html.equals("")) {
            webView.loadUrl(link);
        }
        else {
            webView.loadDataWithBaseURL(link,getpreferences_color+html, "text/html", "utf-8",null);
        }
    }
    private void LoadURL_backgroup(){
        sharedpreferences = getApplicationContext().getSharedPreferences(Preferencesname, MODE_PRIVATE);
        getpreferences_color=sharedpreferences.getString(Color_Setting,"");
        if(html_backgroup.equals("")) {
            webView.loadUrl(link);
        }
        else {
            webView.loadDataWithBaseURL(link,getpreferences_color+html_backgroup, "text/html", "utf-8", null);
        }
    }
    private int getScale(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        Double val = new Double(width)/new Double(Image_ngoisao);
        val = val * 100d;
        return val.intValue();
    }

    @Override
    public void onMenuItemLongClick(View view, int i) {

    }

    //Lay noi dung trang web Vnexpress
    private class Noidung_Vnexpress extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            text_title="<h2 style=\"color:#C62828\">"+tittle+"</h2>";
            text_time="<h5 style=\"color:#C62828\">" + pudate + "</h5>\n";
            text_header="<h4>"+description+"</h4>";
            detail=document.select("div[class=fck_detail width_common]");
            for(Element details:detail) {
                text_details=fit_image+details.html();
            }
            html=text_title+text_time+text_header+text_details;
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
           try {
               LoadURL();
           }catch (NullPointerException e){
               webView.loadUrl(link);
           }
        }
    }
    //Lay noi dung trang web Dantri
    private class Noidung_Dantri extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            text_title="<h2 style=\"color:#43A047\">"+tittle+"</h2>";
            text_header="<h4>"+description+"</h4>";
            text_time="<h5 style=\"color:#43A047\">" + pudate + "</h5>\n";
            detail=document.select("div[class=fon34 mt3 mr2 fon43 detail-content]");
            detail.select("div[class=news-tag-list]").remove();
            for(Element details:detail) {
                text_details=fit_image+details.html();
            }
            html=text_title+text_time+text_header+text_details;
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            try {
                LoadURL();
            }catch (NullPointerException e){
                webView.loadUrl(link);
            }
        }
    }

    private class Noidung_Doisongphapluat extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                text_title = "<h2 style=\"color:#3F51B5\">" + tittle + "</h2>";
                text_time = "<h5 style=\"color:#3F51B5\">" + pudate + "</h5>\n";
                text_header = "<h4>" + description + "</h4>";
                detail = document.select("div[id=main-detail]");
                detail.select("iframe").remove();
                for (Element details : detail) {
                    text_details = fit_image + details.html();
                }
                html = text_title + text_time + text_details;
            }catch (Exception e){}
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            try {
                LoadURL();
            }catch (NullPointerException e){
                webView.loadUrl(link);
            }
        }
    }
    private class Noidung_NgoiSao extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            text_title="<h1 style=\"color:#2962FF;font-size:250%;\">"+tittle+"</h1>";
            text_time="<h3 style=\"color:#2962FF\">" + pudate + "</h3>\n";
            text_header="<h2>"+description+"</h2>";
            detail=document.select("div[class=fck_detail]");
            for(Element details:detail) {
                text_details=color_image+details.html();
            }
            html=text_title+text_time+text_header+text_details;
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            try {
                LoadURL();
            }catch (NullPointerException e){
                webView.loadUrl(link);
            }
        }
    }
    private class Noidung_BongDaPlus extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            text_title="<h2 style=\"color:#00ACC1\">"+tittle+"</h2>";
            text_time="<h5 style=\"color:#00ACC1\">" + pudate + "</h5>\n";
            text_header="<h4>"+description+"</h4>";
            detail=document.select("div[class=contentbox]");
            for(Element details:detail) {
                Elements ul=details.select("ul[class=cat_ref lst]").remove();
                text_details=fit_image+details.html();
            }
            html=text_title+text_time+text_header+text_details;
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            try {
                LoadURL();
            }catch (NullPointerException e){
                webView.loadUrl(link);
            }
        }
    }
    private class Noidung_Kenh14 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            text_title="<h2 style=\"color:#F57C00\">"+tittle+"</h2>";
            text_time="<h5 style=\"color:#F57C00\">" + pudate + "</h5>\n";
            text_header="<h4>"+description+"</h4>";
            detail=document.select("div[class=content]");
            for(Element details:detail) {
                text_details=fit_image+details.html();
            }
            html=text_title+text_time+text_header+text_details;
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            try {
                LoadURL();
            }catch (NullPointerException e){
                webView.loadUrl(link);
            }
        }
    }
    private class Noidung_PCWord extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            text_title="<h2 style=\"color:#673AB7\">"+tittle+"</h2>";
            text_time="<h5 style=\"color:#673AB7\">" + pudate + "</h5>\n";
            text_header="<h4>"+description+"</h4>";
            detail=document.select("div[class=html]");
            for(Element details:detail) {
                Elements ul=details.select("ul").remove();
                text_details=color_image+details.html();
            }
            html=text_title+text_time+text_header+text_details;
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            try {
                LoadURL();
            }catch (NullPointerException e){
                webView.loadUrl(link);
            }
        }
    }
    private class Noidung_Gamek extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            text_title="<h2 style=\"color:#FF5252\">"+tittle+"</h2>";
            text_time="<h5 style=\"color:#FF5252\">" + pudate + "</h5>\n";
            text_header="<h4>"+description+"</h4>";
            String video=fit_image+document.select("div[class=videotop]").html();
            detail=document.select("div[class=rightdetail_content]");
            for(Element details:detail) {
                Elements band=details.select("span[class=IMSNoChangeStyle]").remove();
                text_details=fit_image+details.html();
            }
            html=text_title+text_time+text_header+video+text_details;
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            try {
                LoadURL();
            }catch (NullPointerException e){
                webView.loadUrl(link);
            }
        }
    }
    private class Noidung_TechZ extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            text_title="<h2 style=\"color:#673AB7\">"+tittle+"</h2>";
            text_time="<h5 style=\"color:#673AB7\">" + pudate + "</h5>\n";
            detail=document.select("div[class=copy post-body]");
            for(Element details:detail) {
                Elements element=details.select("p[class=textlin-ads],div[class=desciption-top-detai],div[class=news-relation-top-detail],div[class=inner-article]").remove();
                text_details=fit_image+details.html();
            }
            html=text_title+text_time+text_details;
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            try {
                LoadURL();
            }catch (NullPointerException e){
                webView.loadUrl(link);
            }
        }
    }
    private class Noidung_Zingnews extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            text_title = "<h2 style=\"color:#16A085\">" + tittle + "</h2>";
            text_time = "<h5 style=\"color:#16A085\">" + pudate + "</h5>\n";
            header=document.select("p[itemprop=description]");
            text_header = "<h4>" + header.text() + "</h4>";
            detail=document.select("div[class=content]");
            for (Element details : detail) {
                detail=details.select("div[class=inner-article]").remove();
                Elements video=details.select("div[class=inner-video]").remove();
                text_details = fit_image + details.html();
            }
            html = text_title + text_time + text_header + text_details;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                LoadURL();
            }catch (NullPointerException e){
                webView.loadUrl(link);
            }
        }
    }
    private class Noidung_Sohoa extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... params) {
                text_title="<h2 style=\"color:#673AB7\">"+tittle+"</h2>";
                text_time="<h5 style=\"color:#673AB7\">" + pudate + "</h5>\n";
                text_header="<h4>"+description+"</h4>";
                detail=document.select("div[class=fck_detail width_common]");
                for(Element details:detail) {
                    text_details=fit_image+details.html();
                }
                html=text_title+text_time+text_header+text_details;
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                try {
                    LoadURL();
                }catch (NullPointerException e){
                    webView.loadUrl(link);
                }
            }
    }
    private class Noidung_Vietnamnet extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                text_title = "<h2 style=\"color:#16A085\">" + tittle + "</h2>";
                text_time = "<h5 style=\"color:#16A085\">" + pudate + "</h5>\n";
                detail = document.select("div[class=ArticleContent]");
                Log.wtf("Error", "Không lấy được nội dung!!");
                for (Element details : detail) {
                    Elements select = details.select("img[class=logo-small]").remove();
                    Log.wtf("Error", "Không lấy được nội dung!!");
                    Elements link = details.select("a").remove();
                    text_details = fit_image + details.html();
                }
                html = text_title + text_time + text_header + text_details;
            }catch (Exception e){}
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
           try {
               LoadURL();
           }catch (NullPointerException e){
               webView.loadUrl(link);
           }
        }
    }
    private class Noidung_Tinhte extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            text_title = "<h2 style=\"color:#16A085\">" + tittle + "</h2>";
            text_time = "<h5 style=\"color:#16A085\">" + pudate + "</h5>\n";
            Element element=document.select("blockquote[class=messageText SelectQuoteContainer ugc baseHtml]").first();
            text_details = fit_image + element.html();
            html = text_title + text_time + text_details;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                LoadURL();
            }catch (NullPointerException e){
                webView.loadUrl(link);
            }
        }
    }
    private class Noidung_Vnreview extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                getDocument();
                text_title="<h1 style=\"color:#673AB7;font-size:250%;\">"+tittle+"</h1>";
                text_time="<h3 style=\"color:#673AB7\">" + pudate + "</h3>\n";
                detail=document.select("div[class=journal-content-article]");
                for (Element details:detail) {
                    text_details = color_image+details.html();
                }
                html = text_title +text_time + text_details;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                LoadURL();
            }catch (NullPointerException e){
                webView.loadUrl(link);
            }
        }
    }
    private class Noidung_24h extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            text_title = "<h2 style=\"color:#16A085\">" + tittle + "</h2>";
            text_time = "<h5 style=\"color:#16A085\">" + pudate + "</h5>\n";
            text_header = "<h4>" + document.select("p[class=baiviet-sapo]").text() + "</h4>";
            detail=document.select("div[class= text-conent]");
            for (Element details:detail) {
                Elements like=details.select("div[class=fb-like fb_iframe_widget]").remove();
                text_details = fit_image+details.html();
            }
            html = text_title +text_time+text_header + text_details;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                LoadURL();
            }catch (NullPointerException e){
                webView.loadUrl(link);
            }
        }
    }
    private class Noidung_Nguoiduatin extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            text_title = "<h2 style=\"color:#16A085\">" + tittle + "</h2>";
            text_time = "<h5 style=\"color:#16A085\">" + pudate + "</h5>\n";
            text_header = "<h4>" + document.select("div[class=art-lead ]").text() + "</h4>";
            detail=document.select("div[id=main-detail]");
            for (Element details:detail) {
                text_details = fit_image+details.html();
            }
            html = text_title +text_time+text_header + text_details;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                LoadURL();
            }catch (NullPointerException e){
                webView.loadUrl(link);
            }
        }
    }
    private class Noidung_CAND extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                getDocument();
            text_title = "<h2 style=\"color:#16A085\">" + tittle + "</h2>";
            text_time = "<h5 style=\"color:#16A085\">" + pudate + "</h5>\n";
            text_header = "<h4>" + document.select("div[class=box-widget desnews]").text() + "</h4>";
            detail=document.select("div[id=links]");
            for (Element details:detail) {
                text_details =fit_image+ details.html();
            }
            html = text_title +text_time+text_header + text_details;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                LoadURL();
            }catch (NullPointerException e){
                webView.loadUrl(link);
            }
        }
    }
    private void show_dialog_error(){
        /*new MaterialDialog.Builder(page_load_webview.this)
                .title(R.string.dialog_title_error)
                .content(R.string.dialog_content_error)
                .positiveText(R.string.dialog_button)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        page_load_webview.this.finish();
                        startActivity(new Intent(page_load_webview.this.getIntent()));
                    }
                })
                .show();*/
    }
}
