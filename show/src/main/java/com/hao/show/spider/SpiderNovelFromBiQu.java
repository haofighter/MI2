package com.hao.show.spider;

import com.hao.show.moudle.main.novel.Entity.NovelClassify;
import com.hao.show.moudle.main.novel.Entity.NovelDetail;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class SpiderNovelFromBiQu {
    public static final String BiQuMainUrl = "https://www.xbiquge6.com";

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

    public static List<NovelDetail> getNovelDetail(String html) {
        List<NovelDetail> classifyList = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        Elements rows = doc.select("div#newscontent");
        Elements node = rows.select("li");
        for (Element e : node) {
            Elements name = e.select("span[class=s2]").select("a");
            Elements newChapter = e.select("span[class=s3]");
            Elements author = e.select("span[class=s5]");
            classifyList.add(new NovelDetail(name.text(), BiQuMainUrl + name.attr("href"), author.text(),
                    newChapter.text(), BiQuMainUrl + newChapter.attr("href"))
            );
        }
        return classifyList;
    }
}
