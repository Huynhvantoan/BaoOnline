package com.toan_itc.baoonline.parse_rss.RSSDataXmlHandler;

import android.util.Log;

import com.toan_itc.baoonline.Lib.API;
import com.toan_itc.baoonline.Lib.Class_khaibao;
import com.toan_itc.baoonline.parse_rss.Article.Article;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class RSSDataXmlHandler extends DefaultHandler {
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String PUBDATE = "pubDate";
    private static final String ITEM = "item";
    private static final String LINK = "link";
    private static final String IMAGE="media:content";
    private static final String IMAGE_PCWORLD="media:thumbnail";
    private static final String IMAGE_ZingNews="enclosure";
    private static final String IMAGE_24h = "summaryImg";
    private Article currentArticle = new Article();
    private ArrayList<Article> article = new ArrayList<Article>();
	//Current characters being accumulated
	StringBuffer chars = new StringBuffer();
    private String news="";
    public RSSDataXmlHandler(String news){
        this.news=news;
        Log.wtf("RSSDataXmlHandler:news=",news);
    }
	public void startElement(String uri, String localName, String qName, Attributes atts) {
		chars = new StringBuffer();
        if (qName.equalsIgnoreCase(IMAGE)||qName.equalsIgnoreCase(IMAGE_PCWORLD)||qName.equalsIgnoreCase(IMAGE_ZingNews))
        {
            if(!atts.getValue("url").equalsIgnoreCase("null")){
                currentArticle.setImgLink(atts.getValue("url"));
                Log.wtf("setImgLink=",atts.getValue("url"));
            }
            else{
                currentArticle.setImgLink("");
            }
        }
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (localName.equalsIgnoreCase(TITLE))
		{
            currentArticle.setTitle(chars.toString());
		}
		else if (localName.equalsIgnoreCase(PUBDATE))
		{
           /* if(news.equalsIgnoreCase(Class_khaibao.Dantri)){
                currentArticle.setPubDateDantri(chars.toString());
            }else if(news.equalsIgnoreCase(Class_khaibao.Vnexpress)){
                currentArticle.setPubDate_Vnexpress(chars.toString());
            }else if(news.equalsIgnoreCase(Class_khaibao.Gamek)){
                currentArticle.setPubDate_GameK(chars.toString());
            }else if(news.equalsIgnoreCase(Class_khaibao.DSPL)){
                currentArticle.setPubDate_DSPL(chars.toString());
            }else {
                currentArticle.setPubDate_read(chars.toString());
            }*/
            currentArticle.setPubDate_read(chars.toString());
		}
		else if (localName.equalsIgnoreCase(LINK))
		{
            currentArticle.setUrl(chars.toString());
		}else if (localName.equalsIgnoreCase(IMAGE))
        {
            currentArticle.setImgLink(chars.toString());
        }else if (localName.equalsIgnoreCase(IMAGE_24h))
        {
            currentArticle.setImgLink(chars.toString());
        }
        else if (localName.equalsIgnoreCase(DESCRIPTION)) {
            currentArticle.setDescription(chars.toString(),news);
        }
		if (localName.equalsIgnoreCase(ITEM)) {
            article.add(currentArticle);
            currentArticle = new Article();
		}
	}
	public void characters(char ch[], int start, int length) {
		chars.append(new String(ch, start, length));
	}
    //Ham lay Bao
    public ArrayList<Article> getData(String url) {
        URL urlData = null;
        InputStream input = null;
        SAXParserFactory spf = null;
        SAXParser sp = null;
        XMLReader xml = null;
        Reader reader=null;
        try {
            Log.wtf("http=",url);
            urlData = new URL(url);
            reader= API.getData(urlData);
            spf = SAXParserFactory.newInstance();
            sp = spf.newSAXParser();
            xml = sp.getXMLReader();
            xml.setContentHandler(this);
            xml.parse(new InputSource(reader));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return article;
    }
}
