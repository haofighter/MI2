package com.hao.show.moudle.main.novel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hao.show.R;
import com.hao.show.moudle.main.novel.Entity.NovelDetail;

import java.util.ArrayList;
import java.util.List;

public class NovelListAdapter extends RecyclerView.Adapter<NovelListAdapter.NovelDetailHolder> {
    private Context mContext;
    private List<NovelDetail> novelClassifies = new ArrayList<>();

    public NovelListAdapter(Context context) {
        this.mContext = context;
    }

    public void update(List<NovelDetail> list) {
        if (list != null) {
            novelClassifies = list;
            notifyDataSetChanged();
        }
    }


    @NonNull
    @Override
    public NovelDetailHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NovelDetailHolder(LayoutInflater.from(mContext).inflate(R.layout.novel_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull final NovelDetailHolder novelHolder, final int i) {
        novelHolder.setDate(novelClassifies.get(i));
        novelHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.itemClick(i, novelHolder.view, novelClassifies.get(i));
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
        Log.i("recycle", novelClassifies.size() + "");
        return novelClassifies.size();
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

        public void setDate(NovelDetail novelClassify) {
            novel_name.setText(novelClassify.getTitle());
            novel_new.setText(novelClassify.getNewChapter());
            novel_author.setText(novelClassify.getAuther());
        }
    }


    interface OnItemClickListener {
        void itemClick(int position, View view, Object object);
    }
}
