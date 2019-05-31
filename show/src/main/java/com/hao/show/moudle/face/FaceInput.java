package com.hao.show.moudle.face;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.hao.show.R;
import com.hao.show.base.App;

public class FaceInput {
    /**
     * 提取特征
     *
     * @return 特征图片
     */
    public Bitmap getImage() {
        try {
            return BitmapFactory.decodeResource(App.getInstance().getResources(), R.mipmap.img_20190410_121500);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
