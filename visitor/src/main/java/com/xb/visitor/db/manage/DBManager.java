package com.xb.visitor.db.manage;


import android.util.Log;
import com.xb.visitor.Mqtt.MqttInfo;
import com.xb.visitor.db.dao.FaceInfoDao;
import com.xb.visitor.db.dao.FeatureDao;
import com.xb.visitor.entity.FaceInfo;
import com.xb.visitor.entity.Feature;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class DBManager {


    /**
     * 检查是否存在本Openid的数据
     * 如果存在则进行更新操作
     * 不存在则进行插入操作 并返回插入后的对象
     */
    public static FaceInfo checkFace(FaceInfo faceInfo) {
        FaceInfo chFaceInfo = DBCore.getDaoSession().getFaceInfoDao().queryBuilder().where(FaceInfoDao.Properties.Openid.eq(faceInfo.getOpenid())).limit(1).unique();
        if (chFaceInfo == null) {
            chFaceInfo = faceInfo;
        }
        DBCore.getDaoSession().insertOrReplace(chFaceInfo);
        return DBCore.getDaoSession().getFaceInfoDao().queryBuilder().where(FaceInfoDao.Properties.Openid.eq(faceInfo.getOpenid())).limit(1).unique();
    }

    /**
     * 检查更新人脸的数据
     *
     * @param feature
     * @return
     */
    public static Feature checkFeature(Feature feature) {
        Feature locFeature = DBCore.getDaoSession().getFeatureDao().queryBuilder().where(FeatureDao.Properties.Openid.eq(feature.getOpenid())).limit(1).unique();
        if (locFeature != null) {
            feature.setFeatureId(locFeature.getFeatureId());
            Log.i("解析人脸", "替换：" + feature.getOpenid());
        } else {
            Log.i("解析人脸", "添加：" + feature.getOpenid());
        }
        DBCore.getDaoSession().insertOrReplace(feature);
        return DBCore.getDaoSession().getFeatureDao().queryBuilder().where(FeatureDao.Properties.Openid.eq(feature.getOpenid())).limit(1).unique();
    }


    /**
     * 通过openid查询人脸
     *
     * @param openId
     * @return
     */
    public static Feature selectFeature(String openId) {
        return DBCore.getDaoSession().getFeatureDao().queryBuilder().where(FeatureDao.Properties.Openid.eq(openId)).limit(1).unique();
    }


    /**
     * 查询所有的可能用到人脸
     *
     * @return
     */
    public static List<FaceInfo> checkAllUseFace() {
        QueryBuilder qb = DBCore.getDaoSession().getFaceInfoDao().queryBuilder();
        return qb.where(qb.or(FaceInfoDao.Properties.Flag.eq(2), FaceInfoDao.Properties.Flag.eq(1))).list();
    }

    /**
     * 查询所有的可能用到人脸
     *
     * @return
     */
    public static FaceInfo checkFaceInfo(String openid) {
        return DBCore.getDaoSession().getFaceInfoDao().queryBuilder().where(FaceInfoDao.Properties.Openid.eq(openid)).limit(1).unique();
    }

    /**
     * 查询所有的存储的人脸
     *
     * @return
     */
    public static List<Feature> checkAllFeature() {
        return DBCore.getDaoSession().getFeatureDao().queryBuilder().limit(10000).list();
    }

    /**
     * 查询所有的存储的人脸
     *
     * @return
     */
    public static Feature checkAllFeature(String openid) {
        return DBCore.getDaoSession().getFeatureDao().queryBuilder().where(FeatureDao.Properties.Openid.eq(openid)).limit(1).unique();
    }


    /**
     * 通过消息查询本条消息中的人脸信息是否已生成
     *
     * @param mqttInfo
     * @return
     */
    public static boolean checkFaceIsSave(MqttInfo mqttInfo) {
        FaceInfo faceInfo = DBCore.getDaoSession().getFaceInfoDao().queryBuilder().where(FaceInfoDao.Properties.Openid.eq(mqttInfo.getOpenid()), FaceInfoDao.Properties.Image.eq(mqttInfo.getImage())).limit(1).unique();
        Feature feature = selectFeature(mqttInfo.getOpenid());
        if (faceInfo != null && feature != null) {
            Log.i("过程", "查询到人脸  已解析人脸" + faceInfo.getImage() + "\n消息人脸：" + mqttInfo.getImage());
            if (faceInfo.getImage().equals(mqttInfo.getImage())) {
                Log.i("过程", "检测到人脸已保存");
                return true;
            } else {
                Log.i("过程", "检测到人脸更新");
                return false;
            }
        }
        return false;
    }
}