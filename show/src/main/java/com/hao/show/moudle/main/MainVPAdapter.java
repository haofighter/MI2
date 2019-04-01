package com.hao.show.moudle.main;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hao.lib.Util.DataUtils;
import com.hao.show.R;

import java.util.List;

public class MainVPAdapter extends PagerAdapter {
    List<View> views;

    public void setViews(List<View> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return views == null ? 0 : views.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;

    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(views.get(position));
        if (position == 0) {
            TextView tv = views.get(position).findViewById(R.id.tv);
            byte[] bytes1 = DataUtils.hexStringToByte("00000001");
            byte[] bytes2 = DataUtils.hexStringToByte("00000000");
            tv.append("\n" + "00000050        " + "00000000");
            int int1 = bytesToInt2(bytes1);
            int int2 = bytesToInt2(bytes2);
            tv.append("\n" + int1 + "        " + int2);

            int int3 = (int1 + int2) % 256;
            tv.append("\n" + int3);
            tv.append("\n" + ~int3);
            tv.append("\n" + Integer.toHexString(~int3));
        }
        return views.get(position);
    }


    public static int bytesToInt2(byte[] src) {
        int value;
        value = (int) (((src[0] & 0xFF) << 24)
                | ((src[1] & 0xFF) << 16)
                | ((src[2] & 0xFF) << 8)
                | (src[3] & 0xFF));
        return value;
    }
}
