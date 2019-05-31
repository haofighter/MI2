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

import java.util.List;

public class TextNovelAdapter extends RecyclerView.Adapter<TextNovelAdapter.NovelTextHolder> {
    private Context mContext;

    private List<NovelListItemContent> mNovelPage;

    public TextNovelAdapter(Context context, List<NovelListItemContent> novelPage) {
        this.mContext = context;
        this.mNovelPage = novelPage;
    }

    public List<NovelListItemContent> getDate() {
        return mNovelPage;
    }

    @NonNull
    @Override
    public NovelTextHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.text_item, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new NovelTextHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NovelTextHolder novelHolder, final int i) {
        novelHolder.setDate(mNovelPage.get(i));
        novelHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.itemClick(i, novelHolder.view, mNovelPage.get(i));
                }
            }
        });
    }

    private OnItemClickListener onItemClickListener;

    public TextNovelAdapter setItemClickLisener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    @Override
    public int getItemCount() {
        return mNovelPage == null ? 0 : mNovelPage.size();
    }

    public void setList(List<NovelListItemContent> novelListItemContents) {
        mNovelPage = novelListItemContents;
        notifyDataSetChanged();
    }

    class NovelTextHolder extends RecyclerView.ViewHolder {

        TextView novel_name;
        View view;

        public NovelTextHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            novel_name = itemView.findViewById(R.id.title_item);
        }

        public void setDate(NovelListItemContent novelClassify) {
            novel_name.setText(novelClassify.getTitle());
        }
    }

    public interface OnItemClickListener {
        void itemClick(int position, View view, Object object);
    }
}
