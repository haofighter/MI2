package com.hao.show.moudle.main.novel.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hao.lib.Util.TypeFaceUtils;
import com.hao.lib.view.MITextView;
import com.hao.show.R;
import com.hao.show.db.manage.DBManager;
import com.hao.show.moudle.main.novel.Entity.NovelChapter;

import java.util.List;

public class NovelContentAdapter extends PagerAdapter {
    private Activity mContext;
    List<NovelChapter> novelChapters;


    public NovelContentAdapter(Activity context, List<NovelChapter> novelChapters) {
        mContext = context;
        this.novelChapters = novelChapters;
    }

    public List<NovelChapter> getDate() {
        return novelChapters;
    }

    @Override
    public int getCount() {
        return novelChapters == null ? 0 : novelChapters.size();
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
        List<NovelChapter> selcetNovel = DBManager.selectNovelChapter(novelChapters.get(position).getNid(), (long) position);
        View view = new View(mContext);
        if ((selcetNovel == null && selcetNovel.size() == 0) || selcetNovel.get(0).getChapterContent().equals("")) {
            view = LayoutInflater.from(mContext).inflate(R.layout.null_content, null);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.content_text, null);
            MITextView miText = view.findViewById(R.id.text_content);
            TextView novel_title = view.findViewById(R.id.novel_title);
            novel_title.setText(selcetNovel.get(0).getChapterName());
            miText.setText(selcetNovel.get(0).getChapterContent());
            miText.setTypeface(TypeFaceUtils.getHKWWT(mContext));
        }
        container.addView(view);
        return view;
    }


}
