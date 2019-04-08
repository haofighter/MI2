package com.hao.show.moudle.main.novel.adapter;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hao.lib.Util.SystemUtils;
import com.hao.lib.view.MITextView;
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
        Log.i("小说", str.getChapterContent());
        mContext = context;
        padding5 = SystemUtils.INSTANCE.dip2px(mContext, 5);
//        initText();
        setContent(str);
    }

    public View initText() {
        DisplayMetrics displayMetrics = SystemUtils.INSTANCE.getScreenSize(mContext);
        int dp5 = SystemUtils.INSTANCE.dip2px(mContext, 5);
        int screenHight = displayMetrics.heightPixels - dp5 * 20;
        int screenWight = displayMetrics.widthPixels - dp5 * 2;
        MITextView contentView = (MITextView) LayoutInflater.from(mContext).inflate(R.layout.content_text, null);
        contentView.setPadding(dp5, dp5, dp5, dp5);
//        float textWidth = contentView.getTextSize() + 2 * contentView.getLineSpacingExtra();
//        float texthight = contentView.getTextSize() + 4 * contentView.getLineSpacingExtra();
//        textNum = (int) (screenWight / textWidth);
//        lines = (int) (screenHight / texthight);
//        pageText = textNum * lines;
//        contentView.setLines(lines);

//        Log.i("页面数据", "屏幕长：" + displayMetrics.heightPixels + "    计算文本大小：" + screenHight + "   行数：" + (screenWight / texthight) + "  虚拟键盘：" + SystemUtils.INSTANCE.getKeyBroadHight(mContext));
//        Log.i("页面数据", "屏幕宽：" + displayMetrics.widthPixels + "    计算文本大小：" + screenWight + "  字数：" + (screenHight / textWidth));
        Log.i("页面数据", "文字行数：" + lines + "    每行字数：" + textNum + "      每页字数：" + lines * textNum);
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


    @RequiresApi(api = Build.VERSION_CODES.N)
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
            container.addView(view);
        } else {
            view = initText();
            int end = (position + 1) * pageText > content.getChapterContent().length() ? content.getChapterContent().length() : (position + 1) * pageText;
            Log.i("本页数据", content.getChapterContent().substring(position * pageText, end));
            Log.i("本页数据", content.getChapterContent().substring(position * pageText, end));
            ((MITextView) view).setText(content.getChapterContent());
            ((MITextView) view).setPage(position);
            container.addView(view);
        }
        return view;
    }


    public void addContent(@NotNull NovelContent novelContent) {
        if (content != null) {
            novelContent.setChapterContent(content.getChapterContent() + novelContent.getChapterContent());
        }
        setContent(novelContent);
        notifyDataSetChanged();
    }

}
