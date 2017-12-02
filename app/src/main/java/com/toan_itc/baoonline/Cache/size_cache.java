package com.toan_itc.baoonline.Cache;

import android.content.Context;

import java.io.File;

/**
 * Created by Toan_Kul on 12/28/2014.
 */
public class size_cache {
    private Context _Context;
    public size_cache(Context context){
        this._Context=context;
    }
    public int getFolderSize(File folder) {
        int length = 0;
        File[] files = folder.listFiles();

        int count = files.length;
        for (int i = 0; i < count; i++) {
            if (files[i].isFile()) {
                length += files[i].length();
            }
            else {
                length += getFolderSize(files[i]);
            }
        }
        return length;
    }
}
