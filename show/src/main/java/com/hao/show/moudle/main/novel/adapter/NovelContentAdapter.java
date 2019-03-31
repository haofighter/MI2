package com.hao.show.moudle.main.novel.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hao.show.R;
import com.hao.show.moudle.main.novel.Entity.NovelContent;

public class NovelContentAdapter extends PagerAdapter {
    private NovelContent content;
    private Activity mContext;
    private int pageText = 700;

    public NovelContent getContent() {
        return content;
    }

    public void setContent(NovelContent content) {
        this.content = content;
    }

    public NovelContentAdapter(Activity context, NovelContent str) {
        content = str;
        Log.i("小说内容", str.getChapterContent());
        mContext = context;
    }

    @Override
    public int getCount() {
        int page = 1;
        if (content == null) {
            return page;
        }
        if (content.getChapterContent().length() % pageText != 0) {
            return content.getChapterContent().length() / pageText + 1;
        } else {
            return content.getChapterContent().length() / pageText;
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return o == view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = null;
        if (content == null && content.getChapterContent().length() == 0) {
            view = LayoutInflater.from(mContext).inflate(R.layout.null_content, null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.finish();
                }
            });
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.content_text, null);
            int end = (position + 1) * pageText > content.getChapterContent().length() ? content.getChapterContent().length() : (position + 1) * pageText;
            ((TextView) view).setText(content.getChapterContent().substring(position * pageText, end));
        }
        container.addView(view);
        return view;
    }
}
