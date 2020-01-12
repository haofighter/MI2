package com.hao.show.moudle.main.mainview;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.hao.lib.view.StackLayout.StackLayout;
import com.hao.lib.view.StackLayout.transformer.AlphaTransformer;
import com.hao.lib.view.StackLayout.transformer.AngleTransformer;
import com.hao.lib.view.StackLayout.transformer.StackPageTransformer;
import com.hao.show.R;
import com.hao.show.base.App;
import com.hao.show.db.manage.DBManager;
import com.hao.show.moudle.main.novel.Entity.NovelDetail;
import com.hao.show.moudle.main.novel.Entity.NovelListItemContent;
import com.hao.show.moudle.main.novel.NovelDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainSecondView implements MainView {
    private View mainsecondView;
    StackLayout stackLayout;
    Context context;
    HomeAdapter homeAdapter;

    public MainSecondView(Context context) {
        if (mainsecondView == null) {
            this.context = context;
            mainsecondView = LayoutInflater.from(context).inflate(R.layout.content_second, null);
            initView();
        }
    }

    private void initView() {
        stackLayout = mainsecondView.findViewById(R.id.stack_layout);
        homeAdapter = new MainSecondView.HomeAdapter(App.getInstance(), new ArrayList<NovelListItemContent>());
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
                homeAdapter.addDate(getRandomNovel(10 - homeAdapter.getDate().size()));
            }
        });
    }

    @Override
    public void refresh() {
        homeAdapter.addDate(getRandomNovel(10 - homeAdapter.getDate().size()));
        homeAdapter.notifyDataSetChanged();
    }


    public List<NovelListItemContent> getRandomNovel(int addNovleNum) {
        List<NovelListItemContent> novelCount = DBManager.getNovelCount();
        List<NovelListItemContent> randomNovel = new ArrayList<>();
        if (novelCount.size() > 10) {
            for (int i = 0; i < addNovleNum; i++) {
                int noveIndex = new Random().nextInt(novelCount.size());
                randomNovel.add(novelCount.get(noveIndex));
                novelCount.remove(noveIndex);
            }
        } else {
            randomNovel = novelCount;
        }
        return randomNovel;
    }


    @Override
    public View getMainView() {
        return mainsecondView;
    }

    class HomeAdapter extends StackLayout.Adapter<MainSecondView.RecommendViewHolder, List<NovelListItemContent>> {
        Context mContext;

        public HomeAdapter(Context context, List<NovelListItemContent> listItemContents) {
            super(context, listItemContents);
            this.mContext = context;
        }


        public void addDate(NovelListItemContent novelDetail) {
            getDate().add(novelDetail);
        }

        public void addDate(List<NovelListItemContent> novelDetail) {
            getDate().addAll(novelDetail);
        }

        @NonNull
        @Override
        public MainSecondView.RecommendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.novel_recommend, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new MainSecondView.RecommendViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MainSecondView.RecommendViewHolder viewHolder, final int i) {
            super.onBindViewHolder(viewHolder, i);
            Log.i("填充的数据", getDate().get(i).getTitle() + "       " + i);
            viewHolder.setDate(getDate().get(i));
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NovelDetailActivity.class);
                    intent.putExtra("novel", DBManager.addNovel(getDate().get(i)));
                    context.startActivity(intent);
                }
            });
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
