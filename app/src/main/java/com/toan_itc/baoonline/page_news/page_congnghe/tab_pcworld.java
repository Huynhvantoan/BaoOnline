/*
package com.toan_itc.baoonline.page_news.page_congnghe;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.toan_itc.baoonline.R;
import com.toan_itc.baoonline.page_news.Class_khaibao;
import com.toan_itc.baoonline.page_read_rss.page_load_webview;
import com.toan_itc.baoonline.parse_rss.ListArticleAdapter.ListArticleAdapter_congnghe_pcword;
import com.toan_itc.baoonline.progressbar_dialog.Progressbar.Progressbar_dialog;
import com.toan_itc.baoonline.sqlite_database.DataDB;

*/
/**
 * Created by Toan_Kul on 11/25/2014.
 *//*

public class tab_pcworld extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private Class_khaibao khaibao=new Class_khaibao();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rss_listview, container,
                false);
        final DataDB data=new DataDB();
        khaibao.link=data.getUrlCongnghe(2);
        khaibao.listview=(ListView)view.findViewById(R.id.list_rss);
        khaibao.swipeLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_layout);
        new Image_Asynctask().execute();
        khaibao.swipeLayout.setOnRefreshListener(this);
        khaibao.swipeLayout.setColorSchemeResources(R.color.color_refresh,
                R.color.color_refresh1,
                R.color.color_refresh2,
                R.color.color_refresh3);
        khaibao.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), page_load_webview.class);
                khaibao.page_url = khaibao.listArticle_congnghe.get(position).getUrl();
                khaibao.title=khaibao.listArticle_congnghe.get(position).getTitle();
                khaibao.pudate=khaibao.listArticle_congnghe.get(position).getPubDate_read();
                khaibao.description=khaibao.listArticle_congnghe.get(position).getDescriptionFull();
                i.putExtra("url", khaibao.page_url);
                i.putExtra("actionbar", R.color.myPrimaryColor7);
                i.putExtra("statusbar", R.color.myPrimaryDarkColor7);
                i.putExtra("title",khaibao.title);
                i.putExtra("pudate",khaibao.pudate);
                i.putExtra("description",khaibao.description);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }});
        return view;
    }
    @Override
    public void onRefresh() {
        new AsyncTask<Void,Void,Void>() {
            @Override
            protected void onPreExecute() {
                khaibao.listArticle_congnghe.clear();
                khaibao.listview.removeHeaderView(khaibao.adapter_congnghe_pcword.viewheader);
            }
            @Override
            protected Void doInBackground(Void... params) {
                khaibao.listArticle_congnghe = khaibao.rssDataXmlHandler_congnghe.getData(khaibao.link);
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                khaibao.listview.addHeaderView(khaibao.adapter_congnghe_pcword.getheader());
                khaibao.listview.setAdapter(khaibao.adapter_congnghe_pcword);
                khaibao.adapter_congnghe_pcword.notifyDataSetChanged();
                khaibao.swipeLayout.setRefreshing(false);
                Log.wtf("Refresh", "Complete");
            }
        }.execute();
    }
    private class Image_Asynctask extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            khaibao.pd=new Progressbar_dialog(getActivity(), R.color.myPrimaryDarkColor7);
            khaibao.pd.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            khaibao.listArticle_congnghe = khaibao.rssDataXmlHandler_congnghe.getData(khaibao.link);
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            khaibao.pd.dismiss();
            khaibao.adapter_congnghe_pcword = new ListArticleAdapter_congnghe_pcword(getActivity(),khaibao.listArticle_congnghe);
            khaibao.listview.addHeaderView(khaibao.adapter_congnghe_pcword.getheader());
            khaibao.listview.setAdapter(khaibao.adapter_congnghe_pcword);
        }
    }
}

*/
