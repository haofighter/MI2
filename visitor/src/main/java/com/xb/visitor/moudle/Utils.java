package com.xb.visitor.moudle;

import android.hardware.Camera;
import android.media.FaceRecognizer;

import java.lang.reflect.Method;
import java.util.List;

@SuppressWarnings("deprecation")
public class Utils {

    private static final String TAG = Utils.class.getSimpleName();
    private static List<String> mNames;


    static String getName(int id) {
        int index = id - 1;
        if (mNames == null || index < 0 || index >= mNames.size())
            return "Invalid Index";
        return mNames.get(index);
    }

    /**
     * 需要设置3个参数
     * <p>
     * nElements 人物个数<br>
     * ids 人物id数组<br>
     * fvects 人物特征数据, 每一个人物占用512位, 所以fvects的长度为512*nElements,ids和fvects一一对应,识别结果通过这里设置的ids标志<br>
     */
    public static void setFeatures(Camera camera, List<FaceRecognizer.Feature> features, List<String> names) {
        mNames = names;

        // 人物的个数
        int nElements = features.size();
        // 每个人物的id(id的值需要大于0)
        int[] ids = new int[nElements];
        // 每个人物对应一个512维的float数组
        float[] fvects = new float[nElements * 512];

        for (int i = 0; i < nElements; i++) {
            ids[i] = i + 1;
            System.arraycopy(features.get(i).fvect, 0, fvects, i * 512, 512);
        }

        try {
            Method setFeatures = camera.getClass().getMethod("setFeatures", int.class, int[].class, float[].class);
            setFeatures.invoke(camera, nElements, ids, fvects);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Log.i(TAG, "add " + nElements + " Features to camera");
    }

    static int byte2int(byte[] b, int offset) {
        return b[offset] & 0xFF |
                (b[offset + 1] & 0xFF) << 8 |
                (b[offset + 2] & 0xFF) << 16 |
                (b[offset + 3] & 0xFF) << 24;
    }
}
