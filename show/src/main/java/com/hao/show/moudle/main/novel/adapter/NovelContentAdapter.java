package com.hao.show.moudle.main.novel.adapter;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import com.hao.lib.Util.SystemUtils;
import com.hao.show.R;
import com.hao.show.moudle.main.novel.Entity.NovelContent;
import org.jetbrains.annotations.NotNull;

public class NovelContentAdapter extends PagerAdapter {
    private NovelContent content;
    private Activity mContext;
    private int pageText = 1000;
    int lines;
    int textNum;
    int padding5;

    public NovelContent getContent() {
        return content;
    }

    public void setContent(NovelContent content) {
        this.content = content;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NovelContentAdapter(Activity context, NovelContent str) {
        content = str;
        Log.i("小说", Html.fromHtml(str.getChapterContent()).toString());
        mContext = context;
        padding5 = SystemUtils.INSTANCE.dip2px(mContext, 5);
        initText();
    }

    public TextView initText() {
        DisplayMetrics displayMetrics = SystemUtils.INSTANCE.getScreenSize(mContext);
        int screenHight = displayMetrics.heightPixels - SystemUtils.INSTANCE.getDecorViewHight(mContext) - padding5 * 5;
        int screenWight = displayMetrics.widthPixels - SystemUtils.INSTANCE.dip2px(mContext, 5) * 2;
        TextView contentView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.content_text, null);
        contentView.setPadding(padding5, padding5 * 4, padding5, padding5);
        float textHight = contentView.getTextSize() + contentView.getLineSpacingExtra();
        float textWight = contentView.getTextSize() + 2 * contentView.getLineSpacingExtra();
        textNum = (int) (screenWight / textWight);
        lines = (int) (screenHight / textHight);
        pageText = textNum * lines;
        contentView.setLines(lines);
        return contentView;
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
            view = initText();
            int end = (position + 1) * pageText > content.getChapterContent().length() ? content.getChapterContent().length() : (position + 1) * pageText;
            ((TextView) view).setText(Html.fromHtml(content.getChapterContent().substring(position * pageText, end)));
        }
        container.addView(view);
        return view;
    }

    public void addContent(@NotNull NovelContent novelContent) {
        if (content != null) {
            novelContent.setChapterContent(content.getChapterContent() + novelContent.getChapterContent());
        }
        content = novelContent;
        notifyDataSetChanged();
    }
}
