package com.toan_itc.baoonline.Task;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.toan_itc.baoonline.Activity.BaseActivity;
import com.toan_itc.baoonline.R;
import com.toan_itc.baoonline.SupperRecyclerView.OnMoreListener;
import com.toan_itc.baoonline.SupperRecyclerView.SuperRecyclerView;
import com.toan_itc.baoonline.parse_rss.Article.Article;
import com.toan_itc.baoonline.parse_rss.ListArticleAdapter.ListArticleAdapter;
import com.toan_itc.baoonline.parse_rss.RSSDataXmlHandler.RSSDataXmlHandler;
import com.toan_itc.baoonline.progressbar_dialog.Progressbar.Progressbar_dialog;

import java.util.ArrayList;

/**
 * Created by Toankul PC on 10/1/2015.
 */

public class ParseNewsTask extends AsyncTask<Void,Void,Void> implements SwipeRefreshLayout.OnRefreshListener,OnMoreListener {
    private BaseActivity activity;
    private String link = "";
    private SuperRecyclerView listView;
    private Progressbar_dialog pd;
    private ArrayList<Article> listArticle;
    private RSSDataXmlHandler rssDataXmlHandler;
    private String news = "";
    public ParseNewsTask(BaseActivity activity, String link, SuperRecyclerView listView, String news) {
        this.activity = activity;
        this.link = link;
        this.listView = listView;
        this.news = news;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try {
           // pd = new Progressbar_dialog(activity, R.color.myPrimaryColor);
           // pd.show();
            listView.setRefreshingColorResources(R.color.color_refresh,
                    R.color.color_refresh1,
                    R.color.color_refresh2,
                    R.color.color_refresh3);
            rssDataXmlHandler = new RSSDataXmlHandler(news);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            listArticle = rssDataXmlHandler.getData(link);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        try {
         //   if (pd != null) {
           //     pd.dismiss();
          //  }
            if (listArticle != null) {
                ListArticleAdapter adapter = new ListArticleAdapter(activity, listArticle, news);
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(activity, "Lấy dữ liệu bị lỗi!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {

    }

    @Override
    public void onRefresh() {

    }
}