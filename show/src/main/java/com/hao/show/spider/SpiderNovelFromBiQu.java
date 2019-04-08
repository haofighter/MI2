package com.hao.show.spider;

import com.hao.show.moudle.main.novel.Entity.*;
import org.jetbrains.annotations.NotNull;
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
            classifyList.add(new NovelClassify(node.text(), CheckedUrl(node.attr("href"))));
        }
        return classifyList;
    }

    //从笔趣阁中获取到到小说的列表数据
    public static NovelPage getNovelList(String html) {
        NovelPage novelPage = new NovelPage();
        Document doc = Jsoup.parse(html);
        Elements rows = doc.select("div#newscontent");
        Elements list = rows.select("div[class=l]");
        Elements node = list.select("li");
        for (Element e : node) {
            Elements name = e.select("span[class=s2]").select("a");
            Elements newChapter = e.select("span[class=s3]");
            Elements author = e.select("span[class=s5]");
            novelPage.getNovelListItemContentList().add(new NovelListItemContent(name.text(), CheckedUrl(name.attr("href")), author.text(),
                    newChapter.text(), CheckedUrl(newChapter.attr("href")))
            );
        }

        novelPage.setNextPageUrl(list.select("div[class=page_b]").select("div[class=pagelink]").select("a[class=next]").attr("href"));
        novelPage.setFristPageUrl(list.select("div[class=page_b]").select("div[class=pagelink]").select("a[class=ngroup]").attr("href"));
        novelPage.setLastPageUrl(list.select("div[class=page_b]").select("div[class=pagelink]").select("a[class=last]").attr("href"));
        novelPage.setBeforPageUrl(list.select("div[class=page_b]").select("div[class=pagelink]").select("a[class=prev]").attr("href"));
        return novelPage;
    }

    //获取单个小说的当前信息
    public static NovelDetail getNovelDetail(String html) {
        NovelDetail novelDetail = new NovelDetail();
        Document doc = Jsoup.parse(html);
        Elements frist = doc.select("div[class=box_con]");
        String url = frist.select("div#fmimg").select("img").attr("src");
        String title = frist.select("div#maininfo").select("div#info").select("h1").text();
        Elements someInfo = frist.select("div#maininfo").select("div#info").select("p");
        String auther = someInfo.get(0).text();
        String type = someInfo.get(1).text();
        Elements lastChapter = someInfo.get(3).select("a");
        novelDetail.setImageUrl(CheckedUrl(url));
        novelDetail.setNovel_title(title);
        novelDetail.setNovel_auther(auther);
        novelDetail.setNovel_type(type);
        novelDetail.setLast_chapter_name(lastChapter.text());
        novelDetail.setLast_chapter_url(CheckedUrl(lastChapter.attr("href")));
        String introduce = frist.select("div#maininfo").select("div#intro").select("p").get(1).text();
        novelDetail.setNovel_introduce(introduce);
        List<NovelChapter> chapters = new ArrayList<>();
        Elements selectChapter = frist.select("div#list").select("dd");
        for (Element e : selectChapter) {
            String chapterUrl = e.select("a").attr("href");
            String chapterName = e.select("a").text();
            chapters.add(new NovelChapter(chapterName, CheckedUrl(chapterUrl)));
        }
        novelDetail.setNovelChapters(chapters);
        return novelDetail;
    }


    //用于URL的检测
    private static String CheckedUrl(String string) {
        if (string.startsWith("http")) {
            return string;
        } else {
            return BiQuMainUrl + string;
        }
    }

    @NotNull
    public static NovelContent getNovelContent(@NotNull String html) {
        NovelContent novelContent = new NovelContent();
        Document doc = Jsoup.parse(html);
        Elements frist = doc.select("div[class=box_con]");
        Elements second = frist.select("div[class=bookname]");
        novelContent.setChapterName(second.select("h1").text());
        Elements thrid = second.select("div[class=bottem1]").select("a");
        novelContent.setBeforChapterUrl(CheckedUrl(thrid.get(1).attr("href")));
        novelContent.setNextChapterUrl(CheckedUrl(thrid.select("a").get(3).attr("href")));
        novelContent.setChapterContent(frist.select("div#content").html().replace(" ", "").replace("\n", "").replace("<br>&nbsp;&nbsp;&nbsp;&nbsp;", "\n  ").replace("<br>", "").replace("&nbsp;", ""));
        return novelContent;
    }
}
