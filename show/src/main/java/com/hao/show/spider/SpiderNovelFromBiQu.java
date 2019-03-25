package com.hao.show.spider;

import com.hao.show.moudle.main.novel.Entity.NovelClassify;
import com.hao.show.moudle.main.novel.Entity.NovelDetail;
import com.hao.show.moudle.main.novel.Entity.NovelPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class SpiderNovelFromBiQu {
    public static final String BiQuMainUrl = "http://www.xbiquge.la";

    public static List<NovelClassify> getClassify(String html) {
        List<NovelClassify> classifyList = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        Elements rows = doc.select("div[class=nav]");
        Elements list = rows.select("li");
        for (Element e : list) {
            Elements node = e.select("a");
            classifyList.add(new NovelClassify(node.text(), BiQuMainUrl + node.attr("href")));
        }
        return classifyList;
    }

    public static NovelPage getNovelDetail(String html) {
        NovelPage novelPage = new NovelPage();
        Document doc = Jsoup.parse(html);
        Elements rows = doc.select("div#newscontent");
        Elements list = rows.select("div[class=l]");
        Elements node = list.select("li");
        for (Element e : node) {
            Elements name = e.select("span[class=s2]").select("a");
            Elements newChapter = e.select("span[class=s3]");
            Elements author = e.select("span[class=s5]");
            novelPage.getNovelDetailList().add(new NovelDetail(name.text(), BiQuMainUrl + name.attr("href"), author.text(),
                    newChapter.text(), BiQuMainUrl + newChapter.attr("href"))
            );
        }

        novelPage.setNextPageUrl(list.select("div[class=page_b]").select("div[class=pagelink]").select("a[class=next]").attr("href"));
        novelPage.setFristPageUrl(list.select("div[class=page_b]").select("div[class=pagelink]").select("a[class=ngroup]").attr("href"));
        novelPage.setLastPageUrl(list.select("div[class=page_b]").select("div[class=pagelink]").select("a[class=last]").attr("href"));
        novelPage.setBeforPageUrl(list.select("div[class=page_b]").select("div[class=pagelink]").select("a[class=prev]").attr("href"));
        return novelPage;
    }
}
