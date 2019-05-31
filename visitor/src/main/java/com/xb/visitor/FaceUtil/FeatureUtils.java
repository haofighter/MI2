package com.xb.visitor.FaceUtil;

import android.media.FaceRecognizer;

public class FeatureUtils {

    // 根据已知人物图像生成对应的特征数据.
    // 由于生成特征数据是一个耗时的过程,实际使用中需要提前生成好,然后持久化保存.
    // 下次启动直接加载,对于新的人物做增量更新即可.
    public FaceRecognizer fr;

    private FeatureUtils() {
        if (fr == null) {
            fr = new FaceRecognizer();
        }
    }

    static class FeaTureUtilsHelp {
        final public static FeatureUtils f = new FeatureUtils();

    }

    public static FaceRecognizer getInstance() {
        if (FeaTureUtilsHelp.f.fr == null) {
            FeaTureUtilsHelp.f.fr = new FaceRecognizer();
        }
        return FeaTureUtilsHelp.f.fr;
    }

}
