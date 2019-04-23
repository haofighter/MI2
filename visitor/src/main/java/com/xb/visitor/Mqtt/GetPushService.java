package com.xb.visitor.Mqtt;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.FaceRecognizer;
import android.os.*;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.hao.lib.base.BackCall;
import com.squareup.picasso.Picasso;
import com.xb.visitor.FaceUtil.FaceUtils;
import com.xb.visitor.base.App;
import com.xb.visitor.db.manage.DBManager;
import com.xb.visitor.entity.BitMapInfo;
import com.xb.visitor.entity.FaceInfo;
import com.xb.visitor.entity.Feature;
import com.xb.visitor.moudle.MainActivity;
import com.xb.visitor.moudle.Utils;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by a'su's on 2018/5/15.
 */

public class GetPushService extends Service {

    final static String serverUri = "tcp://129.204.2.73";
    final static String subscriptionTopic = "facetopic";
    private String clientId = "一般使用设备唯一ID";

    private String userName = "admin";
    private String passWord = "admin";
    private MBinder mBinder = new MBinder();
    private MqttConnectOptions options;

    private static final String TAG = "GetPushService";
    private ScheduledExecutorService scheduler;
    private MqttClient client;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 2) {
                System.out.println("------------连接成功-----------------");
                Toast.makeText(App.getInstance(), "连接成功", Toast.LENGTH_SHORT).show();
                try {
                    client.subscribe(subscriptionTopic, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (msg.what == 3) {
                Toast.makeText(App.getInstance(), "连接失败，系统正在重连", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    Picasso picasso;

    @Override
    public void onCreate() {
        super.onCreate();
        // daoSimple = new DaoSimple(App.getInstance());
        clientId = "111";

        System.out.println("设备id---------"
                + clientId);
        init();
        startReconnect();
        picasso = Picasso.get();
    }


    private void startReconnect() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                if (!client.isConnected()) {
                    System.out.println("连接---------设备重连");
                    connect();
                }
            }
        }, 0 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);
    }

    private void init() {
        try {
            //host为主机名，test为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new MqttClient(serverUri, clientId,
                    new MemoryPersistence());
            //MQTT的连接设置
            options = new MqttConnectOptions();
            //设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(true);
            //设置连接的用户名
            options.setUserName(userName);
            //设置连接的密码
            options.setPassword(passWord.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);
            //设置回调
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    //连接丢失后，一般在这里面进行重连
                    System.out.println("连接      connectionLost----------");
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    //publish后会执行到这里
                    System.out.println("连接    deliveryComplete---------"
                            + token.isComplete());

                }

                @Override
                public void messageArrived(String topicName, final MqttMessage message) {
                    try {
                        mqttInfos.add(new Gson().fromJson(new String(message.getPayload()), MqttInfo.class));
                        Log.i("过程 接收数据", "" + new String(message.getPayload()));
                        updateFace();
                    } catch (Exception e) {
                        Log.i("过程 错误", "解析错误" + e.getMessage() + "     " + new String(message.getPayload()));
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void text(int i) {
//        String str = "{\"posno\":[\"111\"],\"flag\":1,\"name\":\"张三\",\"image\":\"http://2t183d9338.iask.in:37092/pic/bipscp//faceinter/oFV0g5TWIPJ8Rtw7W_wygpMyOMmE1555570298424.jpg\",\"openid\":\"oFV0g5TWIPJ8Rtw7W_wygpMyOMmE\"}\n";
//        String str2 = "{\"posno\":[\"111\"],\"flag\":1,\"name\":\"黄睦1\",\"image\":\"http://2t183d9338.iask.in:37092/pic/bipscp//faceinter/oFV0g5ZSku_Mvm36IhGwriLJ77qc1555646194543.jpg\",\"openid\":\"oFV0g5ZSku_Mvm36IhGwriLJ77qc1\"}\n";
        String str3 = "{\"posno\":[\"111\"],\"flag\":2,\"name\":\"黄睦2\",\"image\":\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555666804016&di=3679e2652d4bf5d810d56dcdad56e8e0&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fq_mini%2Cc_zoom%2Cw_640%2Fimages%2F20170818%2F47da3956c5e94da9a158b39103123cbf.jpeg\",\"openid\":\"oFV0g5ZSku_Mvm36IhGwriLJ77qc2\"}\n";
        String str4 = "{\"posno\":[\"111\"],\"flag\":3,\"name\":\"黄睦3\",\"image\":\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555666904856&di=aa795561e20391fc29f482723fe95d8d&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fq_70%2Cc_zoom%2Cw_640%2Fimages%2F20180829%2F32125c2b14724ae98f43ac859270fcc1.jpg\",\"openid\":\"oFV0g5ZSku_Mvm36IhGwriLJ77qc3\"}\n";
        String str5 = "{\"posno\":[\"111\"],\"flag\":1,\"name\":\"黄睦4\",\"image\":\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555666938807&di=000b1f0f08a4335f8e49f2a90c6d66cc&imgtype=0&src=http%3A%2F%2Fwww.cicphoto.com%2Fsyxy%2Fgsbw%2F201405%2FW020140504385617552852.jpg\",\"openid\":\"oFV0g5ZSku_Mvm36IhGwriLJ77qc4\"}\n";

        try {
            switch (i) {
                case 0:
                    mqttInfos.add(new Gson().fromJson(str3, MqttInfo.class));
//                    mqttInfos.add(new Gson().fromJson(str, MqttInfo.class));
                    break;
                case 1:
//                    mqttInfos.add(new Gson().fromJson(str2, MqttInfo.class));
                    mqttInfos.add(new Gson().fromJson(str4, MqttInfo.class));
                    mqttInfos.add(new Gson().fromJson(str5, MqttInfo.class));
//                    mqttInfos.add(new Gson().fromJson(str5, MqttInfo.class));
                    break;
            }
            updateFace();
            Log.i("过程  手动消息", "设置照片:" + mqttInfos.size());
        } catch (Exception e) {
            Log.i("过程", "解析错误:" + e.getMessage());
        }
    }

    List<MqttInfo> mqttInfos = new ArrayList<>();
    boolean down = true;

    public void updateFace() {
        if (!down) {
            return;
        }
        if (mqttInfos.size() != 0) {
            for (int i = 0; i < mqttInfos.get(0).getPosno().size(); i++) {
                Log.i("过程   pos验证", "本地：" + clientId + "                    通知：" + mqttInfos.get(0).getPosno().get(i));
                //判断pos号是否匹配并且是否保存过此图片
                if (mqttInfos.get(0).getPosno().get(i).equals(clientId) && !DBManager.checkFaceIsSave(mqttInfos.get(0))) {
                    Log.i("过程   人脸图片", mqttInfos.get(0).getImage());
                    down = false;

                    Glide.with(App.getInstance()).asBitmap()
                            .load(mqttInfos.get(0).getImage()).diskCacheStrategy(DiskCacheStrategy.ALL)
                            .dontAnimate()
                            .centerCrop()
                            .skipMemoryCache(true)//默认为false
                            .listener(new RequestListener<Bitmap>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                    Log.i("过程   人脸图片", "图片获取失败");
                                    MqttInfo mqttInfo = mqttInfos.get(0);
                                    down = true;
                                    if (mqttInfo.isIsdown()) {

                                    } else {
                                        mqttInfo.setIsdown(true);
                                        updateFace();
                                    }
                                    return true;
                                }

                                @Override
                                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                    try {
                                        Log.i("过程   人脸图片", "图片获取成功");
                                        mBinder.backCall.call(resource);
                                        bitmaps.add(new BitMapInfo(resource, mqttInfos.get(0)));
                                        mqttInfos.remove(0);
                                        down = true;
                                        updateFace();
                                    } catch (Exception e) {
                                        down = true;
                                        Log.i("过程  错误", "加载图片：" + e.getMessage());
                                    }
                                    return false;
                                }
                            })
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    try {
                                        Log.i("过程   人脸图片", "图片获取成功 into");
                                        bitmaps.add(new BitMapInfo(resource, mqttInfos.get(0)));
                                        mqttInfos.remove(0);
                                        down = true;
                                        updateFace();
                                    } catch (Exception e) {
                                        down = true;
                                        Log.i("过程  错误", "加载图片 into：" + e.getMessage());
                                    }
                                }
                            });

                } else {
                    mqttInfos.remove(0);
                    initFace();
                }
            }
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    resolutionFace();
                    initFace();
                }
            }).start();
        }
    }

    List<BitMapInfo> bitmaps = new ArrayList<>();


    public void resolutionFace() {
        for (int i = 0; i < bitmaps.size(); i++) {
            Log.i("过程   人脸图片", "开始解析图片" + bitmaps.get(i).toString().length());
            FaceRecognizer.Feature feature = ((MainActivity) App.getInstance().getNowActivitie()).fr.extractFeature(bitmaps.get(i).getBitmap());
            Log.i("过程   人脸图片", "图片解析成功");
            FaceInfo faceInfo = DBManager.checkFace(new FaceInfo(bitmaps.get(i).getMqttInfo()));
            Log.i("过程   人脸图片", "信息添加或修改");
            Feature locFeature = FaceUtils.getLocFeature(feature);
            Log.i("过程   人脸图片", "特征添加或修改");
            locFeature.setOpenid(faceInfo.getOpenid());
            DBManager.checkFeature(locFeature);
            Log.i("过程   人脸图片", "特征库本地添加完成");
        }
    }


    /**
     * 添加人脸特征
     */
    public void initFace() {
        Log.i("过程   人脸图片", "队列中的人脸已加载完毕");
        List<String> names = new ArrayList<>();
        List<FaceRecognizer.Feature> features = new ArrayList<>();
        List<FaceInfo> faceInfos = DBManager.checkAllUseFace();
        for (int i = 0; i < faceInfos.size(); i++) {
            Log.i("过程  添加人脸识别", "faceInfos的openID         " + faceInfos.get(i).getName());
            Feature feature = DBManager.selectFeature(faceInfos.get(i).getOpenid());
            names.add(faceInfos.get(i).getOpenid());
            features.add(FaceUtils.toFaceRecognizerFeature(feature));
        }
        if (((MainActivity) App.getInstance().getNowActivitie()).mCamera != null) {
            ((MainActivity) App.getInstance().getNowActivitie()).mCamera.startPreview();
            Log.i("过程  添加人脸识别", "替换人脸库");
            if (features.size() != names.size()) {
                Log.i("过程", "人脸数跟序号不匹配   人脸数：" + features.size() + "        序号数：" + names.size());
            } else {
                Utils.setFeatures(((MainActivity) App.getInstance().getNowActivitie()).mCamera, features, names);
            }
        }
    }

    private void connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.connect(options);
                    Message msg = new Message();
                    msg.what = 2;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = 3;
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            scheduler.shutdown();
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        GetPushService.this.stopSelf();
    }

    public class MBinder extends Binder {
        BackCall backCall = new BackCall() {
            @Override
            public void call(Object o) {

            }
        };

        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {

            text(code);
            return super.onTransact(code, data, reply, flags);
        }

        public void setCallBack(BackCall o) {
            backCall = o;
        }
    }

}
