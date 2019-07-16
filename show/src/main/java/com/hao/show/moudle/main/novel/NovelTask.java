package com.hao.show.moudle.main.novel;

import android.util.Log;
import com.hao.lib.Util.CommonSharedPreferences;
import com.hao.lib.Util.MiLog;
import com.hao.show.db.manage.DBManager;
import com.hao.show.moudle.main.novel.Entity.NovelDetail;
import com.hao.show.moudle.main.novel.Entity.NovelListItemContent;
import com.hao.show.spider.SpiderNovelFromBiQu;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NovelTask extends Thread {
    private NovelTask() {
    }

    private int loadIndex = 0;

    private static class NovelTaskHelp {

        final static NovelTask no = new NovelTask();
    }

    public static NovelTask getInstance() {

        return NovelTaskHelp.no;
    }

    @Override
    public void run() {
        try {
            loadIndex = (int) CommonSharedPreferences.get("loadIndex", 0);
            if (!(boolean) CommonSharedPreferences.get("loadAll", false)) {
                //遍历网站上的所有小说 查询到小说相关的小说初始数据 包含作者 小说名 最新章节
                getAllNovel(startErgodic("http://www.xbiquge.la/xiaoshuodaquan/"));
            } else {
                Log.i("遍历网站", "已经遍历完成");
            }
        } catch (Exception e) {
            MiLog.i("遍历小说错误", e.getMessage());
        }
    }

    //获取到当前网页的html
    public String startErgodic(String uri) throws Exception {
        StringBuffer html = new StringBuffer();
        URL url = new URL(uri);
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
        return html.toString();
    }

    /**
     * 入库所有的小说
     *
     * @param html
     */
    @NotNull
    public void getAllNovel(String html) throws Exception {
        Document doc = Jsoup.parse(html);
        Elements novelTypeList = doc.select("div[class=novellist]");

        for (int i = 0; i < novelTypeList.size(); i++) {
            Elements novellist = novelTypeList.get(i).select("li");
            Elements noveltype = novelTypeList.get(i).select("h2");
            for (int j = 0; j < novellist.size(); j++) {
                try {
                    loadIndex = (int) CommonSharedPreferences.get("loadIndex", 0);
                    if (j >= loadIndex) {
                        NovelListItemContent novelListItemContent = new NovelListItemContent();
                        novelListItemContent.setNovelType(noveltype.text());
                        Elements node = novellist.get(j).select("a");
                        novelListItemContent.setTitle(node.text());
                        novelListItemContent.setUrl(node.attr("href"));
                        String novelDetailHtml = startErgodic(novelListItemContent.getUrl());
                        NovelDetail novelDetail = SpiderNovelFromBiQu.getNovelDetail(novelDetailHtml, novelListItemContent);
                        novelListItemContent.setNovelImage(novelDetail.getImageUrl());
                        Log.i("遍历网站", noveltype.text() + "    小说名：" + novelListItemContent.getTitle() + "    图片地址：" + novelListItemContent.getNovelImage());
                        DBManager.addNovel(novelListItemContent);
                        CommonSharedPreferences.get("loadIndex", j);
                    }
                } catch (Exception e) {
                    CommonSharedPreferences.put("loadIndex", ++loadIndex);
                }
            }
        }
        CommonSharedPreferences.put("loadAll", true);
    }


}
