package com.hao.show.moudle.main.novel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hao.show.R;
import com.hao.show.moudle.main.novel.Entity.NovelListItemContent;
import com.hao.show.moudle.main.novel.Entity.NovelPage;

public class NovelListAdapter extends RecyclerView.Adapter<NovelListAdapter.NovelDetailHolder> {
    private Context mContext;
    private NovelPage mNovelPage;

    public NovelListAdapter(Context context) {
        this.mContext = context;
    }

    public void update(NovelPage novelPage) {
        if (novelPage != null) {
            this.mNovelPage = novelPage;
            notifyDataSetChanged();
        }
    }

    public void add(NovelPage novelPage) {
        if (mNovelPage == null && novelPage != null) {
            mNovelPage = novelPage;
        } else if (mNovelPage != null && novelPage != null) {
            mNovelPage.getNovelListItemContentList().addAll(novelPage.getNovelListItemContentList());
            mNovelPage.setNextPageUrl(novelPage.getNextPageUrl());
        }
        notifyDataSetChanged();
    }

    public NovelPage getNowDate() {
        return mNovelPage;
    }


    @NonNull
    @Override
    public NovelDetailHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.novel_item, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new NovelDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NovelDetailHolder novelHolder, final int i) {
        novelHolder.setDate(mNovelPage.getNovelListItemContentList().get(i));
        novelHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.itemClick(i, novelHolder.view, mNovelPage.getNovelListItemContentList().get(i));
                }
            }
        });
    }

    private OnItemClickListener onItemClickListener;

    public NovelListAdapter setItemClickLisener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }


    @Override
    public int getItemCount() {
        return mNovelPage == null ? 0 : mNovelPage.getNovelListItemContentList().size();
    }

    //清除数据
    public void clear() {
        mNovelPage = new NovelPage();
        notifyDataSetChanged();
    }


    class NovelDetailHolder extends RecyclerView.ViewHolder {

        TextView novel_name;
        TextView novel_new;
        TextView novel_author;
        View view;

        public NovelDetailHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            novel_name = itemView.findViewById(R.id.novel_name);
            novel_new = itemView.findViewById(R.id.novel_new);
            novel_author = itemView.findViewById(R.id.novel_author);
        }

        public void setDate(NovelListItemContent novelClassify) {
            novel_name.setText(novelClassify.getTitle());
            novel_new.setText(novelClassify.getNewChapter());
            novel_author.setText(novelClassify.getAuther());

        }
    }


    public interface OnItemClickListener {
        void itemClick(int position, View view, Object object);
    }
}
