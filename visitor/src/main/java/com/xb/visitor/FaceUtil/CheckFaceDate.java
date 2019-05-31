package com.xb.visitor.FaceUtil;


import android.media.FaceRecognizer;

import java.util.List;

/**
 * 人脸检测数据填充
 */
public class CheckFaceDate {
    private List<String> mNames;
    private List<FaceRecognizer.Feature> mFeatures;

    public List<String> getmNames() {
        return mNames;
    }

    public void setmNames(List<String> mNames) {
        this.mNames = mNames;
    }

    public List<FaceRecognizer.Feature> getmFeatures() {
        return mFeatures;
    }

    public void setmFeatures(List<FaceRecognizer.Feature> mFeatures) {
        this.mFeatures = mFeatures;
    }
}
