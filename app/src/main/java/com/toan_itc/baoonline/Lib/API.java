package com.toan_itc.baoonline.Lib;

import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class API {
	private static Reader reader=null;
	public static Reader getData(URL SERVER_URL) {
		try {
			HttpURLConnection conn = (HttpURLConnection) SERVER_URL.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(20000);
			conn.setConnectTimeout(30000);
			conn.connect();
			int code = conn.getResponseCode();
			if (code == 200)  {
				InputStream in = conn.getInputStream();
				reader = new InputStreamReader(in);
			}
			else {
				Log.wtf("error:", "Server responded with status code: ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reader;
	}
}
