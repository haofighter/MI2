package com.hao.show.moudle.main.novel.Entity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.hao.show.R;

import java.util.List;

public class NovelChapterItemAdapter extends BaseAdapter {
    Activity mcontext;
    List<NovelChapter> novelChapterList;
    int page = 1;

    public void setPageAdd() {
        page++;
        notifyDataSetChanged();
    }

    public void setNovelChapterList(List<NovelChapter> novelChapterList) {
        this.novelChapterList = novelChapterList;
        notifyDataSetChanged();
    }

    public NovelChapterItemAdapter(Activity context) {
        mcontext = context;
    }

    @Override
    public int getCount() {
        int showCount = novelChapterList == null ? 0 : novelChapterList.size();
        if (page * 10 > showCount) {
            return showCount;
        }
        return page * 10;
    }

    @Override
    public NovelChapter getItem(int position) {
        return novelChapterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.text_item, null);
            viewHolder.textView = convertView.findViewById(R.id.title_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(novelChapterList.get(position).getChapterName());
        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }
}
