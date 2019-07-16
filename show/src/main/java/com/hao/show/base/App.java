package com.hao.show.base;


import android.support.v4.content.ContextCompat;
import com.hao.lib.Util.SPUtils;
import com.hao.lib.base.MI2App;
import com.hao.lib.base.Rx.Rx;
import com.hao.lib.base.Rx.RxMessage;
import com.hao.lib.base.theme.AppThemeSetting;
import com.hao.show.R;
import com.hao.show.db.manage.DBCore;
import com.hao.show.moudle.main.novel.NovelTask;
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
        initTheme();
        DBCore.init(this);
        NovelTask.getInstance().start();
    }

    public void initTheme() {
        AppThemeSetting.getInstance().setBackground(getResources().getDrawable(R.color.app)).setTextColorResuoce(R.color.white);
    }
}
