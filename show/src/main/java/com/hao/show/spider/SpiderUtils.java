package com.hao.show.spider;

import android.util.Log;
import com.hao.lib.base.Rx.Rx;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpiderUtils {

    /**
     * 通过网址获取到当前网页的html
     * 获取到的网页数据,使用backCall回调进行处理
     * 拉取操作是在子线程中进行处理
     *
     * @param urlString
     * @return
     */
    public static void getHtml(final String urlString, final Object tag) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    StringBuffer html = new StringBuffer();
                    URL url;
                    if (urlString.startsWith("http://") || urlString.startsWith("https://")) {
                        url = new URL(urlString);
                    } else {
                        url = new URL("http://" + urlString);
                    }
                    Log.i("解析地址", "url=" + url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                    BufferedReader br = new BufferedReader(isr);
                    String temp;
                    while ((temp = br.readLine()) != null) {
                        html.append(temp).append("\n");
                    }
                    br.close();
                    isr.close();
                    Rx.getInstance().sendMessage(tag, html.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
