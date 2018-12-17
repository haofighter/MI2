package com.hao.show.moudle.view.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hao.out.base.utils.modle.SystemUtils;
import com.hao.show.R;
import com.hao.show.base.App;

import java.util.ArrayList;
import java.util.List;

public class BottomAdater extends BaseAdapter {
    private List<BottomDate> mBottomDateList = new ArrayList<>();
    private final static int TEXT_SIZE_NO_IMAGE=15;
    private final static int TEXT_SIZE_HAVE_IMAGE=12;


    public BottomAdater(List<BottomDate> bottomDateList) {
        mBottomDateList.addAll(bottomDateList);
    }

    public void addDate(List<BottomDate> bottomDateList) {
        mBottomDateList.addAll(bottomDateList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mBottomDateList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(App.getInstance().getApplicationContext()).inflate(R.layout.bottom_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.im = convertView.findViewById(R.id.gv_i_iv);
            viewHolder.title = convertView.findViewById(R.id.gv_i_tv);
            viewHolder.tip = convertView.findViewById(R.id.gv_item_tip);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        BottomDate bottomDate = mBottomDateList.get(position);
        if (bottomDate.isCheck) {
            if (bottomDate.checkbitmap == null) {
                viewHolder.im.setVisibility(View.GONE);
                viewHolder.title.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
                viewHolder.title.setTextSize(TEXT_SIZE_NO_IMAGE);
            } else {
                viewHolder.title.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        SystemUtils.INSTANCE.dip2px(App.getInstance().getApplicationContext(), TEXT_SIZE_HAVE_IMAGE)+3));
                viewHolder.title.setTextSize(TEXT_SIZE_HAVE_IMAGE);
                if (bottomDate.checkbitmap instanceof Integer) {
                    viewHolder.im.setVisibility(View.VISIBLE);
                    viewHolder.im.setImageResource((Integer) bottomDate.checkbitmap);
                } else if (bottomDate.checkbitmap instanceof Bitmap) {
                    viewHolder.im.setVisibility(View.VISIBLE);
                    viewHolder.im.setImageBitmap((Bitmap) bottomDate.checkbitmap);
                } else if (bottomDate.checkbitmap instanceof Drawable) {
                    viewHolder.im.setVisibility(View.VISIBLE);
                    viewHolder.im.setImageDrawable((Drawable) bottomDate.checkbitmap);
                }
            }
            if (bottomDate.uncheckColor != 0)
                viewHolder.title.setTextColor(ContextCompat.getColor(App.getInstance().getApplicationContext(), bottomDate.checkColor));
        } else {
            if (bottomDate.uncheckbitmap == null) {
                viewHolder.im.setVisibility(View.GONE);
                viewHolder.title.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
                viewHolder.title.setTextSize(TEXT_SIZE_NO_IMAGE);
            } else {
                viewHolder.title.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        SystemUtils.INSTANCE.dip2px(App.getInstance().getApplicationContext(), TEXT_SIZE_HAVE_IMAGE)+3));
                viewHolder.title.setTextSize(TEXT_SIZE_HAVE_IMAGE);
                if (bottomDate.uncheckbitmap instanceof Integer) {
                    viewHolder.im.setVisibility(View.VISIBLE);
                    viewHolder.im.setImageResource((Integer) bottomDate.uncheckbitmap);
                } else if (bottomDate.uncheckbitmap instanceof Bitmap) {
                    viewHolder.im.setVisibility(View.VISIBLE);
                    viewHolder.im.setImageBitmap((Bitmap) bottomDate.uncheckbitmap);
                } else if (bottomDate.uncheckbitmap instanceof Drawable) {
                    viewHolder.im.setVisibility(View.VISIBLE);
                    viewHolder.im.setImageDrawable((Drawable) bottomDate.uncheckbitmap);
                }
            }
            if (bottomDate.uncheckColor != 0)
                viewHolder.title.setTextColor(ContextCompat.getColor(App.getInstance().getApplicationContext(), bottomDate.uncheckColor));
        }
        viewHolder.title.setText(bottomDate.title);
        if (bottomDate.tipNum == 0) {
            viewHolder.tip.setVisibility(View.GONE);
        } else {
            viewHolder.tip.setVisibility(View.VISIBLE);
            viewHolder.tip.setText(bottomDate.tipNum+"");
        }

        return convertView;
    }

    class ViewHolder {
        ImageView im;
        TextView title;
        TextView tip;
    }
}
