package com.hao.show.base;


import android.support.v4.content.ContextCompat;
import com.hao.lib.Util.SPUtils;
import com.hao.lib.base.MI2App;
import com.hao.lib.base.Rx.Rx;
import com.hao.lib.base.Rx.RxMessage;
import com.hao.lib.base.theme.ThemeFactory;
import com.hao.show.R;
import com.hao.show.spider.SpiderNovelFromBiQu;
import com.hao.show.spider.SpiderUtils;

public class App extends MI2App {
    //用于控制数据库更新导致序列化 ID出现错误
    public static final long serialVersionUID = 1L;
    static App app;

    private long updateTime = 0;

    public static App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        SPUtils.init(this);
        getAllNovel();
        initTheme();
    }


    public void getAllNovel() {
        if (System.currentTimeMillis() - updateTime > 24 * 60 * 60 * 1000) {
            Rx.getInstance().addRxMessage(new RxMessage() {
                @Override
                public void rxDo(Object tag, Object o) {
                    if (tag.equals("all")) {
                        SpiderNovelFromBiQu.getAllNovel((String) o);
                        updateTime = System.currentTimeMillis();
                    }
                }
            });
            SpiderUtils.getHtml("http://www.xbiquge.la/xiaoshuodaquan/", "all");
        }
    }


    public void initTheme() {
        ThemeFactory.getInstance().createTheme().setBackBitmap(R.mipmap.background).setTextColorResource(this, R.color.white).load();
    }
}
