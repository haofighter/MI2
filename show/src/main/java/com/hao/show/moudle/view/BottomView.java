package com.hao.show.moudle.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.hao.show.R;
import com.hao.show.moudle.view.adapter.BottomAdater;
import com.hao.show.moudle.view.adapter.BottomDate;

import java.util.List;

public class BottomView {
    private Context mContext;

    public BottomView(Context context, List<BottomDate> bottomDateList) {
        mContext = context;
        init(bottomDateList);
    }

    GridView v;

    private void init(List<BottomDate> bottomDateList) {
        v = (GridView) LayoutInflater.from(mContext).inflate(R.layout.bottom_view, null);
        v.setNumColumns(bottomDateList.size());
        BottomAdater bottomAdater = new BottomAdater(bottomDateList);
        v.setAdapter(bottomAdater);
        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setTag(position);
                mOnClickListener.onClick(view);
            }
        });
    }

    public void addDate(List<BottomDate> bottomDateList) {
        ((BottomAdater) v.getAdapter()).addDate(bottomDateList);
    }

    public View getView() {
        return v;
    }

    public View setViewBackground(Drawable drawable) {
        v.setBackground(drawable);
        return v;
    }

    public View setViewBackground(int rid) {
        v.setBackgroundResource(rid);
        return v;
    }

    View.OnClickListener mOnClickListener;

    public BottomView setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
        return this;
    }
}
