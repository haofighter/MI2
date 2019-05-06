package com.xb.visitor.FaceUtil;

import android.media.FaceRecognizer;
import android.util.Log;
import com.xb.visitor.entity.Feature;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

public class FaceUtils {

    public static Feature getLocFeature(FaceRecognizer.Feature feature) {
        Feature locfeature = new Feature();
        locfeature.fvect = float2String(feature.fvect);
        locfeature.face_x1 = feature.face_x1;
        locfeature.face_y1 = feature.face_y2;
        locfeature.face_x2 = feature.face_x2;
        locfeature.face_y2 = feature.face_y2;
        return locfeature;
    }

    public static FaceRecognizer.Feature toFaceRecognizerFeature(Feature locfeature) {
        FaceRecognizer.Feature feature = null;
        try {

            Log.i("过程   数据转换", "开始");
            Class<FaceRecognizer.Feature> clazz = (Class<FaceRecognizer.Feature>) Class.forName(FaceRecognizer.Feature.class.getName());
            Constructor<FaceRecognizer.Feature>[] constructors = (Constructor<FaceRecognizer.Feature>[]) clazz.getDeclaredConstructors();
            // 按类中定义的顺序输出构造器
            for (int i = 0; i < constructors.length; i++) {
                Constructor<FaceRecognizer.Feature> con = constructors[i];
                con.setAccessible(true); // 得到私有访问权限，如果不设置，则无法实例化对象
                // 输出构造器参数的全部类型
                Type types[] = con.getGenericParameterTypes();

                int typeNums = types.length;

                for (int j = 0; j < typeNums; j++) {
                    System.out.print(types[j]);
                    System.out.print(types[j].toString());
                }

                //实例化对象
                if (feature == null) {
                    feature = con.newInstance(FeatureUtils.getInstance());
                } else {
                    feature.fvect = string2Float(locfeature.fvect);
                    feature.face_x1 = locfeature.face_x1;
                    feature.face_x2 = locfeature.face_x2;
                    feature.face_y1 = locfeature.face_y1;
                    feature.face_y2 = locfeature.face_y2;
                    locfeature = null;
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return feature;
    }


    private static float[] string2Float(String string) {
        String[] strings = string.split(" ");
        float[] fs = new float[strings.length];
        for (int i = 0; i < strings.length; i++) {
            fs[i] = Float.parseFloat(strings[i]);
        }

        return fs;
    }

    private static String float2String(float[] fs) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < fs.length; i++) {
            stringBuilder.append(fs[i] + " ");
        }
        return stringBuilder.toString();
    }
}
