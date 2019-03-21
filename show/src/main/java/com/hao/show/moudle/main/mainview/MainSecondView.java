package com.hao.show.moudle.main.mainview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.hao.mi2.base.Rx.Rx;
import com.hao.mi2.base.Rx.RxMessage;
import com.hao.show.R;
import com.hao.show.moudle.main.novel.Entity.NovelClassify;
import com.hao.show.spider.SpiderNovelFromBiQu;
import com.hao.show.spider.SpiderUtils;

import java.util.List;

public class MainSecondView {
    private View mainsecondView;
    TextView textView;
    Context context;

    public MainSecondView(Context context) {
        if (mainsecondView == null) {
            this.context = context;
            mainsecondView = LayoutInflater.from(context).inflate(R.layout.content_second, null);
            initView();
        }
    }

    private void initView() {
        textView = mainsecondView.findViewById(R.id.content);
    }


    public View getMainsecondView() {
        return mainsecondView;
    }
}
