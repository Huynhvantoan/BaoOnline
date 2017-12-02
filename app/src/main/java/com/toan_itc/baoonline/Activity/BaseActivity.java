package com.toan_itc.baoonline.Activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.toan_itc.baoonline.R;
import com.toan_itc.baoonline.rate_app.AppRater;

import java.io.File;

/**
 * Created by Toankul PC on 8/1/2015.
 */
public class BaseActivity extends AppCompatActivity{
    public ImageLoader imageLoader = ImageLoader.getInstance();
    public DisplayImageOptions options;
    public int screenWidth,screenHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Rate App lan thu 2 kich hoat Remind sau 7 ngay +lan 10 vao app
        AppRater.app_launched(this, 0, 2, 7, 10, true);
        ImageLoader();
        Caculator();
    }
    private void ImageLoader(){
        imageLoader = ImageLoader.getInstance();
        initImageLoader(this, imageLoader);
        options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnLoading(R.drawable.background_transparent)
                .showImageForEmptyUri(R.drawable.background_transparent)
                .showImageOnFail(R.drawable.background_transparent)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }
    public static void initImageLoader(Context context,ImageLoader imageLoader) {
        //Tao duong dan chua Cache tren bo nho SD khai bao memorycache de dua cache vao the sd
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "Android/data/BaoOnline/cache");
        Md5FileNameGenerator md5FileNameGenerator=new Md5FileNameGenerator();
        //Khai bao de su dung thu vien load anh  Android Universal Image Loader
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .denyCacheImageMultipleSizesInMemory()
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .threadPoolSize(5)
                .memoryCache(new WeakMemoryCache())
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(md5FileNameGenerator)
                .diskCache(new UnlimitedDiskCache(cacheDir, null, md5FileNameGenerator))
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs()
                .build();
        imageLoader.init(config);
    }
    private void Caculator() {
        try {
            DisplayMetrics dm = getResources().getDisplayMetrics();
            screenWidth = dm.widthPixels;
            screenHeight = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
