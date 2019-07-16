package com.hao.show.moudle.main.mainview;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.hao.lib.view.RecycleViewHelp.RecycleView;
import com.hao.lib.view.StackLayout.StackLayout;
import com.hao.lib.view.StackLayout.transformer.AlphaTransformer;
import com.hao.lib.view.StackLayout.transformer.AngleTransformer;
import com.hao.lib.view.StackLayout.transformer.StackPageTransformer;
import com.hao.show.R;
import com.hao.show.base.App;
import com.hao.show.db.manage.DBManager;
import com.hao.show.moudle.main.novel.Entity.NovelDetail;
import com.hao.show.moudle.main.novel.Entity.NovelListItemContent;
import com.hao.show.moudle.main.novel.NovelActivity;
import com.hao.show.moudle.main.novel.NovelDetailActivity;
import com.hao.show.moudle.main.novel.adapter.NovelListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainFristView implements MainView {
    private View mainsecondView;
    StackLayout stackLayout;
    Context context;
    HomeAdapter homeAdapter;

    public MainFristView(Context context) {
        if (mainsecondView == null) {
            this.context = context;
            mainsecondView = LayoutInflater.from(context).inflate(R.layout.content_frist, null);
            initView();
        }
    }

    private void initView() {
        stackLayout = mainsecondView.findViewById(R.id.stack_layout);
        homeAdapter = new HomeAdapter(context);
        stackLayout.setAdapter(homeAdapter);
        stackLayout.addPageTransformer(
                new StackPageTransformer(),     // 堆叠
                new AlphaTransformer(),         // 渐变
                new AngleTransformer()          // 角度
        );
        refresh();
        initListener();
    }

    public void initListener() {
        stackLayout.setOnSwipeListener(new StackLayout.OnSwipeListener() {
            @Override
            public void onSwiped(View swipedView, int swipedItemPos, boolean isSwipeLeft, int itemLeft) {
                homeAdapter.remove((Integer) swipedView.getTag());
            }
        });
    }

    @Override
    public void refresh() {
        homeAdapter.notifyDataSetChanged();
    }


    @Override
    public View getMainView() {
        return mainsecondView;
    }

    class HomeAdapter extends StackLayout.Adapter<RecommendViewHolder> {
        Context mContext;

        public HomeAdapter(Context context) {
            mContext = context;
        }

        List<NovelListItemContent> novelDetails = new ArrayList<>();

        public void addDate(NovelListItemContent novelDetail) {
            novelDetails.add(novelDetail);
        }

        public void addDate(List<NovelListItemContent> novelDetail) {
            novelDetails.addAll(novelDetail);
        }

        public void remove(int i) {
            novelDetails.remove(i);
        }

        @NonNull
        @Override
        public RecommendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.novel_recommend, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new RecommendViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecommendViewHolder viewHolder, final int i) {
            viewHolder.setDate(novelDetails.get(i));
            viewHolder.itemView.setTag(i);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NovelDetailActivity.class);
                    intent.putExtra("novel", DBManager.addNovel(novelDetails.get(i)));
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            Log.i("显示数据", "大小：" + novelDetails.size());
            return novelDetails.size();
        }
    }

    class RecommendViewHolder extends StackLayout.ViewHolder {
        ImageView novel_recommend_image;
        TextView novel_recommend_title;
        TextView novel_recommend_auther;

        public RecommendViewHolder(@NonNull View itemView) {
            super(itemView);
            novel_recommend_image = itemView.findViewById(R.id.novel_recommend_image);
            novel_recommend_title = itemView.findViewById(R.id.novel_recommend_title);
            novel_recommend_auther = itemView.findViewById(R.id.novel_recommend_auther);
        }

        public void setDate(NovelListItemContent novelListItemContent) {
            Glide.with(App.getInstance()).load(novelListItemContent.getNovelImage()).into(novel_recommend_image);
            novel_recommend_title.setText(novelListItemContent.getTitle());
            novel_recommend_auther.setText("作者：" + novelListItemContent.getAuther());
        }
    }
}
