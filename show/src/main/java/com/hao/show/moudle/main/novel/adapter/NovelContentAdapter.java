package com.hao.show.moudle.main.novel.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hao.lib.Util.TypeFaceUtils;
import com.hao.lib.base.Rx.Rx;
import com.hao.lib.view.ScorllTextView;
import com.hao.show.R;
import com.hao.show.base.App;
import com.hao.show.db.manage.DBManager;
import com.hao.show.moudle.main.novel.Entity.HistroryReadEntity;
import com.hao.show.moudle.main.novel.Entity.NovelChapter;

import java.util.List;

public class NovelContentAdapter extends PagerAdapter {
    private Activity mContext;
    List<NovelChapter> novelChapters;
    HistroryReadEntity histroryReadEntity;

    public NovelContentAdapter(Activity context, List<NovelChapter> novelChapters) {
        if (novelChapters != null && novelChapters.size() != 0) {
            histroryReadEntity = DBManager.checkHistroy(novelChapters.get(0).getNid());
        }
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


    //不重写该方法 会导致notifyDataSetChanged无效
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void notifyDataSetChanged() {

        super.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        final List<NovelChapter> selcetNovel = DBManager.selectNovelChapter(novelChapters.get(position).getNid(), (long) position);
        View view = new View(mContext);
        if ((selcetNovel == null && selcetNovel.size() == 0) || selcetNovel.get(0).getChapterContent().equals("")) {
            view = LayoutInflater.from(mContext).inflate(R.layout.null_content, null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Rx.getInstance().sendMessage("reload", position);
                }
            });
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.content_text, null);
            ScorllTextView miText = view.findViewById(R.id.text_content);
            TextView novel_title = view.findViewById(R.id.novel_title);
            novel_title.setText(selcetNovel.get(0).getChapterName());
            miText.setText(selcetNovel.get(0).getChapterContent());
            miText.setTypeface(TypeFaceUtils.getFZHJLJT(mContext));
            miText.setTextColor(App.getInstance().getMi2Theme().getTextColorResuoce());
            NovelChapter novelChapter = novelChapters.get(position);
            if (histroryReadEntity != null && histroryReadEntity.getNoverChapterId() == novelChapter.getCid() && histroryReadEntity.getNoverChapter().equals(novelChapter.getChapterName())) {
                miText.setPage(histroryReadEntity.getNoverPage());
            }
            miText.setTextChangetListener(new ScorllTextView.ScorllTextChangeListener() {
                @Override
                public void pageChange(int page) {
                    HistroryReadEntity histroryReadEntity = new HistroryReadEntity();
                    NovelChapter novelChapter = novelChapters.get(position);
                    histroryReadEntity.setNovelId(novelChapter.getNid());
                    histroryReadEntity.setNoverChapterId(novelChapter.getCid());
                    histroryReadEntity.setNoverChapterUrl(novelChapter.getChapterUrl());
                    histroryReadEntity.setNoverPage(page);
                    histroryReadEntity.setNoverChapter(novelChapter.getChapterName());
                    DBManager.insertHistroy(histroryReadEntity);
                }

                @Override
                public void textEnd() {

                }

                @Override
                public void textStart() {

                }

            });
        }
        container.addView(view);
        return view;
    }


}
