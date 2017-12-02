package com.toan_itc.baoonline.page_news.page_gamek;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
 * Created by huynh on 9/30/2015.
 */
@SuppressLint("ValidFragment")
public class page_gamek extends Fragment {
    private BaseActivity activity;
    View view;
    int stt;
    public page_gamek(BaseActivity activity, int stt){
        this.activity=activity;
        this.stt=stt;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.rss_listview, container,false);
        try {
            final DataDB data = new DataDB();
            String link = data.getUrlGameK(stt);
            SuperRecyclerView gridView = (SuperRecyclerView) view.findViewById(R.id.list_rss);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(activity,2,GridLayoutManager.VERTICAL,false);
            gridView.setLayoutManager(gridLayoutManager);
            new ParseNewsTask(activity,link,gridView,Class_khaibao.Gamek).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}