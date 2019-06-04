package com.xb.haikou.config;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.bluering.sdk.qrcode.jtb.JTBQRCodeSDK;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hao.lib.Util.FileUtils;
import com.hao.lib.Util.ThreadUtils;
import com.hao.lib.base.Rx.Rx;
import com.hao.lib.net.OkHttpManager;
import com.szxb.jni.Ymodem;
import com.xb.haikou.BuildConfig;
import com.xb.haikou.base.App;
import com.xb.haikou.config.line.PraseLine;
import com.xb.haikou.config.param.AliPublicKeyEntity;
import com.xb.haikou.config.param.TencentPublicKeyEntity;
import com.xb.haikou.db.manage.DBCore;
import com.xb.haikou.db.manage.DBManager;
import com.xb.haikou.net.FTP.FTPUtils;
import com.xb.haikou.net.NetUrl;
import com.xb.haikou.net.bean.NetResponse;
import com.xb.haikou.net.bean.TenNetResponse;
import com.xb.haikou.net.param.ParamsUtil;
import com.xb.haikou.util.BusToast;
import okhttp3.Call;
import okhttp3.Callback;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class InitConfig {

    //加载bin文件
    public InitConfig initBin() {
        ThreadUtils.getInstance().createSingle("bin").execute(new Runnable() {
            @Override
            public void run() {
                Rx.getInstance().sendMessage("bin加载中", false);
                String binName = BuildConfig.BIN_NAME;
                AppRunConfigEntity config = DBManager.checkConfig();
                if (config == null || !TextUtils.equals(config.getBinVer(), binName)) {
                    AssetManager ass = App.getInstance().getAssets();
                    int k = Ymodem.ymodemUpdate(ass, binName);
                    if (k == 0) {
                        config.setBinVer(binName);
                        DBManager.updateConfig(config);
                        App.appPreload.binLoadSucess = true;
                        BusToast.showToast("固件更新成功", true);
                        Rx.getInstance().sendMessage("bin加载成功", 1);
                    } else {
                        App.appPreload.binLoadSucess = false;
                        BusToast.showToast("固件更新失败", false);
                        Rx.getInstance().sendMessage("bin加载失败", 2);
                    }
                } else {
                    App.appPreload.binLoadSucess = true;
                    Rx.getInstance().sendMessage("bin已成功加载", 1);
                }
            }
        });
        return this;
    }

    //获取腾讯的密钥
    public InitConfig getWxMacKey() {
        new OkHttpManager().setNetType(OkHttpManager.NetType.Post)
                .addFromParam(ParamsUtil.getkeyMap())
                .setUrl(NetUrl.TENCENT_MAC_KEY)
                .setNetBack(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        App.appPreload.TencentMacKeySuc = false;
                        Rx.getInstance().sendMessage("微信秘钥下载失败", 2);
                        Log.e("请求 腾讯秘钥返回", "失败       " + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) {
                        try {
                            String result = response.body().string();
                            Log.e("请求 腾讯秘钥返回", "成功       " + result);
                            TenNetResponse netResponse = new Gson().fromJson(result, TenNetResponse.class);
                            if (netResponse != null && !TextUtils.isEmpty(netResponse.getResult()) && TextUtils.equals(netResponse.getResult(), "success")) {
                                DBCore.getDaoSession().getTencentMacKeyEntityDao().insertOrReplaceInTx(netResponse.getData().getKeys());
                                App.appPreload.TencentMacKeySuc = true;
                                Rx.getInstance().sendMessage("微信秘钥下载完成", 2);
                            } else {
                                App.appPreload.TencentMacKeySuc = false;
                                Rx.getInstance().sendMessage("微信秘钥下载失败", 2);
                            }
                        } catch (Exception e) {
                            Log.e("请求 腾讯秘钥返回", "成功       " + e.getMessage());
                            Rx.getInstance().sendMessage("微信秘钥下载错误", 2);
                        }
                    }
                });
        return this;
    }

    //获取腾讯的密钥
    public InitConfig getWXPublicKey() {
        new OkHttpManager().setNetType(OkHttpManager.NetType.Post)
                .addFromParam(ParamsUtil.getkeyMap())
                .setUrl(NetUrl.TENCENT_PUBLIC_KEY)
                .setNetBack(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        App.appPreload.TencentMacKeySuc = false;
                        Rx.getInstance().sendMessage("微信公钥下载失败", 2);
                        Log.e("请求 腾讯公钥返回", "失败       " + e.toString());
                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                        try {
                            String result = response.body().string();
                            Log.e("请求 腾讯公钥返回", "成功       " + result);
                            NetResponse netResponse = new Gson().fromJson(result, NetResponse.class);

                            if (netResponse != null && !TextUtils.isEmpty(netResponse.getResult()) && TextUtils.equals(netResponse.getResult(), "success")) {
                                List<TencentPublicKeyEntity> tencentPublicKeyEntities = new Gson().fromJson(new Gson().toJson(netResponse.getData().getKeys()), new TypeToken<List<TencentPublicKeyEntity>>() {
                                }.getType());
                                DBCore.getDaoSession().getTencentPublicKeyEntityDao().insertOrReplaceInTx(tencentPublicKeyEntities);
                                App.appPreload.TencentMacKeySuc = true;
                                Rx.getInstance().sendMessage("微信公钥下载完成", 2);
                            } else {
                                App.appPreload.TencentMacKeySuc = false;
                                Rx.getInstance().sendMessage("微信公钥下载失败", 2);
                            }
                        } catch (Exception e) {
                            Log.e("请求 腾讯公钥返回", "成功       " + e.getMessage());
                            App.appPreload.TencentMacKeySuc = false;
                            Rx.getInstance().sendMessage("微信公钥下载错误", 2);
                        }
                    }
                });
        return this;
    }

    //获取支付宝的公钥
    public InitConfig getALPublicKey() {
        new OkHttpManager().setNetType(OkHttpManager.NetType.Post)
                .addFromParam(ParamsUtil.getkeyMap())
                .setUrl(NetUrl.ALI_PUBLIC_KEY)
                .setNetBack(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        App.appPreload.ALPublicKey = false;
                        Log.e("请求 支付宝公钥返回", "失败       " + e.toString());
                        Rx.getInstance().sendMessage("支付宝公钥下载失败", 2);
                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                        try {
                            String result = response.body().string();
                            NetResponse netResponse = new Gson().fromJson(result, NetResponse.class);
                            if (netResponse != null && !TextUtils.isEmpty(netResponse.getResult()) && TextUtils.equals(netResponse.getResult(), "success")) {
                                String key = new Gson().toJson(netResponse.getData().getKeys());
                                Log.e("请求 支付宝公钥返回", "成功       " + key);
                                List<AliPublicKeyEntity> aliPublicKeyEntities = new Gson().fromJson(key, new TypeToken<List<AliPublicKeyEntity>>() {
                                }.getType());
                                DBCore.getDaoSession().getAliPublicKeyEntityDao().insertOrReplaceInTx(aliPublicKeyEntities);
                                App.appPreload.ALPublicKey = true;
                                Rx.getInstance().sendMessage("支付宝公钥下载完成", 2);
                            } else {
                                App.appPreload.ALPublicKey = false;
                                Rx.getInstance().sendMessage("支付宝公钥下载失败", 2);
                            }
                        } catch (Exception e) {
                            Log.e("请求 支付宝公钥返回", "错误       " + e.getMessage());
                            App.appPreload.ALPublicKey = false;
                            Rx.getInstance().sendMessage("支付宝公钥下载错误", 2);
                        }
                    }
                });
        return this;
    }

    //获取交通部的公钥
    public InitConfig getTranPublicKey() {
        Rx.getInstance().sendMessage("交通部秘钥下载中", false);
        new OkHttpManager().setNetType(OkHttpManager.NetType.Post)
                .addFromParam(ParamsUtil.getkeyMap())
                .setUrl(NetUrl.TRAN_MAC_KEY)
                .setNetBack(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        App.appPreload.JTBParam = false;
                        Log.e("请求 交通部证书返回", "失败       " + e.getMessage());
                        Rx.getInstance().sendMessage("交通部秘钥下载失败", 2);
                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                        try {
                            String result = response.body().string();
                            Log.e("请求 交通部证书返回", result);
                            NetResponse netResponse = new Gson().fromJson(result, NetResponse.class);
                            if (netResponse != null && !TextUtils.isEmpty(netResponse.getResult()) && TextUtils.equals(netResponse.getResult(), "success")) {
                                //将数据保存至运行所需的配置之中
                                DBManager.updateConfig(DBManager.checkRunConfig().setJTMAC(netResponse.getData().getKey()));
                                int ret = JTBQRCodeSDK.updateCert(FileUtils.hex2byte(netResponse.getData().getKey()));
                                if (ret == 0) {
                                    Rx.getInstance().sendMessage("交通部证书下载成功", 2);
                                } else {
                                    Rx.getInstance().sendMessage("交通部证书下载错误", 2);
                                    Log.e("请求 交通部证书返回", "设置错误=" + ret);
                                }

                                App.appPreload.JTBParam = true;
                                Rx.getInstance().sendMessage("交通部秘钥下载完成", 2);
                            } else {
                                App.appPreload.JTBParam = false;
                                Rx.getInstance().sendMessage("交通部秘钥下载失败", 2);
                            }
                        } catch (Exception e) {
                            Log.e("请求 交通部返回", "错误       " + e.getMessage());
                            App.appPreload.JTBParam = false;
                            Rx.getInstance().sendMessage("交通部秘钥下载错误", 2);
                        }
                    }
                });
        return this;
    }

    //获取交通部的公钥
    public InitConfig getCertverquery() {
        Rx.getInstance().sendMessage("交通部证书下载中", false);
        new OkHttpManager().setNetType(OkHttpManager.NetType.Post)
                .addFromParam("app_id", DBManager.checkBuildConfig().getMch_id())
                .setUrl(NetUrl.TRAN_CERTVERQUERY)
                .setNetBack(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        App.appPreload.TencentPublicKeySuc = false;
                        Log.e("请求 交通部证书返回", "失败       " + e.getMessage());
                        Rx.getInstance().sendMessage("交通部秘钥下载失败", 2);
                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                        try {
                            String result = response.body().string();
                            Log.e("请求 交通部证书返回", result);
                            NetResponse netResponse = new Gson().fromJson(result, NetResponse.class);

                        } catch (Exception e) {
                            Log.e("请求 交通部证书返回", "错误       " + e.getMessage());
                            App.appPreload.TencentPublicKeySuc = false;
                            Rx.getInstance().sendMessage("交通部证书下载错误", 2);
                        }
                    }
                });
        return this;
    }


    //下载线路文件并进行设置
    //TODO 接口未接通
    public static void setLine(String lineNo) {
        int ret = FTPUtils.downloadContentName(lineNo, "xzbus/line/", "线路下载");
        if (ret == 1) {
            File lineFile = new File(Environment.getExternalStorageDirectory() + "/" + lineNo);
            PraseLine.praseLineFile(FileUtils.readFile(lineFile));
        }
    }

    //下载交通部白名单
    public InitConfig getWhite() {
        Rx.getInstance().sendMessage("下载交通部白名单", false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int ret = FTPUtils.downloadContentName("BN", "xzbus/line/", "线路下载");
                if (ret == 1) {
                    File lineFile = new File(Environment.getExternalStorageDirectory() + "/BN");
                    initWhiteList(FileUtils.readFile(lineFile));
                }
            }
        }).start();
        return this;
    }


    private static void initWhiteList(final byte[] bytes) {
        try {
            String result = null;
            try {
                result = new String(bytes, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String[] sresult = result.split("\r\n");
            List<Whitelist> whitelists = new ArrayList<>();
            for (int i = 0; i < sresult.length; i++) {
                if (i > 1) {
                    String[] sitem = sresult[i].split("\\s+");

                    if (sitem.length > 2) {
                        System.out.println(sitem[0] + sitem[1] + sitem[2]);
                        Whitelist addlist = new Whitelist(null, sitem[0], sitem[1], sitem[2]);
                        whitelists.add(addlist);
                    } else {
                        System.out.println(sitem[0] + sitem[1]);
                        Whitelist addlist = new Whitelist(null, sitem[0], sitem[1], "no word");
                        whitelists.add(addlist);
                    }
                }
            }
            DBCore.getDaoSession().getWhitelistDao().insertOrReplaceInTx(whitelists);
            App.appPreload.WHITE = true;
            Rx.getInstance().sendMessage("白名单导入成功", 1);
        } catch (Exception e) {
            Rx.getInstance().sendMessage("白名单导入失败", 1);
        }
    }


    //下载公交黑名单
    public InitConfig getBlack() {
        Rx.getInstance().sendMessage("下载黑名单", false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int ret = FTPUtils.downloadContentName("BLK_ALL", "xzbus/line/", "线路下载");
                if (ret == 1) {
                    File lineFile = new File(Environment.getExternalStorageDirectory() + "/BLK_ALL");
                    initBlcakList(FileUtils.readFile(lineFile));
                }
            }
        }).start();
        return this;
    }

    private void initBlcakList(byte[] bytes) {
        String result = null;
        try {
            result = new String(bytes, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String[] sresult = result.split("\r\n");
        List<BlackList> list = new ArrayList<>();
        for (int i = 0; i < sresult.length; i++) {
            System.out.println(sresult[i]);
            if (i > 1) {
                String[] sitem = sresult[i].split(",");
                if (sitem.length >= 2) {
                    try {
                        BlackList blackList = new BlackList();
                        blackList.setCard(sitem[1]);
                        blackList.setType(sitem[0]);
                        list.add(blackList);
                    } catch (Exception e) {
                        Log.i("黑名单数据解析失败", sresult[0]);
                    }
                }
            }
        }

        if (BuildConfig.isTest) {
            BlackList blackList = new BlackList();
            blackList.setCard("3105000001000565566");
            blackList.setType("01");
            list.add(blackList);
        }

        DBCore.getDaoSession().getBlackListDao().insertOrReplaceInTx(list);
        Log.i("黑名单", "list.size()=" + list.size());
        App.appPreload.BLACK = true;
        Rx.getInstance().sendMessage("黑名单导入成功", 1);
    }

}
