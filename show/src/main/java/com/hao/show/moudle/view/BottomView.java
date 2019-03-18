package com.hao.show.moudle.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import com.hao.show.R;
import com.hao.show.moudle.view.adapter.BottomAdater;
import com.hao.show.moudle.view.adapter.BottomDate;

import java.util.List;

public class BottomView extends FrameLayout {
    private Context mContext;
    private float height;

    GridView v;

    public BottomView(Context context) {
        this(context, null);
    }

    public BottomView(Context context, AttributeSet atr) {
        this(context, atr, 0);
    }

    public BottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }


    private void init() {
        if (v == null) {
            v = (GridView) LayoutInflater.from(mContext).inflate(R.layout.bottom_view, null);
        }
        addView(v);
    }

    @Override
    public void invalidate() {
        if (v != null && v.getAdapter() != null)
            ((BottomAdater) v.getAdapter()).notifyDataSetChanged();
        super.invalidate();
    }

    //设置显示的参数
    public BottomView setDate(List<BottomDate> bottomDateList) {
        v.setNumColumns(bottomDateList.size());
        if (v != null && v.getAdapter() == null) {
            v.setAdapter(new BottomAdater());
        }
        ((BottomAdater) v.getAdapter()).setDate(bottomDateList);
        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bottomClickListener.click(position, view);
                if (position != ((BottomAdater) v.getAdapter()).getCheckPosition())
                    ((BottomAdater) v.getAdapter()).setCheckPosition(position);
            }
        });
        return this;
    }

    public void addDate(List<BottomDate> bottomDateList) {
        ((BottomAdater) v.getAdapter()).addDate(bottomDateList);
    }

    public View getView() {
        return v;
    }

    public BottomView setViewBackground(Drawable drawable) {
        v.setBackground(drawable);
        return this;
    }

    public BottomView setViewBackground(int rid) {
        v.setBackgroundResource(rid);
        return this;
    }

    BottomViewClickListener bottomClickListener;

    public BottomView setBottomClickListener(BottomViewClickListener bottomClickListener) {
        this.bottomClickListener = bottomClickListener;
        return this;
    }

    public interface BottomViewClickListener {
        void click(int item, View v);
    }
}
