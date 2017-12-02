package com.toan_itc.baoonline.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.toan_itc.baoonline.About_Library.About_Activity;
import com.toan_itc.baoonline.Cache.File_Cache;
import com.toan_itc.baoonline.Cache.size_cache;
import com.toan_itc.baoonline.Menucontext.ContextMenuDialogFragment;
import com.toan_itc.baoonline.Menucontext.MenuObject;
import com.toan_itc.baoonline.Menucontext.interfaces.OnMenuItemClickListener;
import com.toan_itc.baoonline.Menucontext.interfaces.OnMenuItemLongClickListener;
import com.toan_itc.baoonline.Navigation_menu.NavigationDrawerFragment;
import com.toan_itc.baoonline.Navigation_menu.NavigationDrawer_Material_Callbacks;
import com.toan_itc.baoonline.R;
import com.toan_itc.baoonline.Tabhost.PagerSlidingTabStrip;
import com.toan_itc.baoonline.effects_viewpage.AccordionTransformer;
import com.toan_itc.baoonline.page_news.page_bongda.page_bongda;
import com.toan_itc.baoonline.page_news.page_dantri.page_dantri;
import com.toan_itc.baoonline.page_news.page_doisongphapluat.page_dspl;
import com.toan_itc.baoonline.page_news.page_gamek.page_gamek;
import com.toan_itc.baoonline.page_news.page_hot.page_hot;
import com.toan_itc.baoonline.page_news.page_kenh14.page_kenh14;
import com.toan_itc.baoonline.page_news.page_ngoisao.page_ngoisao;
import com.toan_itc.baoonline.page_news.page_vnexpress.page_vnexpress;
import com.toan_itc.baoonline.rate_app.AppRater;
import com.toan_itc.baoonline.sqlite_database.DataDB;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements NavigationDrawer_Material_Callbacks,OnMenuItemClickListener,OnMenuItemLongClickListener {
    private BaseActivity activity=this;
    private Toolbar mToolbar;
    NavigationDrawerFragment mNavigationDrawerFragment;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private ArrayList<ArrayList> arrayList_tab;
    private DataDB dataDB = new DataDB();
    private SystemBarTintManager tintManager;
    private static final long MAX_SIZE = 100;
    size_cache clean_size_cache;
    File_Cache file_cache;
    private DialogFragment mMenuDialogFragment;
    private FragmentManager fragmentManager;
    private PagerSlidingTabStrip slidingTabLayout;
    private Handler handler;
    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        FirebaseCrash.log("Activity created");
        setContentView(R.layout.activity_main);
        DataDB dataDB=new DataDB();
        dataDB.exportJson(this,"tinhot");
        dataDB.exportJson(this,"vnexpress");
        dataDB.exportJson(this,"kenh14");
        dataDB.exportJson(this,"vietbao");
        dataDB.exportJson(this,"dantri");
        dataDB.exportJson(this,"doisongphapluat");
        dataDB.exportJson(this,"bongda");
        dataDB.exportJson(this,"congnghe");
        dataDB.exportJson(this,"gamek");
        //Restart lai App khi bi Focusclose
       // Thread.setDefaultUncaughtExceptionHandler(new Restart_app(this, MainActivity.class));
        //Tạo Toolbar và Silder menu
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Tin Hot");
        fragmentManager = getSupportFragmentManager();
        mNavigationDrawerFragment = (NavigationDrawerFragment) fragmentManager.findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) this.findViewById(R.id.drawer),this.mToolbar);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance((int) getResources().getDimension(R.dimen.menu_context_Height), getMenuObjects());
        //Tao mau cho Statusbar
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        //Tao Tab Host
        pagerAdapter = new ViewPagerAdapterTinHot(fragmentManager);
        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setOffscreenPageLimit(1);
        pager.setAdapter(pagerAdapter);
        pager.setPageTransformer(true, new AccordionTransformer());
        slidingTabLayout = (PagerSlidingTabStrip) findViewById(R.id.SlidingTabLayout);
        slidingTabLayout.setOnTabReselectedListener(new PagerSlidingTabStrip.OnTabReselectedListener() {
            @Override
            public void onTabReselected(int position) {
                Toast.makeText(MainActivity.this, "Tab reselected: " + position, Toast.LENGTH_SHORT).show();
            }
        });
       /* slidingTabLayout.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);
        slidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.ColorBottom_Tab));
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(pager);
        slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });*/
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        switch (position) {
            case 0:
                Toast.makeText(this, "Trang chủ ", Toast.LENGTH_SHORT).show();
                handler = new Handler();
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.myPrimaryColor));
                        slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.myPrimaryColor));
                        tintManager.setStatusBarTintColor(getResources().getColor(R.color.myPrimaryDarkColor));
                        getSupportActionBar().setTitle("Tin nổi bật");
                        pagerAdapter = new ViewPagerAdapterTinHot(fragmentManager);
                        Check_Wifi();
                    }
                };
                handler.postDelayed(r, 100);
                break;
            case 1:
                getSupportActionBar().setTitle("VnExpress ");
                mToolbar.setBackgroundColor(getResources().getColor(R.color.myPrimaryColor1));
                slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.myPrimaryColor1));
                tintManager.setStatusBarTintColor(getResources().getColor(R.color.myPrimaryDarkColor1));
                pagerAdapter = new ViewPagerAdapterVnExpress(fragmentManager);
                Check_Wifi();
                break;
            case 2:
                mToolbar.setBackgroundColor(getResources().getColor(R.color.myPrimaryColor2));
                slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.myPrimaryColor2));
                tintManager.setStatusBarTintColor(getResources().getColor(R.color.myPrimaryDarkColor2));
                getSupportActionBar().setTitle("Dân Trí");
                pagerAdapter = new ViewPagerAdapterDanTri(fragmentManager);
                Check_Wifi();
                break;
            case 3:
                mToolbar.setBackgroundColor(getResources().getColor(R.color.myPrimaryColor3));
                slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.myPrimaryColor3));
                tintManager.setStatusBarTintColor(getResources().getColor(R.color.myPrimaryDarkColor3));
                getSupportActionBar().setTitle("Đời Sống & Pháp Luật");
                pagerAdapter = new ViewPagerAdapterDoisongphapluat(fragmentManager);
                Check_Wifi();
                break;
            case 4:
                mToolbar.setBackgroundColor(getResources().getColor(R.color.myPrimaryColor4));
                slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.myPrimaryColor4));
                tintManager.setStatusBarTintColor(getResources().getColor(R.color.myPrimaryDarkColor4));
                getSupportActionBar().setTitle("Ngôi Sao");
                pagerAdapter = new ViewPagerAdapterNgoiSao(fragmentManager);
                Check_Wifi();
                break;
            case 5:
                mToolbar.setBackgroundColor(getResources().getColor(R.color.myPrimaryColor5));
                slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.myPrimaryColor5));
                tintManager.setStatusBarTintColor(getResources().getColor(R.color.myPrimaryDarkColor5));
                getSupportActionBar().setTitle("Bóng Đá");
                pagerAdapter = new ViewPagerAdapterBongDa(fragmentManager);
                Check_Wifi();
                break;
            case 6:
                mToolbar.setBackgroundColor(getResources().getColor(R.color.myPrimaryColor6));
                slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.myPrimaryColor6));
                tintManager.setStatusBarTintColor(getResources().getColor(R.color.myPrimaryDarkColor6));
                getSupportActionBar().setTitle("Kênh14");
                pagerAdapter = new ViewPagerAdapterKenh14(fragmentManager);
                Check_Wifi();
                break;
            case 7:
                try {
                    mToolbar.setBackgroundColor(getResources().getColor(R.color.myPrimaryColor7));
                    slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.myPrimaryColor7));
                    tintManager.setStatusBarTintColor(getResources().getColor(R.color.myPrimaryDarkColor7));
                    getSupportActionBar().setTitle("Công nghệ");
                    pagerAdapter = new ViewPagerAdapterCongnghe(fragmentManager);
                    Check_Wifi();
                }catch (NullPointerException e){

                }
                break;
            case 8:
                mToolbar.setBackgroundColor(getResources().getColor(R.color.myPrimaryColor8));
                slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.myPrimaryColor8));
                tintManager.setStatusBarTintColor(getResources().getColor(R.color.myPrimaryDarkColor8));
                getSupportActionBar().setTitle("GameK");
                pagerAdapter = new ViewPagerAdapterGameK(fragmentManager);
                Check_Wifi();
                break;
        }
    }
    private void Check_Wifi(){
        /*check_connection check_connection=new check_connection(MainActivity.this);
        if(check_connection.isConnectingToInternet()){
            pager.setAdapter(pagerAdapter);
            slidingTabLayout.setViewPager(pager);
        }
        else{
            dialogCheckInternet=new Dialog_checkInternet(MainActivity.this);
        }*/
        pager.setOffscreenPageLimit(1);
        pager.setAdapter(pagerAdapter);
        slidingTabLayout.setViewPager(pager);
    }

    private class ViewPagerAdapterTinHot extends FragmentStatePagerAdapter {
        public ViewPagerAdapterTinHot(FragmentManager fm) {
            super(fm);
        }

        @Override
        //Tạo Fragment cho tab lưu ý số Tab= đúng số title lấy từ database
        public Fragment getItem(int num) {
            switch (num) {
                case 0:
                    return new page_hot(activity,0,"");
                case 1:
                    return new page_hot(activity,1,"");
                case 2:
                    return new page_hot(activity,2,"");
                case 3:
                    return new page_hot(activity,3,"");
                case 4:
                    return new page_hot(activity,4,"");
                case 5:
                    return new page_hot(activity,5,"url");
            }
            return null;
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            arrayList_tab = dataDB.getTitleTinHot_Tab(activity);
            return (CharSequence) arrayList_tab.get(position);
        }
    }

    private class ViewPagerAdapterVnExpress extends FragmentStatePagerAdapter {
        public ViewPagerAdapterVnExpress(FragmentManager fm) {
            super(fm);
        }

        @Override
        //Tạo Fragment cho tab lưu ý số Tab= đúng số title lấy từ database
        public Fragment getItem(int num) {
            switch (num) {
                case 0:
                    return new page_vnexpress(activity,0);
                case 1:
                    return new page_vnexpress(activity,1);
                case 2:
                    return new page_vnexpress(activity,2);
                case 3:
                    return new page_vnexpress(activity,3);
                case 4:
                    return new page_vnexpress(activity,4);
                case 5:
                    return new page_vnexpress(activity,5);
                case 6:
                    return new page_vnexpress(activity,6);
                case 7:
                    return new page_vnexpress(activity,7);
                case 8:
                    return new page_vnexpress(activity,8);
                case 9:
                    return new page_vnexpress(activity,9);
                case 10:
                    return new page_vnexpress(activity,10);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 11;
        }

        //Tạo TITLE cho TAB
        @Override
        public CharSequence getPageTitle(int position) {
            arrayList_tab = dataDB.getTitleVnExpress_Tab(activity);
            return (CharSequence) arrayList_tab.get(position);
        }
    }

    private class ViewPagerAdapterDanTri extends FragmentStatePagerAdapter {

        public ViewPagerAdapterDanTri(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int num) {
            switch (num) {
                case 0:
                    return new page_dantri(activity,0);
                case 1:
                    return new page_dantri(activity,1);
                case 2:
                    return new page_dantri(activity,2);
                case 3:
                    return new page_dantri(activity,3);
                case 4:
                    return new page_dantri(activity,4);
                case 5:
                    return new page_dantri(activity,5);
                case 6:
                    return new page_dantri(activity,6);
                case 7:
                    return new page_dantri(activity,7);
                case 8:
                    return new page_dantri(activity,8);
                case 9:
                    return new page_dantri(activity,9);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            arrayList_tab = dataDB.getTitleDantri_Tab(activity);
            return (CharSequence) arrayList_tab.get(position);
        }
    }

    private class ViewPagerAdapterDoisongphapluat extends FragmentStatePagerAdapter {
        public ViewPagerAdapterDoisongphapluat(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int num) {
            switch (num) {
                case 0:
                    return new page_dspl(activity,0);
                case 1:
                    return new page_dspl(activity,1);
                case 2:
                    return new page_dspl(activity,2);
                case 3:
                    return new page_dspl(activity,3);
                case 4:
                    return new page_dspl(activity,4);
                case 5:
                    return new page_dspl(activity,5);
                case 6:
                    return new page_dspl(activity,6);
                case 7:
                    return new page_dspl(activity,7);
                case 8:
                    return new page_dspl(activity,8);
                case 9:
                    return new page_dspl(activity,9);
                case 10:
                    return new page_dspl(activity,10);
                case 11:
                    return new page_dspl(activity,11);
                case 12:
                    return new page_dspl(activity,12);
                case 13:
                    return new page_dspl(activity,13);
                case 14:
                    return new page_dspl(activity,14);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 15;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            arrayList_tab = dataDB.getTitleDoisongphapluat_Tab(activity);
            return (CharSequence) arrayList_tab.get(position);
        }
    }

    private class ViewPagerAdapterNgoiSao extends FragmentStatePagerAdapter {
        public ViewPagerAdapterNgoiSao(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int num) {
            switch (num) {
                case 0:
                    return new page_ngoisao(activity,0);
                case 1:
                    return new page_ngoisao(activity,1);
                case 2:
                    return new page_ngoisao(activity,2);
                case 3:
                    return new page_ngoisao(activity,3);
                case 4:
                    return new page_ngoisao(activity,4);
                case 5:
                    return new page_ngoisao(activity,5);
                case 6:
                    return new page_ngoisao(activity,6);
                case 7:
                    return new page_ngoisao(activity,7);
                case 8:
                    return new page_ngoisao(activity,8);
                case 9:
                    return new page_ngoisao(activity,9);
                case 10:
                    return new page_ngoisao(activity,10);
                case 11:
                    return new page_ngoisao(activity,11);
                case 12:
                    return new page_ngoisao(activity,12);
                case 13:
                    return new page_ngoisao(activity,13);
                case 14:
                    return new page_ngoisao(activity,14);
                case 15:
                    return new page_ngoisao(activity,15);
                case 16:
                    return new page_ngoisao(activity,16);
                case 17:
                    return new page_ngoisao(activity,17);
                case 18:
                    return new page_ngoisao(activity,18);
                case 19:
                    return new page_ngoisao(activity,19);
                case 20:
                    return new page_ngoisao(activity,20);
                case 21:
                    return new page_ngoisao(activity,21);
                case 22:
                    return new page_ngoisao(activity,22);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 23;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            arrayList_tab = dataDB.getTitleNgoiSao_Tab(activity);
            return (CharSequence) arrayList_tab.get(position);
        }
    }

    private class ViewPagerAdapterBongDa extends FragmentStatePagerAdapter {
        public ViewPagerAdapterBongDa(FragmentManager fm) {
            super(fm);
        }

        @Override
        //Tạo Fragment cho tab lưu ý số Tab= đúng số title lấy từ database
        public Fragment getItem(int num) {
            switch (num) {
                case 0:
                    return new page_bongda(activity,0);
                case 1:
                    return new page_bongda(activity,1);
                case 2:
                    return new page_bongda(activity,2);
                case 3:
                    return new page_bongda(activity,3);
                case 4:
                    return new page_bongda(activity,4);
                case 5:
                    return new page_bongda(activity,5);
                case 6:
                    return new page_bongda(activity,6);
                case 7:
                    return new page_bongda(activity,7);
                case 8:
                    return new page_bongda(activity,8);
                case 9:
                    return new page_bongda(activity,9);
            }
            return null;
        }
        @Override
        public int getCount() {
            return 10;
        }
        //Tạo TITLE cho TAB
        @Override
        public CharSequence getPageTitle(int position) {
            arrayList_tab = dataDB.getTitleBongda_Tab(activity);
            return (CharSequence) arrayList_tab.get(position);
        }
    }
    private class ViewPagerAdapterKenh14 extends FragmentStatePagerAdapter {
        public ViewPagerAdapterKenh14(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int num) {
            switch (num) {
                case 0:
                    return new page_kenh14(activity,0);
                case 1:
                    return new page_kenh14(activity,1);
                case 2:
                    return new page_kenh14(activity,2);
                case 3:
                    return new page_kenh14(activity,3);
                case 4:
                    return new page_kenh14(activity,4);
                case 5:
                    return new page_kenh14(activity,5);
                case 6:
                    return new page_kenh14(activity,6);
                case 7:
                    return new page_kenh14(activity,7);
                case 8:
                    return new page_kenh14(activity,8);
                case 9:
                    return new page_kenh14(activity,9);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            arrayList_tab = dataDB.getTitleKenh14_Tab(activity);
            return (CharSequence) arrayList_tab.get(position);
        }
    }

    private class ViewPagerAdapterCongnghe extends FragmentStatePagerAdapter {

        public ViewPagerAdapterCongnghe(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int num) {
            switch (num) {
             /*   case 0:
                    return new tab_sohoa();
                case 1:
                    return new tab_vnreview();
                case 2:
                    return new tab_pcworld();
                case 3:
                    return new tab_techz();*/
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            arrayList_tab = dataDB.getTitleCongnghe_Tab(activity);
            return (CharSequence) arrayList_tab.get(position);
        }
    }
    private class ViewPagerAdapterGameK extends FragmentStatePagerAdapter {
        public ViewPagerAdapterGameK(FragmentManager fm) {
            super(fm);
        }

        @Override
        //Tạo Fragment cho tab lưu ý số Tab= đúng số title lấy từ database
        public Fragment getItem(int num) {
            switch (num) {
                case 0:
                    return new page_gamek(activity,0);
                case 1:
                    return new page_gamek(activity,1);
                case 2:
                    return new page_gamek(activity,2);
                case 3:
                    return new page_gamek(activity,3);
                case 4:
                    return new page_gamek(activity,4);
                case 5:
                    return new page_gamek(activity,5);
                case 6:
                    return new page_gamek(activity,6);
                case 7:
                    return new page_gamek(activity,7);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 8;
        }

        //Tạo TITLE cho TAB
        @Override
        public CharSequence getPageTitle(int position) {
            arrayList_tab = dataDB.getTitleGameK_Tab(activity);
            return (CharSequence) arrayList_tab.get(position);
        }
    }
    //Menu
    private List<MenuObject> getMenuObjects() {
        List<MenuObject> menuObjects = new ArrayList<>();
        menuObjects.add(new MenuObject(R.mipmap.ic_back));
     /*   menuObjects.add(new MenuObject(R.mipmap.icn_close, "Chế độ kết nối yếu"));*/
        menuObjects.add(new MenuObject(R.mipmap.ic_danhgia, "Đánh giá"));
        menuObjects.add(new MenuObject(R.mipmap.ic_about, "Thông tin ứng dụng"));
        menuObjects.add(new MenuObject(R.mipmap.icn_close, "Thoát"));
        return menuObjects;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.context_menu) {
            mMenuDialogFragment.show(fragmentManager, "Menu_BáoOnline");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMenuItemClick(View view, int position) {
        switch (position) {
            case 0:
                break;
            case 1:
                AppRater.rateNow(activity);
                break;
            case 2:
                Intent i=new Intent(activity, About_Activity.class);
                startActivity(i);
                break;
            case 3:
                AppExit();
                break;
        }
    }
    @Override
    public void onMenuItemLongClick(View clickedView, int position) {

    }
    private void AppExit()
    {
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        transition();
    }
    //Thoat App khi bam nut back
    @Override
    public void onBackPressed() {
      /*  new MaterialDialog.Builder(activity)
                .title(R.string.dialog_title_exit)
                .content(R.string.dialog_exit)
                .positiveText("Không")
                .negativeText("Thoát")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        dialog.dismiss();
                    }
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        Clear();
                        AppExit();
                    }
                })
                .show();*/
    }
    //Clear cache
    private void Clear(){
    clean_size_cache =new size_cache(getApplicationContext());
    file_cache=new File_Cache(activity);
    long Size= clean_size_cache.getFolderSize(file_cache.getsizeCache(activity));
    Size=Size/1024/1024;
    if(Size>MAX_SIZE)
        {
            ImageLoader.getInstance().clearDiskCache();
            Log.wtf("Complete", "Clear Cache");
            Toast.makeText(activity, "Clear_Cache! Cảm ơn bạn đã sử dụng Báo online.", Toast.LENGTH_LONG).show();
        }
    }
    //Hieu ung thoat app
    private void transition(){
        overridePendingTransition(R.anim.scale_exit, R.anim.scale_exit);
    }
}