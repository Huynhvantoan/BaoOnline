package com.toan_itc.baoonline.page_news.page_hot;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toan_itc.baoonline.Activity.BaseActivity;
import com.toan_itc.baoonline.Lib.Class_khaibao;
import com.toan_itc.baoonline.R;
import com.toan_itc.baoonline.SupperRecyclerView.MySpanSizeLookup;
import com.toan_itc.baoonline.SupperRecyclerView.SuperRecyclerView;
import com.toan_itc.baoonline.Task.ParseNewsTask;
import com.toan_itc.baoonline.sqlite_database.DataDB;

/**
 * Created by Toan on 9/20/2015.
 */
@SuppressLint("ValidFragment")
public class page_hot extends Fragment {
    private BaseActivity activity;
    View view;
    int stt;
    String url;
    public page_hot(BaseActivity activity,int stt,String url){
        this.activity=activity;
        this.stt=stt;
        this.url=url;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.rss_listview, container,false);
        try {
            final DataDB data = new DataDB();
            String link = data.getUrlTinHot(stt);
            SuperRecyclerView gridView = (SuperRecyclerView) view.findViewById(R.id.list_rss);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(activity,2,GridLayoutManager.VERTICAL,false);
            gridLayoutManager.setSpanSizeLookup(new MySpanSizeLookup(3, 1, 2));
            gridView.setLayoutManager(gridLayoutManager);
            new ParseNewsTask(activity,link,gridView,Class_khaibao.Hot).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}