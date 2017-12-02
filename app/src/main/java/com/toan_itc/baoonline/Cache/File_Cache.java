package com.toan_itc.baoonline.Cache;

import android.content.Context;

import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by Toan_Kul on 1/16/2015.
 */
public class File_Cache {
    private File cacheDir;
    private Context _Context;
    private File localFile;
    public File_Cache(Context context) {
        this._Context = context;
    }

    //Tao folder
    public void CreateFolder(Context context) {
        /*String state = android.os.Environment.getExternalStorageState();
        if(state.equals(android.os.Environment.MEDIA_MOUNTED)) {
           for(this.cacheDir = StorageUtils.getOwnCacheDirectory(context, "Android/data/BaoOnline/Cache");;this.cacheDir=_Context.getCacheDir()) {
               if (!this.cacheDir.exists() && this.cacheDir == null) {
                   this.cacheDir.mkdirs();
                   return;
               }
           }
        }*/
        this.cacheDir = StorageUtils.getOwnCacheDirectory(context, "Android/data/BaoOnline/Cache");
        if (!this.cacheDir.exists() && this.cacheDir == null) {
                this.cacheDir.mkdirs();
        }
    }

    public File getsizeCache(Context context) {
        this.cacheDir = StorageUtils.getOwnCacheDirectory(context, "Android/data/BaoOnline/Cache");
        return cacheDir;
    }

    public File loadcache(String paramString) {
        try
        {
            String str = String.valueOf(paramString.hashCode());
            localFile = new File(this.cacheDir, str);
            return localFile;
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }
        return null;
    }
}

