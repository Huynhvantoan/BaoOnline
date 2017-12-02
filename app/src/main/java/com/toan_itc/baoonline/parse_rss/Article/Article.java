package com.toan_itc.baoonline.parse_rss.Article;

import android.util.Log;

import com.toan_itc.baoonline.Lib.Class_khaibao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Toan_Kul on 11/23/2014.
 */
public class Article{
    private String title;
    private String imgLink;
    private String url;
    private String pubDate_read;
    private String description;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setPubDate_read(String pubDate) {
        this.pubDate_read = pubDate;
        /*String s="";
        DateFormat date_format;
        date_format= new SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.ENGLISH);
        if(this.pubDate_read.length()>25) {
            s = this.pubDate_read.substring(0, 25);
        }else{
            s=this.pubDate_read;
        }
        Date date=null;
        try {
            date = date_format.parse(s);
        } catch (ParseException e) {
            date_format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss",Locale.ENGLISH);
            try {
                date = date_format.parse(s);
            } catch (ParseException e1) {
                date_format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a",Locale.ENGLISH);
                try {
                    date = date_format.parse(s);
                } catch (ParseException e2) {
                    date_format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss",Locale.ENGLISH);
                    try {
                        date = date_format.parse(s);
                    } catch (ParseException e3) {
                        date_format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss",Locale.ENGLISH);
                        try {
                            date = date_format.parse(s);
                        } catch (ParseException e4) {
                          e4.printStackTrace();
                        }
                    }
                }
            }
        }
        this.pubDate_read=GetDate(date,date_format);*/
    }
    public String getPubDate_read() {
        return pubDate_read;
    }
    private String GetDate(Date date,DateFormat dateFormat){
        String getdate = dateFormat.format(new Date());
        String time = null;
        try {
            Date date2 = dateFormat.parse(getdate);
            long diff = date2.getTime() - date.getTime();
            long diffseconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long housre = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if(diffDays > 1)
                time =diffDays + " Ngày trước";
            else if (diffDays == 1)
                time ="1 Ngày "+ housre + " Giờ trước";
            else if (housre > 1)
                time =housre + " Giờ "+diffMinutes + " Phút trước";
            else if (housre == 1)
                time = "1 Giờ " + diffMinutes + " Phút trước";
            else if (diffMinutes > 1)
                time =diffMinutes + " Phút trước";
            else  if (diffMinutes == 1)
                time = "1 Phút " + diffseconds + " Giây trước";
            else
                time = "Vừa mới đây";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }
    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
    public String getImgLink() {
        return imgLink;
    }
    public void setDescription(String description,String kenh14) {
        this.description = description;
        int indexOf;
        //parse description for any image or video links
        try {
            if (description.contains("<img ")) {
                String img = description.substring(description.indexOf("<img "));
                img = img.substring(img.indexOf("src=") + 5);
                if(kenh14.equalsIgnoreCase(Class_khaibao.Kenh14)) {
                    indexOf = img.indexOf("'");
                }else {
                    indexOf = img.indexOf("\"");
                }
                if (indexOf == -1) {
                    indexOf = img.indexOf(".jpg") + 4;
                }
                img = img.substring(0, indexOf);
                setImgLink(img);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}