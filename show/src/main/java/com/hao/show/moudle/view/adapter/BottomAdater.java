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
    private final static int TEXT_SIZE_NO_IMAGE = 15;//无图片时的标题文字大小
    private final static int TEXT_SIZE_HAVE_IMAGE = 12;//有图片的时候文字大小
    private int checkPosition = 0;//当前选中的item


    public void setTextSize(int textSize) {

    }

    public void setDate(List<BottomDate> bottomDateList) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
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

        if (position == checkPosition) {
            if (bottomDate.defIcon == null) {
                viewHolder.im.setVisibility(View.GONE);
                viewHolder.title.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
                viewHolder.title.setTextSize(TEXT_SIZE_NO_IMAGE);
            } else {
                viewHolder.title.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        SystemUtils.INSTANCE.dip2px(App.getInstance().getApplicationContext(), TEXT_SIZE_HAVE_IMAGE) + 3));
                viewHolder.title.setTextSize(TEXT_SIZE_HAVE_IMAGE);
                viewHolder.im.setVisibility(View.VISIBLE);
                setImage(bottomDate.checkIcon, viewHolder.im);
            }
            if (bottomDate.uncheckColor != 0 && bottomDate.checkColor != 0)
                viewHolder.title.setTextColor(ContextCompat.getColor(App.getInstance().getApplicationContext(), bottomDate.checkColor));
        } else {
            if (bottomDate.defIcon == null) {
                viewHolder.im.setVisibility(View.GONE);
                viewHolder.title.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
                viewHolder.title.setTextSize(TEXT_SIZE_NO_IMAGE);
            } else {
                viewHolder.title.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        SystemUtils.INSTANCE.dip2px(App.getInstance().getApplicationContext(), TEXT_SIZE_HAVE_IMAGE) + 3));
                viewHolder.title.setTextSize(TEXT_SIZE_HAVE_IMAGE);
                viewHolder.im.setVisibility(View.VISIBLE);
                setImage(bottomDate.checkIcon, viewHolder.im);
            }
            if (bottomDate.uncheckColor != 0 && bottomDate.checkColor != 0)
                viewHolder.title.setTextColor(ContextCompat.getColor(App.getInstance().getApplicationContext(), bottomDate.uncheckColor));
        }

        if (bottomDate.title == null && "".equals(bottomDate.title)) {
            viewHolder.title.setVisibility(View.GONE);
        } else {
            viewHolder.title.setVisibility(View.VISIBLE);
            viewHolder.title.setText(bottomDate.title);
        }
        if (bottomDate.tipNum == 0) {
            viewHolder.tip.setVisibility(View.GONE);
        } else {
            viewHolder.tip.setVisibility(View.VISIBLE);
            viewHolder.tip.setText(bottomDate.tipNum + "");
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPosition = position;
                notifyDataSetChanged();
            }
        });

        return convertView;
    }


    public void setImage(Object img, ImageView iv) {
        if (img instanceof Integer) {
            iv.setImageResource((Integer) img);
        } else if (img instanceof Drawable) {
            iv.setImageDrawable((Drawable) img);
        } else if (img instanceof Bitmap) {
            iv.setImageBitmap((Bitmap) img);
        }
    }

    class ViewHolder {
        ImageView im;
        TextView title;
        TextView tip;
    }

}
