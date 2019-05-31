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
import com.hao.show.moudle.main.novel.Entity.NovelClassify;

import java.util.ArrayList;
import java.util.List;

public class NovelClassifyListAdapter extends RecyclerView.Adapter<NovelClassifyListAdapter.NovelHolder> {
    private Context mContext;
    private List<NovelClassify> novelClassifies = new ArrayList<>();

    public NovelClassifyListAdapter(Context context) {
        this.mContext = context;
    }

    public void update(List<NovelClassify> list) {
        if (list != null) {
            novelClassifies = list;
            notifyDataSetChanged();
        }
    }


    @NonNull
    @Override
    public NovelHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NovelHolder(LayoutInflater.from(mContext).inflate(R.layout.text_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull final NovelHolder novelHolder, final int i) {
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

    public NovelClassifyListAdapter setItemClickLisener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }


    @Override
    public int getItemCount() {
        Log.i("recycle", novelClassifies.size() + "");
        return novelClassifies.size();
    }


    class NovelHolder extends RecyclerView.ViewHolder {

        TextView view;

        public NovelHolder(@NonNull View itemView) {
            super(itemView);
            view = (TextView) itemView;
        }

        public void setDate(NovelClassify novelClassify) {
            view.setText(novelClassify.getTitle());
        }
    }


    interface OnItemClickListener {
        void itemClick(int position, View view, Object object);
    }
}
