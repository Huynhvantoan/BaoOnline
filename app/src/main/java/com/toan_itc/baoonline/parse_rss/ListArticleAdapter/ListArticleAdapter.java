package com.toan_itc.baoonline.parse_rss.ListArticleAdapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.toan_itc.baoonline.Activity.BaseActivity;
import com.toan_itc.baoonline.BannerActivity;
import com.toan_itc.baoonline.Lib.Class_khaibao;
import com.toan_itc.baoonline.R;
import com.toan_itc.baoonline.page_read_rss.page_load_webview;
import com.toan_itc.baoonline.parse_rss.Article.Article;
import com.toan_itc.baoonline.textviewfit.AutofitHelper;
import com.toan_itc.baoonline.textviewfit.AutofitTextView;

import java.util.ArrayList;

/**
 * Created by huynh on 10/1/2015.
 */
public class ListArticleAdapter extends RecyclerView.Adapter<ListArticleAdapter.ViewHolder> {
    private ArrayList<Article> listArticle = new ArrayList<Article>();
    Article list=new Article();
    private BaseActivity activity;
    private String news="";
    private String time="";
    public ListArticleAdapter(BaseActivity ctx, ArrayList<Article> mListArticle,String news) {
        this.activity = ctx;
        this.listArticle = mListArticle;
        this.news=news;
        Log.wtf("ListArticleAdapter:news=",news);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView itemCardView = (CardView)LayoutInflater.from(activity).inflate(R.layout.rss_item_list_row, parent, false);
        return new ViewHolder(itemCardView, activity);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try{
            list = listArticle.get(position);
            if (list != null) {
                holder.title.setText(list.getTitle());
                time = list.getPubDate_read();
                if (time != null) {
                    holder.pubdate.setText(time);
                } else {
                    holder.pubdate.setText("Sai múi giờ");
                }
                activity.imageLoader.displayImage(list.getImgLink(), holder.imgLink, activity.options);
        }}catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return (null != listArticle ? listArticle.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imgLink;
        public AutofitTextView title;
        public TextView pubdate;
        private BaseActivity activity;
        private CardView item;
        RelativeLayout layout_item;
        public ViewHolder(CardView itemView, BaseActivity activity) {
            super(itemView);
            this.item=itemView;
            this.activity = activity;
            this.layout_item=(RelativeLayout)itemView.findViewById(R.id.layout_item);
            this.imgLink = (ImageView) itemView.findViewById(R.id.imgv);
            this.title = (AutofitTextView) itemView.findViewById(R.id.title);
            this.pubdate = (TextView) itemView.findViewById(R.id.pubdate);
            CardView.LayoutParams layoutParams=new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,activity.screenHeight/4);
            layout_item.setLayoutParams(layoutParams);
            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                list = listArticle.get(getLayoutPosition());
                Bundle bundle = new Bundle();
                Intent i = new Intent(activity, BannerActivity.class);
                bundle.putString(Class_khaibao.URL, list.getUrl());
                if(news.equalsIgnoreCase(Class_khaibao.Hot)){
                    bundle.putInt(Class_khaibao.COLOR_ACTIONBAR, R.color.myPrimaryColor5);
                    bundle.putInt(Class_khaibao.COLOR_STATUSBAR, R.color.myPrimaryDarkColor5);
                }else if(news.equalsIgnoreCase(Class_khaibao.Dantri)){
                    bundle.putInt(Class_khaibao.COLOR_ACTIONBAR, R.color.myPrimaryColor2);
                    bundle.putInt(Class_khaibao.COLOR_STATUSBAR, R.color.myPrimaryDarkColor2);
                }
                bundle.putString(Class_khaibao.TITLE, list.getTitle());
                bundle.putString(Class_khaibao.PUDATE, list.getPubDate_read());
                i.putExtra(Class_khaibao.SEND_INTENT, bundle);
                activity.startActivity(i);
                //activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}