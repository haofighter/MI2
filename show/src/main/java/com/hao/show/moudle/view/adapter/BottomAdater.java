package com.hao.show.moudle.view.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.hao.lib.Util.SystemUtils;
import com.hao.lib.elseeffect.AnimalUtils;
import com.hao.show.R;
import com.hao.show.base.App;

import java.util.ArrayList;
import java.util.List;

public class BottomAdater extends BaseAdapter {
    private List<BottomDate> mBottomDateList = new ArrayList<>();
    private final static int TEXT_SIZE_NO_IMAGE = 15;//无图片时的标题文字大小
    private final static int TEXT_SIZE_HAVE_IMAGE = 12;//有图片的时候文字大小
    private int checkPosition = 0;//当前选中的item
    private int oldCheckPosition = -1;//切换前选中的item

    public void setCheckPosition(int checkPosition) {
        this.checkPosition = checkPosition;
        notifyDataSetChanged();
    }

    public int getCheckPosition() {
        return checkPosition;
    }

    public void setTextSize(int textSize) {

    }

    public void setDate(List<BottomDate> bottomDateList) {
        mBottomDateList.addAll(bottomDateList);
    }

    public void addDate(List<BottomDate> bottomDateList) {
        mBottomDateList.addAll(bottomDateList);
        notifyDataSetChanged();
    }

    public void updateItem(int i, BottomDate bottomDate) {
        mBottomDateList.set(i, bottomDate);
    }

    @Override
    public int getCount() {
        return mBottomDateList.size();
    }

    @Override
    public BottomDate getItem(int position) {
        return mBottomDateList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private List<ViewHolder> viewHolders = new ArrayList<>();

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolders.size() >= mBottomDateList.size()) {
            viewHolder = viewHolders.get(position);
        } else {
            convertView = LayoutInflater.from(App.getInstance().getApplicationContext()).inflate(R.layout.bottom_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.contentView = convertView;
            viewHolder.bottom_view_item = convertView.findViewById(R.id.bottom_view_item);
            viewHolder.im = convertView.findViewById(R.id.gv_i_iv);
            viewHolder.title = convertView.findViewById(R.id.gv_i_tv);
            viewHolder.tip = convertView.findViewById(R.id.gv_item_tip);
            viewHolders.add(viewHolder);
        }


        BottomDate bottomDate = mBottomDateList.get(position);

        if (bottomDate.views != null && bottomDate.views.size() > 0) {
            for (int i = 0; i < bottomDate.views.size(); i++) {
                viewHolder.bottom_view_item.addView(bottomDate.views.get(i));
            }
        }

        if (position == checkPosition) {
            Log.i("选择", "选中条目：" + position + "      " + "选中");
            if (bottomDate.defIcon == null) {
                viewHolder.im.setVisibility(View.GONE);
                viewHolder.title.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
                viewHolder.title.setTextSize(TEXT_SIZE_NO_IMAGE);
            } else {
                viewHolder.title.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        SystemUtils.INSTANCE.dip2px(App.getInstance().getApplicationContext(), TEXT_SIZE_HAVE_IMAGE) + 3));
                viewHolder.title.setTextSize(TEXT_SIZE_HAVE_IMAGE);

                if (oldCheckPosition != position) {
                    setImage(bottomDate.defIcon, viewHolder.im);
                    oldCheckPosition = position;
                    Log.i("选择", "动画运行：" + position);
                    ObjectAnimator objectAnimator02 = ObjectAnimator.ofFloat(viewHolder.im, "alpha", 1, 0);
                    new AnimalUtils().addSequentially(objectAnimator02).setDuration(100).setDate(new Object[]{viewHolder.im, bottomDate.checkIcon}).setMAnimatorListener(new AnimalUtils.MAnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation, Object... o) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation, Object... o) {
                            if (o.length >= 2) {
                                setImage(o[1], (ImageView) o[0]);
                                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat((ImageView) o[0], "alpha", 0, 1);
                                objectAnimator.setDuration(200);
                                objectAnimator.start();
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animation, Object... o) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation, Object... o) {

                        }
                    }).startAnimatorSet();
                }
            }
            if (bottomDate.uncheckColor != 0 && bottomDate.checkColor != 0)
                viewHolder.title.setTextColor(ContextCompat.getColor(App.getInstance().getApplicationContext(), bottomDate.checkColor));
        } else if (position == oldCheckPosition) {
            Log.i("选择", "曾今条目：" + position + "      " + "曾选中");
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
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(viewHolder.im, "alpha", 1, 0);
                new AnimalUtils().addSequentially(objectAnimator).setDuration(100).setDate(new Object[]{viewHolder.im, bottomDate.defIcon}).setMAnimatorListener(new AnimalUtils.MAnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation, Object... o) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation, Object... o) {
                        if (o.length >= 2) {
                            ((ImageView) o[0]).setVisibility(View.GONE);
                            setImage(o[1], (ImageView) o[0]);
                            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat((ImageView) o[0], "alpha", 0, 1);
                            objectAnimator.setDuration(200);
                            objectAnimator.start();
                            ((ImageView) o[0]).setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation, Object... o) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation, Object... o) {

                    }
                }).startAnimatorSet();

            }
            if (bottomDate.uncheckColor != 0 && bottomDate.checkColor != 0)
                viewHolder.title.setTextColor(ContextCompat.getColor(App.getInstance().getApplicationContext(), bottomDate.uncheckColor));
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
                setImage(bottomDate.defIcon, viewHolder.im);
            }
            if (bottomDate.uncheckColor != 0 && bottomDate.checkColor != 0)
                viewHolder.title.setTextColor(ContextCompat.getColor(App.getInstance().getApplicationContext(), bottomDate.uncheckColor));
        }

        if (bottomDate.title == null || "".equals(bottomDate.title)) {
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

        return viewHolder.contentView;
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
        RelativeLayout bottom_view_item;
        View contentView;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
